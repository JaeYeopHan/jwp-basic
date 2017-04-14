package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jbee on 2017. 4. 12..
 */
public class JdbcTemplate {

    public static void update(String sql, Object... params) throws SQLException {
        updateQuery((Connection con) -> {
            PreparedStatement pstmt = con.prepareStatement(sql);
            setParameters(pstmt, params);
            return pstmt;
        });
    }

    private static void updateQuery(PreparedStatementCreator creator) throws SQLException {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = creator.createPrepareStatement(con)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {

        }
    }

    public static <T> List<T> queryForAll(String sql, RowMapper<T> rowMapper) throws SQLException {
        ResultSet rs = null;
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            List<T> results = new ArrayList<>();
            rs = pstmt.executeQuery();
            while (rs.next()) {
                results.add(rowMapper.mapRow(rs));
            }
            return results;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    public static <T> T queryForObject(String sql, RowMapper<T> rowMapper, String... arg) throws SQLException {
        ResultSet rs = null;
        try (Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            setParameters(pstmt, arg);
            rs = pstmt.executeQuery();
            T obj = null;
            while (rs.next()) {
                obj = rowMapper.mapRow(rs);
            }
            return obj;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    private static void setParameters(PreparedStatement pstmt, Object[] vargs) throws SQLException {
        for (int i = 0; i < vargs.length; i++) {
            pstmt.setString(i + 1, (String) vargs[i]);
        }
    }
}
