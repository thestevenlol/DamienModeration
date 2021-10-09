package me.stevenlol.damien.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import me.stevenlol.damien.Main;
import me.stevenlol.damien.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MessageCommand extends BaseCommand {

    @CommandAlias("message|msg|m")
    @CommandCompletion("@players @nothing")
    public void message(Player player, String[] args) {
        String formatSend = Main.getPlugin().getConfig().getString("message-format-send");
        String formatGet = Main.getPlugin().getConfig().getString("message-format-get");
        String spyFormat = Main.getPlugin().getConfig().getString("spy-format");
        String errorMessage = Main.getPlugin().getConfig().getString("message-error");
        String prefix = Main.getPlugin().getConfig().getString("message-prefix");
        if (args.length == 0) {
            player.sendMessage(Color.translate(prefix + errorMessage));
        } else if (args.length == 1) {
            player.sendMessage(Color.translate(prefix + errorMessage));
        } else {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(Color.translate(prefix + "&cThe player " + args[0] + " does not exist."));
                return;
            }
            if (!target.isOnline()) {
                player.sendMessage(Color.translate(prefix + "&c" + target.getName() + " is not online."));
                return;
            }

            String message;
            StringBuilder x = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                x.append(args[i]).append(" ");
            }
            message = x.toString().trim();

            player.sendMessage(Color.translate(formatSend
                    .replace("%target%", target.getDisplayName()))
                    .replace("%message%", message));

            target.sendMessage(Color.translate(formatGet
                    .replace("%sender%", player.getDisplayName()))
                    .replace("%message%", message));

            Main.getReplyMap().put(target.getUniqueId(), player.getUniqueId());

            // spying
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

}
