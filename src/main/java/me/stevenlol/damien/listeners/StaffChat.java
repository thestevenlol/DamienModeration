package me.stevenlol.damien.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class StaffChat implements Listener {

    @EventHandler
    public void staffChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
    }

}
