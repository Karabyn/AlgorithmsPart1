package kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

/**
 * Algorithms and Data Structures Part I.
 * Assignment 5.
 * Created by Petro Karabyn on 06-Aug-17.
 */

public class KdTree {

    private Node root;
    private int size;

    private static class Node {
        private final Point2D point;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node left;        // the left/bottom subtree
        private Node right;        // the right/top subtree

        public Node(Point2D point) {
            this.point = point;
        }

    }

    public KdTree() {
        size = 0;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        put(root, p, 1);
    }

    private Node put(Node parent, Point2D point, int height) {
        // CASE 1: empty tree
        if (root == null) {
            root = new Node(point);
            root.rect = new RectHV(0, 0, 1, 1);
            size += 1;
            return root;
        }

        // CASE 2: not empty tree
            // case 2.1: empty node, add new leaf
        if (parent == null) {
            size += 1;
            return new Node(point);
        }
            // case 2.2: not empty node, prune further down
                // case 2.2.1: compare by x coordinates
        if (height % 2 != 0) {  // x coordinates
           if (point.x() < parent.point.x()) { // smaller x
               parent.left = put(parent.left, point, height + 1);
               if (parent.left.rect == null) {
                   parent.left.rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.point.x(), parent.rect.ymax());
               }
           }
           else if (point.x() > parent.point.x() || (point.x() == parent.point.x() && point.y() != parent.point.y())) { // larger or equal x
               parent.right = put(parent.right, point, height + 1);
               if (parent.right.rect == null) {
                   parent.right.rect = new RectHV(parent.point.x(), parent.rect.ymin(), parent.rect.xmax(), parent.rect.ymax());
               }
           }
        }
                // case 2.2.2: compare by y.coordinates
        else { // y coordinates
            if (point.y() < parent.point.y()) { // smaller y
                parent.left = put(parent.left, point, height + 1);
                if (parent.left.rect == null) {
                    parent.left.rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.rect.xmax(), parent.point.y());
                }
            }
            else if (point.y() > parent.point.y() || (point.y() == parent.point.y() && point.x() != parent.point.x())) { // larger or equal y
                parent.right = put(parent.right, point, height + 1);
                if (parent.right.rect == null) {
                    parent.right.rect = new RectHV(parent.rect.xmin(), parent.point.y(), parent.rect.xmax(), parent.rect.ymax());
                }

            }
        }
        // return unchanged parent if the same node is already in a tree
        return parent;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return contains(root, p, 1);
    }

    private boolean contains(Node x, Point2D p, int height) {
        if (x == null) return false;
        if (x.point.equals(p)) return true;

        boolean contains = false;
        if (height % 2 != 0) {  // x coordinates
            if (p.x() < x.point.x()) // smaller x
                contains = contains(x.left, p, height + 1);
            else if (p.x() > x.point.x() || (p.x() == x.point.x() && p.y() != x.point.y())) // larger or equal x
                contains = contains(x.right, p, height + 1);

        }
        else { // y coordinates
            if (p.y() < x.point.y()) // smaller x
                contains = contains(x.left, p, height + 1);
            else if (p.y() > x.point.y() || (p.y() == x.point.y() && p.x() != x.point.x())) // larger or equal x
                contains = contains(x.right, p, height + 1);
        }
        return contains;
    }

    public void draw() {
        draw(root, 1);
    }

    private void draw(Node parent, int height) {
        if (parent != null) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            parent.point.draw();
            StdDraw.setPenRadius();
            if (height % 2 != 0) {  // x`s
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(parent.point.x(), parent.rect.ymin(), parent.point.x(), parent.rect.ymax());
            }
            else { // y's
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(parent.rect.xmin(), parent.point.y(), parent.rect.xmax(), parent.point.y());
            }
            draw(parent.left, height + 1);
            draw(parent.right, height + 1);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        return range(root, rect, new ArrayList<>());
    }

    private List<Point2D> range(Node node, RectHV rect, List<Point2D> list) {
        List<Point2D> pointsInRange = list;
        if (node != null) {
            if (node.rect.intersects(rect)) {
                if (rect.contains(node.point)) {
                    pointsInRange.add(node.point);
                }
                pointsInRange = range(node.left, rect, pointsInRange);
                pointsInRange = range(node.right, rect, pointsInRange);
            }
        }
        return pointsInRange;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (!isEmpty()) {
            return nearest(root, p, root.point);
        }
        return null;
    }

    private Point2D nearest(Node node, Point2D queryPoint, Point2D closest) {
        if (node != null) {
            double currentClosestDistance = queryPoint.distanceSquaredTo(closest);
            if (node.rect.distanceSquaredTo(queryPoint) < currentClosestDistance) {
                if (node.point.distanceSquaredTo(queryPoint) < currentClosestDistance) {
                    closest = node.point;
                }
                closest = nearest(node.left, queryPoint, closest);
                closest = nearest(node.right, queryPoint, closest);
            }
        }
        return closest;
    }

    public static void main(String[] args) {

        KdTree kdTree = new KdTree();
        System.out.println(kdTree.contains(new Point2D(0, 0)));
        kdTree.insert(new Point2D(0, 0)); // top
        kdTree.insert(new Point2D(1, 1)); // right
        kdTree.insert(new Point2D(-1, -1)); // left
        kdTree.insert(new Point2D(0.5, 0.5)); // right, left
        System.out.println(kdTree.contains(new Point2D(0.1, 0.2)));
        //System.out.println("Range: ");
        //List<Point2D> list = (List<Point2D>) kdTree.range(new RectHV(0.26, 0.26, 0.77, 0.78));
        //list.forEach(System.out::println);
        System.out.println("nearest: ");
        System.out.println(kdTree.nearest(new Point2D(0.15, 0.1)));



    }

}
