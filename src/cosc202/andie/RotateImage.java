package cosc202.andie;

import java.awt.image.BufferedImage;

public class RotateImage implements ImageOperation, java.io.Serializable {
    private int degrees;
    RotateImage(int degrees){
        this.degrees = degrees;
    }

    public BufferedImage apply (BufferedImage input){       
        BufferedImage output;
        //Rotates image 180 degrees
        if(degrees == 180){
            output = new BufferedImage(input.getWidth(), input.getHeight(), input.getType());
            for (int y = 0; y < output.getHeight(); y++) {
                for (int x = 0; x < output.getWidth(); x++) {
                    output.setRGB(x, y, input.getRGB(((input.getWidth() - 1) - x), ((input.getHeight() - 1) - y)));
                }
            }
        }
        //Rotates Image 90 degrees to the LEFT
        else if(degrees == 90){
            output = new BufferedImage(input.getHeight(), input.getWidth(), input.getType());
            for (int y = 0; y < output.getHeight(); y++) {
                for (int x = 0; x < output.getWidth(); x++) {
                    output.setRGB(x, y, input.getRGB(((input.getWidth() - 1) - y), ((input.getHeight() - 1) - x)));
                }
            }
            for (int y = 0; y < output.getHeight(); y++) {
                for (int x = 0; x < output.getWidth(); x++) {
                    output.setRGB(x, y, input.getRGB(((input.getWidth() - 1) - y), x));
                }
            }
        }
        //Rotates Image 90 degrees to the RIGHT
        else {
            output = new BufferedImage(input.getHeight(), input.getWidth(), input.getType());
            for (int y = 0; y < output.getHeight(); y++) {
                for (int x = 0; x < output.getWidth(); x++) {
                    output.setRGB(x, y, input.getRGB(((input.getWidth() - 1) - y), ((input.getHeight() - 1) - x)));
                }
            }
            for (int y = 0; y < output.getHeight(); y++) {
                for (int x = 0; x < output.getWidth(); x++) {
                    output.setRGB(x, y, input.getRGB(y, (input.getHeight() - 1) - x));
                }
            }
        }
        
        return output;
    }
}
