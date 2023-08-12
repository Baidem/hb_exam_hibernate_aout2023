package hb.exam.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public SessionFactory buildSessionFactory(){
        return new Configuration().configure().buildSessionFactory();
    }

    public SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    public void close(){
        sessionFactory.close();
    }
}
