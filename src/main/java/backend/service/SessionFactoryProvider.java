package backend.service;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SessionFactoryProvider {
    private static SessionFactoryProvider sessionFactoryProvider = new SessionFactoryProvider();
    private static SessionFactory sessionFactory;

    private SessionFactoryProvider() {
        createConnection();
    }

    private void createConnection() {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    public SessionFactory getSessionFactory() {
        try {
            if (sessionFactory.isClosed()) {
                createConnection();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }



    public static SessionFactoryProvider getSessionFactoryProvider() {
        return sessionFactoryProvider;
    }
}
