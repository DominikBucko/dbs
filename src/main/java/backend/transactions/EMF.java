package backend.transactions;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;

public class EMF {

    public static SessionFactory getSessionFactory()
    {
        try {
            File configFile = new File("resources/hibernate.cfg.xml");
            Configuration cfg = new Configuration();
            cfg.configure(configFile);
            SessionFactory factory = cfg.buildSessionFactory();
            return factory;
        } catch (Exception exception) {
            System.out.println("Failed to create sessionFactory");
        }
        return null;
    }

}
