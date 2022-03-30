package cosc202.andie;

import javax.swing.JOptionPane;

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
}
