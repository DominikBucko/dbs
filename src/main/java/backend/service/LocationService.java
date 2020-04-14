package backend.service;

import backend.entity.Asset;
import backend.entity.Location;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocationService {

    public List<Location> getAll() {
        Connection conn = ConnectionService.getConnectionService().getConnection();
        List<Location> locations = new ArrayList<Location>();
        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * " +
                    "FROM asset_manager.public.location ");
            while (rs.next()) {
                Location location = new Location();
                location.setLocation_id(rs.getInt("location_id"));
                location.setState(rs.getString("state"));
                location.setAddress(rs.getString("address"));
                location.setPostcode(rs.getInt("postcode"));
                locations.add(location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locations;
    }

    public boolean createNew(List<Location> locations) {
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
            return false;
        }
        return true;
    }

    public boolean update(Location location) {
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
            return false;
        }
    }
    
    public boolean delete(Location location) {
        Connection conn = ConnectionService.getConnectionService().getConnection();
        try {
            PreparedStatement sql = conn.prepareStatement(
                    "DELETE FROM location WHERE location_id=?"
            );
            sql.setInt(1, location.getLocation_id());
            sql.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
