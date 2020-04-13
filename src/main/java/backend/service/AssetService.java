package backend.service;

import backend.entity.Asset;
import backend.entity.Department;
import backend.entity.User;
import backend.transactions.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AssetService {

    public List<Asset> getAll() {
        Connection conn = ConnectionService.getConnectionService().getConnection();
        List<Asset> assets = new ArrayList<Asset>();
        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * " +
                    "FROM asset_manager.public.asset " +
                    "INNER JOIN asset_manager.public.department " +
                    "ON asset.asset_department = department.department_id");
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
            e.printStackTrace();
        }
        return assets;
    }

    public boolean createNew(List<Asset> assets) {
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
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean update(Asset asset) {
        try {
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ConnectionService.getConnectionService().getCustomDataSource());
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("index", asset.getAsset_id());
            parameterSource.addValue("name", asset.getName());
            parameterSource.addValue("type", asset.getType());
            parameterSource.addValue("qr_code", asset.getQr_code());
            parameterSource.addValue("category", asset.getAsset_category());
            parameterSource.addValue("department", asset.getDepartment());
            parameterSource.addValue("status", asset.getStatus());
            jdbcTemplate.update("UPDATE asset_manager.public.asset"+
                            " SET name = :name, type = :type, qr_code = :qr_code, asset_category = :category, asset_department = :department, status = :status" +
                            " WHERE asset_id = :index",
                    parameterSource
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Asset> filterBy(String property, String toMatch) {
        return null;
    }

}
