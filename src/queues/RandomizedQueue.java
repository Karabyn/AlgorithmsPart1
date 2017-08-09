package queues;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Algorithms and Data Structures Part I.
 * Assignment 2.
 * Created by Petro Karabyn on 15-Jul-17.
 */

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] q;
    private int head;
    private int tail;
    private int numberOfItems;


    // construct an empty randomized queue
    public RandomizedQueue() {
        q = (Item[]) new Object[2];
        head = 0;
        tail = 0;
        numberOfItems = 0;
    }

    // is the queue empty?
    public boolean isEmpty(){
        return numberOfItems == 0;
    }

    // return the number of items on the queue
    public int size() {
        return numberOfItems;
    }

    private void resize(int length) {
        Item[] tempQ = q.clone();
        q = (Item[]) new Object[length];
        int index = 0;
        for (Item i : tempQ) {
            if (i != null) {
                q[index] = i;
                index += 1;
            }
        }
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        // tail of the queue is out of bounds. Need to resize.
        if (tail == q.length) resize(q.length * 2);
        q[tail] = item; // add an item to the tail of the queue
        numberOfItems += 1;
        tail += 1; // move tail 1 position further
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int index = StdRandom.uniform(head, tail); // index of an item to delete
        Item item = q[index];
        q[index] = q[tail - 1];
        q[tail - 1] = null;
        tail -= 1;
        numberOfItems -= 1;
        if (numberOfItems > 0 && numberOfItems == q.length/4) {
            resize(q.length / 2);
        }
        return item;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        return  q[StdRandom.uniform(head, tail)];
    }

    private void toStr() {
        StdOut.print("queue: ");
        for (Item i : q) {
            StdOut.print(i + ", ");
        }
        StdOut.println();
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator()  {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {

        private int current = head;
        private Item[] shuffledQ = (Item[]) new Object[tail];

        public QueueIterator() {
            for (int i = head; i < tail; i++) {
                shuffledQ[i] = q[i];
            }
            StdRandom.shuffle(shuffledQ);
        }

        public boolean hasNext() {
            return current < tail;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            current += 1;
            return shuffledQ[current - 1];
        }
    }

    // unit testing (optional)
    public static void main(String[] args) {
        /*
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();

        rq.toStr();
        StdOut.println("head: " + rq.head);
        StdOut.println("tail: " + rq.tail);
        StdOut.println();

        rq.enqueue(0);
        rq.toStr();
        StdOut.println("head: " + rq.head);
        StdOut.println("tail: " + rq.tail);
        StdOut.println();

        rq.enqueue(1);
        rq.toStr();
        StdOut.println("head: " + rq.head);
        StdOut.println("tail: " + rq.tail);
        StdOut.println();

        StdOut.println("deque: " + rq.dequeue());
        rq.toStr();
        StdOut.println("head: " + rq.head);
        StdOut.println("tail: " + rq.tail);
        StdOut.println();

        StdOut.println("deque: " + rq.dequeue());
        rq.toStr();
        StdOut.println("head: " + rq.head);
        StdOut.println("tail: " + rq.tail);
        StdOut.println();

        rq.enqueue(2);
        rq.toStr();
        rq.enqueue(3);
        rq.toStr();
        rq.enqueue(4);
        rq.toStr();
        StdOut.println("deque: " + rq.dequeue());
        rq.toStr();
        StdOut.println("deque: " + rq.dequeue());
        rq.toStr();

        StdOut.println(rq.sample());
        StdOut.println(rq.sample());
        StdOut.println(rq.sample());
        StdOut.println(rq.sample());

        rq.enqueue(10);
        rq.enqueue(11);
        rq.enqueue(12);
        rq.enqueue(13);
        rq.enqueue(14);
        rq.toStr();
        Iterator iterator = rq.iterator();
        StdOut.println(iterator.next());
        StdOut.println(iterator.next());
        StdOut.println(iterator.next());
        StdOut.println(iterator.next());
        StdOut.println(iterator.next());
        StdOut.println(iterator.next());
        */
    }

}
