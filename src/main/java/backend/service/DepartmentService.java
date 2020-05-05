package backend.service;

import backend.entity.Asset;
import backend.entity.Department;
import backend.entity.Location;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentService {
    public List<Department> getAll() {
        Connection conn = ConnectionService.getConnectionService().getConnection();
        List<Department> departments = new ArrayList<Department>();
        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * " +
                    "FROM asset_manager.public.department " +
                    "INNER JOIN asset_manager.public.location " +
                    "ON department.department_location = location.location_id");
            while (rs.next()) {
                Department department = new Department();
                department.setDepartment_id(rs.getInt("department_id"));
                department.setDepartment_name(rs.getString("department_name"));
                department.setDepartment_location(rs.getInt("department_location"));
                Location location = new Location();
                location.setLocation_id(rs.getInt("location_id"));
                location.setAddress(rs.getString("address"));
                location.setPostcode(rs.getInt("postcode"));
                location.setState(rs.getString("state"));
                department.setLocation(location);
                departments.add(department);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }
    public boolean createNew(List<Department> departments) {
        try {
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ConnectionService.getConnectionService().getCustomDataSource());
            for (Department department : departments) {
                MapSqlParameterSource parameterSource = new MapSqlParameterSource();
                parameterSource.addValue("department_name", department.getDepartment_name());
                parameterSource.addValue("department_location", department.getLocation().getLocation_id());
                jdbcTemplate.update("INSERT INTO asset_manager.public.department (department_name, department_location) " +
                                "VALUES (:department_name, :department_location)",
                        parameterSource
                );
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean update(Department department) {
        try {
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ConnectionService.getConnectionService().getCustomDataSource());
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("index", department.getDepartment_id());
            parameterSource.addValue("name", department.getDepartment_name());
            parameterSource.addValue("location", department.getLocation().getLocation_id());
            jdbcTemplate.update("UPDATE asset_manager.public.department"+
                            " SET department_name = :name, department_location = :location" +
                            " WHERE department_id = :index",
                    parameterSource
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(Department department) {
        Connection conn = ConnectionService.getConnectionService().getConnection();
        try {
            PreparedStatement sql = conn.prepareStatement(
                    "DELETE FROM department WHERE department_id=?"
            );
            sql.setInt(1, department.getDepartment_id());
            sql.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public int countAll() {
        Connection conn = ConnectionService.getConnectionService().getConnection();
        ResultSet rs;

        try {
            PreparedStatement sql = conn.prepareStatement(
                    "select count(department_id) as POCET\n" +
                            "from department"
            );
            rs = sql.executeQuery();
            rs.next();
            return rs.getInt("POCET");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public List<Department> getAllHib(){
        Session session = SessionFactoryProvider.getSessionFactoryProvider().getSessionFactory().openSession();
        Transaction tx = null;
        List <Department> departments = null;

        try {
            tx = session.beginTransaction();
            departments = session.createQuery("FROM backend.entity.Department").list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }
        return departments;
    }
}
