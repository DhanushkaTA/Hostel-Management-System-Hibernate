package lk.ijse.hms_hibernet.db;

import lk.ijse.hms_hibernet.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class FactoryConfiguration {
    public static FactoryConfiguration factoryConfiguration;

    private SessionFactory sessionFactory;

    private FactoryConfiguration(){
//        Configuration configuration=new Configuration().configure();
//
//        sessionFactory=configuration.buildSessionFactory();

        Configuration configuration = new Configuration();
        //configuring properties file
        Properties p = new Properties();
        try {
            p.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("Hibernate.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        configuration.setProperties(p);

        //configuring entities
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Student.class);
        configuration.addAnnotatedClass(Room.class);
        configuration.addAnnotatedClass(Reservation.class);
        configuration.addAnnotatedClass(Login_Data.class);

        sessionFactory = configuration.buildSessionFactory();
    }

    public static FactoryConfiguration getInstance(){
        return factoryConfiguration==null ? factoryConfiguration=new FactoryConfiguration() :
                factoryConfiguration;
    }

    public Session getSession(){
        return sessionFactory.openSession();
    }
}
