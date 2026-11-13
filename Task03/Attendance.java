
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Attendance {

    static class Student {
        String regNo;
        String name;

        Student(String regNo, String name) {
            this.regNo = regNo;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Student)) {
                return false;
            }

            Student s = (Student) o;
            return regNo.equals(s.regNo);
        }

        // Correct hashCode implementation
        @Override
        public int hashCode() {
            return Objects.hash(regNo);
        }

        @Override
        public String toString() {
            return regNo + " " + name;
        }
    }

    public static void main(String[] args) {

        // Experiment 1: Correct implementation
        System.out.println("EXPERIMENT 1");

        Set<Student> present = new HashSet<>();

        present.add(new Student("192311001", "Arun"));
        present.add(new Student("192311002", "Divya"));
        present.add(new Student("192311001", "Arun"));

        System.out.println("Unique students: " + present.size());

        // Experiment 2: Different names, same registration number
        System.out.println("\nEXPERIMENT 2");

        Set<Student> students = new HashSet<>();

        students.add(new Student("192311003", "Ravi"));
        students.add(new Student("192311003", "Rahul"));
        students.add(new Student("192311004", "Meena"));

        System.out.println("Unique students: " + students.size());
        System.out.println(students);

        // Experiment 3: Changing registration number
        System.out.println("\nEXPERIMENT 3");

        Student s1 = new Student("192311005", "Kiran");

        Set<Student> changedSet = new HashSet<>();

        changedSet.add(s1);

        System.out.println("Before change: "
                + changedSet.contains(s1));

        s1.regNo = "192311006";

        System.out.println("After change: "
                + changedSet.contains(s1));

        System.out.println("\nProgram completed.");
    }
}

