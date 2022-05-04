package cosc202.andie;

import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

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
        
        actions = new ArrayList<Action>();
        actions.add(new StartRecordMacro("Start Recording", null, "Starts to record macro", null));
        actions.add(new StopRecordMacro("Stop Recording", null, "Stops recording macro", null));
        actions.add(new ApplyMacro("Apply Macro", null, "Applies macro to image", null));
        
    }

    /**
     * <p>
     * Create a menu contianing the list of macro actions.
     * </p>
     * 
     * @return The File menu UI element.
     */
    public JMenu createMenu() {
        
        JMenu translateMenu = new JMenu("Macros");

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
            //putValue(Action.MNEMONIC_KEY, mnemonic);
            //putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK));
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
    /**
     * <p>
     * Action to stop recording macro
     * </p>
     */
    public class StopRecordMacro extends ImageAction{
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
        StopRecordMacro(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            //putValue(Action.MNEMONIC_KEY, mnemonic);
            //putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK));
        }
        /**
         * <p>
         * Callback for when the StopMacroRecord action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the StopMacroRecord is triggered.
         * It stops recording events and saves a new .obs file.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e){
            if(!target.getImage().hasImage()){
                //System.out.println("Export error handling");
                PopUp.showMessageDialog("Error: There is no image present!");

            } else {


                 
            }
        }
    }
    /**
     * <p>
     * Action to apply macro
     * </p>
     */
    public class ApplyMacro extends ImageAction{
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
        ApplyMacro(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            //putValue(Action.MNEMONIC_KEY, mnemonic);
            //putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(mnemonic, KeyEvent.CTRL_DOWN_MASK));
        }
        /**
         * <p>
         * Callback for when the ApplyRecord action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ApplyRecord is triggered.
         * It applys the effect of an .obs file to an image.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e){
            if(!target.getImage().hasImage()){
                //System.out.println("Export error handling");
                PopUp.showMessageDialog("Error: There is no image loaded to apply changes to!");

            } else {
                
                System.out.println("here");
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(target);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        String opsFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                        String filename = opsFilepath.substring(opsFilepath.length() - 3, opsFilepath.length());
                        System.out.println(filename);
                        
                        if(!filename.equals("ops")){
                            PopUp.showMessageDialog("Error: Not an .obs file!");
                            return;
                        } 
                        target.getImage().setOps(opsFilepath);
                    } catch (IOException ex) {
                        PopUp.showMessageDialog("Error: Unable to find file. Check file name.");
                        return;
                    } catch (NullPointerException ex) {
                        System.out.println(ex);
                        PopUp.showMessageDialog("Error: Incorrect file type. Please choose a supported image type");
                        return;
                    }catch (Exception ex) {
                        System.out.println(ex);
                        return;
                        //System.exit(1);
                    }
                }
                // try {
                //     File imageFile = new File(imageFilename);
                //     original = ImageIO.read(imageFile);
                //     current = deepCopy(original);
                // } catch (FileNotFoundException ex){
                //     System.out.println(ex);
                //     PopUp.showMessageDialog("Could not create copy due to invalid file name");
                // } 


                 
            }
        }
    }

}
