import java.util.*;

/**
 * Represents Members within the system. Instances have a name and member number.
 * Members can rent and return books books from the library, and remember their own rental transaction history.
 */
public class Member {
    private String name;
    private String memberNumber;

    public List<Action> history = new ArrayList<>();

    /**
     * Constructs a new Member object given a name and member number.
     *
     * @param name The name of the new member.
     * @param memberNumber The member number of the new member.
     */
    public Member(String name, String memberNumber) {
        this.name = name;
        this.memberNumber = memberNumber;
    }

    /**
     * Returns the name of the member.
     *
     * @return The name of the member.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the member number of the member.
     *
     * @return The member number of the member.
     */
    public String getMemberNumber() {
        return memberNumber;
    }

    /**
     * Rents the given book.
     *
     * If the book does not exist or it is already being rented, do nothing and return false.
     * Otherwise, set the renter of the book to this instance of member and return true.
     *
     * @param book The book to rent.
     *
     * @return The outcome of the rental transaction.
     */
    public boolean rent(Book book) {
        return Action.rent(this, book);
    }

    /**
     * Returns the book to the libary.
     *
     * If the book doesn't exist, or the member isn't renting the book, return false.
     * Otherwise, set the renter of the book to null, add it to the rental history, and return true.
     *
     * @param
     */
    public boolean relinquish(Book book) {
        return Action.relinquish(this, book);
    }

    /**
     * Returns all books rented by the member.
     */
    public void relinquishAll() {
        List<Book> books = new ArrayList<>();

        for (Action action : history) {
            if (Action.whoRenting(action.getBook()).equals(this)) {
                books.add(action.getBook());
            }
        }

        for (Book book : books) {
            Action.relinquish(this, book);
        }

    }

    /**
     * Returns the history of books rented, in the order they were returned.
     */
    public List<Book> history() {
        return Action.rented(this);
    }

    /**
     * Returns the list of books currently being rented, in the order they were rented.
     */
    public List<Book> renting() {
        return Action.renting(this);
    }

    /**
     * Returns the intersection of the members' histories, ordered by serial number.
     */
    public static List<Book> commonBooks(Member[] members) {
        return Action.commonBooks(members);
    }
    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member)) return false;
        Member member = (Member) o;
        return Objects.equals(memberNumber, member.memberNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberNumber);
    }
}
