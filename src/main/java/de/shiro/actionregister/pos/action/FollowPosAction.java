package de.shiro.actionregister.pos.action;

import de.shiro.actionregister.pos.PosManager;
import de.shiro.api.blocks.WorldPos;
import de.shiro.system.action.manager.ActionResult;
import de.shiro.system.action.manager.builder.AbstractAction;
import de.shiro.actionregister.pos.config.AbstractPosActionConfig;
import de.shiro.utlits.Utlits;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class FollowPosAction extends AbstractAction<Boolean, AbstractPosActionConfig> {

    public FollowPosAction(AbstractPosActionConfig config) {
        super(config);
    }

    @Override
    public ActionResult<Boolean> execute() throws Exception {
        PosManager posManager = getConfig().getPosManager();
        WorldPos worldPos = posManager.getWorldPosByName(this.getConfig().getPosName());
        if(worldPos != null){
            sendMessage(worldPos);
            return ActionResult.Success();
        }
        getConfig().getISession().sendSessionMessage(ChatColor.RED + "Pos "
                + ChatColor.GRAY + "[" + ChatColor.GOLD + getConfig().getPosName() +ChatColor.GRAY  + "] " + ChatColor.RED + "wurde nicht gefunden");
        return ActionResult.Failed();
    }



    private void sendMessage(WorldPos worldPos) {
        if(worldPos == null) return;
        Player player = getConfig().getISession().getSessionPlayer();
        World world = getConfig().getISession().getSessionWorld();
        if ((int) player.getCompassTarget().getX() == (int) worldPos.getPoint3d().getX()
                && (int) player.getCompassTarget().getY() == (int) worldPos.getPoint3d().getY()
                && (int) player.getCompassTarget().getZ() == (int) worldPos.getPoint3d().getZ()) {
            player.setCompassTarget(world.getSpawnLocation());
            player.sendMessage(ChatColor.GREEN + "Pos " + ChatColor.GRAY + "[" + ChatColor.GOLD + getConfig().getPosName() +ChatColor.GRAY  + "] " +ChatColor.GREEN + "wurde vom Compass entfernt!");
        } else {
            player.setCompassTarget(worldPos.getPoint3d().toLocation(world));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aCompass zeigt nun auf Pos " +
                    ChatColor.GRAY + "[" + ChatColor.GOLD + getConfig().getPosName() +ChatColor.GRAY  + "] " +
                    ChatColor.RED + Utlits.numberToFormat(worldPos.getPoint3d().getX()) + " " +
                    ChatColor.GREEN + Utlits.numberToFormat(worldPos.getPoint3d().getY()) + " " +
                    ChatColor.AQUA + Utlits.numberToFormat(worldPos.getPoint3d().getZ())));

        }

    }

}
