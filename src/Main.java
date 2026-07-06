import exception.BookNotFoundException;
import exception.DatabaseOperationException;
import exception.MemberNotFoundException;
import model.Book;
import model.Member;
import repository.BookRepository;
import repository.MemberRepository;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookRepository bookRepository = new BookRepository();
        MemberRepository memberRepository = new MemberRepository();

        Book book1 = new Book("Clean Code", "Robert C. Martin", 45.50, 10);
        Book book2 = new Book("Effective Java", "Joshua Bloch", 55.00, 7);
        Book book3 = new Book("Head First Java", "Kathy Sierra", 39.99, 12);
        Book book4 = new Book("Java: The Complete Reference", "Herbert Schildt", 60.00, 5);
        Book book5 = new Book("Design Patterns", "Erich Gamma", 49.75, 8);
        bookRepository.addBook(book1);
        bookRepository.addBook(book2);
        bookRepository.addBook(book3);
        bookRepository.addBook(book4);
        bookRepository.addBook(book5);

        Member member1 = new Member("John Doe", "09121234567");
        Member member2 = new Member("Jane Doe", "09129876543");
        Member member3 = new Member("Mike Tyson", "09351234567");
        memberRepository.addMember(member1);
        memberRepository.addMember(member2);
        memberRepository.addMember(member3);

        menu:
        while (true) {
            System.out.println("""
                    ==================== LIBRARY MANAGEMENT ====================
                    1. Add Book
                    2. Add Member
                    3. Update the Price of a Book
                    4. Delete a Book
                    5. Delete a Member
                    6. Exit
                    """);
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Please enter book's name: ");
                    String bookName = scanner.nextLine();
                    System.out.println("Please enter book's author: ");
                    String bookAuthor = scanner.nextLine();
                    System.out.println("Please enter book's price: ");
                    double bookPrice = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.println("Please enter the initial stock: ");
                    int bookStock = scanner.nextInt();
                    scanner.nextLine();
                    try {
                        int booksAdded = bookRepository.addBook(new Book(bookName, bookAuthor, bookPrice, bookStock));
                        if (booksAdded > 0) {
                            System.out.println("Book added successfully!");
                        }
                    } catch (DatabaseOperationException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Please Enter Member's Full Name: ");
                    String memberName = scanner.nextLine();
                    System.out.println("Please Enter Member's Phone: ");
                    String memberPhone = scanner.nextLine();
                    try {
                        int membersAdded = memberRepository.addMember(new Member(memberName, memberPhone));
                        if (membersAdded > 0) {
                            System.out.println("Member added successfully!");
                        }
                    } catch (DatabaseOperationException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Please Enter Book's ID: ");
                    long bookId = scanner.nextLong();
                    scanner.nextLine();
                    System.out.println("Please Enter Book's New Price: ");
                    double newPrice = scanner.nextDouble();
                    scanner.nextLine();
                    try {
                        Book updatedBook = bookRepository.updateBook(bookId, newPrice);
                        if (updatedBook != null) {
                            System.out.printf("Book updated successfully! Book with ID: %d price set to $%.2f",
                                    updatedBook.getId(), updatedBook.getPrice());
                        }
                    } catch (BookNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("Please Enter Book's Id: ");
                    long bookToDeleteId = scanner.nextLong();
                    scanner.nextLine();
                    try {
                        int booksDeleted = bookRepository.deleteBook(bookToDeleteId);
                        if (booksDeleted > 0) {
                            System.out.println("Book deleted successfully!");
                        }
                    } catch (BookNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Please Enter Member's Id: ");
                    long memberToDeleteId = scanner.nextLong();
                    scanner.nextLine();
                    try {
                        int membersDeleted = memberRepository.deleteMember(memberToDeleteId);
                        if (membersDeleted > 0) {
                            System.out.println("Member Deleted Successfully!");
                        }
                    } catch (MemberNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 6:
                    scanner.close();
                    System.out.println("Exiting...");
                    break menu;
                default:
                    System.out.println("Please Enter a Valid Number!");
            }
        }
    }
}
