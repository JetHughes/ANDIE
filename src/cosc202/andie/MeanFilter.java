//CROCS
package cosc202.andie;

import java.awt.image.*;
import java.util.*;

/**
 * <p>
 * ImageOperation to apply a Mean (simple blur) filter.
 * </p>
 * 
 * <p>
 * A Mean filter blurs an image by replacing each pixel by the average of the
 * pixels in a surrounding neighbourhood, and can be implemented by a
 * convoloution.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @see java.awt.image.ConvolveOp
 * @author Steven Mills
 * @version 1.0
 */
public class MeanFilter implements ImageOperation, java.io.Serializable {

    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a
     * 5x5 filter, and so forth.
     */
    private int radius;

    /**
     * <p>
     * Construct a Mean filter with the given size.
     * </p>
     * 
     * <p>
     * The size of the filter is the 'radius' of the convolution kernel used.
     * A size of 1 is a 3x3 filter, 2 is 5x5, and so on.
     * Larger filters give a stronger blurring effect.
     * </p>
     * 
     * @param radius The radius of the newly constructed MeanFilter
     */
    MeanFilter(int radius) {
        this.radius = radius;
    }

    /**
     * <p>
     * Construct a Mean filter with the default size.
     * </p
     * >
     * <p>
     * By default, a Mean filter has radius 1.
     * </p>
     * 
     * @see MeanFilter(int)
     */
    MeanFilter() {
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
     * Apply a Mean filter to an image.
     * </p>
     * 
     * <p>
     * As with many filters, the Mean filter is implemented via convolution.
     * The size of the convolution kernel is specified by the {@link radius}.
     * Larger radii lead to stronger blurring.
     * </p>
     * 
     * @param input The image to apply the Mean filter to.
     * @return The resulting (blurred) image.
     */
    public BufferedImage apply(BufferedImage input) {
        try {
            int size = (2 * radius + 1) * (2 * radius + 1);
            float[] array = new float[size];
            Arrays.fill(array, 1.0f / size);

            Kernel kernel = new Kernel(2 * radius + 1, 2 * radius + 1, array);
            ConvolveOp convOp = new ConvolveOp(kernel);

            BufferedImage tempImage = getExtendedImage(input);

            BufferedImage output = new BufferedImage(tempImage.getColorModel(), tempImage.copyData(null),
                    tempImage.isAlphaPremultiplied(), null);
            convOp.filter(tempImage, output);

            // Remove the added border of width radius
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
