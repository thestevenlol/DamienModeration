package me.stevenlol.damien.sql;

import me.stevenlol.damien.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public static void unBanPlayer(OfflinePlayer player) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
            try (PreparedStatement statement = Main.getSql().createStatement("DELETE FROM damien_player_bans WHERE UUID = ? AND DURATION > 0 OR DURATION = -1")) {
                statement.setString(1, player.getUniqueId().toString());
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
            try (PreparedStatement statement = Main.getSql().createStatement("SELECT * FROM damien_player_bans WHERE UUID = ? AND DURATION > 0 OR DURATION = -1")) {
                statement.setString(1, player.getUniqueId().toString());
                ResultSet set = statement.executeQuery();
                banned.callback(set.next());
            } catch (SQLException e) {
                System.out.println("===================================");
                e.printStackTrace();
                System.out.println("===================================");
            }
        });
    }

    public static void getBanDuration(OfflinePlayer player, Callback<Integer> callback) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
            try (PreparedStatement statement = Main.getSql().createStatement("SELECT DURATION FROM damien_player_bans WHERE UUID = ? AND DURATION > 0 OR DURATION = -1")) {
                statement.setString(1, player.getUniqueId().toString());
                ResultSet set = statement.executeQuery();
                if (set.next()) callback.callback(set.getInt(1));
            } catch (SQLException e) {
                System.out.println("===================================");
                e.printStackTrace();
                System.out.println("===================================");
            }
        });
    }

    public static void modifyBanDuration(OfflinePlayer player, int duration) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
            try (PreparedStatement statement = Main.getSql().createStatement("UPDATE damien_player_bans SET DURATION = ? WHERE UUID = ? AND DURATION > 0")) {
                statement.setInt(1, duration);
                statement.setString(2, player.getUniqueId().toString());
                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("===================================");
                e.printStackTrace();
                System.out.println("===================================");
            }
        });
    }

    public static void getBanInformation(OfflinePlayer player, Callback<String> callback) {
        try (PreparedStatement statement = Main.getSql().getConnection().prepareStatement("SELECT * FROM damien_player_bans WHERE UUID = ? AND DURATION > 0 OR DURATION = -1")) {
            statement.setString(1, player.getUniqueId().toString());
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    String bannerUUID = set.getString(2);
                    String reason = set.getString(3);
                    int durationRemaining = set.getInt(4);
                    String date = set.getString(5);
                    String finalString = bannerUUID + "---" + date + "---" + reason + "---" + durationRemaining;
                    callback.callback(finalString);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
