package cosc202.andie;

import javax.swing.JOptionPane;
import javax.swing.*;

/**
 * Class used for show a messge dialog box
 */
public class PopUp {

    /**
     * Method to show a message box
     * @param message String to show in the pop up box
     */
    public static void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    // TODO add javadoc here
    public static String showInputDialog(String message){
        String inputValue = JOptionPane.showInputDialog(message);
        return inputValue;
    }
    
    /**
     * Method to get an integer value from a JSpinner prompt
     * @param prompt Message to show the user
     * @param defaultValue The inital value
     * @param min The last numbe in the sequence
     * @param max The fist number in the sequence
     * @param stepSize The difference between numbers in the sequence
     * @return
     */
    public static int getSpinnerInt(String prompt, int defaultValue, int min, int max, int stepSize){
        SpinnerNumberModel radiusModel = new SpinnerNumberModel(defaultValue, min, max, stepSize);
        JSpinner radiusSpinner = new JSpinner(radiusModel);
        int option = JOptionPane.showOptionDialog(null, radiusSpinner, prompt + "\tMin: " + min + "\tMax: " + max,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        // Check the return value from the dialog box.
        if (option == JOptionPane.CANCEL_OPTION) {
            // TODO handle this better
            return defaultValue;
        } else if (option == JOptionPane.OK_OPTION) {
            return  radiusModel.getNumber().intValue();
        }
        return defaultValue;
    } 
}
