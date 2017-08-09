package kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Algorithms and Data Structures Part I.
 * Assignment 5.
 * Created by Petro Karabyn on 05-Aug-17.
 */

public class PointSET {

    private final TreeSet<Point2D> treeSet;

    public PointSET() {
        treeSet = new TreeSet<Point2D>();
    }

    public boolean isEmpty() {
        return treeSet.isEmpty();
    }

    public int size() {
        return treeSet.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        treeSet.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return treeSet.contains(p);
    }

    public void draw() {
        treeSet.forEach(Point2D::draw);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        List<Point2D> pointInRange = new ArrayList<>();
        for (Point2D point : treeSet) {
            if (rect.contains(point)) {
                pointInRange.add(point);
            }
        }
        return pointInRange;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        Point2D nearest;
        if (!isEmpty()) {
            nearest = treeSet.first();
            double minDistance = nearest.distanceSquaredTo(p);
            for (Point2D point : treeSet) {
                double thisDistance = point.distanceSquaredTo(p);
                if (thisDistance < minDistance) {
                    minDistance = thisDistance;
                    nearest = point;
                }
            }
            return nearest;
        }
        return null;
    }

    public static void main(String[] args) {
        /*
        PointSET pointSET = new PointSET();
        pointSET.insert(new Point2D(0, 0));
        pointSET.insert(new Point2D(-5, 0));
        pointSET.insert(new Point2D(10, 0));
        pointSET.insert(new Point2D(1, 1));
        pointSET.insert(new Point2D(5, 6));
        pointSET.insert(new Point2D(8, 9));
        pointSET.insert(new Point2D(0.5, 0.5));
        pointSET.insert(new Point2D(10, 10));
        System.out.println(pointSET.treeSet.size());
        System.out.println(pointSET.contains(new Point2D(0, 0)));
        pointSET.draw();
        Iterable set = pointSET.range(new RectHV(-0.5, -0.5, 0.4, 0.4));
        set.forEach(System.out::println);
        System.out.println(pointSET.nearest(new Point2D(5, 7)));
        */

    }
}
