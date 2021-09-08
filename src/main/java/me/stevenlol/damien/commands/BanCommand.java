package me.stevenlol.damien.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import me.stevenlol.damien.Main;
import me.stevenlol.damien.sql.SQLUtils;
import me.stevenlol.damien.utils.Color;
import me.stevenlol.damien.utils.TimeFormat;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class BanCommand extends BaseCommand {

    @CommandAlias("ban")
    @CommandPermission("moderation.ban")
    @CommandCompletion("@OfflinePlayers @nothing")
    public void ban(Player player, String[] args) {
        switch (args.length) {
            case 0 -> player.sendMessage(Color.translate(Main.getPrefix() + "&c/ban <player> <reason>"));
            case 1 -> player.sendMessage(Color.translate(Main.getPrefix() + "&c/ban " + args[0] + " <reason>"));
            default -> {
                StringBuilder x = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    x.append(args[i]).append(" ");
                }
                String reason = x.toString().trim();

                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);


                if (!target.hasPlayedBefore()) {
                    player.sendMessage(Color.translate(Main.getPrefix() + "&c" + args[0] + " has never played on the server before."));
                    return;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String date = sdf.format(new Date());
                SQLUtils.isBanned(target, banned -> {
                    if (banned) {
                        player.sendMessage(Color.translate(Main.getPrefix() + "&c" + target.getName() + " is already banned."));
                        return;
                    }
                    SQLUtils.banPlayer(target, player, reason, date, -1);
                    if (target.isOnline()) {
                        String banMessage = Main.getPlugin().getConfig().getString("ban-message-receiver");
                        banMessage.replaceAll("%date%", date);
                        Bukkit.getScheduler().runTask(Main.getPlugin(), () -> target.getPlayer().kickPlayer(Color.translate(banMessage)));
                    }

                    if (Main.getPlugin().getConfig().getString("ban-message-broadcast") != null) {
                        String message = Main.getPlugin().getConfig().getString("ban-message-broadcast");
                        String newBanMessage = message.replaceAll("%reason%", reason)
                                .replaceAll("%date%", date)
                                .replaceAll("%duration%", "Permanent")
                                .replaceAll("%banner%", player.getName());
                        Bukkit.broadcastMessage(Color.translate(newBanMessage));
                    }

                    player.sendMessage(Color.translate(Main.getPrefix() + "&aSuccessfully banned " + target.getName() + "."));

                });

            }
        }
    }

}
