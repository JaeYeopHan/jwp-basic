package core.jdbc;

import java.sql.Connection;

/**
 * Created by Jbee on 2017. 4. 12..
 */
public abstract class PrepareStatementCreator {

    public PrepareStatementCreator() {
        Connection con = ConnectionManager.getConnection();
    }
    public abstract PrepareStatementCreator createPrepareStatement(Connection con);
}
