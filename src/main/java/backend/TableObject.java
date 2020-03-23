package backend;

import java.sql.Connection;
import java.sql.ResultSet;

public interface TableObject {
    public void fillSelf(ResultSet results, Connection conn);
    public void updateSelf(Connection conn);

}
