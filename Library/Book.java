import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.PrintWriter;

/**
 * Represents Book objects within the system. Instances have a title, author, genre and serial number.
 * They can be rented by members and returned to the library.
 * They store their own transaction history, logging the order of members that rented the book.
 */
public class Book {
    private String serialNumber;
    private String title;
    private String author;
    private String genre;
    public List<Action> history = new ArrayList<>();

    /**
     * Constructs a new Book object. The instance stores the parameters as its own properties.
     *
     * @param serialNumber
     * @param title
     * @param author
     * @param genre
     */
    public Book(String title, String author, String genre, String serialNumber) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.serialNumber = serialNumber;
    }

    /**
     * Returns the title of the book.
     *
     * @return The title of the book.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the author of the book.
     *
     * @return The author of the book.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Returns the genre of the book.
     *
     * @return The genre of the book.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Returns the serial number of the book.
     *
     * @return The serial number of the book.
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Formats the Book object to create the long form of its toString().
     * Format:
     * If the book is rented: [serialNumber]: [title] ([author], [genre])\nRented by [renter number].
     * If the book is available: [serialNumber]: [title] ([author], [genre])\nCurrently available.
     *
     * @return The extended String.
     */
    public String longString() {
        if (Action.isRented(this)) {
            return this.getSerialNumber() + ": " + this.getTitle() + " (" + this.getAuthor() + ", " + this.getGenre() + ")\n" + "Rented by: " + Action.whoRenting(this).getMemberNumber() + ".";
        }

        return this.getSerialNumber() + ": " + this.getTitle() + " (" + this.getAuthor() + ", " + this.getGenre() + ")\n" + "Currently available.";
    }

    /**
     * Formats the Book object to create the short form of its toString().
     * Format:
     * [title] ([author])
     *
     * @return The shortened String.
     */
    public String shortString() {
        return this.title + " (" + this.author + ")";
    }

    /**
     * Returns the renter history, in chronological order.
     *
     * @return The list of members who have rented the book.
     */
    public List<Member> renterHistory() {
        return Action.whoRented(this);
    }

    /**
     * Returns whether the book is currently being rented by a member of the library.
     *
     * @return Whether the book is currently rented.
     */
    public boolean isRented() {
        return Action.isRented(this);
    }

    /**
     * Sets the current renter to be the given member.
     * <p>
     * If the member does not exist, or the book is already being rented, do nothing and return false.
     * If the book is able to be rented, return true.
     *
     * @param member The new person renting the book.
     * @return The outcome of the rental transaction.
     */
    public boolean rent(Member member) {
        return Action.rent(member, this);
    }

    /**
     * Returns the book to the library.
     * <p>
     * If the member does not exist or isn't the current renter, do nothing and return false.
     * If the book is rented by the member, change the current renter and return true.
     *
     * @param member The member returning the book.
     * @return The outcome of the rental transaction.
     */
    public boolean relinquish(Member member) {
        return Action.relinquish(member, this);
    }

    /**
     * Retrieves the book from the given file based on its serial number.
     * <p>
     * If the file or given book doesn't exist, return null.
     * If the file does exist, retrieve the information for the book with the given serial number, and return the newly created book.
     *
     * @param filename     The csv file containing a book collection.
     * @param serialNumber The serial number for the book.
     * @return The Book object created based on the file.
     */
    public static Book readBook(String filename, String serialNumber) {
        if (filename == null || serialNumber == null) {
            return null;
        }

        Book returnbook = null;
        File f = new File(filename);
        try {
            Scanner scan = new Scanner(f);
            while (scan.hasNextLine()) {
                String[] data = scan.nextLine().split(",");
                if (data.length == 4) {
                    if (data[0].equals(serialNumber)) {
                        Book book = new Book(data[1], data[2], data[3], data[0]);
                        returnbook = book;
                    }
                }

            }
        } catch (FileNotFoundException e) {
            return null;
        }

        return returnbook;
    }

    /**
     * Reads in the collection of books from the given file.
     * <p>
     * If the file or given book doesn't exist, return null.
     * If the file exists, traverse through the csv and create a new book object for each line. The method should then return the list of books in the same order they appear in the csv file.
     *
     * @param filename The csv file to read.
     * @return The collection of books stored in the csv file.
     */
    public static List<Book> readBookCollection(String filename) {
        if (filename == null) {
            return null;
        }

        List<Book> bookCollection = new ArrayList<>();
        File f = new File(filename);
        try {
            Scanner scan = new Scanner(f);

            while (scan.hasNext()) {
                String strs = scan.nextLine();
                String[] data = strs.split(",");

                // Skip CSV header line
                if (data[0].equals("serialNumber")) {
                    continue;
                }

                Book book = new Book(data[1], data[2], data[3], data[0]);
                bookCollection.add(book);
            }
        } catch (FileNotFoundException e) {
            return null;
        }

        return bookCollection;
    }

    /**
     * Save the collection of books to the given file.
     * <p>
     * If the file or collection doesn't exist, do nothing.
     * Otherwise, write the collection to file, ensuring that the file maintains the csv format.
     *
     * @param filename The csv file to write to.
     * @param books    The collection of books to write to file.
     */
    public static void saveBookCollection(String filename, Collection<Book> books) {
        if (filename == null || books == null) {
            return;

        }
        File f = new File(filename);
        try {
            PrintWriter writer = new PrintWriter(f);
            writer.print("serialNumber," + "title," + "author," + "genre\n" );
            writer.flush();
            for (Book bookfromCollections : books) {
                writer.print(bookfromCollections.getSerialNumber() + ",");
                writer.print(bookfromCollections.getTitle() + ",");
                writer.print(bookfromCollections.getAuthor() + ",");
                writer.println(bookfromCollections.getGenre());
                writer.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new list containing books by the specified author.
     * <p>
     * If the list or author does not exist, return null.
     * If they do exist, create a new list with all the books written by the given author, sort by serial number, and return the result.
     *
     * @param books  The list of books to filter.
     * @param author The author to filter by.
     * @return The filtered list of books.
     */
    public static List<Book> filterAuthor(List<Book> books, String author) {
        if (books == null || author == null) {
            return null;
        }

        List<Book> booksWithAuthor = new ArrayList<>();
        for (Book booksFromList : books) {
            if (booksFromList.getAuthor().equals(author)) {
                booksWithAuthor.add(booksFromList);
            }
        }

        booksWithAuthor.sort(getCompBySerialNumber());
        return booksWithAuthor;
    }

    /**
     * Creates a new list containing books by the specified genre.
     * <p>
     * If the list or genre does not exist, return null.
     * If they do exist, create a new list with all the books in the specified genre, sort by serial number, and return the result.
     *
     * @param books The list of books to filter.
     * @param genre The genre to filter by.
     * @return The filtered list of books.
     */
    public static List<Book> filterGenre(List<Book> books, String genre) {
        if (books == null || genre == null) {
            return null;
        }

        List<Book> booksWithGenre = new ArrayList<>();
        for (Book booksFromList : books) {
            if (booksFromList.getGenre().equals(genre)) {
                booksWithGenre.add(booksFromList);
            }
        }

        booksWithGenre.sort(getCompBySerialNumber());
        return booksWithGenre;
    }

    public static Comparator<Book> getCompBySerialNumber() {
        Comparator<Book> comp = new Comparator<Book>() {
            @Override
            public int compare(Book s1, Book s2) {
                return s1.getSerialNumber().compareTo(s2.getSerialNumber());
            }
        };
        return comp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(serialNumber, book.serialNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber);
    }
}
