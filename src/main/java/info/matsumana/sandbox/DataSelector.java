package info.matsumana.sandbox;

import com.mysql.fabric.jdbc.FabricMySQLConnection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Fabricサーバに接続してデータ表示するツール
 *
 * @author matsumana
 */
public class DataSelector {

    static final String DRIVER = "com.mysql.fabric.jdbc.FabricMySQLDriver";
    static final String HOST = "localhost";
    static final int PORT = 32274;
    static final String DATABASE = "test";
    static final String SHARD_TABLE = "table0";
    static final String USER = "fabric";
    static final String PASSWORD = "fabric";

    public static void main(String... args) {

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = String.format("jdbc:mysql:fabric://%s:%d/%s?fabricShardTable=%s",
                HOST, PORT, DATABASE, SHARD_TABLE);

        try (FabricMySQLConnection conn = (FabricMySQLConnection) DriverManager.getConnection(url, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM test.table0 ORDER BY id")) {

            ResultSet result = ps.executeQuery();
            while (result.next()) {
                int id = result.getInt(1);
                String name = result.getString(2);

                System.out.println(id + "\t" + name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
