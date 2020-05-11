package backend.service;

import backend.entity.*;
import com.vaadin.flow.component.textfield.IntegerField;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class AssetFaultService {
    public List<AssetFault> getAll(int offset, int limit) {
        Session session = SessionFactoryProvider.getSessionFactoryProvider().getSessionFactory().getCurrentSession();
        List returned = null;
       if (limit == 2147483647) {
            Transaction tx = session.beginTransaction();
            List<AssetFault> count = new ArrayList<>();
            Long c = (Long) session.createQuery("select count(a) from AssetFault a where a.fix_time = null and a.fixable = true").getSingleResult();
            tx.commit();
            for (int i = 0; i < c; i++) {
                count.add(new AssetFault());
            }
            return count;
        }
        try {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("FROM backend.entity.AssetFault a where a.fix_time = null and a.fixable = true");
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            returned = query.list();
            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
        }
        return mapList(returned);
    }

    public boolean dropFromService(int asset_id) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ConnectionService.getConnectionService().getCustomDataSource());
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("index", asset_id);
        try {
            jdbcTemplate.update("UPDATE asset_manager.public.asset" +
                            " SET status = 'FREE' " +
                            " WHERE asset_id = :index",
                    parameterSource
            );
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        markAsFixed(asset_id);
        return true;
    }

    public boolean markAsFixed(int asset_id) {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        Connection conn = ConnectionService.getConnectionService().getConnection();
        try {
            PreparedStatement sql = conn.prepareStatement(
                    "UPDATE asset_manager.public.asset_fault" +
                            " SET fix_time = ?" +
                            " WHERE asset_id = ?"
            );
            sql.setDate(1, date);
            sql.setInt(2, asset_id);
            sql.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean passToService(int asset_id, int fault_id, Boolean fixable) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ConnectionService.getConnectionService().getCustomDataSource());
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("index", asset_id);
        try {
            jdbcTemplate.update("UPDATE asset_manager.public.asset" +
                            " SET status = 'IN SERVICE' " +
                            " WHERE asset_id = :index",
                    parameterSource
            );
            addToAssetFaultService(asset_id, fault_id, fixable);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addToAssetFaultService(int asset_id, int fault_id, Boolean fixable){
        ResultSet rs;
        Connection conn = ConnectionService.getConnectionService().getConnection();
        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        try {
            PreparedStatement sql = conn.prepareStatement(
                    "INSERT INTO asset_fault(fault_id, asset_id, time_of_failure, fixable)" +
                            "VALUES (?, ?, ?, ?);"
            );
            sql.setInt(1, fault_id);
            sql.setInt(2, asset_id);
            sql.setDate(3, date);
            sql.setBoolean(4, fixable);
            sql.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<AssetFault> filter(int offset, int limit, String department_name) {
        Session session = SessionFactoryProvider.getSessionFactoryProvider().getSessionFactory().getCurrentSession();
        List returned = null;
        try {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("FROM backend.entity.AssetFault assetf where " +
                        "lower(assetf.asset.department.department_name) like lower(:department)");
            query.setString("department", department_name + "%");
//            query.setFirstResult(offset); NEJDE
//            query.setMaxResults(limit); NEJDE
            returned = query.list();
            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
        }
        return mapList(returned);
    }

    public List<AssetFault> mapList(List returned) {
        List<AssetFault> mapped = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        for (int i = 0; i < returned.size(); i++) {
            mapped.add(mapper.map(returned.get(i), AssetFault.class));
        }
        return mapped;
    }
}
