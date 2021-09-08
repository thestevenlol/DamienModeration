package me.stevenlol.damien;

import co.aikar.commands.PaperCommandManager;
import me.stevenlol.damien.commands.BanCommand;
import me.stevenlol.damien.commands.Test;
import me.stevenlol.damien.commands.UnBanCommand;
import me.stevenlol.damien.listeners.BanListener;
import me.stevenlol.damien.sql.SQL;
import me.stevenlol.damien.tasks.BanTicker;
import me.stevenlol.damien.utils.CommandCompleter;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main plugin;
    private static SQL sql;
    private static String prefix;

    @Override
    public void onEnable() {
        new Config(this);
        plugin = this;
        sql = new SQL();
        prefix = getConfig().getString("prefix");
        PaperCommandManager manager = new PaperCommandManager(this);

        new CommandCompleter(manager);

        manager.registerCommand(new BanCommand());
        manager.registerCommand(new UnBanCommand());
        manager.registerCommand(new Test());

        getServer().getPluginManager().registerEvents(new BanListener(), this);

        BanTicker.run();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Main getPlugin() {
        return plugin;
    }
    public static SQL getSql() {
        return sql;
    }
    public static String getPrefix() { return prefix; }

}
