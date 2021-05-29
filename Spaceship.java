package Asteroids;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;

import animation.AbstractAnimation;
import animation.AnimatedObject;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

public class Spaceship implements AnimatedObject{
    //set initial position to be in the middle of the canvas
    private int x = 300; 
    private int y = 300;
    
    private int dx = 0; //move amount in x direction
    private int dy = 0; //move amount in y direction
    
    private double angle = 0; //rotate angle
    private static final int SPEED = 3; //
    
    private static final int N_POINTS = 3; //spaceship is made of 3 points
    
    //x positions of 3 points that make up the spaceship
    private int[] X_POINTS = new int[] {-5,0,5}; 
    //y positions of 3 points that make up the spaceship
    private int[] Y_POINTS = new int[] {0,-20,0};
    
    //list of bullets that are fired by the spaceship
    private List<Bullet> bullets = new ArrayList<>(); 
    
    private Polygon shape;
    
    //state of the spaceship
    //0 is dead, 1 is active
    private int state = 0; 
    
    private boolean endGame= false;
    
    // The animation that this object is part of.
    private AbstractAnimation animation;
    
    /**
     * Creates the animated object
     * 
     * @param animation the animation this object is part of
     */
    public Spaceship(AbstractAnimation animation) {
        this.animation = animation;
        
        shape = new Polygon(X_POINTS, Y_POINTS, N_POINTS);
        state = 1;
    }
    
    /**
     * Method to let the spaceship know that the game end
     */
    public void setEndGame() {
        endGame = true;
    }
    
    /**
     * Updates the object's state as you want it to appear on 
     * the next frame of the animation.
     */
    public void nextFrame() {
        
        move();
        
        //wrap the spaceship around the canvas
        if (x > animation.getWidth()) {
            x = 0;
        } else if (x < 0){
            x = animation.getWidth() - 10;
        }
        
        if (y > animation.getHeight()) {
            y = 0;
        } else if (y < 0){
            y = animation.getHeight() - 10;
        }
        
    };
    
    /**
     * Method to move the spaceship
     */
    public void move() {
        
        if (endGame == false) {
            x+=dx;
            y+=dy;
        }
    }
    
    /**
     * Draws the object
     * @param g the graphics context to draw on
     */
    public void paint(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fill(getShape());      
    };
    
    /**
     * Rotate the angle in the left direction
     */
    public void angleLeft() {
        if (endGame == false) {
            angle-=0.2;
        }
    }
    /**
     * Rotate the angle in the right direction
     */
    public void angleRight() {
        if (endGame == false) {
            angle+=0.2;
        }
    }
    
    /**
     * Fire the bullets
     * Once fire is called, a bullet is fired and added to the bullets list
     */
    public void fire() {
        if (endGame == false) {
            double fireX = (Math.sin(angle));
            double fireY = (-Math.cos(angle));
            
            Bullet bullet = new Bullet(animation, x, y, fireX, fireY );
            bullets.add(bullet);
        }  
    }
    
    /**
     * Returns the shape after applying the current translation
     * and rotation
     * @return the shape located as we want it to appear
     */
    public Shape getShape() {
        // AffineTransform captures the movement and rotation we
        // want the shape to have
        AffineTransform at1 = new AffineTransform();
        
        // x, y are where the origin of the shape will be.  In this
        // case, this is the center of the triangle.  See the constructor
        // to see where the points are.
        at1.translate(x, y);
        
        at1.rotate(angle);
        AffineTransform at = at1;
        
        // Create a shape that looks like our triangle, but centered
        // and rotated as specified by the AffineTransform object.
        return at.createTransformedShape(shape);
    }
    
    /**
     * Getter for the bullets list
     * @return bullets list
     */
    public List<Bullet> getBullets() {
        return bullets;
    }
    
    /**
     * Turn the spaceship in into the hyperspace.
     * When the spaceship is turned into the hyperspace,
     * it is moved to a random position
     */
    public void hyperspace() {
        x = (int) (Math.random() * animation.getWidth());
        y = (int) (Math.random() * animation.getHeight());
        
    }
    
    /**
     * Method to take keyboard input.
     * When the left key is clicked, the angle is rotated left.
     * When the right key is clicked, the angle is rotated right.
     * When the space key is clicked, fire a bullet.
     * When the up key is clicked, move the bullet in its pointing direction.
     * When the Q key is clicked, the spaceship is put into the hyperspace.
     * @param e key event
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            angleLeft();
        }

        if (key == KeyEvent.VK_RIGHT) {
            angleRight();
            
        }
        if (key == KeyEvent.VK_SPACE) {
            fire();
        }
        
        if (key == KeyEvent.VK_UP) {
            dx = (int) (SPEED * Math.sin(angle));
            dy = (int) (-SPEED *  Math.cos(angle));
        }
        
        if (key == KeyEvent.VK_Q) {
            hyperspace();
        }
        
    }
    
    /**
     * When the key is released, stop the movement of the spaceship
     * @param e keyevent
     */
    public void keyReleased(KeyEvent e) {
        dx = 0;
        dy = 0;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }

    public void setDX(int dx) {
        this.dx = dx;
    }
    
    public void setDY(int dy) {
        this.dy = dy;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }

    public int getDX() {
        return dx;
    }
    
    public int getDY() {
        return dy;
    }
    
    public double getAngle() {
        return angle;
    }

    
    
    
    
    
    
    

}
