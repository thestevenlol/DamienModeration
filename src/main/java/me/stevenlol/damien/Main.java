package me.stevenlol.damien;

import co.aikar.commands.PaperCommandManager;
import me.stevenlol.damien.commands.BanCommand;
import me.stevenlol.damien.commands.MessageCommand;
import me.stevenlol.damien.commands.ReplyCommand;
import me.stevenlol.damien.commands.UnBanCommand;
import me.stevenlol.damien.listeners.BanListener;
import me.stevenlol.damien.sql.SQL;
import me.stevenlol.damien.tasks.BanTicker;
import me.stevenlol.damien.utils.CommandCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class Main extends JavaPlugin {

    private static Main plugin;
    private static SQL sql;
    private static String prefix;
    private static List<UUID> spys;
    private static HashMap<UUID, UUID> replyMap;

    @Override
    public void onEnable() {
        PaperCommandManager manager = new PaperCommandManager(this);

        new Config(this);
        new CommandCompleter(manager);

        spys = new ArrayList<>();
        plugin = this;
        sql = new SQL();
        replyMap = new HashMap<>();
        prefix = getConfig().getString("prefix");

        manager.registerCommand(new BanCommand());
        manager.registerCommand(new UnBanCommand());
        manager.registerCommand(new MessageCommand());
        manager.registerCommand(new ReplyCommand());

        getServer().getPluginManager().registerEvents(new BanListener(), this);

        BanTicker.run();
    }

    @Override
    public void onDisable() {
        spys.clear();
        replyMap.clear();
    }

    public static Main getPlugin() {
        return plugin;
    }
    public static SQL getSql() {
        return sql;
    }
    public static String getPrefix() { return prefix; }
    public static List<UUID> getSpys() { return spys; }
    public static HashMap<UUID, UUID> getReplyMap() { return replyMap; }

    /*
    todo: temp-ban, report, reports, mute, kick, warn, remove warn, un-mute, clear chat, spy, staff chat, notes
     */

}
