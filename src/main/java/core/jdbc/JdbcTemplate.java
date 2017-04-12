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
    public static void update(PreparedStatementCreator creator) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = creator.createPrepareStatement(con);
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }

    public static <T> List<T> execute(String sql, RowMapper<T> rowMapper) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            List<T> results = new ArrayList<>();
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                results.add(rowMapper.mapRow(rs));
            }
            return results;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }

    public static <T> T executeForObject(String sql, RowMapper<T> rowMapper, String... arg) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            setParameters(pstmt, arg);
            rs = pstmt.executeQuery();

            Object obj = null;
            while (rs.next()) {
                obj = rowMapper.mapRow(rs);
            }
            return (T) obj;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }

    private static void setParameters(PreparedStatement pstmt, String[] vargs) throws SQLException {
        for (int i = 0; i < vargs.length; i++) {
            pstmt.setString(i + 1, vargs[i]);
        }
    }
}
