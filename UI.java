import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UI extends JFrame {
    private Library library;
    private JTable bookTable;
    private DefaultTableModel tableModel;

    public UI() {
        library = new Library();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Library Management System");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 255, 240)); // Light green background

        JLabel titleLabel = new JLabel("Library Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(34, 139, 34)); // Forest green
        add(titleLabel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Title", "Author", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        bookTable = new JTable(tableModel);
        bookTable.setFont(new Font("Arial", Font.PLAIN, 14));
        bookTable.setRowHeight(25);
        bookTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        bookTable.getTableHeader().setBackground(new Color(34, 139, 34)); // Forest green
        bookTable.getTableHeader().setForeground(Color.WHITE);
        bookTable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row % 2 == 0) {
                    setBackground(new Color(240, 255, 240)); // Light green
                } else {
                    setBackground(Color.WHITE);
                }
                if (isSelected) {
                    setBackground(new Color(144, 238, 144)); // Light green selected
                }
                return this;
            }
        });
        JScrollPane scrollPane = new JScrollPane(bookTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4, 10, 10));
        buttonPanel.setBackground(new Color(240, 255, 240)); // Light green

        JButton addButton = new JButton("Add Book");
        JButton viewButton = new JButton("View Books");
        JButton borrowButton = new JButton("Borrow Book");
        JButton returnButton = new JButton("Return Book");

        styleButton(addButton);
        styleButton(viewButton);
        styleButton(borrowButton);
        styleButton(returnButton);

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(borrowButton);
        buttonPanel.add(returnButton);

        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewBooks();
            }
        });

        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrowBook();
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnBook();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(34, 139, 34)); // Forest green
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
    }

    private void addBook() {
        JTextField idField = new JTextField();
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();

        Object[] message = {
                "Book ID:", idField,
                "Title:", titleField,
                "Author:", authorField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add New Book", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String bookId = idField.getText().trim();
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            if (!bookId.isEmpty() && !title.isEmpty() && !author.isEmpty()) {
                Book book = new Book(bookId, title, author);
                if (library.addBook(book)) {
                    JOptionPane.showMessageDialog(this, "Book added successfully.");
                    viewBooks();
                } else {
                    JOptionPane.showMessageDialog(this, "Book ID already exists.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "All fields are required.");
            }
        }
    }

    private void viewBooks() {
        tableModel.setRowCount(0); // Clear table
        List<Book> books = library.viewBooks();
        if (books.isEmpty()) {
            tableModel.addRow(new Object[]{"", "No books in the library.", "", ""});
        } else {
            for (Book book : books) {
                String status = book.isAvailable() ? "Available" : "Borrowed by " + book.getBorrower();
                tableModel.addRow(new Object[]{book.getBookId(), book.getTitle(), book.getAuthor(), status});
            }
        }
    }

    private void borrowBook() {
        JTextField idField = new JTextField();
        JTextField borrowerField = new JTextField();

        Object[] message = {
                "Book ID:", idField,
                "Borrower Name:", borrowerField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Borrow Book", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String bookId = idField.getText().trim();
            String borrowerName = borrowerField.getText().trim();
            if (!bookId.isEmpty() && !borrowerName.isEmpty()) {
                String result = library.borrowBook(bookId, borrowerName);
                JOptionPane.showMessageDialog(this, result);
                viewBooks();
            } else {
                JOptionPane.showMessageDialog(this, "All fields are required.");
            }
        }
    }

    private void returnBook() {
        JTextField idField = new JTextField();

        Object[] message = {
                "Book ID:", idField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Return Book", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String bookId = idField.getText().trim();
            if (!bookId.isEmpty()) {
                String result = library.returnBook(bookId);
                JOptionPane.showMessageDialog(this, result);
                viewBooks();
            } else {
                JOptionPane.showMessageDialog(this, "Book ID is required.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UI());
    }
}
