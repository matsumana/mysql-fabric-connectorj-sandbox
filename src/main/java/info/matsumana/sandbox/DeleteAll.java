package info.matsumana.sandbox;

import com.mysql.fabric.jdbc.FabricMySQLConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Fabricサーバに接続してデータを削除するツール
 *
 * @author matsumana
 */
public class DeleteAll extends DatabaseAccess {

    public static void main(String... args) {

        // shard keyを指定してないので、グローバルグループのmasterノードにだけSQLが実行され、その他のノードにはレプリケーションで伝播する
        String sql = "DELETE FROM table0";

        try (FabricMySQLConnection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
