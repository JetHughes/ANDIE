package cosc202.andie;

import java.awt.event.*;

public class AreaSelect implements MouseListener {
    
    int xOrigin, yOrigin, xEnd, yEnd;
    boolean selected = false;
    ImagePanel target;

    public AreaSelect(ImagePanel target){
        this.target = target;
        target.addMouseListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        xOrigin = e.getX();
        yOrigin = e.getY();
        
        // //for testing purposes to see that the location is being read properly
        // System.out.println(xOrigin + "    " + yOrigin);
    }

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

    public int getXOrigin(){
        return xOrigin;
    }

    public int getYOrigin(){
        return yOrigin;
    }
    
    public int getXEnd(){
        return xEnd;
    }
    
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