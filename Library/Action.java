import java.util.*;

public class Action {
    public static final boolean RENT = true;
    public static final boolean RETURN = false;

    private final Member member;
    private final boolean action;  // RENT/RETURN
    private final Book book;

    private Action(Member member, boolean action, Book book) {
        this.member = member;
        this.action = action;
        this.book = book;
    }

    public Member getMember() {
        return member;
    }

    public boolean getAction() {
        return action;
    }

    public Book getBook() {
        return book;
    }

    public static void addHistory(Member member, boolean action, Book book) {
        Action a = new Action(member, action, book);
        member.history.add(a);
        book.history.add(a);
    }

    public static boolean rent(Member member, Book book) {
        if (book == null || member == null) {
            return false;
        }

        boolean beingRented = isRented(book);
        if (beingRented) {
            return false;
        }

        addHistory(member, Action.RENT, book);
        return true;
    }

    public static boolean isRented(Book book) {
        if (book == null) {
            return false;
        }

        boolean beingRented = false;

        for (Action action : book.history) {
            beingRented = action.getAction() == Action.RENT;
        }

        return beingRented;
    }

    public static boolean relinquish(Member member, Book book) {
        if (member == null || book == null) {
            return false;
        }

        boolean beingRented = isRented(book);
        if (!beingRented) {
            return false;
        }

        Member renter = whoRenting(book);
        if (renter == null) {
            return false;
        }

        if (!renter.equals(member)) {
            return false;
        }

        addHistory(member, Action.RETURN, book);
        return true;
    }

    public static Member whoRenting(Book book) {
        if (book == null) {
            return null;
        }

        Member member = null;
        for (Action action : book.history) {
            boolean beingRented = action.getAction() == Action.RENT;
            if (beingRented) {
                member = action.getMember();
            } else {
                member = null;
            }
        }

        return member;
    }

    public static List<Member> whoRented(Book book) {
        if (book == null) {
            return null;
        }

        List<Member> whoRent = new ArrayList<>();

        if (book.history.isEmpty()) {
            return whoRent;
        }
        for (Action action : book.history) {
            if (action.getAction() == Action.RETURN) {
                whoRent.add(action.getMember());
            }
        }

        return whoRent;
    }

    public static List<Book> rented(Member member) {
        if (member == null) {
            return null;
        }

        List<Book> rentedBooks = new ArrayList<>();

        if (member.history.isEmpty()) {
            return rentedBooks;
        }

        for (Action action : member.history) {
            if (action.getAction() == Action.RETURN) {
                rentedBooks.add(action.getBook());
            }
        }

        return rentedBooks;
    }

    public static List<Book> renting(Member member) {
        if (member == null) {
            return null;
        }

        List<Book> currentBooks = new ArrayList<>();

        for (Action action : member.history) {
            if (action.getAction() == Action.RENT) {
                currentBooks.add(action.getBook());
            } else {
                currentBooks.remove(action.getBook());
            }
        }

        return currentBooks;
    }

    public static List<Book> commonBooks(Member[] members) {
        if (members == null) {
            return null;
        }

        Map<Book, Set<Member>> book_members = new HashMap<>();

        // Check if there is null member in array.
        for (Member member : members) {
            if (member == null) {
                return null;
            }
        }

        // Loop through member return history
        for (Member member : members) {
            for (Action action : member.history) {
                if (action.getAction() == Action.RETURN) {
                    Book book = action.getBook();
                    Set<Member> ms = book_members.get(book);

                    if (ms == null) {
                        ms = new HashSet<>();
                        book_members.put(book, ms);
                    }

                    ms.add(action.getMember());
                }
            }
        }

        List<Book> mostReadBooks = new ArrayList<>();
        for (Map.Entry<Book, Set<Member>> e : book_members.entrySet()) {
            if (e.getValue().size() >= members.length) {
                mostReadBooks.add(e.getKey());
            }
        }

        mostReadBooks.sort(Book.getCompBySerialNumber());
        return mostReadBooks;
    }

    public static boolean isSubset(Set<Member> container, Member[] subset) {
        for (int i = 0; i < subset.length; i++) {
            if (!container.contains(subset[i])) {
                return false;
            }
        }
        return true;
    }
}
