package cosc202.andie;

import java.awt.image.*;
import java.util.*;

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
    static final Random R = new Random(1234);

    /**
     * The number of colors in the image to find
     */
    private int k; 
    
    /**
     * Construct a posterise filter with a given k value
     * @param k the number of colors in the image to find
     */
    public PosteriseFilter (int k){
        this.k = k;
    }

    /**
     * Construct a posterise filter with the default k value of 10
     */
    public PosteriseFilter (){
        this.k = 10;
    }

    /**
     * Create an argbClass object for efficient colour control
     */
    private RGB argbClass;

    /**
     * Applies a posterise filter to an image using the supplied k value
     * @param input the image to apply the filter to
     * @return the resulting posterised image
     */
    public BufferedImage apply(BufferedImage input) {
        argbClass = new RGB(input);

        ArrayList<Point> points = getPoints(input);
        points = calculateCentroids(points);
   
        for (Point point : points) {          
            argbClass.setRGB(point.x, point.y, (point.centroid & 0x00FF0000) >> 16, (point.centroid & 0x0000FF00) >> 8, (point.centroid & 0x000000FF), 255);  
            //output.setRGB(point.x, point.y, point.centroid.getRGB());
        }
        BufferedImage output = new BufferedImage(input.getColorModel(), argbClass.getRaster(), input.isAlphaPremultiplied(), null);
        return output;
    }

    /**
     * Splits a BufferedImage into a list of points. Each with an x, y coordinate, a color, and a representative centroid
     * @param input the input image filter is to be applied to
     * @return returns an array of the image pixels' value
     */
    private ArrayList<Point> getPoints(BufferedImage input){
        ArrayList<Point> points = new ArrayList<>();
        for (int x = 0; x < input.getWidth(); x++) {
            for (int y = 0; y < input.getHeight(); y++) {
                points.add(new Point(argbClass.getRGB(x, y), x, y));
            }
        }
        return points;
    }

    /**
     * Method to find the k colours (centroids) that best represent the image
     * @param points The set of points that represents your image
     * @return An arraylist of the representative colors
     */
    private ArrayList<Point> calculateCentroids(ArrayList<Point> points){
        int[] centroids = distrubuteInitalCentroids(points);
        assignPixels(points, centroids);
        for (int i = 0; i < 5; i++) {
            assignPixels(points, centroids);
            updateCentroids(points, centroids);   
        }
        
        return points;
    }
    
    /**
     * A method to place the centroids in random locations
     * @param points The complete set of points in the image
     */
    private int[] distrubuteInitalCentroids(ArrayList<Point> points){
        int[] centroids = new int[k];
        for (int i = 0; i < k; i++) {
            int val;
            val = points.get(R.nextInt(points.size())).value;
            centroids[i] = val;
        }
        return centroids;
    }

    /**
     * Method to assign pixels to their nearest centroid
     * @param points The complete set of points
     * @param centroids The list of centroids
     */
    private void assignPixels(ArrayList<Point> points, int[] centroids){
        for (Point point : points) {
            double minDist = Double.MAX_VALUE;
            for (int centroid : centroids) {
                double dist = getDistance(point.value, centroid);
                if(dist<minDist) {
                    minDist = dist;
                    point.centroid = centroid;         
                }
            }
        }
    }

    /**
     * Method which moves each centroid to the center of its cluster
     * @param points The complete set of points
     * @param centroids The current list of centroids
     */
    private void updateCentroids(ArrayList<Point> points, int[] centroids){
        for (int centroid : centroids) {
            centroid = meanOfCluster(points, centroid);
        }
    }

    /**
     * Method to find the mean of a cluster of colors
     * @param points The complete set of points
     * @param centroid The cluster you want to find the mean of
     * @return returns the mean color value of a cluster
     */
    private int meanOfCluster(ArrayList<Point> points, int centroid){
        int rSum = 0;
        int gSum = 0;
        int bSum = 0;
        int numPixels = 0;
        for (Point point : points) {
            if(point.centroid == centroid){
                rSum += (point.value & 0x00FF0000) >> 16;
                gSum += (point.value & 0x0000FF00) >> 8;
                bSum += (point.value & 0x000000FF);
            }
            numPixels ++;
        }

        rSum = rSum/numPixels;
        gSum = gSum/numPixels;
        bSum = bSum/numPixels;

        return (rSum << 16) | (gSum << 8) | bSum;
    }

    /**
     * Method to get the difference between the colors of two pixels, by representing their colors as points in 3d space
     * Adapted from: https://stackoverflow.com/questions/23937825/calculating-the-distance-between-2-points-in-2d-and-3d
     * @param a the color of the first first pixel
     * @param b the color of the seconds second pixel
     * @return returns the difference between the colors of two pixels
     */
    public double getDistance(int a, int b){
        double dx = ((a & 0x00FF0000) >> 16) - ((b & 0x00FF0000) >> 16);
        double dy = ((a & 0x0000FF00) >> 8) - ((b & 0x0000FF00) >> 8);
        double dz = (a & 0x000000FF) - (b & 0x000000FF);
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /**
     * A helper class for the posterise filter.
     * 
     * <p>
     * Each point represents a pixel on the image. 
     * Its color, it's representative centroid, and it position
     * </p>
     */
    class Point {
        /** The color value of a pixel */
        int value;
        /** A pixel's representative */
        int centroid; 
        /** X co-ordinate */
        int x; 
        /** Y co-ordinate */
        int y; 

        /**
         * Constructs a point with a position and a color, 
         * and an initial centroid that matches its color
         * @param value The colour of a pixel
         * @param x The x co-ordinate of the pixel
         * @param y The y co-ordinate of the pixel
         */
        Point(int value, int x, int y) {
            this.value = value;
            this.centroid = value;
            this.x = x;
            this.y = y;
        }
    }

}
