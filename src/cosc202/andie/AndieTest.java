package cosc202.andie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.*;

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
        assertEquals( 75, testPanel.getZoom());
    }

    @Test 
    void setZoomTestLowRange(){
        ImagePanel testPanel = new ImagePanel();
        testPanel.setZoom(10);
        assertEquals( 50, testPanel.getZoom());
    }

    @Test 
    void setZoomTestHighRange(){
        ImagePanel testPanel = new ImagePanel();
        testPanel.setZoom(400);
        assertEquals( 200, testPanel.getZoom());
    }

    //AdjustBrightnessTests
    @Test 
    void brightnessDefault(){
        AdjustBrightness test = new AdjustBrightness();
        assertEquals( 10, test.returnBrightness());
    }

    @Test 
    void brightnessChange(){
        AdjustBrightness test = new AdjustBrightness(55);
        assertEquals( 55, test.returnBrightness());
    }
    
    @Test 
    void brightnessAdjustedValuesMidRange(){
        AdjustBrightness test = new AdjustBrightness(55);
        
        assertEquals( 22.75, test.getAdjustedValue(10, 10));
    }

    @Test 
    void brightnessAdjustedValuesLowRange(){
        AdjustBrightness test = new AdjustBrightness(55);
        
        assertEquals( 0, test.getAdjustedValue(-50, 10));
    }

    @Test 
    void brightnessAdjustedValuesHighRange(){
        AdjustBrightness test = new AdjustBrightness(55);
        
        assertEquals( 255, test.getAdjustedValue(500, 500));
    }

    //AdjustContrast Tests
    @Test 
    void contrastDefault(){
        AdjustContrast test = new AdjustContrast();
        assertEquals( 10, test.returnContrast());
    }

    @Test 
    void contrastChange(){
        AdjustContrast test = new AdjustContrast(69);
        assertEquals( 69, test.returnContrast());
    }

    @Test 
    void contrastAdjustedValuesMidRange(){
        AdjustContrast test = new AdjustContrast(55);
        
        assertEquals( 217.5, test.getAdjustedValue(200, 200));
    }

    @Test 
    void contrastAdjustedValuesLowRange(){
        AdjustContrast test = new AdjustContrast(55);
        
        assertEquals( 0, test.getAdjustedValue(50, -50));
    }

    @Test 
    void contrastAdjustedValuesHighRange(){
        AdjustContrast test = new AdjustContrast(55);
        
        assertEquals( 255, test.getAdjustedValue(500, 500));
    }

    //Image Operation

    //Mean Filter
    



}
