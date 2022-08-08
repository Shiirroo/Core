package de.shiro.commands.action;

import de.shiro.actionregister.action.config.ActionActionConfig;
import de.shiro.actionregister.chunk.ChunkSelectConfig;
import de.shiro.actionregister.chunk.SaveChunkConfig;
import de.shiro.api.blocks.Area;
import de.shiro.api.blocks.ChunkPoint;
import de.shiro.commands.commandbuilder.CommandArguments;
import de.shiro.commands.commandbuilder.ckey.CKey;
import de.shiro.system.action.manager.facede.FacedInternal;
import de.shiro.system.action.manager.facede.Facede;
import de.shiro.system.config.ISession;
import de.shiro.utlits.Config;
import lombok.Getter;
import org.bukkit.command.CommandSender;

public class ActionCommands implements ActionCommandsInternal {

    private static final ActionFacade facade = new ActionFacade();

    @Override
    public void list(ISession iSession, CommandSender sender, CommandArguments args) {
        String playerAction = args.getIfExists(iSession, CKey.PlayerActions);
        int page = args.getIfExists(iSession, CKey.Page, 1, true);
        if(playerAction == null) return;
        ActionActionConfig config = new ActionActionConfig(iSession);
        config.setPage(page);


        facade.list(config);
    }

    @Override
    public void clear(ISession iSession, CommandSender sender, CommandArguments args) {
        ActionActionConfig config = new ActionActionConfig(iSession);
        facade.clear(config);
    }

}
