package jm.task.core.jdbc.util;

import java.util.Properties;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/user_viktor";
    private  static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String dialect = "org.hibernate.dialect.MySQL5Dialect";
    private static  Connection connection;
    private static SessionFactory sessionFactory;


    public Util() {
    }

    public static Connection getConnection(){

        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL,USERNAME, PASSWORD);
            System.out.println("Connection NORM");

        } catch (ClassNotFoundException e) {
            System.out.println("Connection CATCH1");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection CATCH2");
            e.printStackTrace();
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties properties = new Properties();

                properties.put(Environment.DRIVER, DRIVER);
                properties.put(Environment.URL, URL);
                properties.put(Environment.USER, USERNAME);
                properties.put(Environment.PASS, PASSWORD);
                properties.put(Environment.DIALECT, dialect);

                properties.put(Environment.SHOW_SQL, true);
                properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                properties.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(properties);
                configuration.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                System.out.println("Connect OK");
            } catch (Throwable ex) {
                System.out.println("getSessionFactory PROVAL");
                ex.printStackTrace();
            }
        }
        return sessionFactory;
    }
 }
