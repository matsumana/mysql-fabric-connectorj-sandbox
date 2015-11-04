package info.matsumana.sandbox;

import com.mysql.fabric.jdbc.FabricMySQLConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Fabricサーバに接続してデータを表示するツール
 *
 * @author matsumana
 */
public class Select extends DatabaseAccess {

    public static void main(String... args) {

        String sql = "SELECT * FROM table0 WHERE id = ?";

        try (FabricMySQLConnection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // shard key
            int shardKey = 10;
            conn.setShardKey(String.valueOf(shardKey));

            ps.setInt(1, shardKey);
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
