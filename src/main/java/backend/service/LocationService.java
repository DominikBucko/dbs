package backend.service;

import backend.entity.Asset;
import backend.entity.Location;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class LocationService {

    private static final Logger LOGGER = Logger.getLogger(LocationService.class.getName());

    public List<Location> getAll(int offset, int limit) {
        LOGGER.info("GETTING ALL LOCATIONS");
        Connection conn = ConnectionService.getConnectionService().getConnection();
        List<Location> locations = new ArrayList<Location>();
        try {
            ResultSet rs;
            PreparedStatement sql = conn.prepareStatement("SELECT * " +
                    "FROM asset_manager.public.location " +
                    "LIMIT ? " +
                    "OFFSET ?");
            sql.setInt(1, limit);
            sql.setInt(2, offset);
            rs = sql.executeQuery();
            while (rs.next()) {
                Location location = new Location();
                location.setLocation_id(rs.getInt("location_id"));
                location.setState(rs.getString("state"));
                location.setAddress(rs.getString("address"));
                location.setPostcode(rs.getInt("postcode"));
                locations.add(location);
            }
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
        }
        return locations;
    }



    public int countAll() {
        LOGGER.info("COUNTING LOCATION");
        Connection conn = ConnectionService.getConnectionService().getConnection();
        ResultSet rs;

        try {
            PreparedStatement sql = conn.prepareStatement(
                    "select count(location_id) as POCET\n" +
                            "from location"
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

    public boolean createNew(List<Location> locations) {
        LOGGER.info("CREATING LOCATION");
        try {
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ConnectionService.getConnectionService().getCustomDataSource());
            for (Location location : locations) {
                MapSqlParameterSource parameterSource = new MapSqlParameterSource();
                parameterSource.addValue("state", location.getState());
                parameterSource.addValue("address", location.getAddress());
                parameterSource.addValue("postcode", location.getPostcode());
                jdbcTemplate.update("INSERT INTO asset_manager.public.location (state, address, postcode) " +
                                "VALUES (:state, :address , :postcode)",
                        parameterSource
                );
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.warning(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean update(Location location) {
        LOGGER.info("UPDATING LOCATION");
        try {
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ConnectionService.getConnectionService().getCustomDataSource());
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("index", location.getLocation_id());
            parameterSource.addValue("state", location.getState());
            parameterSource.addValue("address", location.getAddress());
            parameterSource.addValue("postcode", location.getPostcode());
            jdbcTemplate.update("UPDATE asset_manager.public.location"+
                            " SET state = :state, address = :address, postcode = :postcode" +
                            " WHERE location_id = :index",
                    parameterSource
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.warning(e.getMessage());
            return false;
        }
    }
    
    public boolean delete(Location location) {
        LOGGER.info("DELETING LOCATION");
        Connection conn = ConnectionService.getConnectionService().getConnection();
        try {
            PreparedStatement sql = conn.prepareStatement(
                    "DELETE FROM location WHERE location_id=?"
            );
            sql.setInt(1, location.getLocation_id());
            sql.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.warning(e.getMessage());
            return false;
        }
        return true;
    }

    public List<Location> getAllHib(){
        Session session = SessionFactoryProvider.getSessionFactoryProvider().getSessionFactory().openSession();
        Transaction tx = null;
        List <Location> locations = null;

        try {
            tx = session.beginTransaction();
            locations = session.createQuery("FROM backend.entity.Location").list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
        }
        return locations;
    }
}
