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

    Random r = new Random();

    /**
     * The number of colors in the image to find
     */
    private int k; 
    
    /**
     * Construct a posterise filter with a given k value
     */
    public PosteriseFilter (int k){
        this.k = k;
    }

    /**
     * Constrct a posterise filter with the default k value of 10
     */
    public PosteriseFilter (){
        this.k = 10;
    }
    
    public BufferedImage apply(BufferedImage input) {
        ArrayList<Point> points = getPoints(input);
        points = calculateCentroids(points);

        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);  
        
        for (Point point : points) {            
            output.setRGB(point.x, point.y, point.centroid.getRGB());
        }
        
        return output;
    }

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
     * Method to find the k colours that best represent the image
     * @return
     */
    private ArrayList<Point> calculateCentroids(ArrayList<Point> points){
        Color[] centroids = distrubuteInitalCentroids(points);
        assignPixels(points, centroids);
        for (int i = 0; i < 20; i++) {
            assignPixels(points, centroids);
            updateCentroids(points, centroids);   
        }
        
        return points;
    }

    private Color[] distrubuteInitalCentroids(ArrayList<Point> points){
        Random r = new Random();
        Color[] centroids = new Color[k];
        
        for (int i = 0; i < centroids.length; i++) {
            //TODO avoid duplicates
            int val = r.nextInt(points.size());;
            if(points.get(val) != null){
                centroids[i] = points.get(val).value;
            } else {
                System.out.println("point " + val +" is null");
            }            
        }

        return centroids;
    }

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

    private void updateCentroids(ArrayList<Point> points, Color[] centroids){
        for (Color centroid : centroids) {
            centroid = meanOfCluster(points, centroid);
        }
    }

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

        Color newCenter = new Color((int)rSum, (int)gSum, (int)bSum);

        return newCenter;
    }

    /**
     * Method to get the difference between the colors of two pixels, by representing their colors as points in 3d space
     * Adapted from: https://stackoverflow.com/questions/23937825/calculating-the-distance-between-2-points-in-2d-and-3d
     * @param a the color of the first first pixel
     * @param b the color of the seconds second pixel
     * @return
     */
    public double getDistance(Color a, Color b){
        double dx = a.getRed() - b.getRed();
        double dy = a.getGreen() - b.getGreen();
        double dz = a.getBlue() - b.getBlue();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
    
    private void printCentroids(Color[] centroids, ArrayList<Point> points){
        for (Color color : centroids) {
            System.out.print(color + ": ");
            int sum = 0;
            for (Point point : points) {
                if(point.centroid.equals(color)) sum++;
            }
            System.out.println(sum);
        }
    }

    class Point {
        Color value;
        Color centroid;
        int x;
        int y;

        Point(Color value, int x, int y) {
            this.value = value;
            this.centroid = value;
            this.x = x;
            this.y = y;
        }
    }

}
