
import java.util.Comparator;
import java.util.PriorityQueue;

class Patient {
    final String name;
    final int severity;
    final long arrival;

    Patient(String name, int severity, long arrival) {
        this.name = name;
        this.severity = severity;
        this.arrival = arrival;
    }

    @Override
    public String toString() {
        return name + "(S" + severity + ")";
    }
}

public class Triage {

    private long counter = 0;

    // Lower severity number first.
    // For equal severity, earlier arrival first.
    private final PriorityQueue<Patient> queue =
        new PriorityQueue<>(
            Comparator.comparingInt((Patient p) -> p.severity)
                      .thenComparingLong(p -> p.arrival)
        );

    void admit(String name, int severity) {

        if (severity < 1 || severity > 5) {
            System.out.println("Invalid severity for " + name);
            return;
        }

        queue.add(new Patient(name, severity, counter++));
    }

    Patient next() {
        return queue.poll();
    }

    // Display the waiting list in treatment order
    void displayWaitingList() {

        PriorityQueue<Patient> copy =
            new PriorityQueue<>(queue);

        System.out.println("Waiting list in treatment order:");

        while (!copy.isEmpty()) {
            System.out.print(copy.poll() + " ");
        }

        System.out.println();
    }

    public static void main(String[] args) {

        Triage er = new Triage();

        System.out.println("HOSPITAL EMERGENCY TRIAGE");
        System.out.println("--------------------------------");

        // Admit 8 patients
        er.admit("Ravi", 3);
        er.admit("Meena", 1);
        er.admit("John", 3);
        er.admit("Asha", 5);
        er.admit("Kumar", 1);
        er.admit("Lata", 2);
        er.admit("Omar", 3);
        er.admit("Neha", 5);

        // Display the waiting list
        er.displayWaitingList();

        // Treat patients in priority order
        System.out.println("\nPATIENT TREATMENT ORDER:");

        Patient p;

        while ((p = er.next()) != null) {
            System.out.print(p + " ");
        }

        System.out.println("\n\nAll patients processed.");

        // Growth investigation
        System.out.println("\nPERFORMANCE INVESTIGATION");
        System.out.println("--------------------------------");

        int[] sizes = {1000, 5000, 10000};

        for (int size : sizes) {
            benchmark(size);
        }
    }

    static void benchmark(int size) {

        Comparator<Patient> order =
            Comparator.comparingInt((Patient p) -> p.severity)
                      .thenComparingLong(p -> p.arrival);

        // Measure insertion
        PriorityQueue<Patient> addQueue =
            new PriorityQueue<>(order);

        long startAdd = System.nanoTime();

        for (int i = 0; i < size; i++) {
            addQueue.add(
                new Patient("Patient" + i, (i % 5) + 1, i)
            );
        }

        long endAdd = System.nanoTime();

        // Prepare a separate queue for removal measurement
        PriorityQueue<Patient> removeQueue =
            new PriorityQueue<>(order);

        for (int i = 0; i < size; i++) {
            removeQueue.add(
                new Patient("Patient" + i, (i % 5) + 1, i)
            );
        }

        long startRemove = System.nanoTime();

        while (!removeQueue.isEmpty()) {
            removeQueue.poll();
        }

        long endRemove = System.nanoTime();

        System.out.println("\nQueue size: " + size);
        System.out.println(
            "Insertion time: " + (endAdd - startAdd) + " ns"
        );
        System.out.println(
            "Removal time: " + (endRemove - startRemove) + " ns"
        );
    }
}

