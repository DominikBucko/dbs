package backend.service;

import backend.entity.Asset;
import backend.entity.User;
import backend.entity.Department;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    public List<User> getAll(int offset, int limit) {
        Connection conn = ConnectionService.getConnectionService().getConnection();
        List<User> assets = new ArrayList<User>();
        try {
            ResultSet rs;
            PreparedStatement sql = conn.prepareStatement("SELECT user_id, first_name, surname, city , address, postcode, user_department, login, password, is_admin,\n" +
                            "department_name, count(user_info) from \"user\"\n" +
                            "INNER JOIN department d on user_department = d.department_id\n" +
                            "LEFT JOIN ticket t on user_id = t.user_info\n" +
                            "GROUP BY user_id, department_name " +
                            "LIMIT ? " + "OFFSET ?");
            sql.setInt(1, limit);
            sql.setInt(2, offset);
            rs = sql.executeQuery();
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
                department.setDepartment_id(rs.getInt("user_department"));
                department.setDepartment_name(rs.getString("department_name"));
                user.setDepartment(department);
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setIs_admin(rs.getBoolean("is_admin"));
//                user.setTicketCount(rs.getInt("count"));
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

    public void updateUser(User user) {
        try {
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ConnectionService.getConnectionService().getCustomDataSource());
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("id", user.getUser_id());
            parameterSource.addValue("fname", user.getFirst_name());
            parameterSource.addValue("surname", user.getSurname());
            parameterSource.addValue("city", user.getCity());
            parameterSource.addValue("address", user.getAddress());
            parameterSource.addValue("postcode", user.getPostcode());
            parameterSource.addValue("user_department", user.getDepartment().getDepartment_id());
            parameterSource.addValue("login", user.getLogin());
            parameterSource.addValue("password", user.getPassword());
            parameterSource.addValue("is_admin", user.getIs_admin());
            jdbcTemplate.update("UPDATE asset_manager.public.user" +
                            " SET first_name = :fname, surname = :surname, city = :city, address = :address, postcode = :postcode, user_department = :user_department, login = :login, password = :password, is_admin = :is_admin" +
                            " WHERE user_id = :id",
                    parameterSource
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<User> filterBy(String property, String toMatch) {
        ResultSet rs;
        Connection conn = ConnectionService.getConnectionService().getConnection();
        try {
            PreparedStatement sql = conn.prepareStatement(
                    "SELECT user_id, first_name, surname, city , address, postcode, user_department, login, password, is_admin,\n" +
                            "department_name, count(user_info) from \"user\"\n" +
                            "INNER JOIN department d on user_department = d.department_id\n" +
                            "LEFT JOIN ticket t on user_id = t.user_info\n" +
                            "WHERE (lower("+ property +")) LIKE lower(?)\n" +
//                            "AND time_returned IS NOT NULL\n" +
                            "GROUP BY user_id, department_name\n" +
                            "ORDER BY count(user_info) DESC;"
            );
            sql.setString(1, toMatch + "%");
            rs = sql.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<User>();
            }
        List<User> users = new ArrayList<User>();
        try {
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
//                department.setDepartment_id(rs.getInt("department_id"));
                department.setDepartment_name(rs.getString("department_name"));
                user.setDepartment(department);
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setIs_admin(rs.getBoolean("is_admin"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    
    public boolean delete(User user) {
        Connection conn = ConnectionService.getConnectionService().getConnection();
        try {
            PreparedStatement sql = conn.prepareStatement(
                    "DELETE FROM \"user\" WHERE user_id=?"
            );
            sql.setInt(1, user.getUser_id());
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
                    "select count(user_id) as POCET\n" +
                            "from \"user\""
            );
            rs = sql.executeQuery();
            rs.next();
            return rs.getInt("POCET");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public User getUserByUsername(String username) {
        return new User();
    }

    public List<User> getAllHib(){
        Session session = SessionFactoryProvider.getSessionFactoryProvider().getSessionFactory().openSession();
        Transaction tx = null;
        List <User> users = null;

        try {
            tx = session.beginTransaction();
            users = session.createQuery("FROM backend.entity.User").list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }
        return users;
    }
}
