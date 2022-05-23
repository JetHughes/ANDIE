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

    /**
     * Method to show a input box
     * @param message text to be presented to user
     * @return inputValue the value of the string that was entered
     */
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
     * @return returns value of spinner
     */
    public static int getSpinnerInt(String prompt, int defaultValue, int min, int max, int stepSize){
        //SpinnerNumberModel radiusModel = new SpinnerNumberModel(defaultValue, min, max, stepSize);

        if(min >= 1){
            min -= 1;
        }

        JSlider Slider = new JSlider(JSlider.HORIZONTAL, min, max, defaultValue);
        Slider.setPaintLabels(true);

        Slider.setPaintTrack(true);
        Slider.setPaintTicks(true);
        Slider.setPaintLabels(true);
        Slider.setMajorTickSpacing(max/2);
        Slider.setMinorTickSpacing(stepSize);

        int option = JOptionPane.showOptionDialog(null, Slider, prompt + " | Min: " + min + "\t | Max: " + max,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        // Check the return value from the dialog box.
        if (option == JOptionPane.CANCEL_OPTION) {            
            return 1;
        } else if (option == JOptionPane.OK_OPTION) {
            System.out.println(Slider.getValue());
            return  Slider.getValue();
        }
        return defaultValue;
    } 
}
