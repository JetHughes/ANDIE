package cosc202.andie;

import java.awt.image.*;

public class AdjustBrightness implements ImageOperation, java.io.Serializable{
    private double percent;

    AdjustBrightness(double percent){
        this.percent = percent;
    }

    AdjustBrightness(){
        this(10);
    }

    public BufferedImage apply(BufferedImage input) {
        double amount = 255.0 * (percent/100.0);

        System.out.println("Adjust Brightness by: " + amount);

        return input;
    }
}
