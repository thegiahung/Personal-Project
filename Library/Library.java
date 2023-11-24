import java.util.*;

public class Library {
    public static final String HELP_STRING =
        "EXIT ends the library process\nCOMMANDS outputs this help string\n\nLIST ALL [LONG] outputs either the short or long string for all books\nLIST AVAILABLE [LONG] outputs either the short of long string for all available books\nNUMBER COPIES outputs the number of copies of each book\nLIST GENRES outputs the name of every genre in the system\nLIST AUTHORS outputs the name of every author in the system\n\nGENRE <genre> outputs the short string of every book with the specified genre\nAUTHOR <author> outputs the short string of every book by the specified author\n\nBOOK <serialNumber> [LONG] outputs either the short or long string for the specified book\nBOOK HISTORY <serialNumber> outputs the rental history of the specified book\n\nMEMBER <memberNumber> outputs the information of the specified member\nMEMBER BOOKS <memberNumber> outputs the books currently rented by the specified member\nMEMBER HISTORY <memberNumber> outputs the rental history of the specified member\n\nRENT <memberNumber> <serialNumber> loans out the specified book to the given member\nRELINQUISH <memberNumber> <serialNumber> returns the specified book from the member\nRELINQUISH ALL <memberNumber> returns all books rented by the specified member\n\nADD MEMBER <name> adds a member to the system\nADD BOOK <filename> <serialNumber> adds a book to the system\n\nADD COLLECTION <filename> adds a collection of books to the system\nSAVE COLLECTION <filename> saves the system to a csv file\n\nCOMMON <memberNumber1> <memberNumber2> ... outputs the common books in members\' history";

    public static void main(String[] args) {
        Library lib = new Library();
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.print("user: ");
            String input = scan.nextLine();

            if (input.toUpperCase().equals("EXIT")) {
                System.out.println("Ending Library process.");
                break;
            }

            if (input.toUpperCase().equals("COMMANDS")) {
                System.out.println(HELP_STRING);
                System.out.println();
                continue;
            }

            if (input.toUpperCase().equals("LIST ALL")) {
                lib.getAllBooks(false);
                continue;
            }

            if (input.toUpperCase().equals("LIST ALL LONG")) {
                lib.getAllBooks(true);
                continue;
            }

            if (input.toUpperCase().equals("LIST AVAILABLE")) {
                lib.getAvailableBooks(false);
                continue;
            }

            if (input.toUpperCase().equals("LIST AVAILABLE LONG")) {
                lib.getAvailableBooks(true);
                continue;
            }

            if (input.toUpperCase().equals("NUMBER COPIES")) {
                lib.getCopies();
                continue;
            }

            if (input.toUpperCase().equals("LIST GENRES")) {
                lib.getGenres();
                continue;
            }

            if (input.toUpperCase().equals("LIST AUTHORS")) {
                lib.getAuthors();
                continue;
            }

            if (input.toUpperCase().startsWith("GENRE")) {
                String genre = input.substring("GENRE ".length()).trim();
                lib.getBooksByGenre(genre);
                continue;
            }

            if (input.toUpperCase().startsWith("AUTHOR")) {
                String author = input.substring("AUTHOR ".length()).trim();
                lib.getBooksByAuthor(author);
                continue;
            }

            if (input.toUpperCase().startsWith("BOOK")) {
                String[] book = input.split("\\s+");
                if (!book[1].toUpperCase().equals("HISTORY")) {
                    if (book.length == 3) {
                        lib.getBook(book[1], true);
                        continue;
                    } else {
                        lib.getBook(book[1], false);
                        continue;
                    }
                } else {
                    String bookHistory = input.substring("BOOK HISTORY ".length()).trim();
                    lib.bookHistory(bookHistory);
                }
            }

            if (input.toUpperCase().startsWith("MEMBER")) {
                String member[] = input.split("\\s+");
                if (member[1].toUpperCase().equals("BOOKS")) {
                    lib.getMemberBooks(member[2]);
                } else if (member[1].toUpperCase().equals("HISTORY")) {
                    lib.memberRentalHistory(member[2]);
                } else {
                    lib.getMember(member[1]);
                }
            }

            if (input.toUpperCase().startsWith("RENT ")) {
                String[] setRent = input.split("\\s+");
                lib.rentBook(setRent[1], setRent[2]);
            }

            if (input.toUpperCase().startsWith("RELINQUISH")) {
                String[] setRel = input.split("\\s+");
                if (!setRel[1].toUpperCase().equals("ALL")) {
                    lib.relinquishBook(setRel[1], setRel[2]);
                } else {
                    lib.relinquishAll(setRel[2]);
                }
            }

            if (input.toUpperCase().startsWith("ADD")) {
                String[] add = input.split("\\s+");
                if (add[1].toUpperCase().equals("MEMBER")) {
                    String name = input.substring("ADD MEMBER ".length()).trim();
                    lib.addMember(name);
                } else if (add[1].toUpperCase().equals("BOOK")) {
                    lib.addBook(add[2], add[3]);
                } else if (add[1].toUpperCase().equals("COLLECTION")) {
                        lib.addCollection(add[2]);
                }
            }

            if (input.toUpperCase().startsWith("SAVE")) {
                String[] save = input.split("\\s+");
                lib.saveCollection(save[2]);
            }

            if (input.toUpperCase().startsWith("COMMON")) {
                String members = input.substring("COMMON ".length()).trim();
                String[] data = members.split("\\s+");
                lib.common(data);
            }
        }
    }

    LibraryState state = new LibraryState();

    /**
     * Prints out the formatted strings for all books in the system.
     *
     * Invoked by "LIST ALL", this method prints out either the short string or long string of all books (depending on whether "LONG" is included in the command), seperated by new lines.
     * If there are no books in the system, output "No books in system."
     *
     * @param fullString Whether to print short or long strings
     */
    public void getAllBooks(boolean fullString) {
        if (state.books.isEmpty()) {
            System.out.println("No books in system.\n");
            return;
        } else if (!state.books.isEmpty()) {
            if (fullString == false) {
                for (Book book : state.books) {
                    System.out.println(book.shortString());
                }
                System.out.println();
            } else {
                for (Book book : state.books) {
                    System.out.println(book.longString());
                    System.out.println();
                }
            }
        }
    }

    /**
     * public void getAvailableBooks(boolean fullString)
     * Prints out the formatted strings for all available books in the system.
     *
     * Invoked by "LIST AVAILABLE", this method prints out either the short string or long string of all available books (depending on whether "LONG" is included in the command), seperated by new lines. Note that a book is available when it is not currently rented by a member.
     * If there are no books in the system, output "No books in system."
     * If there are no available books, output "No books available."
     * @param fullString Whether to print short or long strings
     */
    public void getAvailableBooks(boolean fullString) {
        if (state.books.isEmpty()) {
            System.out.println("No books in system.\n");
            return;
        }

        if (fullString == false) {
            List<Book> bookAvailable = new ArrayList<>();
            for (Book book : state.books) {
                if (!book.isRented()) {
                    bookAvailable.add(book);
                }
            }
            if (bookAvailable.isEmpty()) {
                System.out.println("No books available.\n");
                return;
            }

            for (Book book : bookAvailable) {
                System.out.println(book.shortString());
            }
            System.out.println();
        } else {
            List<Book> bookAvailable = new ArrayList<>();
            for (Book book : state.books) {
                if (!book.isRented()) {
                    bookAvailable.add(book);
                }
            }
            if (bookAvailable.isEmpty()) {
                System.out.println("No books available.\n");
                return;
            }

            for (Book book : bookAvailable) {
                System.out.println(book.longString());
                System.out.println();
            }
        }
    }
    /**
     * Prints out the number of copies of each book in the system.
     *
     * Invoked by "NUMBER COPIES", this method prints out the number of copies present of each book, seperated by new lines, sorted lexicographically. Note that books are considered copies if they have the same short string.
     *
     * If there are no books in the system, output "No books in system."
     * Format: "[short string]: [number]"
     */
    public void getCopies() {
        state.getCopies();
    }

    /**
     *Prints out all the genres in the system.
     *
     * Invoked by "LIST GENRES", this method prints out the list of genres stored in the system. Each genre should only be printed once, and they should be printed in alphabetical order.
     * If there are no books, output "No books in system."
     */
    public void getGenres() {
        state.getGenres();
    }

    /**
     * Prints out all the authors in the system.
     *
     * Invoked by "LIST AUTHORS", this method prints out the list of authors in the system. Each genre should only be printed once, and they should be printed in alphabetical order.
     * If there are no books, output "No books in system."
     */
    public void getAuthors() {
        state.getAuthors();
    }

    /**
     * Prints all books in the system with the specified genre.
     *
     * Invoked by "GENRE [genre]", this method outputs all the books in the system with the specified genre. Each book should have its short string printed on a new line, and books should be ordered by serial number.
     * If there are no books, output "No books in system."
     * If there are no books of the specified genre, output "No books with genre [genre]."
     *
     * @param genre The genre to filter by
     */
    public void getBooksByGenre(String genre) {
        state.getBooksByGenre(genre);
    }

    /**
     * Prints all books in the system by the specified author.
     *
     * Invoked by "AUTHOR [author]", this method outputs all the books in the system that were written by the specified author. Each book should have its short string printed on a new line, and books should be ordered by serial number.
     * If there are no books, output "No books in system."
     * If there are no books by the specified author, output "No books by author [author]."
     *
     * @param author The author to filter by
     */
    public void getBooksByAuthor(String author) {
        state.getBooksByAuthor(author);
    }

    /**
     * Prints either the short or long string of the specified book.
     *
     * Invoked by the command "BOOK [serialNumber] [LONG]", this method prints out the details for the specified book.
     * If there are no books, output "No books in system."
     * If the book does not exist, output "No such book in system."
     * If fullString is true, output the long string of the book.
     * If fullString is false, output the short string of the book.
     *
     * @param serialNumber The serial number of the book
     * @param fullString Whether to print short or long string
     */
    public void getBook(String serialNumber, boolean fullString) {
        state.getBook(serialNumber, fullString);
    }

    /**
     * Prints out all the member numbers of members who have previously rented a book.
     *
     * Invoked by the command "BOOK HISTORY [serialNumber]", this method outputs every member that has rented the given book, in the order of rents.
     * If the book does not exist in the system, output "No such book in system."
     * If the book has not been rented, output "No rental history."
     *
     * @param serialNumber
     */
    public void bookHistory(String serialNumber) {
        state.bookHistory(serialNumber);
    }

    /**
     * Adds a book to the system by reading it from a csv file.
     *
     * Invoked by the command "ADD BOOK [file] [serialNumber]", this method reads the given file, searches for the serial number, and then adds the book to the system.
     * If the file does not exist, output "No such file."
     * If the book does not exist within the file, output "No such book in file."
     * If the book's serial number is already present in the system, output "Book already exists in the system."
     * If the book is successfully added, output "Successfully added: [shortstring]."
     *
     * @param bookFile
     * @param serialNumber
     */
    public void addBook(String bookFile, String serialNumber) {
        state.addBook(bookFile, serialNumber);
    }

    /**
     * Loans out a book to a member within the system.
     *
     * Invoked by the command "RENT [memberNumber] [serialNumber]", this method loans out the specified book to the given member.
     * If there are no members in the system, output "No members in system."
     * If there are no books in the system, output "No books in system."
     * If the member does not exist, output "No such member in system."
     * If the book does not exist, output "No such book in system."
     * If the book is already being loaned out, output "Book is currently unavailable."
     * If the book is successfully loaned out, output "Success."
     *
     * @param memberNumber
     * @param serialNumber
     */
    public void rentBook(String memberNumber, String serialNumber) {
        state.rentBook(memberNumber, serialNumber);
    }

    public void relinquishBook(String memberNumber, String serialNumber) {
        state.relinquishBook(memberNumber, serialNumber);
    }

    public void relinquishAll(String memberNumber) {
        state.relinquishAll(memberNumber);
    }

    public void getMember(String memberNumber) {
        state.getMember(memberNumber);
    }

    public void getMemberBooks(String memberNumber) {
        state.getMemberBooks(memberNumber);
    }

    public void memberRentalHistory(String memberNumber) {
        state.memberRentalHistory(memberNumber);
    }

    public void addMember(String name) {
        state.addMember(name);
    }

    public void saveCollection(String filename) {
        state.saveCollection(filename);
    }

    public void addCollection(String filename) {
        state.addCollection(filename);
    }

    public void common(String[] memberNumbers) {
        state.common(memberNumbers);
    }
}
