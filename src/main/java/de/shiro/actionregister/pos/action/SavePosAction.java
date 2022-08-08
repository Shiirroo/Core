package de.shiro.actionregister.pos.action;

import de.shiro.actionregister.pos.PosManager;
import de.shiro.actionregister.pos.config.PosWithPointConfig;
import de.shiro.api.blocks.WorldPos;
import de.shiro.system.action.manager.ActionResult;
import de.shiro.system.action.manager.builder.AbstractAction;
import de.shiro.actionregister.pos.config.AbstractPosActionConfig;
import de.shiro.utlits.Config;
import de.shiro.utlits.Log;
import de.shiro.utlits.Utlits;
import it.unimi.dsi.fastutil.Pair;
import org.bukkit.ChatColor;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static de.shiro.utlits.Utlits.mapper;
import static de.shiro.utlits.Utlits.readJsonFile;

public class SavePosAction extends AbstractAction<Boolean, PosWithPointConfig> {

    public SavePosAction(PosWithPointConfig config) {
        super(config);
    }

    @Override
    public ActionResult<Boolean> execute() throws IOException, InterruptedException {
        PosManager posManager = getConfig().getPosManager();
        if(getConfig().getPosName().contains("*")) return ActionResult.Failed();
        Pair<File, WorldPos> pair = posManager.hasManagePerm(getConfig().getPosName());
        if (pair != null) {
            if (pair.first() == null && pair.second() == null) {
                getConfig().getISession().sendSessionMessage(ChatColor.RED + "Pos "
                        + ChatColor.GRAY + "[" + ChatColor.GOLD + getConfig().getPosName() + ChatColor.GRAY + "] " + ChatColor.RED + "cannot be saved");
                return ActionResult.Failed();
            } else if (!pair.first().getParentFile().getName().equalsIgnoreCase(getConfig().getISession().getExecutorID().toString())) {
                Boolean deleted = deleteOldPosAction();
                if (deleted == null || !deleted) {
                    getConfig().getISession().sendSessionMessage(ChatColor.RED + "Pos "
                            + ChatColor.GRAY + "[" + ChatColor.GOLD + getConfig().getPosName() + ChatColor.GRAY + "] " + ChatColor.RED + "does not exist");
                    return ActionResult.Failed();
                }
            }
        }



        savePos();
        sendMessage(ChatColor.translateAlternateColorCodes('&', "&aPos "
                + ChatColor.GRAY + "[" + ChatColor.GOLD + getConfig().getPosName() +ChatColor.GRAY  + "] " + "&awas saved: " +
                ChatColor.RED + Utlits.numberToFormat(getConfig().getPos().getX()) + " " +
                ChatColor.GREEN + Utlits.numberToFormat(getConfig().getPos().getY()) + " " +
                ChatColor.AQUA + Utlits.numberToFormat(getConfig().getPos().getZ())));
        return ActionResult.Success();
    }

    public Boolean deleteOldPosAction(){
        DeletePosAction deletePosAction = new DeletePosAction(new AbstractPosActionConfig(Config.getServerSession(), getConfig().getPosName()));
        ActionResult<Boolean> delete = addSubAction(deletePosAction).getResult(TimeUnit.SECONDS, 15000);

        //ActionResult<Boolean> delete = newActionFuture(deletePosAction).getResult(TimeUnit.SECONDS, 15000);
        return delete.getResult();
    }

    private void savePos() throws IOException {
        mapper.writeValue(new File(getConfig().getPosManager().getPlayerFolderFile().getAbsolutePath() + "/" + getConfig().getPosName() + ".json"), new WorldPos(getConfig()));
    }



    private void sendMessage(String message) {
        getConfig().getISession().sendSessionMessage(message);
    }

}
