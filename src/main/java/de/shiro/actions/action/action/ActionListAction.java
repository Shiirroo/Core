package de.shiro.actions.action.action;

import de.shiro.Record;
import de.shiro.actions.action.config.ActionActionConfig;
import de.shiro.system.action.PlayerAction;
import de.shiro.system.action.manager.ActionResult;
import de.shiro.system.action.manager.builder.AbstractAction;
import de.shiro.system.config.ISession;
import de.shiro.utlits.log.Log;

import java.util.Collection;

public class ActionListAction extends AbstractAction<Boolean, ActionActionConfig> {

    public ActionListAction(ActionActionConfig config) {
        super(config, true);
    }

    @Override
    public ActionResult<Boolean> execute() {
        Log.info(getISession().getPlayerAction().getActionFutureQueue().size() + " Actions in Queue");
        int actions = getISession().getISessionManager().getISessions().stream()
                .map(ISession::getPlayerAction)
                .map(PlayerAction::getActionFutureQueue)
                .map(Collection::size).mapToInt(Integer::intValue).sum();


        getISession().sendSessionMessage(Record.getPrefix() + getISession().getPlayerAction().getActionFutureQueue().size() + " Actions in your Queue [Total Action: "+ actions +"]");
        return ActionResult.of(true);
    }

}
