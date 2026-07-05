package repository;

import exception.DatabaseOperationException;
import model.Member;
import util.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
}
