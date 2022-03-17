package cosc202.andie;

import java.awt.image.*;
import java.util.*;

public class AdjustBrightness implements ImageOperation, java.io.Serializable{
    private int percent;

    AdjustBrightness(int percent){
        this.percent = percent;
    }

    AdjustBrightness(){
        this(10);
    }

    public BufferedImage apply(BufferedImage input) {
        double amount = 255 * (percent/100);

        System.out.println("Adjust Brightness by: " + amount);

        return input;
    }
}
