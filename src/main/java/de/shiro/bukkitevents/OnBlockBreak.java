package de.shiro.bukkitevents;

import de.shiro.Record;
import de.shiro.actions.recods.action.RecordAddAction;
import de.shiro.actions.recods.config.RecordAddActionConfig;
import de.shiro.api.blocks.GameBlock;
import de.shiro.api.blocks.Point3;
import de.shiro.api.blocks.Point3D;
import de.shiro.commands.commandbuilder.CommandAPI;
import de.shiro.commands.commandbuilder.Commands;
import de.shiro.commands.record.RecordCommandsInternal;
import de.shiro.commands.record.RecordFacade;
import de.shiro.record.records.BlockBreakRecord;
import de.shiro.record.records.ItemSource;
import de.shiro.record.records.PlayerJoinRecord;
import de.shiro.system.action.manager.CommandManager;
import de.shiro.system.config.ISession;
import de.shiro.utlits.Config;
import org.bukkit.Particle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class OnBlockBreak implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event) {
        GameBlock gameBlock = new GameBlock(new Point3(event.getBlock().getLocation()), event.getBlock());
        ItemStack itemStack = new ItemStack(event.getBlock().getType());


        BlockBreakRecord data = new BlockBreakRecord(event.getPlayer().getUniqueId(),event.getPlayer().getWorld().getName(),  event.isCancelled(), gameBlock, itemStack);
        RecordAddActionConfig config = new RecordAddActionConfig(Config.getServerSession(), data);
        RecordFacade.lookupHelper(config);
    }

}
