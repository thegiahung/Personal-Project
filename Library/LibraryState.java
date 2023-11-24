import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class LibraryState {
    private int nextMemberId = 100000;
    public List<Member> members = new ArrayList<>();
    public List<Book> books = new ArrayList<>();
    public boolean isMemberExisted(String memberNumber) {
        if (memberNumber == null) {
            return false;
        }

        for (Member member : members) {
            if (member.getMemberNumber().equals(memberNumber)) {
                return true;
            }
        }

        return false;
    }

    public boolean isBookExisted(String serialNumber) {
        if (serialNumber == null) {
            return false;
        }

        for (Book book : books) {
            if (book.getSerialNumber().equals(serialNumber)) {
                return true;
            }
        }

        return false;
    }

    public void common(String[] memberNumbers) {
        if (members.isEmpty()) {
            System.out.println("No members in system.\n");
            return;
        }

        if (books.isEmpty()) {
            System.out.println("No books in system.\n");
            return;
        }

        for (String memNumber : memberNumbers) {
            if (!isMemberExisted(memNumber)) {
                System.out.println("No such member in system.\n");
                return;
            }
        }

        int n = memberNumbers.length;
        Set<String> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            if (!set.contains(memberNumbers[i])) {
                set.add(memberNumbers[i]);
            } else {
                System.out.println("Duplicate members provided.\n");
                return;
            }
        }

        List<Book> bk = new ArrayList<>();
        for (Book book : books) {
            if (isSubset(whoRentedTheBook(book.getSerialNumber()), memberNumbers)) {
                bk.add(book);
            }
        }
        if (bk.isEmpty()) {
            System.out.println("No common books.\n");
            return;
        }

        for (Book book : bk) {
            System.out.println(book.shortString());
        }
        System.out.println();
    }

    public void addCollection(String filename) {
        List<Book> bk = Book.readBookCollection(filename);
        if (bk == null) {
            System.out.println("No such collection.\n");
            return;
        }

        int n = 0;
        for (Book book : bk) {
            if (!isBookExisted(book.getSerialNumber())) {
                books.add(book);
                n++;
            }
        }

        if (n > 0) {
            System.out.println(n + " books successfully added.\n");
        } else {
            System.out.println("No books have been added to the system.\n");
        }
    }

    public void saveCollection(String filename) {
        if (books.isEmpty()) {
            System.out.println("No books in system.\n");
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
            System.out.println("Success.\n");
            return;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addMember(String name) {
        Member member = new Member(name, Integer.toString(nextMemberId));
        members.add(member);
        nextMemberId++;
        System.out.println("Success.\n");
    }

    public void memberRentalHistory(String memberNumber) {
        if (memberNumber == null) {
            return;
        }

        if (members.isEmpty()) {
            System.out.println("No members in system.\n");
            return;
        }

        if (!isMemberExisted(memberNumber)) {
            System.out.println("No such member in system.\n");
            return;
        }

        List<Book> bk = new ArrayList<>();
        for (Member member : members) {
            if (member.getMemberNumber().equals(memberNumber)) {
                bk = Action.rented(member);
            }
        }

        if (bk.isEmpty()) {
            System.out.println("No rental history for member.\n");
            return;
        } else {
            for (Book rentedBook : bk) {
                System.out.println(rentedBook.shortString());
            }
            System.out.println();
        }
    }

    public void getMemberBooks(String memberNumber) {
        if (memberNumber == null) {
            return;
        }

        if (members.isEmpty()) {
            System.out.println("No members in system.\n");
            return;
        }

        if (!isMemberExisted(memberNumber)) {
            System.out.println("No such member in system.\n");
            return;
        }

        List<Book> bk = new ArrayList<>();
        for (Member member : members) {
            if (member.getMemberNumber().equals(memberNumber)) {
                bk = Action.renting(member);
            }
        }

        if (bk.isEmpty()) {
            System.out.println("Member not currently renting.\n");
            return;
        }

        for (Book book : bk) {
            System.out.println(book.shortString());
        }
        System.out.println();
    }

    public void getMember(String memberNumber) {
        if (memberNumber == null) {
            return;
        }

        if (members.isEmpty()) {
            System.out.println("No members in system.\n");
            return;
        }

        if (!isMemberExisted(memberNumber)) {
            System.out.println("No such member in system.\n");
            return;
        }

        for (Member member : members) {
            if (member.getMemberNumber().equals(memberNumber)) {
                System.out.println(member.getMemberNumber() + ": " + member.getName());
            }
        }
        System.out.println();
    }

    public void relinquishAll(String memberNumber) {
        if (memberNumber == null) {
            return;
        }

        if (members.isEmpty()) {
            System.out.println("No members in system.\n");
            return;
        }

        if (!isMemberExisted(memberNumber)) {
            System.out.println("No such member in system.\n");
            return;
        }

        for (Book book : books) {
            if (Action.isRented(book)) {
                for (Member member : members) {
                    if (member.getMemberNumber().equals(memberNumber)) {
                        if (Action.whoRenting(book).getMemberNumber().equals(memberNumber))
                            if (!Action.renting(member).isEmpty()) {
                                Action.addHistory(member, Action.RETURN, book);
                            } else {
                                System.out.println("Unable to return book.\n");
                                return;
                            }
                    }
                }
            }
        }
        System.out.println("Success.\n");
    }

    public void relinquishBook(String memberNumber, String serialNumber) {
        if (memberNumber == null || serialNumber == null) {
            return;
        }
        if (members.isEmpty()) {
            System.out.println("No members in system.\n");
            return;
        }

        if (books.isEmpty()) {
            System.out.println("No books in system.\n");
            return;
        }

        if (!isMemberExisted(memberNumber)) {
            System.out.println("No such member in system.\n");
            return;
        }

        if (!isBookExisted(serialNumber)) {
            System.out.println("No such book in system.\n");
            return;
        }

        for (Book book : books) {
            if (book.getSerialNumber().equals(serialNumber)) {
                if (!Action.isRented(book)) {
                    System.out.println("Unable to return book.\n");
                    return;
                } else {
                    for (Member member : members) {
                        if (member.getMemberNumber().equals(memberNumber)) {
                            if (Action.whoRenting(book).getMemberNumber().equals(memberNumber)) {
                                Action.addHistory(member, Action.RETURN, book);
                                System.out.println("Success.\n");
                                return;
                            } else {
                                System.out.println( "Unable to return book.\n");
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    public void rentBook(String memberNumber, String serialNumber) {
        if (memberNumber == null || serialNumber == null) {
            return;
        }

        if (members.isEmpty()) {
            System.out.println("No members in system.\n");
            return;
        }

        if (books.isEmpty()) {
            System.out.println("No books in system.\n");
            return;
        }

        if (!isMemberExisted(memberNumber)) {
            System.out.println("No such member in system.\n");
            return;
        }

        if (!isBookExisted(serialNumber)) {
            System.out.println("No such book in system.\n");
            return;
        }

        for (Book book : books) {
            if (book.getSerialNumber().equals(serialNumber)) {
                if (Action.isRented(book)) {
                    System.out.println("Book is currently unavailable.\n");
                } else {
                    for (Member member : members) {
                        if (member.getMemberNumber().equals(memberNumber)) {
                            Action.addHistory(member, Action.RENT, book);
                            System.out.println("Success.\n");
                            return;
                        }
                    }
                }
            }
        }
    }

    public void addBook(String bookFile, String serialNumber) {
        if (bookFile == null || serialNumber == null) {
            return;
        }

        if (isBookExisted(serialNumber)) {
            System.out.println("Book already exists in system.\n");
            return;
        }

        Book returnbook = null;
        File f = new File(bookFile);
        try {
            Scanner scan = new Scanner(f);
            while (scan.hasNextLine()) {
                String[] data = scan.nextLine().split(",");
                if (data[0].equals("serialNumber")) {
                    continue;
                }
                if (data[0].equals(serialNumber)) {
                    returnbook = new Book(data[1], data[2], data[3], data[0]);
                }
            }

            if (returnbook == null) {
                System.out.println("No such book in file.\n");
            } else {
                books.add(returnbook);
                System.out.println("Successfully added: " + returnbook.shortString() + ".\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("No such file.\n");
        }
    }

    public void bookHistory(String serialNumber) {
        if (serialNumber == null) {
            return;
        }

        List<Member> mem = new ArrayList<>();
        if (!isBookExisted(serialNumber)) {
            System.out.println("No such book in system.\n");
            return;
        }
        for (Book book : books) {
            for (Action action : book.history) {
                if (action.getBook().getSerialNumber().equals(serialNumber)) {
                    if (action.getAction() == Action.RETURN) {
                        mem.add(action.getMember());
                    }
                }
            }
        }

        if (mem.isEmpty()) {
            System.out.println("No rental history.\n");
        } else {
            for (Member member : mem) {
                System.out.println(member.getMemberNumber());
            }
            System.out.println();
        }
    }

    public void getBook(String serialNumber, boolean fullString) {
        if (books.isEmpty()) {
            System.out.println("No books in system.\n");
            return;
        } if (!isBookExisted(serialNumber)) {
            System.out.println("No such book in system.\n");
            return;
        }

        if (fullString == false) {
            for (Book book : books) {
                if (book.getSerialNumber().equals(serialNumber)) {
                    System.out.println(book.shortString());
                }
            }
            System.out.println();
        }

        else {
            for (Book book : books) {
                if (book.getSerialNumber().equals(serialNumber)) {
                    System.out.println(book.longString());
                }
            }
            System.out.println();
        }
    }

    public void getBooksByAuthor(String author) {
        if (books.isEmpty()) {
            System.out.println("No books in system.\n");
            return;
        }

        List<Book> bks = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().equals(author)) {
                bks.add(book);
            }
        }

        if (bks.isEmpty()) {
            System.out.println("No books by " + author + ".\n");
        } else {
            for (Book book : bks) {
                System.out.println(book.shortString());
            }
            System.out.println();
        }
    }


    public void getBooksByGenre(String genre) {
        if (books.isEmpty()) {
            System.out.println("No books in system.\n");
            return;
        }

        List<Book> bks = new ArrayList<>();
        for (Book book : books) {
            if (book.getGenre().equals(genre)) {
                bks.add(book);
            }
        }

        if (bks.isEmpty()) {
            System.out.println("No books with genre " + genre + ".\n");
        } else {
            for (Book book : bks) {
                System.out.println(book.shortString());
            }
            System.out.println();
        }
    }

    public void getAuthors() {
        if (books.isEmpty()) {
            System.out.println("No books in system.\n");
            return;
        }

        Set<String> author = new HashSet<>();
        for (Book books : books) {
            author.add(books.getAuthor());
        }
        //Convert genre set to a list
        List<String> authorList = new ArrayList<>(author);
        Collections.sort(authorList);

        for (String strs : authorList) {
            System.out.println(strs);
        }
        System.out.println();
    }

    public void getGenres() {
        if (books.isEmpty()) {
            System.out.println("No books in system.\n");
            return;
        }

        Set<String> genre = new HashSet<>();
        for (Book books : books) {
            genre.add(books.getGenre());
        }
        //Convert genre set to a list
        List<String> genreList = new ArrayList<>(genre);
        Collections.sort(genreList);

        for (String strs : genreList) {
            System.out.println(strs);
        }
        System.out.println();
    }

    public void getCopies() {
        if (books.isEmpty()) {
            System.out.println("No books in system.\n");
            return;
        }

        Map<String, Integer> bookWithCopies = new HashMap<>();
        for (Book book : books) {
            if (bookWithCopies.containsKey(book.shortString())) {
                Integer numCopies = bookWithCopies.get(book.shortString());
                bookWithCopies.put(book.shortString(), numCopies + 1);
            } else {
                bookWithCopies.put(book.shortString(), 1);
            }
        }
        Map<String, Integer> treeMap = new TreeMap<>(bookWithCopies);
        for (Map.Entry<String, Integer> e : treeMap.entrySet()) {
            System.out.println(e.getKey() + ": " + e.getValue());
        }
        System.out.println();
    }

    public List<String> whoRentedTheBook(String serialNumber) {
        List<String> mem = new ArrayList<>();
        for (Book book : books) {
            for (Action action : book.history) {
                if (action.getBook().getSerialNumber().equals(serialNumber)) {
                    if (action.getAction() == Action.RETURN) {
                        mem.add(action.getMember().getMemberNumber());
                    }
                }
            }
        }
        return mem;
    }

    public boolean isSubset(List<String> container, String[] subset) {
        for (int i = 0; i < subset.length; i++) {
            if (!contains(container, subset[i])) {
                return false;
            }
        }

        return true;
    }

    public boolean contains(List<String> container, String element) {
        for (int j = 0; j < container.size(); j++) {
            if (container.get(j).equals(element)) {
                return true;
            }
        }

        return false;
    }
}
