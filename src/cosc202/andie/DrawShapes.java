package cosc202.andie;

import java.awt.image.*;

import javax.swing.JColorChooser;
import javax.swing.JLabel;

/**
 * <p>
 * ImageOperation to draw shapes onto an image
 * </p>
 * 
 * <p>
 * can draw rectangles, lines or circles
 * </p>
 */
public class DrawShapes implements ImageOperation, java.io.Serializable {
    /**
    * The co-ordinate values for the shape to be drawn
    */
    int xOrigin, yOrigin, xEnd, yEnd;
    JColorChooser tcc;
    JLabel banner;
    java.awt.Color color;
    
    /**
     * <p>
     * Construct a DrawShapes with given co-ordinates
     * </p>
     * 
     * <p>
     * The co-ordinates are used as a 'boundary' for the shape to be
     * drawn within
     * </p>
     * 
     * @param xOrigin The upper-left most co-ordinates
     * @param yOrigin The upper-left most co-ordinates
     * @param xEnd The lower-right most co-ordinates
     * @param yEnd The lower-right most co-ordinates
     */
    DrawShapes(int xOrigin, int yOrigin, int xEnd, int yEnd, java.awt.Color color){
        this.xOrigin = xOrigin;
        this.yOrigin = yOrigin;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        this.color = color;
    }

    /**
     * <p>
     * Apply a drawing to an image.
     * </p>
     * 
     * <p>
     * The drawing is done by taking the co-ordinates from
     * the users mouse selection and drawing the shape within
     * those bounds
     * </p>
     * 
     * @param input The image to be drawn on.
     * @return The resulting image.
     */
    public BufferedImage apply(BufferedImage input) {
        for (int y = yOrigin; y < yEnd; y++) {
            for (int x = xOrigin; x < xEnd; x++) {
                input.setRGB(x, y, color.getRGB());
            }
        }
        return input;
    }
}
