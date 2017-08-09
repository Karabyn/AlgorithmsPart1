package collinear;

import java.util.*;
import java.util.HashMap;

/**
 * Algorithms and Data Structures Part I.
 * Assignment 3.
 * Created by Petro Karabyn on 15-Jul-17.
 */

public class FastCollinearPointsHashMap {

    private final Point[] points;
    private int numOfSegments;

    public FastCollinearPointsHashMap(Point[] points) {
        if (points == null) throw new IllegalArgumentException(); // check for null argument

        for (Point point : points)  if (point == null) throw new IllegalArgumentException(); // check for null points

        for (int i = 0; i < points.length; i++) // check for duplicates
            for (int j = i + 1; j < points.length; j++)
                if (j != i && points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY)
                    throw new IllegalArgumentException();

        this.points = points;
        this.numOfSegments = 0;
    }

    public int numberOfSegments() {
        return numOfSegments;
    }

    private HashMap<ArrayList<Point>, Double> getAllSlopes() {
        HashMap<ArrayList<Point>, Double> slopes = new HashMap<>();
        for (int i = 0; i < points.length; i++) { // iterate through all points
            for (int j = i + 1; j < points.length; j++) { // find slopes between the first point and all the others
                double slopeValue = points[i].slopeTo(points[j]); // calculate slope between two points
                if (slopes.containsValue(slopeValue)) { // there is a point with the same slope already
                    for(Map.Entry<ArrayList<Point>, Double> entry : slopes.entrySet()) {
                        if(Objects.equals(slopeValue, entry.getValue()) && !entry.getKey().contains(points[j])) {
                            entry.getKey().add(points[j]);
                        }
                    }
                }
                else {
                    ArrayList<Point> pair = new ArrayList<>();
                    pair.add(points[i]);
                    pair.add(points[j]);
                    slopes.put(pair, points[i].slopeTo(points[j]));
                }
                // System.out.println(slopes.toString());
            }
            // System.out.println("allSegments: ");
        }
        return slopes;

    }

    public LineSegment[] segments() {
        HashMap<ArrayList<Point>, Double> slopes = getAllSlopes();

        List<Point[]> properSegments = new ArrayList<>();
        // get only endpoints from the sets of all points in segments with 4+ points
        for (ArrayList<Point> allSegments : slopes.keySet()) {
            //System.out.println(allSegments);
            if(allSegments.size() >= 4) {
                allSegments.sort(Comparator.naturalOrder());
                //System.out.println("4+ sorted: " + allSegments);
                Point[] endpoints = {allSegments.get(0), allSegments.get(allSegments.size() - 1)};
                //System.out.println("endpoints: " + endpoints);
                if(!properSegments.contains(endpoints)) {
                    properSegments.add(endpoints);
                }
            }
            //System.out.println();
        }
        System.out.println("Segments");
        LineSegment[] lineSegments = new LineSegment[properSegments.size()];
        for(int i = 0; i < properSegments.size(); i++) {
            lineSegments[i] = new LineSegment(properSegments.get(i)[0], properSegments.get(i)[1]);
            System.out.println(lineSegments[i].toString());
        }
        return lineSegments;
    }

    public static void main(String[] args) {
        Point[] points = {new Point(19000,10000), new Point(18000,10000), new Point(32000,10000), new Point(21000,10000), new Point(1234, 5678), new Point(14000, 10000)}; //new Point(0,0), new Point(4, 4), new Point(1,1), new Point(2, 6), new Point(2,2), new Point(3,3)};
        FastCollinearPointsHashMap fcp = new FastCollinearPointsHashMap(points);
        fcp.segments();
    }
}
