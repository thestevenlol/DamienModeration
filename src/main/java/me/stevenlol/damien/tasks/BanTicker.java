package me.stevenlol.damien.tasks;

import me.stevenlol.damien.Main;
import me.stevenlol.damien.sql.SQLUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class BanTicker {

    public static void run() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getPlugin(), () -> {
            for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                SQLUtils.isBanned(offlinePlayer, banned -> {
                    if (banned) {
                        SQLUtils.getBanDuration(offlinePlayer, duration -> SQLUtils.modifyBanDuration(offlinePlayer, duration - 1));
                    }
                });
            }
        }, 0, 20);
    }

}
