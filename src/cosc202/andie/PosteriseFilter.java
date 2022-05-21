package cosc202.andie;

import java.awt.image.*;
import java.awt.Color;
import java.util.*;

/**
 * <p>
 * ImageOperation to apply a Mean (simple blur) filter.
 * </p>
 * 
 * <p>
 * A Mean reduced the number of colours in an image by replacing each pixel 
 * with a representative colour, found using a k means clustering of the colours in the image
 * </p>
 *  
 */
public class PosteriseFilter implements ImageOperation, java.io.Serializable {

    /**
     * An instance of the Random class
     */
    Random r = new Random();

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
     * Applies a posterise filter to an image using the supplied k value
     * @param input the image to apply the filter to
     * @return the resulting posterised image
     */
    public BufferedImage apply(BufferedImage input) {
        ArrayList<Point> points = getPoints(input);
        points = calculateCentroids(points);

        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);  
        
        for (Point point : points) {            
            output.setRGB(point.x, point.y, point.centroid.getRGB());
        }
        
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
                points.add(new Point(new Color(input.getRGB(x,y)), x, y));
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
        Color[] centroids = new Color[k];
        distrubuteInitalCentroids(points, centroids);
        assignPixels(points, centroids);
        for (int i = 0; i < 50; i++) {
            assignPixels(points, centroids);
            updateCentroids(points, centroids);   
        }
        
        return points;
    }
    
    /**
     * A method to place the centroids in random locations
     * @param points The complete set of points in the image
     * @param centroids the cluster of what to find the mean of
     */
    private void distrubuteInitalCentroids(ArrayList<Point> points, Color[] centroids){
        Random r = new Random();
        
        boolean[] used = new boolean[points.size()];
        for (int i = 0; i < centroids.length; i++) {
            int val;
            do{
                val = r.nextInt(points.size());
            } while(used[val]);
            centroids[i] = points.get(val).value;
        }
    }

    /**
     * Method to assign pixels to thier nearest centroid
     * @param points The complete set of points
     * @param centroids The list of centroids
     */
    private void assignPixels(ArrayList<Point> points, Color[] centroids){
        for (Point point : points) {
            double minDist = Double.MAX_VALUE;
            for (Color centroid : centroids) {
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
    private void updateCentroids(ArrayList<Point> points, Color[] centroids){
        for (Color centroid : centroids) {
            centroid = meanOfCluster(points, centroid);
        }
    }

    /**
     * Method to find the mean of a cluster of colors
     * @param points The complete set of points
     * @param centroid The cluster you want to find the mean of
     * @return returns the mean color value of a cluster
     */
    private Color meanOfCluster(ArrayList<Point> points, Color centroid){
        double rSum = 0;
        double gSum = 0;
        double bSum = 0;
        double numPixels = 0;
        for (Point point : points) {
            if(point.centroid.equals(centroid)){
                rSum += point.value.getRed();
                gSum += point.value.getGreen();
                bSum += point.value.getBlue();
            }
            numPixels ++;
        }

        rSum = rSum/numPixels;
        gSum = gSum/numPixels;
        bSum = bSum/numPixels;

        return new Color((int)rSum, (int)gSum, (int)bSum);
    }

    /**
     * Method to get the difference between the colors of two pixels, by representing their colors as points in 3d space
     * Adapted from: https://stackoverflow.com/questions/23937825/calculating-the-distance-between-2-points-in-2d-and-3d
     * @param a the color of the first first pixel
     * @param b the color of the seconds second pixel
     * @return returns the difference between the colors of two pixels
     */
    public double getDistance(Color a, Color b){
        double dx = a.getRed() - b.getRed();
        double dy = a.getGreen() - b.getGreen();
        double dz = a.getBlue() - b.getBlue();
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
        Color value;
        /** A pixel's representative */
        Color centroid; 
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
        Point(Color value, int x, int y) {
            this.value = value;
            this.centroid = value;
            this.x = x;
            this.y = y;
        }
    }

}
