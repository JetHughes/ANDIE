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
 * A Median filter blurs an image by replacing each pixel with the median value
 * of the
 * pixels in a surrounding neighbourhood
 * </p>
 */
public class MedianFilter implements ImageOperation, java.io.Serializable {

    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a
     * 5x5 filter, and so forth.
     */
    private int radius;

    /**
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
    public MedianFilter() {
        this(1);
    }

    /**
     * <p>
     * Create a buffered image object with a border of size radius and pixels along
     * an axis mimicing those pixels around the extremities of the original input in
     * order to extend a filter allowing for the filter to completely doctor an
     * image and reach all pixels within the image
     * buffered image
     * </p>
     * 
     * @param input the buffered image object to be extended
     * @return returns the new buffered image object with an extended margin
     *         relative to the existing biffered image object's border
     */
    private BufferedImage getExtendedImage(BufferedImage input) {
        // Create new BufferedImage object greater in size than the input image by the
        // radius on all sides to allow complete image convolution
        BufferedImage tempImage = new BufferedImage(input.getWidth() + radius * 2, input.getHeight() + radius * 2,
                input.getType());
        // Original image exists in tempImage within a one radius boundary
        tempImage.getGraphics().drawImage(input, radius, radius, null);

        // Sets rgb values for the upper and lower blank pixels
        int rgb;
        boolean north = true;
        for (int wPtr = 0; wPtr < tempImage.getWidth(); wPtr++) {
            // If outside left boundary
            if (wPtr <= radius) {
                // If upper boundary
                if (north) {
                    rgb = input.getRGB(0, 0);
                } else {
                    rgb = input.getRGB(0, input.getHeight() - 1);
                }
                // If outside right boundary
            } else if (wPtr >= tempImage.getWidth() - radius) {
                // If upper boundary
                if (north) {
                    rgb = input.getRGB(input.getWidth() - 1, 0);
                } else {
                    rgb = input.getRGB(input.getWidth() - 1, input.getHeight() - 1);
                }
            } else {
                if (north) {
                    rgb = input.getRGB(wPtr - radius, 0);
                } else {
                    rgb = input.getRGB(wPtr - radius, input.getHeight() - 1);
                }
            }

            // Set RGB values for added pixels
            // For all pixels above last known pixel copy said pixel's value
            if (north) {
                for (int hPtr = 0; hPtr <= radius; hPtr++) {
                    tempImage.setRGB(wPtr, hPtr, rgb);
                }
            } else {
                for (int hPtr = tempImage.getHeight() - radius; hPtr < tempImage.getHeight(); hPtr++) {
                    tempImage.setRGB(wPtr, hPtr, rgb);
                }
            }

            // Once upper boundary complete, continue to bottom
            if (wPtr == tempImage.getWidth() - 1 && north) {
                north = false;
                wPtr = 0;
            }
        }

        // Set colour values for vertical plane unknown pixels (excluding corners)
        boolean west = true;
        for (int hPtr = radius; hPtr < tempImage.getHeight() - radius; hPtr++) {
            if (west) {
                rgb = input.getRGB(0, hPtr - radius);
                for (int wPtr = 0; wPtr <= radius; wPtr++) {
                    tempImage.setRGB(wPtr, hPtr, rgb);
                }
            } else {
                rgb = input.getRGB(input.getWidth() - 1, hPtr - radius);
                for (int wPtr = tempImage.getWidth() - radius; wPtr < tempImage.getWidth(); wPtr++) {
                    tempImage.setRGB(wPtr, hPtr, rgb);
                }
            }
            if (hPtr == tempImage.getHeight() - radius - 1 && west) {
                west = false;
                hPtr = radius;
            }
        }
        return tempImage;
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
        int centVal = ((2 * radius + 1) * (2 * radius + 1)) / 2;
        return vals[centVal];
    }

    /**
     * <p>
     * Apply a Median filter to an image.
     * </p>
     * 
     * <p>
     * The strength of the blur is specified by the {@link radius}.
     * a larger radius leads to stronger blurring through a greater surrounding
     * quantity of values to find the median values.
     * </p>
     * 
     * @param input The image to apply the Median filter to.
     * @return The resulting (blurred) image.
     */
    public BufferedImage apply(BufferedImage input) {

        try {
            int size = (2 * radius + 1) * (2 * radius + 1);

            BufferedImage tempImage = getExtendedImage(input);

            BufferedImage output = new BufferedImage(tempImage.getColorModel(), tempImage.copyData(null),
                    tempImage.isAlphaPremultiplied(), null);

            // Iterate over pixels within the image
            for (int y = radius; y < output.getHeight() - radius ; y++) {
                for (int x = radius; x < output.getWidth() - radius; x++) {

                    // Initialise arrays to contain each pixel colour (and transparency) value
                    // within an area determined by the radius
                    int[] arrA = new int[size];
                    int[] arrR = new int[size];
                    int[] arrG = new int[size];
                    int[] arrB = new int[size];

                    // Iterate over pixels within the area of the given radius, push colour and
                    // transparency values into arrays
                    int innPos = 0;
                    for (int e = y - radius; e <= y + radius; e++) {
                        for (int i = x - radius; i <= x + radius; i++) {
                            int argb = tempImage.getRGB(i, e);
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

                    // Find the median values of the colour and transparency values within the
                    // determined area
                    int medianA = getMedian(arrA);
                    int medianR = getMedian(arrR);
                    int medianG = getMedian(arrG);
                    int medianB = getMedian(arrB);

                    // Transform copied image to apply the new colour and transparency to the given
                    // pixel,
                    // Create new colour and transparency value equal to the median of the values
                    // within the area determined,
                    int argb = (medianA << 24) | (medianR << 16) | (medianG << 8) | medianB;
                    output.setRGB(x, y, argb);
                }
            }

            output = output.getSubimage(radius, radius, input.getWidth(), input.getHeight());

            return output;

        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println(ex);
            PopUp.showMessageDialog("An ArrayIndexOutOfBounds has occured. Returning input");
            return input;
        } catch (Exception e) {
            System.out.println(e);
            PopUp.showMessageDialog("An unknown error has occured. Returning input");
            return input;
        }
    }
}
