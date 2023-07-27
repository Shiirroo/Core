package de.shiro.bukkitevents;

import de.shiro.Record;
import de.shiro.actions.recods.config.RecordAddActionConfig;
import de.shiro.api.blocks.GameBlock;
import de.shiro.api.blocks.Point3;
import de.shiro.commands.record.RecordFacade;
import de.shiro.record.records.BlockBreakRecord;
import de.shiro.record.records.BlockPlaceRecord;
import de.shiro.system.config.ISession;
import de.shiro.utlits.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Optional;

public class OnBlockPlace implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockPlaceEvent event) {
        GameBlock gameBlock = new GameBlock(new Point3(event.getBlock().getLocation()), event.getBlock());
        BlockPlaceRecord data = new BlockPlaceRecord(event.getPlayer().getUniqueId(),event.getPlayer().getWorld().getName(),  event.isCancelled(), gameBlock, event.getItemInHand());
        RecordAddActionConfig config = new RecordAddActionConfig(Config.getServerSession(), data);
        RecordFacade.lookupHelper(config);
    }



}
