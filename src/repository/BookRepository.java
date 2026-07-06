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
                return new Book(resultSet.getLong("id"), resultSet.getString("title"), resultSet.getString("author"), resultSet.getDouble("price"), resultSet.getInt("stock"));
            } else {
                throw new BookNotFoundException("Couldn't find book with id: " + id);
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Book find operation failed: " + e.getMessage());
        }
    }

    public Book updateBook(long id, double newPrice) {
        try (
                Connection connection = DatabaseConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE book SET price = ? WHERE id = ?")
        ) {
            preparedStatement.setDouble(1, newPrice);
            preparedStatement.setLong(2, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new BookNotFoundException("Couldn't find book with id: " + id);
            }
            return findBook(id);
        } catch (SQLException e) {
            throw new DatabaseOperationException("Book update operation failed: " + e.getMessage());
        }
    }

    public int deleteBook(long id) {
        try (
                Connection connection = DatabaseConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM book WHERE id = ?")
        ) {
            preparedStatement.setLong(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new BookNotFoundException("Couldn't find book with id: " + id);
            }
            return rowsAffected;
        } catch (SQLException e) {
            throw new DatabaseOperationException("Book delete operation failed: " + e.getMessage());
        }
    }

    public void showAllBooks() {
        try (
                Connection connection = DatabaseConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM book")
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                do {
                    System.out.printf("""
                            BOOK
                            ID: %d
                            Title: %s
                            Author: %s
                            Price: $%.2f
                            Stock: %d
                            """, resultSet.getLong(1), resultSet.getString(2),
                            resultSet.getString(3), resultSet.getDouble(4), resultSet.getInt(5));
                } while (resultSet.next());
            } else {
                throw new BookNotFoundException("No books in database");
            }
        } catch (SQLException e){
            throw new DatabaseOperationException(e.getMessage());
        }
    }
}
