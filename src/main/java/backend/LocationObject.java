package backend;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LocationObject implements Table {
    Connection connection = null;
    List<Integer> location_id = new ArrayList<>();
    List<String> state = new ArrayList<>();
    List<String> address = new ArrayList<>();
    List<Integer> postcode = new ArrayList<>();

    public LocationObject(Connection conn){
        connection = conn;
    }


    @Override
    public void fillSelf() {
        Connection conn = this.connection;
        String query;
        ResultSet rs = null;
        query = "SELECT location_id, state, address, postcode FROM location";
        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()){
                location_id.add(rs.getInt("location_id"));
                state.add(rs.getString("state"));
                address.add(rs.getString("address"));
                postcode.add(rs.getInt("postcode"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNew(Connection conn, String state, String address, int postcode) throws SQLException {
                String query = "INSERT INTO public.location(state, address, postcode)" +
                "VALUES ("+"\'"+state+"\'"+", "+"\'"+address+"\'"+", "+postcode+");";
        Statement st = conn.createStatement();
        st.executeUpdate(query);
    }

    public void deleteByID(Connection conn, int id) throws SQLException {
        String query = "DELETE FROM public.location WHERE location_id = "+id+";";
        Statement st = conn.createStatement();
        st.executeUpdate(query);
    }

    @Override
    public void updateSelf() {

    }
}
