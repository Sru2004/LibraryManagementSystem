import java.util.List;
import java.util.Scanner;

public class Main {
    private static Library library = new Library();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    displayBooks();
                    break;
                case 3:
                    borrowBook();
                    break;
                case 4:
                    returnBook();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\nLibrary Management System");
        System.out.println("1. Add Book");
        System.out.println("2. Display Books");
        System.out.println("3. Borrow Book");
        System.out.println("4. Return Book");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addBook() {
        System.out.print("Enter Book ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Author: ");
        String author = scanner.nextLine();

        Book book = new Book(id, title, author);
        if (library.addBook(book)) {
            System.out.println("Book added successfully.");
        } else {
            System.out.println("Book ID already exists.");
        }
    }

    private static void displayBooks() {
        List<Book> books = library.viewBooks();
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
        } else {
            System.out.println("Books in the library:");
            for (Book book : books) {
                String status = book.isAvailable() ? "Available" : "Borrowed by " + book.getBorrower();
                System.out.println("ID: " + book.getBookId() + ", Title: " + book.getTitle() + ", Author: " + book.getAuthor() + ", Status: " + status);
            }
        }
    }

    private static void borrowBook() {
        System.out.print("Enter Book ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Borrower Name: ");
        String borrower = scanner.nextLine();

        String result = library.borrowBook(id, borrower);
        System.out.println(result);
    }

    private static void returnBook() {
        System.out.print("Enter Book ID: ");
        String id = scanner.nextLine();

        String result = library.returnBook(id);
        System.out.println(result);
    }
}
