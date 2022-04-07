package cosc202.andie;

import java.awt.image.*;
import java.awt.Color;

/** 
 * <p>
 * ImageOperation to adjust the contrast of an image
 * </p>
 * 
 * <p>
 * Adjusts the contrast of an image using equation 2.50
 * from the cosc202 lab book
 * </p>
 * 
 * @author Jet Hughes
*/
public class AdjustContrast implements ImageOperation, java.io.Serializable{

    /** 
     * The percentage to increase or decrease the contrast
    */
    private double contrast;

    /**
     * <p>
     * Construct an AdjustContrast with the given Contrast
     * </p>
     * 
     * @param contrast The percentage to increase or decrease the Contrast
     */
    public AdjustContrast(double contrast){
        this.contrast = contrast;
    }

    /**
     * <p>
     * Construct a new AdjustContrast operation with the default Contrast
     * </p>
     * 
     * <p>
     * BY default, the percentage is 10
     * </p>
     * 
     * @see AdjustContrast(int)
     */
    public AdjustContrast(){
        this(10);
    }

    /**
     * Mehod for testing
     * @return
     */
    public double returnContrast(){
        return contrast;
    }

    /**
     * <p>
     * Apply Contrast adjustment to an image
     * </p>
     * 
     * <p>
     * The Contrast adjustment is applied using the equation 2.5 of the 202 lab book.
     * Where b = 0 and c = contrast;
     * The lab book is here: https://cosc202.cspages.otago.ac.nz/lab-book/COSC202LabBook.pdf
     * </p>
     * 
     * @param input the image to apply the Contrast adjustment to
     * @return The resulting adjusted image
     */
    public BufferedImage apply(BufferedImage input) {

        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        for (int y = 0; y < input.getHeight(); y++) {// loops for image matrix
            for (int x = 0; x < input.getWidth(); x++) {
                Color c = new Color(input.getRGB(x, y));

                // adding factor to rgb values
                int r = c.getRed();
                int b = c.getBlue();
                int g = c.getGreen();

                r = (int) getAdjustedValue(contrast, r);
                g = (int) getAdjustedValue(contrast, g);
                b = (int) getAdjustedValue(contrast, b);

                output.setRGB(x, y, new Color(r,g,b).getRGB());
            }
        }

        return output;
    }
    
    /**
     * Method to apply a Contrast adjusted to a color channel of a pixel
     * @param c the value to adjust the Contrast by
     * @param v the value to adjust
     * @return The adjusted value
     */
    public double getAdjustedValue(double c, double v) {
        double result = (1 + (c / 100)) * (v - 127.5);

        if(result > 255) {
            result = 255;
        } else if (result < 0) {
            result = 0;
        }

        return result;
    }
}
