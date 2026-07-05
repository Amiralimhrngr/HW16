package repository;

import exception.BookInsertionException;
import model.Book;
import util.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookRepository {

    public int addBook(Book book) {
        try (
                Connection connection = DatabaseConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO book (title, author, price, stock) VALUES (?,?,?,?);")
        ) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setDouble(3, book.getPrice());
            preparedStatement.setInt(4, book.getStock());
            return preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new BookInsertionException("Book insertion failed: " + e.getMessage());
        }
    }
}
