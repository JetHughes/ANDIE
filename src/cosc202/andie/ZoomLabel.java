package cosc202.andie;

import java.text.DecimalFormat;
import javax.swing.JLabel;

/**
 * Class that shows the current zoom level of an {@link ImagePanel}
 */
public class ZoomLabel extends JLabel {
    DecimalFormat df = new DecimalFormat("##");

    /**
     * Update the zoom label
     * @param zoom The new zoom level
     */
    public void setZoom(double zoom){
        this.setText(df.format(zoom));
        revalidate();
    }    
}
