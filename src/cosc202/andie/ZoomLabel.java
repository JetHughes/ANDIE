package cosc202.andie;

import java.text.DecimalFormat;

import javax.swing.JLabel;

public class ZoomLabel extends JLabel {
    DecimalFormat df = new DecimalFormat("##");
    public void setZoom(double zoom){
        this.setText(df.format(zoom));
        revalidate();
    }    
}
