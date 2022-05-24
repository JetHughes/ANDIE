package cosc202.andie;

import java.awt.Color;
import java.awt.event.*;

import javax.swing.JPanel;

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
    
    /** New Color object */
    Color FinalColour = Andie.FinalColour;


    /** Boolean values representing method article completion */
    boolean recording, selecting = false;

    /** New Color object */
    Color color = new Color(173,216,230);
    /**New JPanel object */
    JPanel window = new JPanel();

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
        target.add(window);
        window.setBackground(color);
        if(type != "crop"){

            if(type.toLowerCase().contains("line")){
                weight = Math.abs(PopUp.getSliderInt("Enter Line Weight", 1, 1, 50, 5));
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
        if(target.getImage().getRecording()){
            recording = true;
            target.getImage().setRecording(false);
        }
        window.setVisible(true);
        selecting = true;
        zoomLevel = target.getZoom()/100;
        xOrigin = (int) (e.getX());
        yOrigin = (int) (e.getY());
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
        window.setVisible(false);
        xEnd = (int) (e.getX());
        yEnd = (int) (e.getY());
        target.removeMouseListener(this);
        target.removeMouseMotionListener(this);

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
            target.getImage().apply(new Crop((int) (xOrigin/zoomLevel), (int) (yOrigin/zoomLevel), (int) (xEnd/zoomLevel), (int) (yEnd/zoomLevel)));
            target.repaint();
            target.getParent().revalidate();
        }
        else{
            target.getImage().apply(new DrawShapes((int) (xOrigin/zoomLevel), (int) (yOrigin/zoomLevel), (int) (xEnd/zoomLevel), (int) (yEnd/zoomLevel), FinalColour, type, weight));
            target.repaint();
            target.getParent().revalidate();
        }
    }

    @Override
	public void mouseDragged(MouseEvent e) {
        xEnd = (int) (e.getX());
        yEnd = (int) (e.getY());
        if(xOrigin > xEnd && yOrigin > yEnd){
            window.setBounds(xEnd, yEnd, xOrigin - xEnd, yOrigin - yEnd);
        }else if(xOrigin > xEnd){
            window.setBounds(xEnd, yOrigin, xOrigin - xEnd, yEnd - yOrigin);
        }else if(yOrigin > yEnd){
            window.setBounds(xOrigin, yEnd, xEnd - xOrigin, yOrigin - yEnd);
        }else{
            window.setBounds(xOrigin, yOrigin, xEnd - xOrigin, yEnd - yOrigin);
        }
	}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {}
}