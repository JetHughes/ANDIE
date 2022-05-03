package cosc202.andie;

import java.util.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * <p>
 * Actions provided by the Macro menu
 * </p>
 * 
 * <p>
 * The Macro menu contations actions to Record, Save, and Implement macros
 * </p>
 */
public class MacroActions {
    
    /**
     * A list of actions for the macro menu
     */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of macro menu actions
     * </p>
     */
    public MacroActions(){

    }

    /**
     * <p>
     * Create a menu contianing the list of macro actions.
     * </p>
     * 
     * @return The File menu UI element.
     */
    public JMenu createMenu() {
        JMenu translateMenu = new JMenu("Translate");

        for (Action action : actions) {
            translateMenu.add(new JMenuItem(action));
        }

        return translateMenu;
    }

    /**
     * <p>
     * Action to start recording macro
     * </p>
     */
    public class StartRecordMacro extends ImageAction{
        /**
         * <p>
         * Create a new macro action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        StartRecordMacro(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            putValue(Action.MNEMONIC_KEY, mnemonic);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK));
        }
        /**
         * <p>
         * Callback for when the StartMacroRecord action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the StartMacroRecord is triggered.
         * It creates a new .obs file and starts to record the actions performed.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e){
            if(!target.getImage().hasImage()){
                //System.out.println("Export error handling");
                PopUp.showMessageDialog("Error: Load an image before starting to record!");

            } else {


                 
            }
        }
    }

}
