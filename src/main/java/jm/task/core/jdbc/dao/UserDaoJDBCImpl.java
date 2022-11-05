package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {

    private static Connection connection;

    public UserDaoJDBCImpl() {
        connection = getConnection();
    }

    public void createUsersTable() {

        String sql = "CREATE TABLE IF NOT EXISTS user_zadaja ( id INT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(45) NULL, lastName VARCHAR(45) NULL, age INT NULL;";

        try (Statement statement = connection.createStatement()){
            connection.setAutoCommit(false);
            statement.execute(sql);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }



    }

    public void dropUsersTable() {

    }

    public void saveUser(String name, String lastName, byte age) {

    }

    public void removeUserById(long id) {

    }

    public List<User> getAllUsers() {
        return null;
    }

    public void cleanUsersTable() {

    }
}
