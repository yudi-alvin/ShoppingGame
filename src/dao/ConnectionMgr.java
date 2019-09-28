package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
* <p>This is the connection manager class</p>
* @author  BB - BB Coder, BB No Sleep
* @version 1.0
* @since   2018-04
*/

public class ConnectionMgr {
    // init database constants
    private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/cookingDb?verifyServerCertificate=false&useSSL=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    // init connection object
    private Connection connection;
    // init properties object
    private Properties properties;

    // create properties
    private Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.setProperty("user", USERNAME);
            properties.setProperty("password", PASSWORD);
        }
        return properties;
    }

    /**
     * Connect to the database
     * @return a connection object
     */
    public Connection connect() {
        if (connection == null) {
            try {
                // loads the driver 
                Class.forName(DATABASE_DRIVER);
                connection = DriverManager.getConnection(DATABASE_URL, getProperties());
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                System.out.println("Unable to connect to database!");            
            }
        }
        return connection;
    }
    /**
     * Disconnect from the database
     */
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Connection to the database cannot be closed!");
            }
        }
    }

}