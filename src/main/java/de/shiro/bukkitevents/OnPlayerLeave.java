package de.shiro.bukkitevents;

import de.shiro.system.config.ISession;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.EventListener;

public class OnPlayerLeave implements Listener {


    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        //TODO FIX FOR CLEAR!
        ISession.getOrAddISession(event.getPlayer().getUniqueId(), event.getPlayer().getName()).getPlayerAction().reset();
        event.setQuitMessage(ChatColor.GRAY + "[" + ChatColor.RED + "-" + ChatColor.GRAY + "] " + event.getPlayer().getDisplayName());

    }
}
