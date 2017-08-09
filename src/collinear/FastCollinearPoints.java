package collinear;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Algorithms and Data Structures Part I.
 * Assignment 3.
 * Created by Petro Karabyn on 15-Jul-17.
 */

public class FastCollinearPoints {

    private final Point[] points;
    private LinkedList<LinkedList<Point>> properSegments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException(); // check for null argument

        for (Point point : points)  if (point == null) throw new IllegalArgumentException(); // check for null points

        for (int i = 0; i < points.length; i++) // check for duplicates
            for (int j = i + 1; j < points.length; j++)
                if (j != i && points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY)
                    throw new IllegalArgumentException();

        this.points = points.clone();
    }

    public int numberOfSegments() {
        return properSegments.size();
    }

    private void findSegments() {
        Arrays.sort(points);
        properSegments = new LinkedList<>();

        for (Point point : points) {
            Point[] slopeOrderPoints = points.clone();
            Arrays.sort(slopeOrderPoints, point.slopeOrder());
            LinkedList<Point> segment = new LinkedList<>();
            segment.add(point); // add point i as origin

            // index 0 in slope order sorted is always the origin point (slope negative infinity)
            // this loop compares i to j and i to j+1. j only needs to loop through N-2
            for (int j = 1; j < points.length - 1; j++) {

                Point slopePoint = slopeOrderPoints[j];
                Point nextSlopePoint = slopeOrderPoints[j+1];

                double slope = point.slopeTo(slopePoint);
                double nextSlope = point.slopeTo(nextSlopePoint);

                if (slope == nextSlope) {
                    if (segment.get(segment.size() - 1) != slopePoint) {
                        segment.add(slopePoint);
                    }
                    segment.add(nextSlopePoint);
                }

                // clear segment if no match (end of segment or loop)
                if (slope != nextSlope || j == points.length - 2) {
                    // first output segment if it is large enough
                    if (segment.size() > 3) {
                        Point first = segment.removeFirst();
                        Point second = segment.removeFirst();
                        Point last = segment.removeLast();
                        if (first.compareTo(second) < 0) {
                            LinkedList<Point> endpoints = new LinkedList<>();
                            endpoints.add(first);
                            endpoints.add(last);
                            properSegments.add(endpoints);
                        }
                    }
                    segment.clear();
                    segment.add(point);
                }
            }
        }
    }


    public LineSegment[] segments() {
        findSegments();
        LineSegment[] lineSegments = new LineSegment[properSegments.size()];
        for (int i = 0; i < properSegments.size(); i++) {
            lineSegments[i] = new LineSegment(properSegments.get(i).get(0), properSegments.get(i).get(1));
            // System.out.println(lineSegments[i].toString());
        }
        return lineSegments;
    }

    public static void main(String[] args) {

        Point[] points = {new Point(19000,10000), new Point(18000,10000), new Point(32000,10000), new Point(21000,10000), new Point(1234, 5678), new Point(14000, 10000),
                new Point(0, 0), new Point(1, 1), new Point(2, 2), new Point(3, 3), new Point(4, 4)};
        FastCollinearPoints fcp = new FastCollinearPoints(points);
        fcp.segments();
    }
}
