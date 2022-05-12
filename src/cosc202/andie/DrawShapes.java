package cosc202.andie;

import java.awt.image.*;

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
    int xOrigin;
    int yOrigin;
    int xEnd;
    int yEnd;
    
    DrawShapes(int xOrigin, int yOrigin, int xEnd, int yEnd){
        this.xOrigin = xOrigin;
        this.yOrigin = yOrigin;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
    }

    public BufferedImage apply(BufferedImage input) {
        for (int y = yOrigin; y < yEnd; y++) {
            for (int x = xOrigin; x < xEnd; x++) {
                input.setRGB(x, y, 0);
            }
        }
        return input;
    }
}
