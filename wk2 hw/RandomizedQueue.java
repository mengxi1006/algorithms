/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int N;

    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
        N = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

    // resize the queue
    private void resize(int size) {
        Item[] temp = (Item[]) new Object[size];
        for (int i = 0; i < N; i++) {
            temp[i] = queue[i];
        }
        queue = temp;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("input argument cannot be empty");

        if (queue.length == N) resize(2 * N);

        queue[N++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException();

        int ind = StdRandom.uniform(0, N);

        Item item = queue[ind];

        if (ind != N - 1) {
            queue[ind] = queue[N - 1];
        }
        queue[N - 1] = null;
        N--;

        if (N > 0 && N == queue.length / 4) resize(queue.length / 2);

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new java.util.NoSuchElementException();

        int ind = StdRandom.uniform(0, N);

        return queue[ind];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new IndependentIterator();
    }

    private class IndependentIterator implements Iterator<Item> {

        private Item[] newQueue;
        private int current;

        public IndependentIterator() {
            newQueue = (Item[]) new Object[N];
            current = 0;

            for (int i = 0; i < N; i++) {
                newQueue[i] = queue[i];
            }
            StdRandom.shuffle(newQueue);
        }

        public boolean hasNext() {
            return current < N;
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();

            Item item = newQueue[current];

            current++;
            
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {

    }
}
