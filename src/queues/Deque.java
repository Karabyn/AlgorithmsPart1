package queues;

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Algorithms and Data Structures Part I.
 * Assignment 2.
 * Created by Petro Karabyn on 14-Jul-17.
 */

/**
 * Deque data structure
 * @param <Item> object of any type
 */
public class Deque<Item> implements Iterable<Item> {

    private int n;          // size of the deque
    private Node first;     // element first in the deque
    private Node last;      // element last in the deque

    /**
     * Inner class for objects in the deque.
     * Stores an item and a link to the next and previous nodes.
     */
    private class Node {
        private Item item;
        private Node next;
        private Node previous;
    }

    /**
     * construct an empty deque
     */
    public Deque() {
        n = 0;
        first = null;
        last = null;
    }

    /**
     * is the deque empty?
     * @return true if the deque is empty
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * @return n: the number of items on the deque
     */
    public int size() {
        return n;
    }

    /**
     * add the item to the front
     * @param item object of any type
     */
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node oldFirst = first; // copy the first item
        first = new Node(); // create a new node
        first.item = item; // save item to the node
        first.next = oldFirst; // make a reference to the second item (oldFirst)
        if (n != 0)
            oldFirst.previous = first; // make a second item point to the first one.
        if (n == 0)
            last = first;
        n += 1; // update the size of the deque
    }

    /**
     * add the item to the end
     * @param item object of any type
     */
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.previous = oldLast;
        if (n != 0)
            oldLast.next = last;
        if (n == 0)
            first = last;
        n += 1;
    }

    /**
     * remove and return the item from the front
     * @return
     */
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = first.item; // copy item in the front.
        first = first.next; // update the first item in the deque. Garbage collector will take care of the old one.
        n -= 1; // update the size of the deque
        if (n != 0)
            first.previous = null;
        if (n == 0) {
            last = null;
        }
        return item; // return the item
    }

    /**
     * remove and return the item from the end
     * @return
     */
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = last.item;
        last = last.previous;
        n -= 1;
        if (n != 0)
            last.next = null;
        if (n == 0) {
            first = null;
        }
        return item;
    }

    /**
     * return an iterator over items in order from front to end
     * @return an object of a new iterator.
     */
    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    /**
     * Implementation of a Deque iterator
     */
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (current == null) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    /**
     * unit testing (optional)
     * @param args
     */
    public static void main(String[] args) {
        /*
        Deque<Integer> deque = new Deque<>();
        StdOut.printf("isEpmpty()---Expected: true---Result: %s\n", deque.isEmpty());
        deque.addFirst(3);
        StdOut.printf("isEpmpty()---Expected: false---Result: %s\n", deque.isEmpty());
        deque.addFirst(2);
        deque.addFirst(1);
        deque.addFirst(0);
        Iterator<Integer> iterator = deque.iterator();
        StdOut.println(iterator.next());
        StdOut.println(iterator.next());
        StdOut.println(iterator.next());
        StdOut.println(iterator.next());

        StdOut.println();
        Deque<Integer> deque2 = new Deque<>();
        deque2.addFirst(0);
        deque2.addLast(1);
        deque2.addLast(2);
        deque2.removeFirst();
        deque2.addLast(3);
        deque2.removeFirst();
        deque2.addFirst(-1);
        deque2.removeLast();
        deque2.removeLast();
        deque2.removeLast();
        deque2.addLast(4);
        deque2.addLast(5);
        deque2.removeFirst();
        // deque2.removeFirst();
        // deque2.removeLast();
        Iterator<Integer> iterator2 = deque2.iterator();
        StdOut.println(iterator2.next());

        StdOut.println();
        Deque<Integer> deque3 = new Deque<>();
        deque3.addLast(3);
        deque3.addLast(2);
        deque3.addLast(1);
        deque3.removeFirst();
        deque3.removeFirst();
        //deque3.removeFirst();
        //deque3.removeFirst();
        //deque3.removeFirst();
        Iterator<Integer> iterator3 = deque3.iterator();
        StdOut.println(iterator3.next());
        // StdOut.println(iterator3.next());
        // StdOut.println(iterator3.next());
        */
    }

}
