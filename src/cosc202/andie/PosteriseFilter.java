package cosc202.andie;

import java.util.ArrayList;
import java.util.Random;
import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a Posterise filter.
 * </p>
 * 
 * <p>
 * Reduces the number of colours in an image by replacing each pixel 
 * with a representative colour, found using a k means clustering of the colours in the image
 * </p>
 *  
 */
public class PosteriseFilter implements ImageOperation, java.io.Serializable {
    /**
     * An instance of the Random class
     */
    static final Random R = new Random();

    /**
     * The number of colors in the image to find
     */
    private int k;

    
    /**
     * Construct a posterise filter with a given k value
     * 
     * @param k the number of colors in the image to find
     */
    public PosteriseFilter(int k) {
        this.k = k;
    }
    
    /**
     * Construct a posterise filter with the default k value of 10
     */
    public PosteriseFilter() {
        this(5);
    }
    
    /**
     * Create an argbClass object for efficient colour control
     */
    private RGB argbClass;
    
    /**
     * Arraylist containing information about each pixel
     */
    private ArrayList<Pixel> pixels = new ArrayList<Pixel>();

    /**
     * Applies a posterise filter to an image using the supplied k value
     * @param input the image to apply the filter to
     * @return the resulting posterised image
     */
    public BufferedImage apply(BufferedImage input) {

        //read pixels
        argbClass = new RGB(input);
        for (int x = 0; x < input.getWidth(); x++) {
            for (int y = 0; y < input.getHeight(); y++) {
                int argb = argbClass.getRGB(x, y);
                int a = (argb & 0xFF000000) >> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);
                pixels.add(new Pixel(x, y, r, g, b, a));
            }
        }

        //get inital random centroids
        ArrayList<Centroid> centroids = initalCentroids();
        
        //compute centroids
        int runs = 5;
        for (int run = 0; run < runs; run++) {
            for (Pixel pixel : pixels) {
                double minDist = Double.MAX_VALUE;
                for (int i = 0; i < centroids.size(); i++) {
                    double dist = getDistance(centroids.get(i), pixel);
                    if(dist < minDist) {
                        minDist = dist;
                        pixel.clusterNo = i;
                    }
                }
            }       
            for (Centroid c : centroids) {
                System.out.println(run + ": (" + c.r + ", " + c.g + ", " + c.b + ")");
            }
            centroids = recomputeCentroids(); 
        }

        //write image
        for (Pixel pixel : pixels) {          
            Centroid c = centroids.get(pixel.clusterNo); 
            argbClass.setRGB(pixel.x, pixel.y, c.r, c.g, c.b, pixel.a);  
        }
        BufferedImage output = new BufferedImage(input.getColorModel(), argbClass.getRaster(), input.isAlphaPremultiplied(), null);
        System.out.println("done");
        return output;
    }

    /**
     * Randomly select k pixels for the starting points of the centroids
     * @return An arraylist of {@link Centroid}
     */
    private ArrayList<Centroid> initalCentroids(){
        ArrayList<Centroid> centroids = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            int r = pixels.get(R.nextInt(pixels.size())).r;
            int g = pixels.get(R.nextInt(pixels.size())).g;
            int b = pixels.get(R.nextInt(pixels.size())).b;
            
            Centroid centroid = new Centroid(r, g, b);

            centroids.add(centroid);
        }
        return centroids;
    }

    /**
     * Recomputer the center of each centroid
     * @return An arraylist of {@link Centroid} each in the middle of their cluster
     */
    private ArrayList<Centroid> recomputeCentroids(){
        ArrayList<Centroid> centroids = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            centroids.add(calculateCentroid(i));
        }
        return centroids;
    } 

    /**
     * Re-center a centroid on its cluster
     * @param clusterNo which centroid to re-center on its cluster
     * @return A new {@link Centroid} in the center of its cluster
     */
    private Centroid calculateCentroid(int clusterNo){        
        ArrayList<Integer> pixelsInCluster = new ArrayList<>();
        for (int i = 0; i < pixels.size(); i++) {
            Pixel pixel = pixels.get(i);
            if(pixel.clusterNo == clusterNo) {
                pixelsInCluster.add(i);
            }
        }        
        
        double sumR = 0.0;
        double sumG = 0.0;
        double sumB = 0.0;
        for (int i : pixelsInCluster) {
            sumR += pixels.get(i).r;
            sumG += pixels.get(i).g;
            sumB += pixels.get(i).b;
        }
        
        sumR /= pixelsInCluster.size();
        sumG /= pixelsInCluster.size();
        sumB /= pixelsInCluster.size();
        return new Centroid((int)sumR, (int)sumG, (int)sumB);
    }

    /**
     * Get the (euclidean) distance between a Centroid and a pixel
     * @param centroid the centroid
     * @param pixel the pixel
     * @return A the distance between them as a double
     */
    public double getDistance(Centroid centroid, Pixel pixel){
        double sum = 0;
        sum += Math.pow(centroid.r - pixel.r, 2);
        sum += Math.pow(centroid.g - pixel.g, 2);
        sum += Math.pow(centroid.b - pixel.b, 2);

        return Math.sqrt(sum);
    }

    /**
     * Method for testing getdistance method
     * @param r1 red value of pixel 1
     * @param g1 green value of pixel 1
     * @param b1 blue value of pixel 1
     * @param r2 red value of pixel 2
     * @param g2 green value of pixel 2
     * @param b2 blue value of pixel 2
     * @return The value of {@link getDistance} for a centroid and a pixel of these values
     */
    public double testGetDistance(int r1, int g1, int b1, int r2, int g2, int b2){
        return getDistance(new Centroid(r1, g1, b1), new Pixel(0, 0, r2, g2, b2, 255));
    }

    /**
     * Helper class for Posterise filter
     * 
     * Contains a r, g and b value
     */
    static class Centroid {
        int r;
        int g;
        int b;

        /**
         * Constructor
         * @param r red value
         * @param g green value
         * @param b blue value
         */
        public Centroid(int r, int g, int b){
            this.r = r;
            this.g = g;
            this.b = b;
        }
    }

    /**
     * Helper class for Posterise filter
     * 
     * Contains a pixels postition, separated rgba values, and which cluster it belongs to
     */
    static class Pixel {
        int x;
        int y;

        int a;
        int r;
        int g;
        int b;

        int clusterNo;

        /**
         * Constructor
         * @param x x position of the pixel
         * @param y y position of the pixel
         * @param r red value
         * @param g green value
         * @param b blue value
         * @param a alpha value
         */
        public Pixel(int x, int y, int r, int g, int b, int a){
            this.r = r;
            this.g = g;
            this.b = b;
            this.a = a;
            this.x = x;
            this.y = y;
        }
    }
}
