package backend;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DepartmentObject implements Table {
    Connection connection = null;
    List<Integer> department_id = new ArrayList<>();
    List<String> name = new ArrayList<>();
    List<Integer> location = new ArrayList<>();

    public DepartmentObject(Connection conn){
        connection = conn;
    }


    @Override
    public void fillSelf() {
        Connection conn = this.connection;
        String query;
        ResultSet rs = null;
        query = "SELECT department_id, department_name, department_location FROM department";
        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()){
                department_id.add(rs.getInt("department_id"));
                name.add(rs.getString("department_name"));
                location.add(rs.getInt("department_location"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNew(Connection conn, String name, int location) throws SQLException {
                String query = "INSERT INTO public.department(department_name, department_location)" +
                "VALUES ("+"\'"+name+"\'"+", "+location+");";
        Statement st = conn.createStatement();
        st.executeUpdate(query);
    }

    public void deleteByID(Connection conn, int id) throws SQLException {
        String query = "DELETE FROM public.department WHERE department_id = "+id+";";
        Statement st = conn.createStatement();
        st.executeUpdate(query);
    }

    @Override
    public void updateSelf() {

    }
}
