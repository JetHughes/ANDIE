package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a Gaussian filter.
 * </p>
 * 
 * <p>
 * A Gaussian filter blurs an image based on a 2-Dimensional Gaussian Equation, 
 * and can be implemented by a convoloution.
 * </p>
 * 
 **/
public class GaussianFilter implements ImageOperation, java.io.Serializable {
    private int radius;
    GaussianFilter(int radius){
        this.radius = radius;
    }
    GaussianFilter(){
        this(1);
    }

    /**
     * <p>
     * Apply a Gaussian filter to an image.
     * </p>
     * 
     * @param input The image to apply the Gaussian filter to.
     * @return The resulting (blurred) image.
     */

    public BufferedImage apply(BufferedImage input){
        
        float[] array = {};

        Kernel kernel = new Kernel(3, 3, array);
        ConvolveOp convOp = new ConvolveOp(kernel);
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null),
                input.isAlphaPremultiplied(), null);
        convOp.filter(input, output);

        return output;
    } 
}
