package me.stevenlol.damien.sql;

import me.stevenlol.damien.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUtils {

    public static void banPlayer(OfflinePlayer player, Player banner, String reason, String date, int duration) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
           try (PreparedStatement statement = Main.getSql().createStatement("INSERT INTO damien_player_bans (UUID,BANNER,REASON,DURATION,DATE) VALUES (?,?,?,?,?)")) {
               statement.setString(1, player.getUniqueId().toString());
               statement.setString(2, banner.getUniqueId().toString());
               statement.setString(3, reason);
               statement.setInt(4, duration);
               statement.setString(5, date);
               statement.executeUpdate();
           } catch (SQLException e) {
               System.out.println("===================================");
               e.printStackTrace();
               System.out.println("===================================");
           }
        });
    }

    public static void isBanned(OfflinePlayer player, Callback<Boolean> banned) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
            try (PreparedStatement statement = Main.getSql().createStatement("SELECT * FROM damien_player_bans WHERE UUID = ? AND DURATION > 0 OR DURATION != -1")) {
                statement.setString(1, player.getUniqueId().toString());
                ResultSet set = statement.executeQuery();
                banned.callback(set.next());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

}
