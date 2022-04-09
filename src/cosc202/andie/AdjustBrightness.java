package cosc202.andie;

import java.awt.image.*;
import java.awt.Color;

/** 
 * <p>
 * ImageOperation to adjust the brightness of an image
 * </p>
 * 
 * <p>
 * Changes the brightness of an image using 
 * equation 2.5 from the cosc202 lab book.
 * </p> 
*/
public class AdjustBrightness implements ImageOperation, java.io.Serializable{

    /** 
     * <p>
     * The percentage to increase or decrease the brightness
     * </p>
    */
    private double brightness;

    /**
     * <p>
     * Construct an AdjustBrightness with the given brightness
     * </p>
     * 
     * @param brightness The percentage to increase or decrease the brightness
     */
    public AdjustBrightness(double brightness){
        this.brightness = brightness;
    }

    /**
     * <p>
     * Construct a new AdjustBrightness operation with the default brightness
     * </p>
     * 
     * <p>
     * BY default, the percentage is 10
     * </p>
     * 
     * @see AdjustBrightness(int)
     */
    public AdjustBrightness(){
        this(10);
    }

    /**
     * <p>
     * Method for testing
     * </p>
     * 
     * @return returns the brightness value
     */
    public double returnBrightness(){
        return brightness;
    }

    /**
     * <p>
     * Apply brightness adjustment to an image
     * </p>
     * 
     * <p>
     * The brightness adjustment is applied using the equation 2.5 of the 202 lab book.
     * Where b = brightness and c = 0;
     * The lab book is here: https://cosc202.cspages.otago.ac.nz/lab-book/COSC202LabBook.pdf
     * </p>
     * 
     * @param input the image to apply the brightness adjustment to
     * @return The resulting adjusted image
     */
    public BufferedImage apply(BufferedImage input) {

        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);  
        for (int y = 0; y < input.getHeight() ; y++) {//loops for image matrix
            for (int x = 0; x < input.getWidth() ; x++) {        
                Color c=new Color(input.getRGB(x,y));
            
                //adding factor to rgb values
                int r=c.getRed();
                int b=c.getBlue();
                int g=c.getGreen();

                r = (int)getAdjustedValue(brightness, r);
                g = (int)getAdjustedValue(brightness, g);
                b = (int)getAdjustedValue(brightness, b);

                output.setRGB(x, y, new Color(r,g,b).getRGB());
            }
        }

        return output;
    }

    /**
     * <p>
     * Method to apply a brightness adjusted to a color channel of a pixel
     * </p>
     * 
     * @param b the value to adjust the brightness by
     * @param v the value to adjust
     * @return The adjusted value
     */
    public double getAdjustedValue(double b, double v){      
        double result = (v - 127.5) + 127.5 * (1+(b/100));

        if(result > 255){
            result = 255;
        } else if (result < 0) {
            result = 0;
        }

        return result;
    }
}
