package backend.service;

import backend.entity.Department;
import backend.entity.Department;
import backend.entity.Location;
import backend.transactions.ConnectionService;

import java.sql.Connection;
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
}
