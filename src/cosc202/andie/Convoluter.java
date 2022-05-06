package cosc202.andie;

import java.awt.image.*;
import java.awt.Color;

/**
 * This class convolutes an image
 * Heavily influenced by this article: http://ramok.tech/2018/09/27/convolution-in-java/
 * TODO improve javadoc
 */
public class Convoluter {

    /**
     * Applys a border aware convolution to a buffered image
     * @param input Image to convolve
     * @param kernel kernel used for convolution
     * @param offset Offset used if you expect the convolution to produce negative values
     * @return A new BufferedImage with the covolution applied
     */
    public static BufferedImage applyConvolution(BufferedImage input, float[][] kernel, int offset) {

        //read image to 3d array
        float[][][] image = new float[4][input.getWidth()][input.getHeight()];
        for (int x = 0; x < input.getWidth(); x++) {
            for (int y = 0; y < input.getHeight(); y++) {
                Color c = new Color(input.getRGB(x, y));
                image[0][x][y] = c.getRed();
                image[1][x][y] = c.getGreen();
                image[2][x][y] = c.getBlue();
                image[3][x][y] = c.getAlpha();
            }
        }
        
        //convolve and write image
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        for (int x = 0; x < input.getWidth(); x++) {
            for (int y = 0; y < input.getHeight(); y++) {
                //convolve each of the color channels of each pixel separately using the given kernel
                int red = convolvePixel(image[0], x, y, kernel, offset);
                int green = convolvePixel(image[1], x, y, kernel, offset);
                int blue = convolvePixel(image[2], x, y, kernel, offset);

                //Write new pixel to output image, alpha is not changed
                Color c = new Color(red, green, blue, (int)image[3][x][y]);
                output.setRGB(x, y, c.getRGB());
            }
        }

        return output;
    }

    private static int convolvePixel(float[][] input, int x, int y, float[][] kernel, int offset) {
        float out = 0;
        int radius = (kernel.length-1)/2;
        //multiply the kernel matrix with the sub-matrix of the input surrounding the givel pixel
        for (int i = 0; i < kernel.length; i++) {
            for(int j = 0; j < kernel[i].length; j++) {
                //We subtract the radius from the x, y values so that the kernel is "centered" on the pixel
                //This means some pixels will be "outside" of the image
                //So we constrain these pixels to the nearest valid pixel
                int constrainedX = constrain(x+i-radius, input.length);
                int constrainedY = constrain(y+i-radius, input[i].length);
                out += (kernel[i][j] * input[constrainedX][constrainedY]);
            }
        }
        //We also need to ensure the pixel does not go outside the range 0-255
        //Some filters are designed to produce negative values
        //to account for these we can make zero become the midpoint by applying an offset
        return constrain((int)(out + offset), 256);
    }

    private static int constrain(int y, int range){
        if(y < 0) return 0; //if its below zero return zero
        if(y >= range) return range - 1; //if its bigger than the range return the range - 1
        return y; //otherwise do nothing
    }
}
