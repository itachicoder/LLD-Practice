package pu.com.ay.SolidPrinciples.SingleResponsibilityPrinciple;

// Book class to hold book information
class Book {
    private String title;
    private String author;
    private String isbn;
    private double price;

    public Book(String title, String author, String isbn, double price) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
    }

    // Getters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public double getPrice() { return price; }
}

// Invoice class only handles invoice calculations
class Invoice {
    private Book book;
    private int quantity;
    private double discountRate;
    private double taxRate;
    private double total;

    public Invoice(Book book, int quantity, double discountRate, double taxRate) {
        this.book = book;
        this.quantity = quantity;
        this.discountRate = discountRate;
        this.taxRate = taxRate;
        this.total = calculateTotal();
    }

    public double calculateTotal() {
        double price = (book.getPrice() * quantity);
        double discount = price * discountRate;
        double priceAfterDiscount = price - discount;
        double tax = priceAfterDiscount * taxRate;
        return priceAfterDiscount + tax;
    }

    // Getters
    public Book getBook() { return book; }
    public int getQuantity() { return quantity; }
    public double getDiscountRate() { return discountRate; }
    public double getTaxRate() { return taxRate; }
    public double getTotal() { return total; }
}

// Separate class for printing invoices
class InvoicePrinter {
    private Invoice invoice;

    public InvoicePrinter(Invoice invoice) {
        this.invoice = invoice;
    }

    public void printInvoice() {
        System.out.println("=== INVOICE ===");
        System.out.println("Book Details:");
        System.out.println("Title: " + invoice.getBook().getTitle());
        System.out.println("Author: " + invoice.getBook().getAuthor());
        System.out.println("ISBN: " + invoice.getBook().getIsbn());
        System.out.println("\nQuantity: " + invoice.getQuantity());
        System.out.println("Price per unit: $" + invoice.getBook().getPrice());
        System.out.println("Discount Rate: " + (invoice.getDiscountRate() * 100) + "%");
        System.out.println("Tax Rate: " + (invoice.getTaxRate() * 100) + "%");
        System.out.println("\nTotal: $" + String.format("%.2f", invoice.getTotal()));
        System.out.println("=============");
    }
}

// Separate class for storing invoices
class InvoicePersistence {
    private Invoice invoice;

    public InvoicePersistence(Invoice invoice) {
        this.invoice = invoice;
    }

    public void saveToFile(String filename) {
        // Simulated file saving logic
        System.out.println("Saving invoice to file: " + filename);
        // Actual file saving code would go here
    }

    public void saveToDatabase() {
        // Simulated database saving logic
        System.out.println("Saving invoice to database...");
        System.out.println("Invoice for book '" + invoice.getBook().getTitle() + 
                          "' saved with total amount: $" + invoice.getTotal());
        // Actual database saving code would go here
    }
}

// Main class to demonstrate usage
class BookInvoiceDemo {
    public static void main(String[] args) {
        // Create a book
        Book book = new Book("Clean Code", "Robert C. Martin", "978-0132350884", 44.99);

        // Create an invoice
        Invoice invoice = new Invoice(book, 2, 0.1, 0.08);  // 2 books, 10% discount, 8% tax

        // Print the invoice
        InvoicePrinter printer = new InvoicePrinter(invoice);
        printer.printInvoice();

        // Save the invoice
        InvoicePersistence persistence = new InvoicePersistence(invoice);
        persistence.saveToFile("invoice.txt");
        persistence.saveToDatabase();
    }
}