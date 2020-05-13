package backend.service;

import backend.entity.Asset;
import backend.entity.Department;
import backend.entity.Window;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WindowFunction {
    public List<Window> getAll(int offset, int limit){
        Connection conn = ConnectionService.getConnectionService().getConnection();
        ResultSet rs;
        List<Window> windows = new ArrayList<Window>();

        try {
            PreparedStatement sql = conn.prepareStatement(
                    "SELECT tmp.department_name, tmp.state, tmp.asset_count, tmp.rank " +
                            "FROM(select department_name, l.state, count(asset_id) as asset_count, " +
                            "DENSE_RANK() OVER (partition BY l.state ORDER BY count(asset_id) DESC) as rank " +
                            "FROM asset a " +
                            "JOIN department d on a.asset_department = d.department_id " +
                            "JOIN location l on d.department_location = l.location_id " +
                            "GROUP BY d.department_name, l.state) tmp " +
                            "WHERE RANK < 4 " +
                            "ORDER BY state ASC, asset_count Desc " +
                            "LIMIT ? " + "OFFSET ?"
            );
            sql.setInt(1, limit);
            sql.setInt(2, offset);
            rs = sql.executeQuery();

            while (rs.next()) {
                Window window = new Window();
//                asset.setAsset_id(rs.getInt("asset_id"));
                window.setDepartment_name(rs.getString("department_name"));
                window.setState(rs.getString("state"));
                window.setAsset_count(rs.getInt("asset_count"));
                window.setRank(rs.getInt("rank"));
                windows.add(window);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return windows;
    }

    public int countAll() {
        Connection conn = ConnectionService.getConnectionService().getConnection();
        ResultSet rs;
        try {
            PreparedStatement sql = conn.prepareStatement(
                    "SELECT count(tmp.department_name) as POCET " +
                            "FROM(select department_name, l.state, count(asset_id) as asset_count, " +
                            "DENSE_RANK() OVER (partition BY l.state ORDER BY count(asset_id) DESC) as rank " +
                            "FROM asset a " +
                            "JOIN department d on a.asset_department = d.department_id " +
                            "JOIN location l on d.department_location = l.location_id " +
                            "GROUP BY d.department_name, l.state) tmp " +
                            "WHERE RANK < 4 "
            );
            rs = sql.executeQuery();
            rs.next();
            return rs.getInt("POCET");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
