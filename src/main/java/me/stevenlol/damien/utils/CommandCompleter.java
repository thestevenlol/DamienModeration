package me.stevenlol.damien.utils;

import co.aikar.commands.PaperCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CommandCompleter {

    public CommandCompleter(PaperCommandManager manager) {
        manager.getCommandCompletions().registerAsyncCompletion("OfflinePlayers", c -> {
            return Arrays.stream(Bukkit.getOfflinePlayers()).map(OfflinePlayer::getName).collect(Collectors.toList());
        });
    }

}
