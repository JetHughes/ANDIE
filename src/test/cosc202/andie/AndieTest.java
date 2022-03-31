package test.cosc202.andie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cosc202.andie.AdjustBrightness;
import cosc202.andie.AdjustContrast;
import cosc202.andie.ImagePanel;

public class AndieTest {

    @Test 
    void dumbyTest(){
    }
    //Image Panel Tests
    @Test
    void getZoomInitialValue(){
        ImagePanel testPanel = new ImagePanel();
        Assertions.assertEquals(100.0, testPanel.getZoom());
    }

    @Test 
    void setZoomTestMidRange(){
        ImagePanel testPanel = new ImagePanel();
        testPanel.setZoom(75);
        Assertions.assertEquals( 75, testPanel.getZoom());
    }

    @Test 
    void setZoomTestLowRange(){
        ImagePanel testPanel = new ImagePanel();
        testPanel.setZoom(10);
        Assertions.assertEquals( 50, testPanel.getZoom());
    }

    @Test 
    void setZoomTestHighRange(){
        ImagePanel testPanel = new ImagePanel();
        testPanel.setZoom(400);
        Assertions.assertEquals( 200, testPanel.getZoom());
    }

    //AdjustBrightnessTests
    @Test 
    void brightnessDefault(){
        AdjustBrightness test = new AdjustBrightness();
        Assertions.assertEquals( 10, test.returnBrightness());
    }

    @Test 
    void brightnessChange(){
        AdjustBrightness test = new AdjustBrightness(55);
        Assertions.assertEquals( 55, test.returnBrightness());
    }
    
    @Test 
    void brightnessAdjustedValuesMidRange(){
        AdjustBrightness test = new AdjustBrightness(55);    
        Assertions.assertEquals( 22.75, test.getAdjustedValue(10, 10));
    }

    @Test 
    void brightnessAdjustedValuesLowRange(){
        AdjustBrightness test = new AdjustBrightness(55);
        Assertions.assertEquals( 0, test.getAdjustedValue(-50, 10));
    }

    @Test 
    void brightnessAdjustedValuesHighRange(){
        AdjustBrightness test = new AdjustBrightness(55);
        Assertions.assertEquals( 255, test.getAdjustedValue(500, 500));
    }

    //AdjustContrast Tests
    @Test 
    void contrastDefault(){
        AdjustContrast test = new AdjustContrast();
        Assertions.assertEquals( 10, test.returnContrast());
    }

    @Test 
    void contrastChange(){
        AdjustContrast test = new AdjustContrast(69);
        Assertions.assertEquals( 69, test.returnContrast());
    }

    @Test 
    void contrastAdjustedValuesMidRange(){
        AdjustContrast test = new AdjustContrast(55);
        Assertions.assertEquals( 217.5, test.getAdjustedValue(200, 200));
    }

    @Test 
    void contrastAdjustedValuesLowRange(){
        AdjustContrast test = new AdjustContrast(55);
        Assertions.assertEquals( 0, test.getAdjustedValue(50, -50));
    }

    @Test 
    void contrastAdjustedValuesHighRange(){
        AdjustContrast test = new AdjustContrast(55);
        Assertions.assertEquals( 255, test.getAdjustedValue(500, 500));
    }

    //Image Operation

    //Mean Filter
    



}