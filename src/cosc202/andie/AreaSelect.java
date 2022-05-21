package cosc202.andie;
import java.awt.Color;
import java.awt.event.*;

import javafx.scene.shape.Rectangle;

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
    
    int xOrigin, yOrigin, xEnd, yEnd, weight;
    double zoomLevel;
    ImagePanel target;
    String type;
    ColorChooser cs;
    boolean released, done = false;
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
        xEnd = (int) (e.getX()/zoomLevel);
        yEnd = (int) (e.getY()/zoomLevel);
        target.removeMouseListener(this);
        target.removeMouseMotionListener(this);
        released = true;

        //makes it so that all mouse movements work
        if(type.toLowerCase() != "line"){
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
        if(done){
            target.getImage().undo();
            done = false;
        }
		target.getImage().apply(new DrawShapes(xOrigin, yOrigin, e.getX(), e.getY(), color, "selecting", 1));
        target.repaint();
        target.getParent().revalidate();
        done = true;
	}

	@Override
	public void mouseMoved(MouseEvent e) {}
}