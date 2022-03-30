package cosc202.andie;

import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class TranslateActions {

    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Translate menu actions.
     * </p>
     */
    public TranslateActions() {
        actions = new ArrayList<Action>();
        actions.add(new RotateImageAction("Rotate 180", null, "Rotate 180", null, 180));
        actions.add(new RotateImageAction("Rotate Left 90", null, "Rotate 180", null, -90));
        actions.add(new RotateImageAction("Rotate Right 90", null, "Rotate 180", null, 90));

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
    public class RotateImageAction extends ImageAction{
        
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
        RotateImageAction(String name, ImageIcon icon, String desc, Integer mnemonic, Integer degrees){
            super(name, icon, desc, mnemonic);
            this.degrees = degrees;
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
}
