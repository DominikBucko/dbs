package backend.service;

import backend.entity.Asset;
import backend.entity.Department;
import backend.transactions.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return false;
    }

    public boolean update(Asset asset) {
        return false;
    }

    public List<Asset> filterBy(String property, String toMatch) {
        return new ArrayList<Asset>();
    }

}
