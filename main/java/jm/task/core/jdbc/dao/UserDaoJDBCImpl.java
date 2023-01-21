package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    Connection connection = getConnection();
    PreparedStatement preparedStatement = null;

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sql = "CREATE TABLE kata" +
                "(ID INTEGER NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(255), " +
                "last_name VARCHAR(255), " +
                "age INTEGER, " +
                "PRIMARY KEY (id))";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {

    }


    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String sql = "INSERT INTO kata (name,last_name,age) VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLSyntaxErrorException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) throws SQLException {
        String sql = "DELETE FROM kata WHERE ID=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
        if (connection != null) {
            connection.close();
        }
    }

    public List<User> getAllUsers() throws SQLException, NullPointerException {
        List<User> getAllUsers1 = new ArrayList<>();
        String sql = "SELECT * FROM kata";
        try (Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                User user = new User();

                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_Name"));
                user.setAge(resultSet.getByte("age"));

                getAllUsers1.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return getAllUsers1;
    }

    public void cleanUsersTable() throws SQLException {
        String sql = "TRUNCATE TABLE kata";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        }
    }
}
