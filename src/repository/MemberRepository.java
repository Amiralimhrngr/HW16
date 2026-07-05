package repository;

import exception.DatabaseOperationException;
import exception.MemberNotFoundException;
import model.Member;
import util.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRepository {
    public int addMember(Member member) {
        try (
                Connection connection = DatabaseConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO member(full_name, phone) VALUES (?,?)")
        ) {
            preparedStatement.setString(1, member.getFullName());
            preparedStatement.setString(2, member.getPhone());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Member insertion failed: " + e.getMessage());
        }
    }

    public Member findMember(long id) {
        try (
                Connection connection = DatabaseConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM member WHERE id = ?")
        ) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Member(resultSet.getLong("id"), resultSet.getString("full_name"), resultSet.getString("phone"));
            } else {
                throw new MemberNotFoundException("Couldn't find member with id: " + id);
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Member find operation failed: " + e.getMessage());
        }
    }

    public int deleteMember(long id) {
        try (
                Connection connection = DatabaseConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM member WHERE id = ?")
        ) {
            preparedStatement.setLong(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0){
                throw new MemberNotFoundException("Couldn't find member with id: " + id);
            }
            return rowsAffected;
        } catch (SQLException e) {
            throw new DatabaseOperationException("Member delete operation failed: " + e.getMessage());
        }
    }
}
