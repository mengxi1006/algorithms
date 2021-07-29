/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        // Deque<String> text = new Deque<String>();
        RandomizedQueue<String> textRand = new RandomizedQueue<String>();

        int k = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            textRand.enqueue(StdIn.readString());
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(textRand.dequeue());
        }
    }
}
