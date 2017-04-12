package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Jbee on 2017. 4. 12..
 */
@FunctionalInterface
public interface PreparedStatementCreator {
    PreparedStatement createPrepareStatement(Connection con) throws SQLException;
}
