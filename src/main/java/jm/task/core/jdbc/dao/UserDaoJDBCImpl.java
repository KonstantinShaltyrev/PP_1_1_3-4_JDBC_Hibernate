package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    final static private String SAVE_USER = "INSERT INTO utilTestDB (name, lastName, age) VALUES(?, ?, ?)";
    final static private String GET_ALL = "SELECT * FROM utilTestDB";
    final static private String DELETE_ID = "DELETE FROM utilTestDB WHERE id = ?";
    final static private String DELETE_ALL = "TRUNCATE utilTestDB";
    final static private String DELETE_TABLE = "DROP TABLE utilTestDB";
    final static private String CREATE_TABLE = "CREATE TABLE utilTestDB " +
                                        "(id BIGINT not null PRIMARY KEY AUTO_INCREMENT, " +
                                        " name VARCHAR(45) not null, " +
                                        " lastName VARCHAR(45) not null, " +
                                        " age TINYINT not null)"; //+ " PRIMARY KEY ( id ))";

    private Connection connection = new Util().getConnection();


    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {

        try (Statement statement = connection.createStatement()) {

            if (!connection
                    .getMetaData()
                    .getTables(null, "jdbc_task_core", "utiltestdb", new String[] {"Table"})
                    .next()) {

                statement.executeUpdate(CREATE_TABLE);
            }

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void dropUsersTable() {

        try (Statement statement = connection.createStatement()) {

            if (connection.getMetaData()
                    .getTables(null, "jdbc_task_core", "utiltestdb", new String[] {"Table"})
                    .next()) {

                statement.executeUpdate(DELETE_TABLE);
            }

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.execute();

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void removeUserById(long id) {

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ID)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public List<User> getAllUsers() {

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)) {

            ResultSet res = preparedStatement.executeQuery();
            List<User> list = new ArrayList<>();

            while (res.next()) {

                User user = new User(res.getString("name"), res.getString("lastName"), res.getByte("age"));

                user.setId(res.getLong("id"));
                list.add(user);
            }

            return list;

        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return null;
    }

    public void cleanUsersTable() {

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL)) {

            preparedStatement.executeUpdate();

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}
