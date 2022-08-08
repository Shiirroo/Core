package de.shiro.actionregister.pos.action;

import de.shiro.actionregister.pos.PosManager;
import de.shiro.api.blocks.WorldPos;
import de.shiro.system.action.manager.ActionResult;
import de.shiro.system.action.manager.builder.AbstractAction;
import de.shiro.actionregister.pos.config.AbstractPosActionConfig;
import de.shiro.utlits.Log;
import de.shiro.utlits.Utlits;
import it.unimi.dsi.fastutil.Pair;
import org.bukkit.ChatColor;

import java.io.File;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class DeletePosAction extends AbstractAction<Boolean, AbstractPosActionConfig> {

    public DeletePosAction(AbstractPosActionConfig config) {
        super(config);
    }

    @Override
    public ActionResult<Boolean> execute() throws InterruptedException {
        PosManager posManager = getConfig().getPosManager();
        Pair<File, WorldPos> pair = posManager.hasManagePerm(getConfig().getPosName());
        if (pair != null) {
             if(pair.first() != null && pair.second() != null && pair.first().delete()){
                sendPosMessage(pair.second());
                return ActionResult.Success(true);
            }
        }
        getConfig().getISession().sendSessionMessage(ChatColor.RED + "Pos "
                + ChatColor.GRAY + "[" + ChatColor.GOLD + getConfig().getPosName() +ChatColor.GRAY  + "] " + ChatColor.RED + "existiert nicht!");
        return ActionResult.Failed();
    }


    public void sendPosMessage(WorldPos worldPos){
        getConfig().getISession().sendSessionMessage(ChatColor.translateAlternateColorCodes('&', "&aPos " +
                ChatColor.GOLD + worldPos.getPosName() + " &awurde gelöscht: " +
                ChatColor.RED + Utlits.numberToFormat(worldPos.getPoint3d().getX()) + " " +
                ChatColor.GREEN + Utlits.numberToFormat(worldPos.getPoint3d().getY()) + " " +
                ChatColor.AQUA + Utlits.numberToFormat(worldPos.getPoint3d().getZ())));

    }



}
