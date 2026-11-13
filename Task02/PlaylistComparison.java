
import java.util.*;

public class PlaylistComparison {

    static volatile int result;

    static void testArrayList(int size) {

        ArrayList<Integer> list = new ArrayList<>();

        long start = System.nanoTime();

        // Add elements at the end
        for (int i = 0; i < size; i++) {
            list.add(i);
        }

        long addTime = System.nanoTime() - start;

        // Insert at the front
        start = System.nanoTime();

        for (int i = 0; i < 100; i++) {
            list.add(0, i);
        }

        long insertTime = System.nanoTime() - start;

        // Remove from the front
        start = System.nanoTime();

        for (int i = 0; i < 100; i++) {
            list.remove(0);
        }

        long removeTime = System.nanoTime() - start;

        // Access K-th element
        start = System.nanoTime();

        int sum = 0;

        for (int i = 0; i < 10000; i++) {
            sum += list.get(i % list.size());
        }

        long accessTime = System.nanoTime() - start;
        result = sum;

        // Iterate through the list
        start = System.nanoTime();

        sum = 0;

        for (int value : list) {
            sum += value;
        }

        long iterateTime = System.nanoTime() - start;
        result = sum;

        System.out.println("ArrayList:");
        System.out.println("Add: " + addTime / 1000000.0 + " ms");
        System.out.println("Insert front: " + insertTime / 1000000.0 + " ms");
        System.out.println("Remove front: " + removeTime / 1000000.0 + " ms");
        System.out.println("Access: " + accessTime / 1000000.0 + " ms");
        System.out.println("Iteration: " + iterateTime / 1000000.0 + " ms");
    }

    static void testLinkedList(int size) {

        LinkedList<Integer> list = new LinkedList<>();

        long start = System.nanoTime();

        // Add elements at the end
        for (int i = 0; i < size; i++) {
            list.add(i);
        }

        long addTime = System.nanoTime() - start;

        // Insert at the front
        start = System.nanoTime();

        for (int i = 0; i < 100; i++) {
            list.addFirst(i);
        }

        long insertTime = System.nanoTime() - start;

        // Remove from the front
        start = System.nanoTime();

        for (int i = 0; i < 100; i++) {
            list.removeFirst();
        }

        long removeTime = System.nanoTime() - start;

        // Access K-th element
        start = System.nanoTime();

        int sum = 0;

        for (int i = 0; i < 10000; i++) {
            sum += list.get(i % list.size());
        }

        long accessTime = System.nanoTime() - start;
        result = sum;

        // Iterate through the list
        start = System.nanoTime();

        sum = 0;

        for (int value : list) {
            sum += value;
        }

        long iterateTime = System.nanoTime() - start;
        result = sum;

        System.out.println("LinkedList:");
        System.out.println("Add: " + addTime / 1000000.0 + " ms");
        System.out.println("Insert front: " + insertTime / 1000000.0 + " ms");
        System.out.println("Remove front: " + removeTime / 1000000.0 + " ms");
        System.out.println("Access: " + accessTime / 1000000.0 + " ms");
        System.out.println("Iteration: " + iterateTime / 1000000.0 + " ms");
    }

    static void testArrayDeque(int size) {

        ArrayDeque<Integer> queue = new ArrayDeque<>();

        long start = System.nanoTime();

        for (int i = 0; i < size; i++) {
            queue.addLast(i);
        }

        long addTime = System.nanoTime() - start;

        start = System.nanoTime();

        for (int i = 0; i < 100; i++) {
            queue.addFirst(i);
        }

        long frontAddTime = System.nanoTime() - start;

        start = System.nanoTime();

        for (int i = 0; i < 100; i++) {
            queue.removeFirst();
        }

        long removeTime = System.nanoTime() - start;

        System.out.println("ArrayDeque (Up Next Queue):");
        System.out.println("Add at end: " + addTime / 1000000.0 + " ms");
        System.out.println("Add at front: " + frontAddTime / 1000000.0 + " ms");
        System.out.println("Remove from front: " + removeTime / 1000000.0 + " ms");
    }

    public static void main(String[] args) {

        int[] sizes = {1000, 5000, 10000};

        for (int size : sizes) {

            System.out.println("\n================================");
            System.out.println("PLAYLIST SIZE: " + size);
            System.out.println("================================");

            testArrayList(size);
            System.out.println();

            testLinkedList(size);
            System.out.println();

            testArrayDeque(size);
        }

        System.out.println("\nComparison completed.");
    }
}

