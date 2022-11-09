package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import javax.persistence.TypedQuery;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;


public class UserDaoHibernateImpl extends Util implements UserDao {
    private Transaction transaction;
    public UserDaoHibernateImpl() {

    }
    @Override
    public void createUsersTable() {

        String sql = "CREATE TABLE IF NOT EXISTS User (" + // НАЗВАНИЕ ТАБЛИЦЫ
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(45) , " +
                    "lastName VARCHAR(45), " +
                    "age INT )";

        try (Session session = getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        }catch (Exception e){
            if (transaction != null){transaction.rollback();}
            e.getStackTrace();
        }


    }

    @Override
    public void dropUsersTable() {

        String sql = "DROP TABLE IF EXISTS User";   // НАЗВАНИЕ ТАБЛИЦЫ

        try (Session session = getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        }catch (Exception e){
            if (transaction != null){transaction.rollback();}
            e.getStackTrace();
        }


    }

    private SessionFactory sessionFactory;
    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.getStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {

        try (Session session = getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            User user = session.load(User.class, id);
            session.delete(user);
            transaction.commit();
        }catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
            e.getStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT w FROM User w";
        try (Session session = getSessionFactory().openSession()){
            TypedQuery<User> query =
                    session.createQuery(sql, User.class);
            return query.getResultList();
        }
    }

    @Override
    public void cleanUsersTable() {
        String sql = "DELETE FROM User";
        try (Session session = getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.createQuery(sql).executeUpdate();
            transaction.commit();
        }catch (Exception e){
            if (transaction != null){transaction.rollback();}
            e.getStackTrace();
        }
    }
}
