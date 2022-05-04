//CROCS
package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a Sharpen filter.
 * </p>
 * 
 * <p>
 * A Sharpen filter enhances the difference between neighbouring values,
 * and can be implemented as a convoloution.
 * </p>
 * 
 **/

public class SharpenFilter implements ImageOperation, java.io.Serializable {

    /**
     * <p>
     * Constructor for SharpenFilter class
     * </p>
     */
    SharpenFilter() {
    };

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
        // As sharpen filter kernel is 3 by 3 the radius is set at 1
        int radius = 1;
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
     * Apply a Sharpen filter to an image.
     * </p>
     * 
     * @param input The image to apply the Sharpen filter to.
     * @return The resulting (sharpened) image.
     */
    public BufferedImage apply(BufferedImage input) {

        float[] array = { 0, -1 / 2.0f, 0, -1 / 2.0f, 3.0f, -1 / 2.0f, 0, -1 / 2.0f, 0 };

        Kernel kernel = new Kernel(3, 3, array);
        ConvolveOp convOp = new ConvolveOp(kernel);

        BufferedImage tempImage = getExtendedImage(input);

        BufferedImage output = new BufferedImage(tempImage.getColorModel(), tempImage.copyData(null),
                tempImage.isAlphaPremultiplied(), null);
        convOp.filter(tempImage, output);

        output = output.getSubimage(1, 1, input.getWidth(), input.getHeight());

        return output;
    }
}
