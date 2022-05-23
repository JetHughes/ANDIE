package cosc202.andie;

import java.io.*;
import javax.imageio.*;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
/**
 * <p>
 * Class that shows the current state of recording in {@link EditableImage}
 * </p>
 */
public class MacroLabel extends JLabel {
    /**
     * The recording icon
     */
    private ImageIcon macroIcon;
    /**
     * Update the macro label
     * @param recording a boolean. True if recording
     */
    public void setMacroLabel(boolean recording){
        try {
            macroIcon = new ImageIcon(ImageIO.read(new File("./src/macroIcon.png")));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if(recording){
            this.setIcon(macroIcon);
        } else {
            this.setText("");
        }
    }
        
        
      
}



