package info.matsumana.sandbox;

import com.mysql.fabric.jdbc.FabricMySQLConnection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author matsumana
 */
public abstract class DatabaseAccess {

    static final String DRIVER = "com.mysql.fabric.jdbc.FabricMySQLDriver";
    static final String HOST = "localhost";
    static final int PORT = 32274;
    static final String DATABASE = "test";
    static final String SHARD_TABLE = "table0";
    // static final String FABRIC_USER = "admin";
    // static final String FABRIC_PASSWORD = "fabric";
    static final String USER = "fabric";
    static final String PASSWORD = "fabric";

    static FabricMySQLConnection getConnection() {

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = String.format("jdbc:mysql:fabric://%s:%d/%s",
                HOST, PORT, DATABASE);

        Properties properties = new Properties();
        // properties.put("fabricUsername", FABRIC_USER);
        // properties.put("fabricPassword", FABRIC_PASSWORD);
        properties.put("fabricShardTable", SHARD_TABLE);
        properties.put("fabricReportErrors", "true");
        properties.put("user", USER);
        properties.put("password", PASSWORD);

        try {
            return (FabricMySQLConnection) DriverManager.getConnection(url, properties);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
