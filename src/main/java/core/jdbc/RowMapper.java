package core.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Jbee on 2017. 4. 12..
 */
@FunctionalInterface
public interface RowMapper<T> {
    T mapRow(ResultSet rs) throws SQLException;
}
