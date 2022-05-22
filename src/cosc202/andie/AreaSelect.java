package cosc202.andie;

import java.awt.Color;
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
public class AreaSelect implements MouseListener, MouseMotionListener {
    
    /** Values depicting the co-ordinates and weight of the image */
    int xOrigin, yOrigin, xEnd, yEnd, weight;
    /** The level of the zoom applied */
    double zoomLevel;
    /** The ImagePanel the image is to be applied to */
    ImagePanel target;
    /** The action to be applied */
    String type;
    /** Colour picker instance */
    ColorChooser cs;
    /** Boolean values representing method article completion */
    boolean released, done = false;
    /** New Color object */
    java.awt.Color color = new Color(220, 220, 220);

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
     * @param type The action to be applied
     */
    public AreaSelect(ImagePanel target, String type){
        this.target = target;
        this.type = type;
        target.addMouseListener(this);
        target.addMouseMotionListener(this);
        if(type != "crop"){
            cs = new ColorChooser();
            if(type.toLowerCase().contains("line")){
                weight = Math.abs(PopUp.getSpinnerInt("Enter Line Weight", 1, 1, 50, 5));
            }
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
        target.getImage().undo();
        target.getImage().redoOps.pop();
        xEnd = (int) (e.getX()/zoomLevel);
        yEnd = (int) (e.getY()/zoomLevel);
        target.removeMouseListener(this);
        target.removeMouseMotionListener(this);
        released = true;

        //makes it so that all mouse movements work
        if(type != "Draw Line"){
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
        }

        if(type == "crop"){
            target.getImage().apply(new Crop(xOrigin, yOrigin, xEnd, yEnd));
            target.repaint();
            target.getParent().revalidate();
        }
        else{
            target.getImage().apply(new DrawShapes(xOrigin, yOrigin, xEnd, yEnd, cs.color, type, weight));
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

	@Override
	public void mouseDragged(MouseEvent e) {
        xEnd = e.getX();
        yEnd = e.getY();
        if(done){
            target.getImage().undo();
            target.getImage().redoOps.pop();
            done = false;
        }
        if(xOrigin > xEnd && yOrigin > yEnd){
            target.getImage().apply(new DrawShapes(xEnd, yEnd, xOrigin, yOrigin, color, "selecting", 1));
        }else if(xOrigin > xEnd){
            target.getImage().apply(new DrawShapes(xEnd, yOrigin, xOrigin, yEnd, color, "selecting", 1));
        }else if(yOrigin > yEnd){
            target.getImage().apply(new DrawShapes(xOrigin, yEnd, xEnd, yOrigin, color, "selecting", 1));
        }else{
            target.getImage().apply(new DrawShapes(xOrigin, yOrigin, xEnd, yEnd, color, "selecting", 1));
        }
        target.repaint();
        target.getParent().revalidate();
        done = true;
	}

	@Override
	public void mouseMoved(MouseEvent e) {}
}