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
        int size = (2*radius+1);
        float [][] array = new float[size][size];
        float sigma = radius/3.0f;
        double sum = 0;
        int row = 0;
        int col = 0;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {                
                double base = Math.E * (1/(2 * Math.PI * Math.pow(sigma, 2.0)));
                double pow = -((Math.pow(x, 2) + Math.pow(y, 2)) / (2*Math.pow(sigma, 2)));    
                array[x+radius][y+radius] = (float)Math.pow(base, pow);  
                sum += array[x+radius][y+radius];
            }
        }

        //normalise array and flatten array
        float[] normflatArr = new float[size * size];
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                normflatArr[count] = array[i][j]/(float)sum;
                count ++;
            }
        }

        //print arrat (for debugging)
        for (float f : normflatArr) {
            System.out.println(f + ", \n");
        }

        Kernel kernel = new Kernel(2*radius+1, 2*radius+1, normflatArr);
        ConvolveOp convOp = new ConvolveOp(kernel);
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null),
                input.isAlphaPremultiplied(), null);
        convOp.filter(input, output);

        return output;
    } 
}
