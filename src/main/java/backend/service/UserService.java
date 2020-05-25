package backend.service;

import backend.entity.Asset;
import backend.entity.User;
import backend.entity.Department;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class UserService {

    private static final Logger LOGGER = Logger.getLogger(TicketService.class.getName());

    public List<User> getAll(int offset, int limit) {
        LOGGER.info("GETTING USERS");
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
            LOGGER.warning(e.getMessage());
        }
        return assets;
    }

    public boolean createNew(List<User> userList) {
        LOGGER.info("CREATING NEW USER");

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
        LOGGER.info("UPDATING USER");
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
            LOGGER.warning(e.getMessage());
        }
    }

    public List<User> filterBy(String property, String toMatch) {
        LOGGER.info("FILTERING USERS");
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
            LOGGER.warning(e.getMessage());
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
            LOGGER.warning(e.getMessage());
        }
        return users;
    }
    
    public boolean delete(User user) {
        LOGGER.info("DELETING USER");
        Connection conn = ConnectionService.getConnectionService().getConnection();
        try {
            PreparedStatement sql = conn.prepareStatement(
                    "DELETE FROM \"user\" WHERE user_id=?"
            );
            sql.setInt(1, user.getUser_id());
            sql.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.warning(e.getMessage());
            return false;
        }
        return true;
    }

    public int countAll() {
        LOGGER.info("COUNTING USERS");
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
            LOGGER.warning(e.getMessage());
        }
        return 0;
    }

    public User getUserByUsername(String username){
        LOGGER.info("FILTERING USER");
        Session session = SessionFactoryProvider.getSessionFactoryProvider().getSessionFactory().openSession();
        Transaction tx = null;
        List <User> users = null;
        User usersDTO = null;

        try {
            tx = session.beginTransaction();

            Query query = session.createQuery("FROM backend.entity.User user where user.login = :username");
            query.setParameter("username", username);
            users = query.list();
            ModelMapper modelMapper = new ModelMapper();
            usersDTO = modelMapper.map(users.get(0), User.class);

            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            LOGGER.warning(e.getMessage());
        }
        return usersDTO;
    }

    public String exportToCsv() {
        LOGGER.info("EXPORTING USERS TO CSV");
        Connection conn = ConnectionService.getConnectionService().getConnection();
        String filename = "/tmp/" + new Date().getTime() + "_" + "users" + ".csv";
        String file = "'" + filename + "'";
        try {
            PreparedStatement sql = conn.prepareStatement("COPY \"user\" TO "+ file +" WITH (FORMAT CSV , HEADER )");
            sql.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.warning(e.getMessage());
        }
        return filename;
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
            LOGGER.warning(e.getMessage());
        }
        return users;
    }
}
