package backend.service;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SessionFactoryProvider {
    private static SessionFactoryProvider sessionFactoryProvider;
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

    /**
     * Returns hibernate SessionFactory and handles closed connections
     * @return hibernate SessionFactory
     */
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

    /**
     * Returns SessionFactoryProvider instance, if instance is not yet created, function creates one.
     * @return SessionFactoryProvider instance (Singleton)
     */
    public static SessionFactoryProvider getSessionFactoryProvider() {
        if (sessionFactoryProvider == null) {
            sessionFactoryProvider = new SessionFactoryProvider();
        }
        return sessionFactoryProvider;
    }

    /**
     * Provides a hibernate session while handling closed connections
     * @return hibernate Session
     */
    public static Session getSession() {
        Session session = getSessionFactoryProvider().getSessionFactory().getCurrentSession();
        if (session == null) {
            return getSessionFactoryProvider().getSessionFactory().openSession();
        }
        else return session;
    }
}