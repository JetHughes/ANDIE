package cosc202.andie;

import java.awt.image.*;
import java.awt.Color;

public class AdjustBrightness implements ImageOperation, java.io.Serializable{
    private double contrast;
    private double brightness;

    AdjustBrightness(double contrast, double brightness){
        this.contrast = contrast;
        this.brightness = brightness;
    }

    AdjustBrightness(){
        this(10, 10);
    }

    public BufferedImage apply(BufferedImage input) {

        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);  
        for (int y = 0; y < input.getHeight() ; y++) {//loops for image matrix
            for (int x = 0; x < input.getWidth() ; x++) {        
                Color c=new Color(input.getRGB(x,y));
            
                //adding factor to rgb values
                int r=c.getRed();
                int b=c.getBlue();
                int g=c.getGreen();

                r = (int)getAdjustedValue(contrast, brightness, r);
                g = (int)getAdjustedValue(contrast, brightness, g);
                b = (int)getAdjustedValue(contrast, brightness, b);

                output.setRGB(x, y, new Color(r,g,b).getRGB());           
        
            }
        }

        return output;
    }

    public double getAdjustedValue(double c, double b, double v){      
        double result = (1+(c/100))*(v-127.5) + 127.5 * (1+(b/100));

        if(result > 255){
            result = 255;
        } else if (result < 0) {
            result = 0;
        }

        return result;
    }
}
