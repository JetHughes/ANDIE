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
    double zoomLevel;
    ImagePanel target;
    String type;
    ColorChooser cs;

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
    public AreaSelect(ImagePanel target, String type){
        this.target = target;
        this.type = type;
        target.addMouseListener(this);
        if(type == "draw"){
            cs = new ColorChooser();
        }
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
        zoomLevel = target.getZoom()/100;
        xOrigin = (int) (e.getX()/zoomLevel);
        yOrigin = (int) (e.getY()/zoomLevel);
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
        xEnd = (int) (e.getX()/zoomLevel);
        yEnd = (int) (e.getY()/zoomLevel);
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

        if(type == "draw"){
            target.getImage().apply(new DrawShapes(xOrigin, yOrigin, xEnd, yEnd, cs.color));
            target.repaint();
            target.getParent().revalidate();
        }
        else if (type == "crop"){
            target.getImage().apply(new Crop(xOrigin, yOrigin, xEnd, yEnd));
            target.repaint();
            target.getParent().revalidate();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}