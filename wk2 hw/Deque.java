/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        private Item item;
        private Node next;
    }

    private Node first;
    private Node last;
    private int N;

    public Deque() {
        first = null;
        last = null;
        N = 0;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("input argument cannot be empty");

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        N++;
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("input argument cannot be empty");

        // Node oldLast = last;
        Node newNode = new Node();
        newNode.item = item;
        newNode.next = null;
        if (isEmpty()) {
            first = newNode;
        }
        else {
            Node temp = first;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
        N++;
    }

    public Item removeFirst() {
        if (isEmpty()) throw new java.util.NoSuchElementException();

        Item item = first.item;
        first = first.next;
        if (isEmpty()) last = null;
        N--;
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) throw new java.util.NoSuchElementException();

        Item item;

        if (first.next == null) {
            item = first.item;
            first = null;
        }
        else {
            Node temp = first;
            while (temp.next.next != null) {
                temp = temp.next;
            }
            Node lastNode = temp.next;
            item = lastNode.item;
            temp.next = null;
            lastNode = null;
        }

        N--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new FrontToBackIterator();
    }

    private class FrontToBackIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();

            Item item = current.item;

            if (current.next != null) {
                current = current.next;
            }
            else current = null;

            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        Deque<String> text = new Deque<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("of")) text.addFirst(item);
            else if (!text.isEmpty()) StdOut.print(text.removeFirst() + " ");
        }
        StdOut.println("(" + text.size() + " left on stack)");
    }
}
