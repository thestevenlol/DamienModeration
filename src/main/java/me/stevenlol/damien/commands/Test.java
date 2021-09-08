package me.stevenlol.damien.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import me.stevenlol.damien.Main;
import org.bukkit.entity.Player;

public class Test extends BaseCommand {

    @CommandAlias("test")
    public void test(Player player) {
        String message = Main.getPlugin().getConfig().getString("ban-message-receiver");
        String newMessage = message.replace("%date%", "uagwdo");
        player.sendMessage(newMessage);
    }

}
