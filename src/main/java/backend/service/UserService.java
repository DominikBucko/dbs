package backend.service;

import backend.entity.User;
import backend.entity.Department;
import backend.transactions.ConnectionService;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.*;

@Service
public class UserService {
    public List<User> getAll() {
        Connection conn = ConnectionService.getConnectionService().getConnection();
        List<User> assets = new ArrayList<User>();
        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * from \"user\"" +
                    "INNER JOIN department d on \"user\".user_department = d.department_id");
            while (rs.next()) {
                User user = new User();
                user.setUser_id(rs.getInt("user_id"));
                user.setFirst_name(rs.getString("first_name"));
                user.setSurname(rs.getString("surname"));
                user.setCity(rs.getString("city"));
                user.setAddress(rs.getString("address"));
                user.setPostcode(rs.getInt("postcode"));
                user.setUser_department(rs.getInt("user_department"));
                Department department = new Department();
                department.setDepartment_id(rs.getInt("department_id"));
                department.setDepartment_name(rs.getString("department_name"));
                user.setDepartment(department);
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setIs_admin(rs.getBoolean("is_admin"));
                assets.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assets;
    }

    public boolean createNew(List<User> userList) {
        try {
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ConnectionService.getConnectionService().getCustomDataSource());
            for (User user : userList) {
                MapSqlParameterSource parameterSource = new MapSqlParameterSource();
                parameterSource.addValue("fname", user.getFirst_name());
                parameterSource.addValue("surname", user.getSurname());
                parameterSource.addValue("city", user.getCity());
                parameterSource.addValue("address", user.getAddress());
                parameterSource.addValue("postcode", user.getPostcode());
                parameterSource.addValue("user_department", user.getUser_department());
                parameterSource.addValue("login", user.getLogin());
                parameterSource.addValue("password", user.getPassword());
                parameterSource.addValue("is_admin", user.getIs_admin());
                jdbcTemplate.update("INSERT INTO asset_manager.public.user (first_name, surname, city, address, postcode, user_department, login, password, is_admin) " +
                                "VALUES (:fname, :surname, :city, :address, :postcode, :user_department, :login, :password, :is_admin)",
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
}
