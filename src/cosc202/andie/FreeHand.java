
package cosc202.andie;

import java.util.*;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;


public class FreeHand {
    Andie a = new Andie();
    ImagePanel target = Andie.target;
    String type;
    boolean on = false;
    public static JLabel macroLabel = new JLabel("");
    protected ArrayList<Action> actions;

    public FreeHand() {
        actions = new ArrayList<Action>();
        actions.add(new FreeHandAction("Free Hand Drawing", null, "Free Hand Drawing", Integer.valueOf(KeyEvent.VK_BACK_SLASH)));


    }


    public JMenu createMenu() {
        JMenu viewMenu = new JMenu("View");

        for (Action action: actions) {
            viewMenu.add(new JMenuItem(action));
        }

        return viewMenu;
    }

    public class FreeHandAction extends ImageAction {


        FreeHandAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.MNEMONIC_KEY, mnemonic);
		    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK));
        }

        public void actionPerformed(ActionEvent e) {
            
            if(on == true){
                on = false;
                macroLabel.setText("");
            }else if(on == false){

                if(!target.getImage().hasImage()){
                    PopUp.showMessageDialog("Error: No image to draw on!");

                } else {
                    macroLabel.setText("RIGHT CLICK TO CHANGE PEN SIZE");
                    on = true;
                    new FreeHandDraw(target);
                }
            }
        }

    }

    public class FreeHandDraw implements MouseListener, MouseMotionListener{

        int xOrigin, yOrigin, xEnd, yEnd, weight = 10;

        double zoomLevel;

        ImagePanel target;

        String type;

        Color FinalColour = Andie.FinalColour;

        boolean recording, selecting = false;

        JPanel window = new JPanel();

        JColorChooser tcc;

        JLabel banner;

        java.awt.Color color;

        private boolean first = true;




        public FreeHandDraw(ImagePanel target){
            this.target = target;
            target.addMouseListener(this);
            target.addMouseMotionListener(this);
            target.add(window);
            window.setBackground(color);   
            
                
            

        }

        public void mouseDragged(MouseEvent e) {
        xOrigin = xEnd;
        yOrigin = yEnd;
        xEnd = (int) (e.getX()/zoomLevel);
        yEnd = (int) (e.getY()/zoomLevel);
    
        if(first){
            xOrigin = xEnd;
            yOrigin = yEnd;
            first  = false;
        }
        String type = "Line";
        target.getImage().apply(new DrawShapes(xOrigin, yOrigin, xEnd, yEnd, FinalColour, type, weight));
        target.repaint();
        target.getParent().revalidate();

        }

        @Override
        public void mouseMoved(MouseEvent e){}

        @Override
        public void mouseClicked(MouseEvent e){}

        @Override
        public void mousePressed(MouseEvent e){
            
            if (SwingUtilities.isLeftMouseButton(e)) {
                if(target.getImage().getRecording()){
                    recording = true;
                    target.getImage().setRecording(false);
                }
                window.setVisible(true);
                selecting = true;
                zoomLevel = target.getZoom()/100;
                xOrigin = (int) (e.getX()/zoomLevel);
                yOrigin = (int) (e.getY()/zoomLevel);
            }

            if (SwingUtilities.isRightMouseButton(e)) {
                weight = Math.abs(PopUp.getSliderInt("Enter Line Weight", 1, 1, 50, 5));
            }
        }

        @Override
        public void mouseReleased(MouseEvent e){
            
            
            if(on == true){
                macroLabel.setText("");
                window.setVisible(false);

                xEnd = (int) (e.getX()/zoomLevel);
                yEnd = (int) (e.getY()/zoomLevel);

                target.removeMouseListener(this);
                target.removeMouseMotionListener(this);

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
            }

             
        }

        @Override
        public void mouseEntered(MouseEvent e){}

        @Override
        public void mouseExited(MouseEvent e){}
    }

}
