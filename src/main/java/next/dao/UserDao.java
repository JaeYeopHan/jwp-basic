package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import core.jdbc.JdbcTemplate;
import core.jdbc.PreparedStatementCreator;
import core.jdbc.RowMapper;
import next.model.User;

public class UserDao {
    public void insert(User user) throws SQLException {
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        JdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPrepareStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
                return pstmt;
            }
        });
    }

    public void update(User user) throws SQLException {
        String sql = "UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?";
        JdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPrepareStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, user.getPassword());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getUserId());
                return pstmt;
            }
        });
    }

    public List<User> findAll() throws SQLException {
        String sql = "SELECT * FROM USERS";
        return JdbcTemplate.execute(sql);
    }

    public User findByUserId(String userId) throws SQLException {
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        return JdbcTemplate.executeForObject(sql, (ResultSet rs) ->
                new User(rs.getString("userId"),
                         rs.getString("password"),
                         rs.getString("name"),
                         rs.getString("email")),
                userId);
    }
}
