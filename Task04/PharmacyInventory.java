
import java.util.*;

public class PharmacyInventory {

    static final int LOOKUPS = 100000;
    static volatile long result;

    public static void main(String[] args) {

        int[] sizes = {1000, 5000, 10000};

        System.out.println("HOSPITAL PHARMACY INVENTORY");
        System.out.println("---------------------------------------------");

        for (int size : sizes) {

            Map<String, Integer> hashMap = new HashMap<>();
            Map<String, Integer> treeMap = new TreeMap<>();
            Map<String, Integer> linkedMap = new LinkedHashMap<>();

            // Insert medicine records into all three maps
            for (int i = 1; i <= size; i++) {
                String code = String.format("MED%05d", i);
                int stock = (i * 7) % 200;

                hashMap.put(code, stock);
                treeMap.put(code, stock);
                linkedMap.put(code, stock);
            }

            // Measure 100,000 medicine lookups
            long hashTime = measureLookup(hashMap, size);
            long treeTime = measureLookup(treeMap, size);
            long linkedTime = measureLookup(linkedMap, size);

            // Measure time to create inventory reports
            long hashReport = measureReport(hashMap);
            long treeReport = measureReport(treeMap);
            long linkedReport = measureReport(linkedMap);

            System.out.println("\nNumber of medicines: " + size);
            System.out.println("Lookup time for " + LOOKUPS + " searches:");
            System.out.println("HashMap       : " + hashTime + " ns");
            System.out.println("TreeMap       : " + treeTime + " ns");
            System.out.println("LinkedHashMap : " + linkedTime + " ns");

            System.out.println("Report creation time:");
            System.out.println("HashMap       : " + hashReport + " ns");
            System.out.println("TreeMap       : " + treeReport + " ns");
            System.out.println("LinkedHashMap : " + linkedReport + " ns");
        }

        // Demonstrate the ordering behavior
        System.out.println("\nORDERING DEMONSTRATION");

        Map<String, Integer> hash = new HashMap<>();
        Map<String, Integer> tree = new TreeMap<>();
        Map<String, Integer> linked = new LinkedHashMap<>();

        String[] codes = {"MED003", "MED001", "MED002"};

        for (String code : codes) {
            hash.put(code, 50);
            tree.put(code, 50);
            linked.put(code, 50);
        }

        System.out.println("HashMap       : " + hash.keySet());
        System.out.println("TreeMap       : " + tree.keySet());
        System.out.println("LinkedHashMap : " + linked.keySet());

        System.out.println("\nProgram completed successfully.");
    }

    static long measureLookup(Map<String, Integer> map, int size) {

        long total = 0;

        // Warm-up searches
        for (int i = 1; i <= 10000; i++) {
            String code = String.format("MED%05d", (i % size) + 1);
            total += map.get(code);
        }

        long start = System.nanoTime();

        for (int i = 1; i <= LOOKUPS; i++) {
            String code = String.format("MED%05d", (i % size) + 1);
            Integer stock = map.get(code);

            if (stock != null) {
                total += stock;
            }
        }

        long end = System.nanoTime();
        result = total;

        return end - start;
    }

    static long measureReport(Map<String, Integer> map) {

        long start = System.nanoTime();

        StringBuilder report = new StringBuilder();

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            report.append(entry.getKey())
                  .append(" : ")
                  .append(entry.getValue())
                  .append("\n");
        }

        long end = System.nanoTime();

        // Keep the generated report in use
        result = report.length();

        return end - start;
    }
}

