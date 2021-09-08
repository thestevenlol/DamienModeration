package me.stevenlol.damien.listeners;

import me.stevenlol.damien.Main;
import me.stevenlol.damien.sql.SQLUtils;
import me.stevenlol.damien.utils.Color;
import me.stevenlol.damien.utils.TimeFormat;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class BanListener implements Listener {

    @EventHandler
    public void asyncPlayerPreLogin(AsyncPlayerPreLoginEvent e) {

        try (PreparedStatement statement = Main.getSql().createStatement("SELECT * FROM damien_player_bans WHERE UUID = ? AND DURATION = -1")) {
            statement.setString(1, e.getUniqueId().toString());
            ResultSet set = statement.executeQuery();
            SQLUtils.getBanInformation(Bukkit.getOfflinePlayer(e.getUniqueId()), infoString -> {
                String[] infos = infoString.split("---");
                String reason = infos[2];
                String bannerUUID = infos[0];
                String date = infos[1];
                int duration = Integer.parseInt(infos[3]);

                String finalDur = duration > 0 ? TimeFormat.time(duration) : "Permanent";

                String banMessage = Main.getPlugin().getConfig().getString("ban-message-receiver");
                String newBanMessage = banMessage.replaceAll("%reason%", reason)
                        .replaceAll("%date%", date)
                        .replaceAll("%duration%", finalDur)
                        .replaceAll("%banner%", Bukkit.getOfflinePlayer(UUID.fromString(bannerUUID)).getName());

                if (set.next()) e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, Color.translate(newBanMessage));
            });
        } catch (SQLException ex) {
            System.out.println("===================================");
            ex.printStackTrace();
            System.out.println("===================================");
        }
    }
}
