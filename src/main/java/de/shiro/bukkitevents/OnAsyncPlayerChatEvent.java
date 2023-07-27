package de.shiro.bukkitevents;

import de.shiro.Record;
import de.shiro.api.blocks.Point3D;
import de.shiro.record.records.PlayerLeaveRecord;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnAsyncPlayerChatEvent implements Listener {


    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerAsyncChat(AsyncPlayerChatEvent event) {
        //PlayerLeaveRecord data = new PlayerLeaveRecord(event.getPlayer().getUniqueId(), new Point3D(event.getPlayer().getLocation()), event.getPlayer().getWorld().getName());
        //Record.getManager().getRecordManager().addRecord(data);
    }
}
