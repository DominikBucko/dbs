package backend.service;

import backend.entity.Asset;
import backend.entity.Department;
import backend.entity.Location;
import backend.entity.User;
import com.opencsv.CSVWriter;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.FileWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class AssetService {
    private static final Logger LOGGER = Logger.getLogger(AssetService.class.getName());

    public List<Asset> getAll(int offset, int limit) {
        LOGGER.info("GETTING ALL ASSETS");
        Connection conn = ConnectionService.getConnectionService().getConnection();
        List<Asset> assets = new ArrayList<Asset>();
        try {
            ResultSet rs;
            PreparedStatement sql = conn.prepareStatement(
                    "SELECT * " +
                            "FROM asset_manager.public.asset " +
                            "INNER JOIN asset_manager.public.department " +
                            "ON asset.asset_department = department.department_id " +
                            "LIMIT ? " + "OFFSET ?"
            );
            sql.setInt(1, limit);
            sql.setInt(2, offset);
            rs = sql.executeQuery();
            while (rs.next()) {
                Asset asset = new Asset();
                asset.setAsset_id(rs.getInt("asset_id"));
                asset.setName(rs.getString("name"));
                asset.setType(rs.getString("type"));
                asset.setQr_code(rs.getString("qr_code"));
                asset.setAsset_category(rs.getString("asset_category"));
                asset.setAsset_department(rs.getInt("asset_department"));
                asset.setStatus(rs.getString("status"));
                Department department = new Department();
                department.setDepartment_id(rs.getInt("department_id"));
                department.setDepartment_name(rs.getString("department_name"));
                asset.setDepartment(department);
                assets.add(asset);
            }
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
        }
        return assets;
    }
  
    public boolean createNew(List<Asset> assets) {
        LOGGER.info("CREATING ASSET");
        try {
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ConnectionService.getConnectionService().getCustomDataSource());
            for (Asset asset : assets) {
                MapSqlParameterSource parameterSource = new MapSqlParameterSource();
                parameterSource.addValue("name", asset.getName());
                parameterSource.addValue("type", asset.getType());
                parameterSource.addValue("qr_code", asset.getQr_code());
                parameterSource.addValue("category", asset.getAsset_category());
                parameterSource.addValue("department", asset.getDepartment().getDepartment_id());
                parameterSource.addValue("status", asset.getStatus());
                jdbcTemplate.update("INSERT INTO asset_manager.public.asset (\"name\", \"type\", qr_code, asset_category, asset_department, status) " +
                                "VALUES (:name, :type , :qr_code, :category, :department, :status)",
                        parameterSource
                );
                return true;
            }
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean update(Asset asset) {
        LOGGER.info("UPDATING ASSET");
        try {
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ConnectionService.getConnectionService().getCustomDataSource());
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("index", asset.getAsset_id());
            parameterSource.addValue("name", asset.getName());
            parameterSource.addValue("type", asset.getType());
            parameterSource.addValue("qr_code", asset.getQr_code());
            parameterSource.addValue("category", asset.getAsset_category());
            parameterSource.addValue("department", asset.getDepartment().getDepartment_id());
            parameterSource.addValue("status", asset.getStatus());
            jdbcTemplate.update("UPDATE asset_manager.public.asset" +
                            " SET \"name\" = :name, \"type\" = :type, qr_code = :qr_code, asset_category = :category, asset_department = :department, status = :status" +
                            " WHERE asset_id = :index",
                    parameterSource
            );
            return true;
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    public boolean delete(Asset asset) {
        LOGGER.info("DELETING ASSET");
        Connection conn = ConnectionService.getConnectionService().getConnection();
        try {
            PreparedStatement sql = conn.prepareStatement(
                    "DELETE FROM asset WHERE asset_id=?"
            );
            sql.setInt(1, asset.getAsset_id());
            sql.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.warning(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Counts all rows in the table.
     * @return Number of rows of given table
     */
    public int countAll() {
        LOGGER.info("COUNTING ASSETS");
        Connection conn = ConnectionService.getConnectionService().getConnection();
        ResultSet rs;

        try {
            PreparedStatement sql = conn.prepareStatement(
                    "select count(asset_id) as POCET\n" +
                            "from asset"
            );
            rs = sql.executeQuery();
            rs.next();
            return rs.getInt("POCET");
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
    public int countStats( String department, String status, String category) {
        LOGGER.info("COUNTING ASSET STATISTIC");
        Connection conn = ConnectionService.getConnectionService().getConnection();
        ResultSet rs;
        if (department == null) {
            department = "%";
        }
        try {
            PreparedStatement sql = conn.prepareStatement(
                    "select count(*) as POCET from (select \"name\", \"type\", asset_category, status, department_name, count(*) as \"SUM\" from asset\n" +
                            "JOIN department d on asset_department = department_id\n" +
                            "where d.department_name LIKE ? and status LIKE ? and asset_category LIKE ?\n" +
                            "group by \"name\",\"type\", asset_category, status, d.department_name " +
                            "order by \"SUM\" DESC) as tmp"
            );
            sql.setString(1, department);
            sql.setString(2, status);
            sql.setString(3, category);
            rs = sql.executeQuery();
            rs.next();
            return rs.getInt("POCET");
        }catch (SQLException e) {
            e.printStackTrace();
            LOGGER.warning(e.getMessage());
        }
        return 0;
    }
    public List<Asset> getStats(int offset, int limit, String department, String status, String category) {
        LOGGER.info("GETTING ALL ASSETS STATISTICS");
        Connection conn = ConnectionService.getConnectionService().getConnection();
        if (department == null) {
            department = "%";
        }
        List<Asset> assets = new ArrayList<Asset>();
        ResultSet rs;
        try {
            PreparedStatement sql = conn.prepareStatement(
                    "select \"name\", \"type\", asset_category, status, department_name, count(*) as \"SUM\" from asset\n" +
                            "JOIN department on asset_department = department_id\n" +
                            "where department_name LIKE ? and status LIKE ? and asset_category LIKE ?--REGEX\n" +
                            "group by \"name\",\"type\", asset_category, status, department_name\n" +
                            "order by \"SUM\" DESC\n" +
                            "LIMIT ?\n" +
                            "OFFSET ?"
            );
            sql.setString(1, department);
            sql.setString(2, status);
            sql.setString(3, category);
            sql.setInt(4, limit);
            sql.setInt(5, offset);
            rs = sql.executeQuery();

            while (rs.next()) {
                Asset asset = new Asset();
//                asset.setAsset_id(rs.getInt("asset_id"));
                asset.setName(rs.getString("name"));
                asset.setType(rs.getString("type"));
                asset.setAsset_category(rs.getString("asset_category"));
                asset.setStatus(rs.getString("status"));
                Department department1 = new Department();
                department1.setDepartment_name(rs.getString("department_name"));
                asset.setDepartment(department1);
                asset.setCount(rs.getInt("SUM"));
                assets.add(asset);
            }
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
        }
        return assets;
    }

    public List<Asset> filterBy(String property, String toMatch) {
        LOGGER.info("FILTERING ASSETS");
        ResultSet rs;
        Connection conn = ConnectionService.getConnectionService().getConnection();
        try {
            PreparedStatement sql = conn.prepareStatement(
                    "SELECT asset_id, \"name\", \"type\", qr_code , status, asset_category, department_name from \"asset\"\n" +
                            "INNER JOIN department d on asset_department = d.department_id\n" +
                            "WHERE (lower(" + property + ")) LIKE lower(?)\n" +
                            "GROUP BY asset_id, department_name;"
            );
            sql.setString(1, toMatch + "%");
            rs = sql.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Asset>();
        }
        ;

        List<Asset> assets = new ArrayList<Asset>();
        try {
            while (rs.next()) {
                Asset asset = new Asset();
                asset.setAsset_id(rs.getInt("asset_id"));
                asset.setName(rs.getString("name"));
                asset.setType(rs.getString("type"));
                asset.setQr_code(rs.getString("qr_code"));
                asset.setAsset_category(rs.getString("asset_category"));
//                asset.setAsset_department(rs.getInt("asset_department"));
                asset.setStatus(rs.getString("status"));
                Department department = new Department();
//                department.setDepartment_id(rs.getInt("department_id"));
                department.setDepartment_name(rs.getString("department_name"));
                asset.setDepartment(department);
                assets.add(asset);
            }
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
        }
        return assets;
    }

    public List<Asset> getAvailable(Department department, int limit, int offset) {
        LOGGER.info("GETTING ALL FREE ASSETS");
        Session session = SessionFactoryProvider.getSession();
        Transaction tx = null;
        List returned = null;
        List<Asset> assetsDTO = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        try {
            tx = session.beginTransaction();
            returned = session.createQuery("FROM Asset ass where ass.department.department_id = :department_id ")
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .setParameter("department_id", department.getDepartment_id())
                    .list();
            tx.commit();

            for (int i = 0; i < returned.size(); i++) {
                assetsDTO.add(mapper.map(returned.get(i), Asset.class));
            }
        }
        catch (HibernateException e) {
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
        }
        return assetsDTO;
    }

    public List<Asset> getAllHib(int offset, int limit){
        Session session = SessionFactoryProvider.getSessionFactoryProvider().getSessionFactory().openSession();
        Transaction tx = null;
        List <Asset> assets = null;

        try {
            tx = session.beginTransaction();
            assets = session.createQuery("FROM backend.entity.Asset as ass group by ass.asset_id")
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
        }
        return assets;
    }

    public Asset getByID(int ID) {
        LOGGER.info("FILTERING ASSETS BY ID");
        Session session = SessionFactoryProvider.getSessionFactoryProvider().getSessionFactory().openSession();
        Transaction tx = null;
        List returned;
        Asset asset = null;
        ModelMapper mapper = new ModelMapper();

        try {
            tx = session.beginTransaction();
            returned = session.createQuery("FROM Asset a where a.asset_id = :id")
                    .setParameter("id", ID)
                    .list();
             for (int i = 0; i < returned.size(); i++) {
                asset = mapper.map(returned.get(i), Asset.class);
            }
        } catch (HibernateException e) {
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
        }
        return asset;

    }
}
