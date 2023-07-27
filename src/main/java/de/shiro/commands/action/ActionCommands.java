package de.shiro.commands.action;

import de.shiro.actions.action.config.ActionActionConfig;
import de.shiro.commands.commandbuilder.CommandArguments;
import de.shiro.commands.commandbuilder.ckey.CKey;
import de.shiro.system.config.ISession;
import org.bukkit.command.CommandSender;

public class ActionCommands implements ActionCommandsInternal {

    private static final ActionFacade facade = new ActionFacade();

    @Override
    public void list(ISession iSession, CommandSender sender, CommandArguments args) {
        String playerAction = args.getIfExists(CKey.PlayerActions, "*");
        int page = args.getIfExists(iSession, CKey.Page, 1, true);
        if(playerAction == null) {
            iSession.sendSessionMessage("No actions in List");
            return;
        }
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
