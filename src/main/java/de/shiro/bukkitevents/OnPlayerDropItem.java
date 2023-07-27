package de.shiro.bukkitevents;

import de.shiro.Record;
import de.shiro.actions.recods.config.RecordAddActionConfig;
import de.shiro.api.blocks.Point3;
import de.shiro.commands.record.RecordFacade;
import de.shiro.record.records.DropItemRecord;
import de.shiro.record.records.ItemSource;
import de.shiro.record.records.PlayerLeaveRecord;
import de.shiro.system.config.ISession;
import de.shiro.utlits.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.Optional;

public class OnPlayerDropItem implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        DropItemRecord data = new DropItemRecord(event.getPlayer().getUniqueId(),event.getPlayer().getWorld().getName(),
                event.isCancelled(), new Point3(event.getPlayer().getLocation()), event.getItemDrop().getItemStack(), ItemSource.PLAYER);
        RecordAddActionConfig config = new RecordAddActionConfig(Config.getServerSession(), data);
        RecordFacade.lookupHelper(config);
    }



}