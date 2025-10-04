import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books;

    public Library() {
        books = new ArrayList<>();
    }

    public boolean addBook(Book book) {
        for (Book b : books) {
            if (b.getBookId().equals(book.getBookId())) {
                return false; // Book ID already exists
            }
        }
        books.add(book);
        return true;
    }

    public List<Book> viewBooks() {
        return new ArrayList<>(books);
    }

    public String borrowBook(String bookId, String borrowerName) {
        for (Book book : books) {
            if (book.getBookId().equals(bookId)) {
                if (book.isAvailable()) {
                    book.setBorrower(borrowerName);
                    return "Book borrowed successfully.";
                } else {
                    return "Book is already borrowed.";
                }
            }
        }
        return "Book not found.";
    }

    public String returnBook(String bookId) {
        for (Book book : books) {
            if (book.getBookId().equals(bookId)) {
                if (!book.isAvailable()) {
                    book.setBorrower(null);
                    return "Book returned successfully.";
                } else {
                    return "Book is not borrowed.";
                }
            }
        }
        return "Book not found.";
    }
}
