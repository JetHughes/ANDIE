package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply an Emboss filter.
 * </p>
 * 
 * <p>
 * An Emboss filter create the effect of the image being 
 * pressed into or raised out of a sheet of paper.
 * There are eight basic filters depending on the direction of embossing they simulate
 * </p>
 * 
 **/
public class EmbossFilter implements ImageOperation {
    
    /**
     * <p>
     * The direction of the embossing effect
     * </p>
     */
    int direction = 1;

    /**
     * <p>
     * Constructor method for the EmbossFilter class
     * </p>
     */
    public EmbossFilter(int type) {
        this.direction = type;
    }

    /**
     * Default Constructor for EmbossFilter
     */
    public EmbossFilter() {}

    private float[][][] kernels = {
        {{0, 0, 0},{1, 0, -1},{0, 0, 0}},
        {{0, 0, 0},{-1, 0, 1},{0, 0, 0}},
        {{0, 0, 1},{0, 0, 0},{-1, 0, 0}},
        {{0, 0, -1},{0, 0, 0},{1, 0, 0}},
        {{0, 1, 0},{0, 0, 0},{0, -1, 0}},
        {{0, -1, 0},{0, 0, 0},{0, 1, 0}},
        {{1, 0, 0},{0, 0, 0},{0, 0, -1}},
        {{-1, 0, 0},{0, 0, 0},{0, 0, 1}},
        // Sobel filters
        {{-1/2, 0, 1/2},{-1, 0, 1},{-1/2, 0, 1/2}},
        {{-1/2, -1, -1/2},{0, 0, 0},{1/2, 1, 1/2}}

    };

    /**
     * <p>
     * Apply an EmbossFilter filter to an image.
     * </p>
     * 
     * @param input The image to apply the Emboss filter to.
     * @return The resulting (embossed) image.
     */
    public BufferedImage apply(BufferedImage input) {

        BufferedImage output = Convoluter.applyConvolution(input, kernels[direction-1], 127);

        return output;
    }
}
