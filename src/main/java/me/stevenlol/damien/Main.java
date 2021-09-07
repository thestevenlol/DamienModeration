package me.stevenlol.damien;

import co.aikar.commands.PaperCommandManager;
import me.stevenlol.damien.sql.SQL;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main plugin;
    private static SQL sql;
    private static String prefix;

    @Override
    public void onEnable() {
        plugin = this;
        sql = new SQL();
        prefix = getConfig().getString("prefix");
        Config config = new Config(this);
        PaperCommandManager manager = new PaperCommandManager(this);

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
