package de.shiro.bukkitevents;

import de.shiro.system.config.ISession;
import de.shiro.utlits.Utlits;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerJoin implements Listener {


    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        //ADD ISESSION TO PLAYER
        ISession.checkSessionOrCreate(event.getPlayer().getUniqueId(), event.getPlayer().getName());
        event.setJoinMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "+" + ChatColor.GRAY + "] " + event.getPlayer().getDisplayName());




    }


}
