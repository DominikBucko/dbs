package backend.transactions;

import backend.Mapping;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

public class AssetsManager {


    public void get_assets() {
        EMF emf = new EMF();
        SessionFactory factory = emf.getSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;

        try {
//            tx = session.beginTransaction();
//            Query q = session.createNativeQuery("SELECT * FROM public.asset;");
//            List results = q.getResultList();
//            List<Object> assets =  q.getResultList();
            Connection connection = Mapping.getConnection("postgres", "BoboJeSmutny");
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM asset_manager.public.asset");
            rs.next();
//            System.out.println("Asset" + assets[0] + " " + assets[1]);
        }
        catch (Exception cause) {
            System.out.println("ERROR IN QUERY");
            throw new RuntimeException(cause);
        }
    }



}
