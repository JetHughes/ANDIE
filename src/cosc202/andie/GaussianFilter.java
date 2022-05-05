package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a Gaussian filter.
 * </p>
 * 
 * <p>
 * A Gaussian filter blurs an image based on a 2-Dimensional Gaussian Equation,
 * and can be implemented by a convolution.
 * </p>
 * 
 **/
public class GaussianFilter implements ImageOperation, java.io.Serializable {

    /**
     * <p>
     * The radius of the Gaussian filter to be applied
     * </p>
     */
    private int radius;

    /**
     * <p>
     * Constructor method for the GaussianFilter class
     * </p>
     * 
     * @param radius The radius of the gaussian filter desired
     */
    public GaussianFilter(int radius) {
        this.radius = radius;
    }

    /**
     * <p>
     * Alternate constructor method for the Gaussian filter class
     * with no parameters
     * </p>
     */
    public GaussianFilter() {
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
     * Method for finding the Gaussian function value of the input coordinates.
     * </p>
     * 
     * @param x     The horizontal locational value of the determined kernel.
     * @param y     The vertical locational value of the determined kernel.
     * @param sigma The multiplier to ensure kernel edge values aren't cut off too
     *              much.
     * @return Returns the Gaussian function value at the given coordinates.
     */
    public double getGaussian(int x, int y, float sigma) {
        return (1 / (2 * Math.PI * Math.pow(sigma, 2))
                * Math.exp(-(Math.pow(x, 2) + Math.pow(y, 2)) / (2 * Math.pow(sigma, 2))));
    }

    /**
     * <p>
     * Apply a Gaussian filter to an image.
     * </p>
     * 
     * @param input The image to apply the Gaussian filter to.
     * @return The resulting (blurred) image.
     */
    public BufferedImage apply(BufferedImage input) {
        int size = (2 * radius + 1);
        float[][] array = new float[size][size];
        float sigma = radius / 3.0f;
        double sum = 0;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                double result = getGaussian(x, y, sigma);
                array[x + radius][y + radius] = (float) result;
                sum += array[x + radius][y + radius];
            }
        }

        // normalise array
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                array[i][j] = array[i][j] / (float) sum;
            }
        }

        //flatten array
        // float[] normflatArr = new float[size * size];
        // count = 0;
        // for (int i = 0; i < array.length; i++) {
        //     for (int j = 0; j < array.length; j++) {
        //         normflatArr[count] = array[i][j];
        //         count++;
        //     }
        // }

        // print array (for debugging)
        // for (float f : normflatArr) {
        // System.out.println(f + ", ");
        // }

        //Kernel kernel = new Kernel(2 * radius + 1, 2 * radius + 1, normflatArr);
        //ConvolveOp convOp = new ConvolveOp(kernel);

        // BufferedImage tempImage = ImgExtend.extend(input, radius);

        // BufferedImage output = new BufferedImage(tempImage.getColorModel(), tempImage.copyData(null),
        //         tempImage.isAlphaPremultiplied(), null);
        // convOp.filter(tempImage, output);

        // output = output.getSubimage(radius, radius, input.getWidth(), input.getHeight());

        BufferedImage output = Convoluter.applyConvolution(input, array, 0);

        return output;
    }
}
