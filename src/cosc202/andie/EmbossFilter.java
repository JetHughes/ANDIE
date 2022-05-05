package cosc202.andie;

import java.awt.image.*;

public class EmbossFilter implements ImageOperation {
    int type = 1;
    /**
     * <p>
     * Constructor method for the EmbossFilter class
     * </p>     *      
     */
    public EmbossFilter(int type) {
        this.type = type;
    }

    /**
     * Default Constructor for EmbossFilter
     */
    public EmbossFilter() {}

    /**
     * <p>
     * Apply an EmbossFilter filter to an image.
     * </p>
     * 
     * @param input The image to apply the Emboss filter to.
     * @return The resulting (embossed) image.
     */
    public BufferedImage apply(BufferedImage input) {
        float[][] kernel;
        switch (type) {
            case 1:
                kernel = new float[][]{{0, 0, 0},{1, 0, -1},{0, 0, 0}};
                break;
            case 2:
                kernel = new float[][]{{1, 0, 0},{0, 0, 0},{0, 0, -1}};
                break;
            case 3:
                kernel = new float[][]{{0, 1, 0},{0, 0, 0},{0, -1, 0}};
                break;
            case 4:
                kernel = new float[][]{{0, 0, 1},{0, 0, 0},{-1, 0, 0}};
                break;
            case 5:
                kernel = new float[][]{{0, 0, 0},{-1, 0, 1},{0, 0, 0}};
                break;
            case 6:
                kernel = new float[][]{{-1, 0, 0},{0, 0, 0},{0, 0, 1}};
                break;
            case 7:
                kernel = new float[][]{{0, -1, 0},{0, 0, 0},{0, 1, 0}};
                break;
            case 8:
                kernel = new float[][]{{0, 0, -1},{0, 0, 0},{1, 0, 0}};
                break;
        
            default:
                kernel = new float[][]{{0, 0, 0},{1, 0, -1},{0, 0, 0}};
                break;
        }


        BufferedImage output = Convoluter.applyConvolution(input, kernel, 127);

        return output;
    }
}
