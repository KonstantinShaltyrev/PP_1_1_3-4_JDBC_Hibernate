package jm.task.core.jdbc.util;

import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/jdbc_task_core?serverTimezone=UTC&autoReconnect=true&useSSL=false";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    private static Connection connection;

    public Util() {
        try {
            Driver driver = new Driver();

            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
