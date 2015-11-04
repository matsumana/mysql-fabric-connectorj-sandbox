package info.matsumana.sandbox;

import com.mysql.fabric.jdbc.FabricMySQLConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.IntStream;

/**
 * Fabricサーバに接続してデータを生成するツール
 *
 * @author matsumana
 */
public class Insert extends DatabaseAccess {

    public static void main(String... args) {

        String sql = "INSERT INTO table0 VALUES (?, ?)";

        try (FabricMySQLConnection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            IntStream.rangeClosed(1, 20)
                    .forEach(i -> {
                        try {
                            // shard key
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
