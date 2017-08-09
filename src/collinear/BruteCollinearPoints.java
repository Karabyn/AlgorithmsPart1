package collinear;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Algorithms and Data Structures Part I.
 * Assignment 3.
 * Created by Petro Karabyn on 15-Jul-17.
 */
public class BruteCollinearPoints {

    private final Point[] points;
    private List<List<Point>> combinations;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException(); // check for null argument

        for (Point point : points)  if (point == null) throw new IllegalArgumentException(); // check for null points

        for (int i = 0; i < points.length; i++) // check for duplicates
            for (int j = i + 1; j < points.length; j++)
                if (j != i && points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY)
                    throw new IllegalArgumentException();

        this.points = points.clone();
    }

    // the number of line segments2
    public int numberOfSegments() {
        return combinations.size();
    }

    private void findSegments() {
        int n = points.length;
        combinations = new ArrayList<>(); // contains all combinations of 4 points.
        int numOfSegments = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int l = k + 1; l < n; l++) {
                        if (points[i].slopeOrder().compare(points[j], points[k]) == 0) {
                            if (points[i].slopeTo(points[k]) == points[i].slopeTo(points[l])) {
                                combinations.add(new ArrayList<>());
                                List<Point> currentCombination = combinations.get(numOfSegments);
                                currentCombination.add(points[i]);
                                currentCombination.add(points[j]);
                                currentCombination.add(points[k]);
                                currentCombination.add(points[l]);
                                currentCombination.sort(Comparator.naturalOrder());
                                numOfSegments += 1;
                            }
                        }
                    }
                }
            }
        }
    }

    public LineSegment[] segments() {
        findSegments();
        LineSegment[] lineSegments = new LineSegment[combinations.size()];
        for (int i = 0; i < combinations.size(); i++) {
            lineSegments[i] = new LineSegment(combinations.get(i).get(0), combinations.get(i).get(3));
        }
        return lineSegments;
    }

    public static void main(String[] args) {

        Point[] points2 =  {new Point(19000,10000), new Point(18000,10000), new Point(32000,10000), new Point(21000,10000), new Point(1234, 5678), new Point(14000, 10000)};
        BruteCollinearPoints brp2 = new BruteCollinearPoints(points2);
        LineSegment[] lineSegments = brp2.segments();
        /*
        System.out.println("Line segments:");
        for (LineSegment lineSegment : lineSegments) {
            System.out.println(lineSegment.toString());
        }
        */
    }
}