//CROCS
package cosc202.andie;

import java.awt.image.*;
import java.util.*;

/**
 * <p>
 * ImageOperation to apply a Median (simple blur) filter.
 * </p>
 * 
 * <p>
 * A Median filter blurs an image by replacing each pixel by the average of the
 * pixels in a surrounding neighbourhood, and can be implemented by a
 * convoloution.
 * </p>
 */
public class MedianFilter implements ImageOperation, java.io.Serializable {

    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a
     * 5x5 filter, and so forth.
     */
    private int radius;

    /**
     * <p>
     * Construct a Median filter with the given size.
     * </p>
     * 
     * <p>
     * The size of the filter is the 'radius' of the convolution kernel used.
     * A size of 1 is a 3x3 filter, 2 is 5x5, and so on.
     * Larger filters give a stronger blurring effect.
     * </p>
     * 
     * @param radius The radius of the newly constructed MedianFilter
     */
    MedianFilter(int radius) {
        this.radius = radius;
    }

    /**
     * <p>
     * Construct a Median filter with the default size.
     * </p
     * >
     * <p>
     * By default, a Median filter has radius 1.
     * </p>
     * 
     * @see MedianFilter(int)
     */
    MedianFilter() {
        this(1);
    }

    /**
     * <p>
     * Find the median value in an array
     * </p>
     * 
     * @param vals The input array containing integers
     * @return The median value of the array
     */
    public int getMedian(int[] vals) {
        Arrays.sort(vals);
        return vals[radius + 1];
    }

    /**
     * <p>
     * Apply a Median filter to an image.
     * </p>
     * 
     * <p>
     * The Median filter is implemented through convolution.
     * The size of the convolution kernel is specified by the {@link radius}.
     * a larger radius leads to stronger blurring.
     * </p>
     * 
     * @param input The image to apply the Median filter to.
     * @return The resulting (blurred) image.
     */
    public BufferedImage apply(BufferedImage input) {

        try {
            int size = (2 * radius + 1) * (2 * radius + 1);
            BufferedImage output = new BufferedImage(input.getColorModel(),
                    input.copyData(null), input.isAlphaPremultiplied(), null);

            // Iterate over pixels within the image
            for (int y = radius; y < input.getHeight() - (radius + 1); y++) {
                for (int x = radius; x < input.getWidth() - (radius + 1); x++) {

                    // Initialise arrays to contain each pixel colour value within a kernel
                    int[] arrA = new int[size];
                    int[] arrR = new int[size];
                    int[] arrG = new int[size];
                    int[] arrB = new int[size];

                    // Iterate over pixels within the kernel
                    int innPos = 0;
                    for (int e = y; e < y + (2 * radius + 1); e++) {
                        for (int i = x; i < x + (2 * radius + 1); i++) {
                            int argb = input.getRGB(i, e);
                            int a = (argb & 0xFF000000) >> 24;
                            int r = (argb & 0x00FF0000) >> 16;
                            int g = (argb & 0x0000FF00) >> 8;
                            int b = (argb & 0x000000FF);

                            arrA[innPos] = a;
                            arrR[innPos] = r;
                            arrG[innPos] = g;
                            arrB[innPos] = b;
                            innPos++;
                        }
                    }

                    int medianA = getMedian(arrA);
                    int medianR = getMedian(arrR);
                    int medianG = getMedian(arrG);
                    int medianB = getMedian(arrB);

                    // Create new colour value equal to the median of all values within the kernel
                    int argb = (medianA << 24) | (medianR << 16) | (medianG << 8) | medianB;
                    output.setRGB(x, y, argb);
                }
            }
            return output;

        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println(ex);
            System.out.println("An ArrayIndexOutOfBounds has occured. Returning input");
            return input;
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("An unknown error has occured. Returning input");
            return input;
        }
    }
}
