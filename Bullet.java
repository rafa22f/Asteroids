package Asteroids;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;

import animation.AbstractAnimation;
import animation.AnimatedObject;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class Bullet implements AnimatedObject{
    //set the initial position to be at the origin
    private int x= 0;
    private int y= 0;
    
    private double dx = 0; //move amount in x direction
    private double dy =0; //move amount in y direction
    
    private static final int WIDTH = 3; 
    private static final int HEIGHT = 3;
    
    private Rectangle shape = new Rectangle(x,y,WIDTH, HEIGHT);
    
    private static final int SPEED = 7;
    // The animation that this object is part of.
    private AbstractAnimation animation;
    
    /**
     * Creates the animated object
     * 
     * @param animation the animation this object is part of
     * @param x the position of the bullet
     * @param y the position of the bullet
     * @param dx move amount in x direction
     * @param dy move amount in y direction
     */
    public Bullet(AbstractAnimation animation, int x, int y, double dx, double dy) {
        this.animation = animation;
        this.x = x;
        this.y = y;
        this.dx = SPEED * dx;
        this.dy = SPEED * dy;
        
    }
    /**
     * Updates the object's state as you want it to appear on 
     * the next frame of the animation.
     */
    public void nextFrame() {
        x+= dx;
        y+= dy;
    };
    
    /**
     * Draws the object
     * @param g the graphics context to draw on
     */
    public void paint(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fill(getShape());
        
    };
    

    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public void setDX(double dx) {
        this.dx = dx;
    }
    
    public void setDY(double dy) {
        this.dy = dy;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public double getDX() {
        return dx;
    }
    
    public double getDY() {
        return dy;
    }
    
    /**
     * Get the shape of the bullet after transformation
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
        
        AffineTransform at = at1;
        
        // Create a shape that looks like our triangle, but centered
        // and rotated as specified by the AffineTransform object.
        return at.createTransformedShape(shape);
    }

}
