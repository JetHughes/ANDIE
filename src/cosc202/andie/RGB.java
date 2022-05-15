package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * This class retrieves the colour values of a pixel from a given image and can
 * set the pixels colour values. This class is used instead of BufferedImage's
 * default getRGB() and setRGB() methods as said methods are inefficient as they
 * conduct many checks not required for ANDIE
 * </p>
 * Influenced heavily by the following articles:
 * https://stackoverflow.com/questions/6319465/fast-loading-and-drawing-of-rgb-data-in-bufferedimage/12062505#12062505
 * https://stackoverflow.com/questions/6524196/java-get-pixel-array-from-image
 * https://www.tabnine.com/web/assistant/code/rs/5c76ac53e70f8700017dc8ed#L206
 * https://www.programcreek.com/java-api-examples/?api=java.awt.image.Raster
 */
public class RGB {

    private int width, height;
    private boolean hasAlpha;
    private int pixelLength;
    private byte[] pixels;
    private WritableRaster raster;

    /**
     * Constructor method for the class, generates all required data used later for
     * efficiency
     * 
     * @param image the image pixel rgb values are required to be found from
     */
    public RGB(BufferedImage image) {

        pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        width = image.getWidth();
        height = image.getHeight();
        hasAlpha = image.getAlphaRaster() != null;
        pixelLength = 3;
        if (hasAlpha) {
            pixelLength = 4;
        }

        DataBuffer buffer = new DataBufferByte(pixels, pixels.length);
        raster = Raster.createInterleavedRaster(buffer, width, height, pixelLength * width, pixelLength,
                new int[] { 0, 1, 2 }, null);

    }

    /**
     * <p>
     * This method creates a list of an image's rgb values via the Raster get data
     * method, and returns the argb values at the given indices. The point of this
     * method is to bypass the lengthy processing times utilised by the
     * BufferedImage getRgb method (colour space checking and converting everytime
     * the method is called significantly slows this method down)
     * </p>
     * 
     * @param x the x value of the pixels location being checked
     * @param y the y value of the pixels location being checked
     * @return returns the argb value of the pixel at the given indices
     */
    public int getRGB(int x, int y) {
        int pos = (y * pixelLength * width) + (x * pixelLength);
        int argb = -16777216; // 255 alpha
        if (hasAlpha) {
            argb = (((int) pixels[pos++] & 0xff) << 24); // alpha
        }
        argb += (((int) pixels[pos++] & 0xff) << 16); // red
        argb += (((int) pixels[pos++] & 0xff) << 8); // green
        argb += ((int) pixels[pos++] & 0xff); // blue
        return argb;
    }

    /**
     * Sets the given pixels colour values. The point of this method is to bypass
     * the lengthy processing times utilised by the BufferedImage setRgb method
     * (colour space checking and converting everytime the method is called
     * significantly slows this method down)
     * 
     * @param x the x coordinate of the desired pixel
     * @param y the y coordinate of the desired pixel
     * @param r the red value of the desired pixel
     * @param g the green value of the desired pixel
     * @param b the blue value of the desired pixed
     * @param a the alpha value of the desired pixel
     */
    public void setRGB(int x, int y, int r, int g, int b, int a) {
        raster.setPixel(x, y, new int[] {b, g, r, a});
    }

    /**
     * returns the updates raster
     * 
     * @return returns the updated raster
     */
    public WritableRaster getRaster() {
        return raster;
    }
}