import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Task 1 - Student Management System (Console, ArrayList based)
 * Features: Add Student, View Students, Search Student by ID
 */
public class StudentManagementSystem {

    // Step 1: Student class with basic attributes
    static class Student {
        private final int id;
        private final String name;
        private final double marks;

        Student(int id, String name, double marks) {
            this.id = id;
            this.name = name;
            this.marks = marks;
        }

        int getId() { return id; }
        String getName() { return name; }
        double getMarks() { return marks; }

        @Override
        public String toString() {
            return String.format("ID: %-5d Name: %-20s Marks: %.2f", id, name, marks);
        }
    }

    // Step 5: Store data using ArrayList
    private static final ArrayList<Student> students = new ArrayList<>();
    private static final Scanner sc = new Scanner(System.in);

    // Step 2: Insert student records
    private static void addStudent() {
        int id = readInt("Enter student ID: ");
        if (findById(id) != null) {
            System.out.println("A student with ID " + id + " already exists.");
            return;
        }
        System.out.print("Enter student name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        double marks = readDouble("Enter marks (0-100): ");
        if (marks < 0 || marks > 100) {
            System.out.println("Marks must be between 0 and 100.");
            return;
        }
        students.add(new Student(id, name, marks));
        System.out.println("Student added successfully.");
    }

    // Step 3: Display all student details
    private static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }
        System.out.println("\n----- Student Records -----");
        for (Student s : students) {
            System.out.println(s);
        }
    }

    // Step 4: Search student by ID
    private static Student findById(int id) {
        for (Student s : students) {
            if (s.getId() == id) return s;
        }
        return null;
    }

    private static void searchStudent() {
        int id = readInt("Enter student ID to search: ");
        Student s = findById(id);
        System.out.println(s != null ? "Found -> " + s : "No student found with ID " + id);
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = sc.nextInt();
                sc.nextLine();
                return v;
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double v = sc.nextDouble();
                sc.nextLine();
                return v;
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Exit");
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> searchStudent();
                case 4 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
