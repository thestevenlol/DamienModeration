package me.stevenlol.damien;

public class Config {

    public Config(Main plugin) {
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveDefaultConfig();
    }

}
