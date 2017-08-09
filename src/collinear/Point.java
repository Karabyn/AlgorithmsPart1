package collinear; /**
 * Algorithms and Data Structures Part I.
 * Assignment 3.
 * Created by Petro Karabyn on 15-Jul-17.
 */

/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
         if (that.y == this.y && that.x != this.x) return 0.0; // horizontal
         if (that.y != this.y && that.x == this.x) return Double.POSITIVE_INFINITY; // vertical
         if (that.y == this.y && that.x == this.x) return Double.NEGATIVE_INFINITY; // equal
         return (double) (that.y - this.y) / (that.x - this.x);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
     public int compareTo(Point that) {
         if (this.y < that.y) return -1;
         if (this.y > that.y) return +1;
         if (this.x < that.x) return -1;
         if (this.x > that.x) return +1;
         return 0;
     }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     * the point (x1, y1) is less than the point (x2, y2) if and only if
     * the slope (y1 − y0) / (x1 − x0) is less than the slope (y2 − y0) / (x2 − x0)
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new SlopeOrderComparator();

        /* ---------------------------------------
        implementation with lambda expression

        return (o1, o2) -> {
            if (slopeTo(o1) > slopeTo(o2)) return 1;
            if (slopeTo(o1) < slopeTo(o2)) return -1;
            return 0; // slopeTo(o1) == slopeTo(o2)
        };
        -----------------------------------------*/

        /* ---------------------------------------
         implementation with anonymous class
         return new Comparator<Point>() {
            public int compare(Point o1, Point o2) {
               if (slopeTo(o1) > slopeTo(o2)) return 1;
               if (slopeTo(o1) < slopeTo(o2)) return -1;
               return 0; // slopeTo(o1) == slopeTo(o2)
            }

        };
        -------------------------------------------*/

    }

    private class SlopeOrderComparator implements Comparator<Point> {

        @Override
        public int compare(Point o1, Point o2) {
            if (slopeTo(o1) > slopeTo(o2)) return 1;
            if (slopeTo(o1) < slopeTo(o2)) return -1;
            return 0; // slopeTo(o1) == slopeTo(o2)
        }
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /*
        Point p0 = new Point(0, 0);
        Point p1 = new Point(1, 0);
        Point p2 = new Point(1, 1);
        Point p3 = new Point(0, 1);
        Point p4 = new Point(0, -1);

        // test slopeTo()
        System.out.println("Testing slopeTo()");
        System.out.println(p0.slopeTo(p2)); // different
        System.out.println(p0.slopeTo(p0)); // equal
        System.out.println(p0.slopeTo(p1)); // horizontal
        System.out.println(p3.slopeTo(p4)); // vertical
        */
    }
}