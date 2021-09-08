package me.stevenlol.damien.tasks;

import me.stevenlol.damien.Main;
import me.stevenlol.damien.sql.SQLUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class MuteTicker {

    public static void run() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getPlugin(), () -> {
            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {

            });
        }, 0, 20);
    }

}
