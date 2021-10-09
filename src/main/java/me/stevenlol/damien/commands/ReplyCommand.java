package me.stevenlol.damien.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import me.stevenlol.damien.Main;
import me.stevenlol.damien.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ReplyCommand extends BaseCommand {

    @CommandAlias("reply|r")
    public void reply(Player player, String[] args) {
        String prefix = Main.getPlugin().getConfig().getString("message-prefix");
        if (Main.getReplyMap().get(player.getUniqueId()) == null) {
            player.sendMessage(Color.translate(prefix + "&cYou have nobody to reply to!"));
            return;
        }
        Player target = Bukkit.getPlayer(Main.getReplyMap().get(player.getUniqueId()));
        String formatSend = Main.getPlugin().getConfig().getString("message-format-send");
        String formatGet = Main.getPlugin().getConfig().getString("message-format-get");
        String spyFormat = Main.getPlugin().getConfig().getString("spy-format");
        String errorMessage = Main.getPlugin().getConfig().getString("message-error");
        if (target == null) {
            player.sendMessage(Color.translate(prefix + "&c" + Bukkit.getOfflinePlayer(Main.getReplyMap().get(player.getUniqueId())).getName() + " is no longer online."));
            return;
        }

        String message;
        StringBuilder x = new StringBuilder();
        for (String m : args) {
            x.append(m).append(" ");
        }
        message = x.toString().trim();

        player.sendMessage(Color.translate(formatSend
                .replace("%target%", target.getDisplayName()))
                .replace("%message%", message));

        target.sendMessage(Color.translate(formatGet
                .replace("%sender%", player.getDisplayName()))
                .replace("%message%", message));

        Main.getReplyMap().put(target.getUniqueId(), player.getUniqueId());

        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            if (Main.getSpys().contains(onlinePlayer.getUniqueId())) {
                onlinePlayer.sendMessage(Color.translate(spyFormat
                        .replace("%sender%", player.getName())
                        .replace("%target%", target.getDisplayName()))
                        .replace("%message%", message));
            }
        });

    }

}
