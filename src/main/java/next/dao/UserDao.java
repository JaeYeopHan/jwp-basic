package next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import core.jdbc.JdbcTemplate;
import next.model.User;

public class UserDao {
    public void insert(User user) throws SQLException {
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        JdbcTemplate.update(sql, user.getUserId(), user.getPassword(),
                                      user.getName(), user.getEmail());
    }

    public void update(User user) throws SQLException {
        String sql = "UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?";
        JdbcTemplate.update(sql, user.getPassword(), user.getName(),
                                      user.getEmail(), user.getUserId());
    }

    public List<User> findAll() throws SQLException {
        String sql = "SELECT * FROM USERS";
        return JdbcTemplate.queryForAll(sql, (ResultSet rs) ->
                new User(rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")));
    }

    public User findByUserId(String userId) throws SQLException {
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        return JdbcTemplate.queryForObject(sql, (ResultSet rs) ->
                new User(rs.getString("userId"),
                         rs.getString("password"),
                         rs.getString("name"),
                         rs.getString("email")),
                userId);
    }
}
