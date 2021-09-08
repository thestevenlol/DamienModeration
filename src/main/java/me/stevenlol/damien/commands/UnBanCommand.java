package me.stevenlol.damien.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import me.stevenlol.damien.Main;
import me.stevenlol.damien.sql.SQLUtils;
import me.stevenlol.damien.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class UnBanCommand extends BaseCommand {

    @CommandAlias("unban")
    @CommandPermission("moderation.unban")
    @CommandCompletion("@OfflinePlayers")
    public void ban(Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage(Color.translate(Main.getPrefix() + "&c/unban <player>"));
        } else {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            if (!target.hasPlayedBefore()) {
                player.sendMessage(Color.translate(Main.getPrefix() + "&c" + args[0] + " has never played on the server before."));
                return;
            }
            SQLUtils.isBanned(target, banned -> {
                if (!banned) {
                    player.sendMessage(Color.translate(Main.getPrefix() + "&c" + target.getName() + " is not banned."));
                    return;
                }
                SQLUtils.unBanPlayer(target);
                player.sendMessage(Color.translate(Main.getPrefix() + "&aSuccessfully unbanned " + target.getName() + "."));
            });
        }
    }

}
