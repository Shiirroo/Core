package de.shiro.bukkitevents;

import de.shiro.Record;
import de.shiro.actions.recods.config.RecordAddActionConfig;
import de.shiro.api.blocks.Point3;
import de.shiro.commands.record.RecordFacade;
import de.shiro.record.records.DropItemRecord;
import de.shiro.record.records.PlayerJoinRecord;
import de.shiro.system.config.ISession;
import de.shiro.utlits.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Optional;

public class OnPlayerJoin implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        PlayerJoinRecord data = new PlayerJoinRecord(event.getPlayer().getUniqueId(), new Point3(event.getPlayer().getLocation()), event.getPlayer().getWorld().getName());
        RecordAddActionConfig config = new RecordAddActionConfig(Config.getServerSession(), data);
        RecordFacade.lookupHelper(config);

    }



}
