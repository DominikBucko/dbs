package backend;

import java.sql.Connection;
import java.sql.ResultSet;

public interface Table {
    public void fillSelf();
    public void updateSelf();
}
