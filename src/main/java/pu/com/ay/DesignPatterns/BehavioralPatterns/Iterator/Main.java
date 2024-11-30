package pu.com.ay.DesignPatterns.BehavioralPatterns.Iterator;

// Let me explain the Iterator pattern and the example:
// Key Benefits of Iterator Pattern:

// Separates traversal logic from collection implementation
// Supports multiple concurrent iterations
// Easy to add new traversal methods without modifying collections

interface Iterator<T> {
    boolean hasNext();
    T next();
    void reset();
}

// Collection interface
interface Collection<T> {
    Iterator<T> getIterator();
}

// Concrete Iterator
class BookIterator implements Iterator<Book> {
    private Book[] books;
    private int position;

    public BookIterator(Book[] books) {
        this.books = books;
        position = 0;
    }

    @Override
    public boolean hasNext() {
        return position < books.length;
    }

    @Override
    public Book next() {
        if(hasNext()) {
            return books[position++];
        }
        return null;
    }

    @Override
    public void reset() {
        position = 0;
    }
}

// Concrete Collection
class Library implements Collection<Book> {
    private Book[] books;

    public Library(Book[] books) {
        this.books = books;
    }

    @Override
    public Iterator<Book> getIterator() {
        return new BookIterator(books);
    }
}

// Element class
class Book {
    private String name;
    private String author;

    public Book(String name, String author) {
        this.name = name;
        this.author = author;
    }

    @Override
    public String toString() {
        return name + " by " + author;
    }
}

// Client code
public class Main {
    public static void main(String[] args) {
        Book[] books = {
            new Book("Design Patterns", "Gang of Four"),
            new Book("Clean Code", "Robert Martin"),
            new Book("Refactoring", "Martin Fowler")
        };

        Library library = new Library(books);
        Iterator<Book> iterator = library.getIterator();

        // Iterate through books
        while(iterator.hasNext()) {
            Book book = iterator.next();
            System.out.println(book);
        }

        // Reset and iterate again
        iterator.reset();
        System.out.println("\nAfter reset:");
        while(iterator.hasNext()) {
            Book book = iterator.next();
            System.out.println(book);
        }
    }
}