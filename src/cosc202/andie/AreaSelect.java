package cosc202.andie;

import java.awt.event.*;

/**
 * <p>
 * Class to select an area for an operation
 * </p>
 * 
 * <p>
 * Selects and stores four ints representing x and y co-ordinates
 * </p>
 */
public class AreaSelect implements MouseListener {
    
    int xOrigin, yOrigin, xEnd, yEnd;
    boolean selected = false;
    ImagePanel target;

    /**
     * <p>
     * Construct an AreaSelect with given image
     * </p>
     * 
     * <p>
     * The image is passed so that the mouse listener can be added
     * without creating jframes
     * </p>
     * 
     * @param target The image/pane for the select to take place
     */
    public AreaSelect(ImagePanel target){
        this.target = target;
        target.addMouseListener(this);
    }

    /**
     * <p>
     * Listens for when the mouse is pressed
     * </p>
     * 
     * @param e The event triggering this callback.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        xOrigin = e.getX();
        yOrigin = e.getY();
        
        // //for testing purposes to see that the location is being read properly
        // System.out.println(xOrigin + "    " + yOrigin);
    }

    /**
     * <p>
     * Listens for when the mouse is released
     * </p>
     * 
     * @param e The event triggering this callback.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        xEnd = e.getX();
        yEnd = e.getY();
        target.removeMouseListener(this);

        //makes it so that all mouse movements work
        if(xOrigin > xEnd){
            int n = xOrigin;
            xOrigin = xEnd;
            xEnd = n;
        }
        if(yOrigin > yEnd){
            int n = yOrigin;
            yOrigin = yEnd;
            yEnd = n;
        }

        // target.getImage().apply(new DrawShapes(getXOrigin(), getYOrigin(), getXEnd(), getYEnd()));
        // target.repaint();
        // target.getParent().revalidate();
    }

    /**
     * <p>
     * Allows other classes to access the co-ordinates
     * </p>
     * 
     * @return The origional x co-ordinate for the mouse
     */
    public int getXOrigin(){
        return xOrigin;
    }

    /**
     * <p>
     * Allows other classes to access the co-ordinates
     * </p>
     * 
     * @return The origional y co-ordinate for the mouse
     */
    public int getYOrigin(){
        return yOrigin;
    }
    
    /**
     * <p>
     * Allows other classes to access the co-ordinates
     * </p>
     * 
     * @return The final x co-ordinate for the mouse
     */
    public int getXEnd(){
        return xEnd;
    }
    
    /**
     * <p>
     * Allows other classes to access the co-ordinates
     * </p>
     * 
     * @return The final y co-ordinate for the mouse
     */
    public int getYEnd(){
        return yEnd;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}