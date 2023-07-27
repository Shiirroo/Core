package de.shiro.bukkitevents;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class OnEntityDamageByEntity implements Listener {


    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
    public void OnEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
       event.getDamager().getType().getEntityClass();




    }
}
