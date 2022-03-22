package cosc202.andie;

//import java.awt.*;
import java.awt.image.BufferedImage;


public class RotateImage implements ImageOperation, java.io.Serializable {
    RotateImage(){

    }

    public BufferedImage apply (BufferedImage input){
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), input.getType());

        for (int y = 0; y < output.getHeight(); y++) {
            for (int x = 0; x < output.getWidth(); x++) {
                output.setRGB(x, y, input.getRGB(((input.getWidth() - 1) - x), ((input.getHeight() - 1) - y)));
            }
        }

        return output;
    }
}
