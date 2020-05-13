package backend;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AssetObject implements Table {
    Connection connection;
    List<Integer> asset_id = new ArrayList<>();
    List<String> name = new ArrayList<>();
    List<String> type = new ArrayList<>();
    List<String> category = new ArrayList<>();
    List<Integer> asset_department = new ArrayList<>();


    public AssetObject(Connection conn){
        connection = conn;
    }

    @Override
    public void fillSelf() {
        Connection conn = this.connection;
        String query;
        ResultSet rs = null;
        query = "SELECT asset_id, name, type, asset_category, asset_department FROM asset";
        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()){
                asset_id.add(rs.getInt("asset_id"));
                name.add(rs.getString("name"));
                type.add(rs.getString("type"));
                category.add(rs.getString("asset_category"));
                asset_department.add(rs.getInt("asset_department"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNew(Connection conn, String name, String type, String category, int department) throws SQLException {
        String query = "INSERT INTO public.asset(" +
                "name, type, asset_category, asset_department)" +
                "VALUES ("+"\'"+name+"\'"+", "+"\'"+type+"\'"+", "+"\'"+category+"\'"+", "+department+");";
        Statement st = conn.createStatement();
        st.executeUpdate(query);
    }

    public void deleteByID(Connection conn, int id) throws SQLException {
        String query = "DELETE FROM public.asset WHERE asset_id = "+id+";";
        Statement st = conn.createStatement();
        st.executeUpdate(query);
    }

    @Override
    public void updateSelf() {

    }
}
