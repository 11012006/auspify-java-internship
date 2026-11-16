import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Task 3 - Library Management System (Console)
 * Features: Add/Remove Books, Issue/Return System, Search Functionality
 */
public class LibraryManagementSystem {

    // Step 1: Book class with details
    static class Book {
        private final int id;
        private final String title;
        private final String author;
        private boolean issued;

        Book(int id, String title, String author) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.issued = false;
        }

        int getId() { return id; }
        String getTitle() { return title; }
        String getAuthor() { return author; }
        boolean isIssued() { return issued; }
        void setIssued(boolean issued) { this.issued = issued; }

        @Override
        public String toString() {
            return String.format("ID: %-4d Title: %-28s Author: %-18s Status: %s",
                    id, title, author, issued ? "Issued" : "Available");
        }
    }

    private static final ArrayList<Book> books = new ArrayList<>();
    private static final Scanner sc = new Scanner(System.in);

    private static Book findById(int id) {
        for (Book b : books) {
            if (b.getId() == id) return b;
        }
        return null;
    }

    // Step 2: Book insertion
    private static void addBook() {
        int id = readInt("Enter book ID: ");
        if (findById(id) != null) {
            System.out.println("A book with ID " + id + " already exists.");
            return;
        }
        System.out.print("Enter title: ");
        String title = sc.nextLine().trim();
        System.out.print("Enter author: ");
        String author = sc.nextLine().trim();
        if (title.isEmpty() || author.isEmpty()) {
            System.out.println("Title and author cannot be empty.");
            return;
        }
        books.add(new Book(id, title, author));
        System.out.println("Book added successfully.");
    }

    // Step 2: Book removal
    private static void removeBook() {
        int id = readInt("Enter ID of the book to remove: ");
        Book b = findById(id);
        if (b == null) {
            System.out.println("Book not found.");
        } else if (b.isIssued()) {
            System.out.println("Cannot remove a book that is currently issued.");
        } else {
            books.remove(b);
            System.out.println("Book removed successfully.");
        }
    }

    // Step 3: Display available books (option to show all)
    private static void displayBooks(boolean onlyAvailable) {
        boolean any = false;
        System.out.println(onlyAvailable ? "\n----- Available Books -----" : "\n----- All Books -----");
        for (Book b : books) {
            if (!onlyAvailable || !b.isIssued()) {
                System.out.println(b);
                any = true;
            }
        }
        if (!any) System.out.println("No books to display.");
    }

    // Step 4: Search by title or ID
    private static void searchBooks() {
        System.out.print("Search by (1) ID or (2) Title: ");
        String opt = sc.nextLine().trim();
        if (opt.equals("1")) {
            int id = readInt("Enter book ID: ");
            Book b = findById(id);
            System.out.println(b != null ? b : "No book found with ID " + id);
        } else if (opt.equals("2")) {
            System.out.print("Enter title keyword: ");
            String key = sc.nextLine().trim().toLowerCase();
            boolean found = false;
            for (Book b : books) {
                if (b.getTitle().toLowerCase().contains(key)) {
                    System.out.println(b);
                    found = true;
                }
            }
            if (!found) System.out.println("No matching books found.");
        } else {
            System.out.println("Invalid option.");
        }
    }

    // Step 5: Manage issue/return status
    private static void issueBook() {
        int id = readInt("Enter ID of the book to issue: ");
        Book b = findById(id);
        if (b == null) {
            System.out.println("Book not found.");
        } else if (b.isIssued()) {
            System.out.println("Sorry, this book is already issued.");
        } else {
            b.setIssued(true);
            System.out.println("Book \"" + b.getTitle() + "\" issued successfully.");
        }
    }

    private static void returnBook() {
        int id = readInt("Enter ID of the book to return: ");
        Book b = findById(id);
        if (b == null) {
            System.out.println("Book not found.");
        } else if (!b.isIssued()) {
            System.out.println("This book was not issued.");
        } else {
            b.setIssued(false);
            System.out.println("Book \"" + b.getTitle() + "\" returned successfully.");
        }
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

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== Library Management System =====");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Display Available Books");
            System.out.println("4. Display All Books");
            System.out.println("5. Search Book");
            System.out.println("6. Issue Book");
            System.out.println("7. Return Book");
            System.out.println("8. Exit");
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> addBook();
                case 2 -> removeBook();
                case 3 -> displayBooks(true);
                case 4 -> displayBooks(false);
                case 5 -> searchBooks();
                case 6 -> issueBook();
                case 7 -> returnBook();
                case 8 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
