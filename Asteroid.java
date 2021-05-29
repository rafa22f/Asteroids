package Asteroids;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.Random;

import animation.AbstractAnimation;
import animation.AnimatedObject;

/**
 * This class creates an asteroid
 */
public class Asteroid implements AnimatedObject{
	    //an asteroid is a polygon
	    private Polygon p=new Polygon();
	    
	    // The starter position of the polygon
	    private int x;
	    private int y;
	    
	    //the starter direction of the polygon
	    private int iniDirectX;
	    private int iniDirectY;
	    
	    // The number of pixels to move horizontally on each frame of the animation.
	    private int moveAmount1;
	    
	    // The number of pixels to move vertically on each frame of the animation.
	    private int moveAmount2;
	    
	    //The asteroid is not shot at the very beginning
	    private boolean shotByShip = false;
	    
	    //The size of the asteroid
	    private int size;
	    
	    // The animation that this object is part of
	    private AbstractAnimation animation;

	    
	     /**
         * Creates the asteroid
         * @param animation the animation this object is part of
         * @param size an integer
         * @param iniX the starter x-value
         * @param iniY the starter y-value
         * 
         */
        public Asteroid(AbstractAnimation animation, int size, int iniX, int iniY) {
            //set initial state
            this.animation = animation;
            this.size=size;
            this.x = iniX;
            this.y = iniY;
            //set the initial move direction and moving speed
            Random start = new Random();
            this.iniDirectX = (start.nextInt(2) == 0) ? -1 : 1;
            this.iniDirectY = (start.nextInt(2) == 0) ? -1 : 1;
            this.moveAmount1 = iniDirectX *((int) (Math.random() * ((3 - 1) + 1)) + 1);
            this.moveAmount2 = iniDirectY *((int) (Math.random() * ((3 - 1) + 1)) + 1);
            
          //polygon with a random number of sides
            Random rand = new Random();
            int num = rand.nextInt ((5-3)+1) + 5;
            for (int i = 0; i < num; i++) {
                //random integer to create more irregular sides
                int n = rand.nextInt((8-5)+1)+ 8;
                //x and y coordinate of the point
                int pos_x = (int) (x + 5*size * Math.cos((i * Math.toRadians(360))/n));
                int pos_y = (int) (y + 5*size * Math.sin((i * Math.toRadians(360))/n));
                p.addPoint(pos_x, pos_y);             
            }
        }

	    /**
	     * Draws the asteroid if it's not shot
	     * 
	     * @param g the graphics context to draw on.
	     */
	    public void paint(Graphics2D g) {
	        if (!(shotByShip)) {
	            g.setColor(Color.BLACK);
	            g.drawPolygon(p);
	            } 
	    }
	    
	    /**
	     * Finds the rightmost point of the asteroid
	     * @return maxX an integer
	     */
	    public int findMaxX() {
	    	int maxX=p.xpoints[0];
	    	for(int i=0;i<p.xpoints.length;i++) {
	    		if(p.xpoints[i]>maxX) {
	    			maxX=p.xpoints[i];
	    		}
	    	}
	    	return maxX;
	    }
	    
	    /**
	     * Finds the leftmost point of the asteroid
	     * @return minX an integer
	     */
	    public int findMinX() {
	    	int minX=p.xpoints[0];
	    	for(int i=0;i<p.xpoints.length;i++) {
	    		if(p.xpoints[i]<minX) {
	    			minX=p.xpoints[i];
	    		}
	    	}
	    	return minX;
	    }
	    
	    /**
	     * Finds the bottom point of the asteroid
	     * @return maxY an integer
	     */
	    public int findMaxY() {
	    	int maxY=p.ypoints[0];
	    	for(int i=0;i<p.ypoints.length;i++) {
	    		if(p.ypoints[i]>maxY) {
	    			maxY=p.ypoints[i];
	    		}
	    	}
	    	return maxY;
	    }
	    
	    /**
	     * Finds the top point of the asteroid
	     * @return minY an integer
	     */
	    public int findMinY() {
	    	int minY=p.ypoints[0];
	    	for(int i=0;i<p.ypoints.length;i++) {
	    		if(p.ypoints[i]<minY) {
	    			minY=p.ypoints[i];
	    		}
	    	}
	    	return minY;
	    }

	    /**
	     * Moves the asteroid a small amount. 
	     * If it reaches the left or right edge, 
	     * or the top or bottom edge, it bounces.
	     */
	    public void nextFrame() {
	        // Update the x and y value to move in the current direction
	    	p.translate(moveAmount1, moveAmount2);

	        // Check if the right edge of asteroid is beyond the right
	        // edge of the window. If it is, change the direction, 
	    	//so it will move left on its next move.
	        if (findMaxX()> animation.getWidth()) {
	            moveAmount1 = moveAmount1 * -1;
	        }

	        // Check if the left edge of the asteroid is beyond the left
	        // edge of the window. If it is, change the direction,
	        //so it will move right on its next move.
	        else if (findMinX()< 0) {
	            moveAmount1 = moveAmount1 * -1;
	        }
	        
	        // Check if the top of the asteroid is beyond the top
	        // edge of the window. If it is, change the direction, 
	        //so it will move left on its next move.
	        if (findMaxY()> animation.getHeight()) {
	            moveAmount2 = moveAmount2 * -1;
	        }

	        // Check if the bottom of the asteroid is beyond the bottom
	        // edge of the window. If it is, change the direction, 
	        //so it will move right on its next move.
	        else if (findMinY() < 0) {
	            moveAmount2 = moveAmount2 * -1;
	        }
	    }
	    
	    public Rectangle getBound() {
	        return p.getBounds();
	    }
	    
	    // For TESTING only
	    int getMoveAmount1() {
            return moveAmount1;
        }
        
	    // For TESTING only
        int getMoveAmount2() {
            return moveAmount2;
        }

	    
	    // For TESTING only
	    void setX(int x) {
	        this.x = x;
	    }
	    
	    // For TESTING only
	    void setY(int y) {
	    	this.y=y;
	    }
	    
	    /**
	     * Checks if the asteroid is shot
	     * @return shotByShip a boolean
	     */
	    boolean getShot() {
	    	return shotByShip;
	    }
	    
	    /**
	     * Gets the polygon
	     * 
	     */
	    public Shape getShape() {
	        return p;
	        
	    }
	    
	    /**
	     * Gets the size of the asteroid
	     * @return size an integer
	     */
	    public int getSize() {
	    	return size;
	    }

}

