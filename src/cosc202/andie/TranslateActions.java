package cosc202.andie;

import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.event.*;

/**
 * <p>
 * Actions provided by the Translate menu
 * </p>
 * 
 * <p>
 * The Translate menu contains actions that affects the rotation and scale
 * of the image.
 * </p>
 */
public class TranslateActions {

    /** A List of actions for the translate menu */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Translate menu actions.
     * </p>
     */
    public TranslateActions() {
        actions = new ArrayList<Action>();
        actions.add(new RotateImageAction180("Rotate 180°", null, "Rotate 180°", Integer.valueOf(KeyEvent.VK_F), 180));
        actions.add(new RotateImageAction90L("Rotate Left 90°", null, "Rotate 180°", Integer.valueOf(KeyEvent.VK_I), -90));
        actions.add(new RotateImageAction90R("Rotate Right 90°", null, "Rotate 180°", Integer.valueOf(KeyEvent.VK_R), 90));
        actions.add(new FlipImageActionhorizontally("Flip Horizontal", null, "Flip Horizontal", Integer.valueOf(KeyEvent.VK_H), true));
        actions.add(new FlipImageActionvertically150("Flip Vertical", null, "Flip Vertical", Integer.valueOf(KeyEvent.VK_V), false));
        actions.add(new ScaleImageAction("Scale %150", null, "Scale %150", Integer.valueOf(KeyEvent.VK_COMMA), 1.5));
        actions.add(new ScaleImageAction("Scale %50", null, "Scale 150", Integer.valueOf(KeyEvent.VK_PERIOD), 0.5));

    }

    /**
     * <p>
     * Create a menu contianing the list of Translate actions.
     * </p>
     * 
     * @return The File menu UI element.
     */
    public JMenu createMenu() {
        JMenu translateMenu = new JMenu("Translate");

        for(Action action: actions) {
            translateMenu.add(new JMenuItem(action));
        }

        return translateMenu;
    }

    /**
     * <p>
     * Action to rotate Image.
     * </p>
     * 
     */
    public class RotateImageAction90R extends ImageAction{
        
        /**
        * The number of degrees to rotate the image, also used to 
        * differentiate between left and right rotation
        */
        private int degrees;

        /**
         * <p>
         * Create a new rotate action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        RotateImageAction90R(String name, ImageIcon icon, String desc, Integer mnemonic, Integer degrees){
            super(name, icon, desc, mnemonic);
            this.degrees = degrees;
            putValue(Action.MNEMONIC_KEY, mnemonic);
		    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK));
        }

        /**
         * <p>
         * Callback for when the rotate action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the RotateImageAction is triggered.
         * It rotates the image by the specified number of degrees
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e){
            target.getImage().apply(new RotateImage(degrees));
            target.repaint();
            target.getParent().revalidate();
        }
    }

    public class RotateImageAction90L extends ImageAction{
        
        /**
        * The number of degrees to rotate the image, also used to 
        * differentiate between left and right rotation
        */
        private int degrees;

        /**
         * <p>
         * Create a new rotate action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        RotateImageAction90L(String name, ImageIcon icon, String desc, Integer mnemonic, Integer degrees){
            super(name, icon, desc, mnemonic);
            this.degrees = degrees;
            putValue(Action.MNEMONIC_KEY, mnemonic);
		    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK));
        }

        /**
         * <p>
         * Callback for when the rotate action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the RotateImageAction is triggered.
         * It rotates the image by the specified number of degrees
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e){
            target.getImage().apply(new RotateImage(degrees));
            target.repaint();
            target.getParent().revalidate();
        }
    }

    public class RotateImageAction180 extends ImageAction{
        
        /**
        * The number of degrees to rotate the image, also used to 
        * differentiate between left and right rotation
        */
        private int degrees;

        /**
         * <p>
         * Create a new rotate action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        RotateImageAction180(String name, ImageIcon icon, String desc, Integer mnemonic, Integer degrees){
            super(name, icon, desc, mnemonic);
            this.degrees = degrees;
            putValue(Action.MNEMONIC_KEY, mnemonic);
		    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK));
        }

        /**
         * <p>
         * Callback for when the rotate action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the RotateImageAction is triggered.
         * It rotates the image by the specified number of degrees
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e){
            target.getImage().apply(new RotateImage(degrees));
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * Action to flip an image
     */
    public class FlipImageActionhorizontally extends ImageAction{

        /**
         * Whether to flip horizontally or vertically
         */
        boolean isX;
        
        /**
         * <p>
         * Create a new rotate action.
         * </p>
         * 
         * @param isX 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        FlipImageActionhorizontally(String name, ImageIcon icon, String desc, Integer mnemonic, Boolean isX){
            super(name, icon, desc, mnemonic);
            this.isX = isX;
            putValue(Action.MNEMONIC_KEY, mnemonic);
		    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK));
        }

        /**
         * <p>
         * Callback for when the flip action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FlipImageAction is triggered.
         * It flip the image vertically or horizontally
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e){
            target.getImage().apply(new FlipImage(isX));
            target.repaint();
            target.getParent().revalidate();
        }
    }

    public class FlipImageActionvertically150 extends ImageAction{

        /**
         * Whether to flip horizontally or vertically
         */
        boolean isX;
        
        /**
         * <p>
         * Create a new rotate action.
         * </p>
         * 
         * @param isX 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        FlipImageActionvertically150(String name, ImageIcon icon, String desc, Integer mnemonic, Boolean isX){
            super(name, icon, desc, mnemonic);
            this.isX = isX;
            putValue(Action.MNEMONIC_KEY, mnemonic);
		    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK));
        }

        /**
         * <p>
         * Callback for when the flip action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FlipImageAction is triggered.
         * It flip the image vertically or horizontally
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e){
            target.getImage().apply(new FlipImage(isX));
            target.repaint();
            target.getParent().revalidate();
        }
    }

    public class ScaleImageAction extends ImageAction{

        /** How much to scale the image */
        double scale;

        /**
         * <p>
         * Create a new rotate action.
         * </p>
         * 
         * @param scale How much to scale the image
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ScaleImageAction(String name, ImageIcon icon, String desc, Integer mnemonic, Double scale){
            super(name, icon, desc, mnemonic);
            this.scale = scale;
            putValue(Action.MNEMONIC_KEY, mnemonic);
		    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK));
        }

        /**
         * <p>
         * Callback for when the rotate action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the RotateImageAction is triggered.
         * It rotates the image by the specified number of degrees
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e){
            target.getImage().apply(new ScaleImage(scale));
            target.repaint();
            target.getParent().revalidate();
        }
    }

    public class ScaleImageAction50 extends ImageAction{

        /** How much to scale the image */
        double scale;

        /**
         * <p>
         * Create a new rotate action.
         * </p>
         * 
         * @param scale How much to scale the image
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ScaleImageAction50(String name, ImageIcon icon, String desc, Integer mnemonic, Double scale){
            super(name, icon, desc, mnemonic);
            this.scale = scale;
            putValue(Action.MNEMONIC_KEY, mnemonic);
		    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK));
        }

        /**
         * <p>
         * Callback for when the rotate action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the RotateImageAction is triggered.
         * It rotates the image by the specified number of degrees
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e){
            target.getImage().apply(new ScaleImage(scale));
            target.repaint();
            target.getParent().revalidate();
        }
    }
}
