package Asteroids;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import animation.AbstractAnimation;
import animation.AnimatedObject;
import animation.demo.AnimatedObjectDemo;
import animation.demo.AnimationDemo;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
public class Game extends AbstractAnimation implements KeyListener {
    

    // The width of the window, in pixels.
    private static final int WINDOW_WIDTH = 600;
    
    // The height of the window, in pixels.
    private static final int WINDOW_HEIGHT = 600;
    
    private static final int STARTSIZE=4; //initial size of the 5 asteroids
    
    private Spaceship spaceship = new Spaceship(this);
    //list of bullets fired by the spaceship
    private List<Bullet> bullets = spaceship.getBullets();
    //list of asteroids
    private List<Asteroid> asteroids=new ArrayList<Asteroid>();
    //start the game with 5 asteroid
    private int numAsteroids = 5; 
    //variable to tell whether exist asteroids in game
    private boolean noAsteroid = false; 
    //variable to tell whether the game has ended
    private boolean endGame = false; 
    //score of the user
    private ScoringService score = new ScoringService();
    
    /**
     * Create a game 
     */
    public Game() {
        addKeyListener(this);
        setFocusable(true); 
        //start position of the asteroids
        Random start; 
        
        //for each of the asteroids, set the start position to a random number
        for (int i = 0; i < numAsteroids; i++) {
            start = new Random();
            int initPos = start.nextInt ((500-20)+1) + 20;
            //the asteroid should has some distance from the spaceship
            //at the beginning
	        while (150<initPos && initPos<450) {
	        	initPos = start.nextInt ((500-20)+1) + 20;
	        }
            asteroids.add(new Asteroid(this,STARTSIZE, initPos, initPos));
        }
    }
    
    
    @Override
    /**
     * Updates the animated object for the next frame of the animation
     * and repaints the window.
     */
    protected void nextFrame() {
          if (endGame == false) {
                List<Asteroid> newAsteroids=new ArrayList<Asteroid>();
                List<Asteroid> hitAsteroids=new ArrayList<Asteroid>();
                List<Bullet> hitBullets=new ArrayList<Bullet>();
                
                if (asteroids.size()==0) {
                    noAsteroid = true;
                }
                
                //check collision between each asteroid and spaceship
                for (Asteroid asteroid: asteroids) {
                    if (checkCollision(spaceship, asteroid)) {
                          endGame = true;
                          spaceship.setEndGame();
                          return;
                    }
                }
                spaceship.nextFrame();
                
                
                //remove all out-of-window bullet
                int bulletIndex = 0;
                while(bulletIndex< bullets.size()) {
                    Bullet bullet = bullets.get(bulletIndex);
                    if (bullet.getX()> WINDOW_WIDTH || bullet.getX()<0 || 
                            bullet.getY() > WINDOW_HEIGHT || bullet.getY() < 0) {
                        bullets.remove(bulletIndex);
                    }
                    else {bulletIndex++;}
                }
 
                //breaks down the asteroids that were hit
                for (int i=0;i<bullets.size();i++) {
                    Bullet bullet = bullets.get(i);
                    for (Asteroid asteroid: asteroids) {
                        //if the asteroid is hit, increase score
                        if (checkCollision(asteroid, bullet)) {
                            score.hitAsteroid(asteroid);
                            
                            //add the asteroid and bullet to the list that will be destroyed
                            hitAsteroids.add(asteroid);
                            hitBullets.add(bullet);
                            
                            //if the asteroid is bigger than the smallest size
                            //break it down around the position of the asteroid
                            if(asteroid.getSize()>1) {
                                Rectangle bound = asteroid.getBound();
                                int iniX = bound.x;
                                int iniY = bound.y;
                                newAsteroids.add(new Asteroid(this,(int) (asteroid.getSize()*0.5), iniX+10, iniY+10));
                                newAsteroids.add(new Asteroid(this,(int) (asteroid.getSize()*0.5), iniX-10, iniY-10));
                            }
                        }
                     }
                    bullet.nextFrame();
                }
                
                //add in smaller asteroids
                for(Asteroid newAsteroid:newAsteroids) {
                    asteroids.add(newAsteroid);
                }
                newAsteroids.removeAll(newAsteroids);
                
                //destroy asteroids that have been hit by the bullet
                for(Asteroid hitAsteroid:hitAsteroids) {
                    asteroids.remove(hitAsteroid);
                }
               
                //destroy bullet that hits an asteroid 
                for(Bullet hitBullet:hitBullets) {
                    bullets.remove(hitBullet);
                }
                
                //update asteroid position
                for (Asteroid asteroid: asteroids) {
                    asteroid.nextFrame();
                }
                
                repaint();
          }

    }

    /**
     * Check whether two object collide.  This tests whether their shapes intersect.
     * @param shape1 the first shape to test
     * @param shape2 the second shape to test
     * @return true if the shapes intersect
     */
    static boolean checkCollision(AnimatedObject shape1,
            AnimatedObject shape2) {
        return shape2.getShape().intersects(shape1.getShape().getBounds2D());
    }


    /**
     * Paint the animation by painting the objects in the animation.
     * @param g the graphic context to draw on
     */
    public void paintComponent(Graphics g) {
        // Note that your code should not call paintComponent directly.
        // Instead your code calls repaint (as shown in the nextFrame
        // method above, and repaint will call paintComponent.
        
        super.paintComponent(g);
        spaceship.paint((Graphics2D) g);
        
        //paint all the bullets
        for (Bullet bullet : bullets) {
            bullet.paint((Graphics2D) g);
        }
        
        //paint all the asteroids
        for(Asteroid asteroid: asteroids) {
            asteroid.paint((Graphics2D) g);
        }
        
        //paint the score
        g.drawString("Score: "+score.getScore(), 40, 20);
        
        //if there is no more asteroid, print out "You Win"
        if (noAsteroid) {
            Font font = new Font("Serif", Font.PLAIN, 40);
            g.setFont(font);
            g.drawString("You Win! :))", 200, 200);
        }
        
        //if the game has ended, print out "Game Over!"
        if (endGame) {
            Font font = new Font("Serif", Font.PLAIN, 40);
            g.setFont(font);
            g.drawString("Game Over!", 200, 200);
        }
    }
    
    /**
     * get the width of the window
     */
    public int getWidth() {
    	return WINDOW_WIDTH;
    }
    
    /**
     * response to type
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    /**
     * response to keyboard press
     */
    @Override
    public void keyPressed(KeyEvent e) {
      spaceship.keyPressed(e);
    }

    /**
     * response to keyborad press release
     */
    @Override
    public void keyReleased(KeyEvent e) {
      spaceship.keyReleased(e);
    }

    //for testing
    void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }
    
    //for testing
    List<Bullet> getBullets(){
        return bullets;
    }
    
    //for testing
    void addAsteroid(Asteroid asteroid) {
        asteroids.add(asteroid);
    }
    
    //for testing
    void setAsteroids(List<Asteroid> temp) {
        asteroids = temp;
    }
    
    //for testing
    List<Asteroid> getAsteroids(){
        return asteroids;
    }
    
    //for testing
    boolean getEndGame() {
        return endGame;
    }
    
    //for testing
    boolean getNoAsteroid() {
        return noAsteroid;
    }
    
    Spaceship getShip() {
        return spaceship;
    }
    
    /**
     * The main method creates a window for the animation to run in,
     * initializes the animation and starts it running.
     * @param args none
     */
    public static void main(String[] args) {
        // JFrame is the class for a window.  Create the window,
        // set the window's title and its size.
        JFrame f = new JFrame();
        f.setTitle("Game");
        f.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        
        // This says that when the user closes the window, the
        // entire program should exit.
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the animation.
        Game game = new Game();

        // Add the animation to the window
        Container contentPane = f.getContentPane();
        contentPane.add(game, BorderLayout.CENTER);
        
        // Display the window.
        f.setVisible(true);

        // Start the animation
        game.start();

    }
}
