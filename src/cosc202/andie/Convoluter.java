package cosc202.andie;

import java.awt.image.*;
import java.awt.Color;

/**
 * This class convolutes an image
 * TODO improve javadoc
 */
public class Convoluter {

    /**
     * Applys a border aware convolution to a buffered image
     * @param input Image to convolve
     * @param kernel kernel used for convolution
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
                int red = convolvePixel(image[0], x, y, kernel, offset);
                int green = convolvePixel(image[1], x, y, kernel, offset);
                int blue = convolvePixel(image[2], x, y, kernel, offset);

                Color c = new Color(red, green, blue, (int)image[3][x][y]);
                output.setRGB(x, y, c.getRGB());
            }
        }

        return output;
    }

    private static int convolvePixel(float[][] input, int x, int y, float[][] kernel, int offset) {
        float out = 0;
        int radius = (kernel.length-1)/2;
        for (int i = 0; i < kernel.length; i++) {
            for(int j = 0; j < kernel[i].length; j++) {
                out += (kernel[i][j] * input[constrainX(x+i-radius, input.length)][constrainY(y+j-radius, input[i].length)]);
            }
        }
        return safePixelValue(out + offset);
    }

    private static int safePixelValue(float p){        
        if(p < 0) return 0;
        if(p > 255) return 255;
        return (int)p;
    }

    private static int constrainY(int y, int height){
        if(y < 0) return 0;
        if(y >= height) return height - 1;
        return y;
    }

    private static int constrainX(int x, int width){
        if(x < 0) return 0; 
        if(x >= width) return width - 1;
        return x;
    }
}
