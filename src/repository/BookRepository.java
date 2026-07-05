package repository;

import exception.DatabaseOperationException;
import exception.BookNotFoundException;
import model.Book;
import util.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        } catch (SQLException e) {
            throw new DatabaseOperationException("Book insertion failed: " + e.getMessage());
        }
    }

    public Book findBook(long id) {
        try (
                Connection connection = DatabaseConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM book WHERE id = ?")
        ) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Book(id, resultSet.getString("title"), resultSet.getString("author"), resultSet.getDouble("price"), resultSet.getInt("stock"));
            } else {
                throw new BookNotFoundException("Couldn't find book with id: " + id);
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Book find operation failed: " + e.getMessage());
        }
    }

}
