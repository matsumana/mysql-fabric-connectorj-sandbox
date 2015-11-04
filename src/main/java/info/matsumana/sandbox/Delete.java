package info.matsumana.sandbox;

import com.mysql.fabric.jdbc.FabricMySQLConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.IntStream;

/**
 * Fabricサーバに接続してデータを削除するツール
 *
 * @author matsumana
 */
public class Delete extends DatabaseAccess {

    public static void main(String... args) {

        String sql = "DELETE FROM table0 WHERE id = ?";

        try (FabricMySQLConnection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            IntStream.rangeClosed(1, 20)
                    .forEach(i -> {
                        try {
                            // shard key
                            conn.setShardKey(String.valueOf(i));

                            ps.setInt(1, i);

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
