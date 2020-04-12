package backend.transactions;

import backend.Mapping;
import backend.entity.Asset;
import org.hibernate.*;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AssetsManager {


    public void get_assets() {
        EMF emf = new EMF();
        SessionFactory factory = emf.getSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Query q = session.createNativeQuery("SELECT * FROM \"asset\"");
            List<Object> assets =  q.getResultList();

//            Connection connection = Mapping.getConnection("postgres", "BoboJeSmutny");
//            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM asset_manager.public.asset");
//            while (rs.next()) {
//                Asset asset = new Asset();
//                asset.setAsset_id(rs.getInt("asset_id"));
//                asset.setName(rs.getString("name"));
//                asset.setType(rs.getString("type"));
//                asset.setQr_code(rs.getString("qr_code"));
//                asset.setAsset_category(rs.getString("asset_category"));
//                asset.setAsset_department(rs.getInt("asset_department"));
//                asset.setStatus(rs.getString("status"));
//                assets.add(asset);

//            System.out.println("Asset" + assets[0] + " " + assets[1]);
//            System.out.println("blablabla");

        }
        catch (Exception cause) {
            System.out.println("ERROR IN QUERY");
            throw new RuntimeException(cause);
        }
    }


}
