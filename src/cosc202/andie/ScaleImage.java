package cosc202.andie;

import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to flip Image
 * </p>
 * 
 * <p>
 * flip either vertically or horizontally
 * </p>
 */
public class ScaleImage implements ImageOperation, java.io.Serializable {
    int scale;
    /**
     * <p>
     * Construct a Flip by given axis
     * </p>
     * 
     * <p>
     * The 'isX' determines what axis the image
     * is flipped about
     * </p>
     * 
     * @param scale The scale for the image to be resized to
     */
    ScaleImage(int scale){
        this.scale = scale;
    }

    /**
     * <p>
     * Apply a Flip to an image.
     * </p>
     * 
     * <p>
     * The flip is done by taking the pixels of the origonal image
     * and placing them in their 'flipped' positions in a new image
     * and then returning the new image
     * </p>
     * 
     * @param input The image to be flipped.
     * @return The resulting (flipped) image.
     */
    public BufferedImage apply (BufferedImage input){       
        try{
            BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), input.getType()); 
            if(scale == 150){
                
            }
            else{
                
            }
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
