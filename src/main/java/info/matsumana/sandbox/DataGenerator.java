package info.matsumana.sandbox;

import com.mysql.fabric.jdbc.FabricMySQLConnection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.IntStream;

/**
 * Fabricサーバに接続してテストデータを生成するツール
 *
 * @author matsumana
 */
public class DataGenerator {

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
             PreparedStatement ps = conn.prepareStatement("INSERT INTO test.table0 VALUES(?, ?)")) {

            IntStream.rangeClosed(1, 20)
                    .forEach(i -> {
                        try {
                            conn.setShardKey(String.valueOf(i));
                            ps.setInt(1, i);
                            ps.setString(2, "hoge");
                            ps.executeUpdate();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
