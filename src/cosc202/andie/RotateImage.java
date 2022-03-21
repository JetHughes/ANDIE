package cosc202.andie;

import java.awt.image.*;

public class RotateImage implements ImageOperation, java.io.Serializable {
    RotateImage(){

    }

    public BufferedImage apply (BufferedImage input){
        //Just so it stops yelling at me
        return input;
    }
}
