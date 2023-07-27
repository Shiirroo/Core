package de.shiro.bukkitevents;

import de.shiro.Record;
import de.shiro.actions.recods.config.RecordAddActionConfig;
import de.shiro.api.blocks.GameBlock;
import de.shiro.api.blocks.Point3;
import de.shiro.api.blocks.Point3D;
import de.shiro.commands.record.RecordFacade;
import de.shiro.record.records.BlockPlaceRecord;
import de.shiro.record.records.PlayerJoinRecord;
import de.shiro.record.records.PlayerLeaveRecord;
import de.shiro.system.config.ISession;
import de.shiro.utlits.Config;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.EventListener;
import java.util.Optional;

public class OnPlayerLeave implements Listener {


    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerLeave(PlayerQuitEvent event) {
        PlayerLeaveRecord data = new PlayerLeaveRecord(event.getPlayer().getUniqueId(), event.getPlayer().getWorld().getName(), new Point3(event.getPlayer().getLocation()));
        RecordAddActionConfig config = new RecordAddActionConfig(Config.getServerSession(), data);
        RecordFacade.lookupHelper(config);
    }
}
