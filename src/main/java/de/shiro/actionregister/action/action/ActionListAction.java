package de.shiro.actionregister.action.action;

import de.shiro.actionregister.action.config.ActionActionConfig;
import de.shiro.system.action.manager.ActionFuture;
import de.shiro.system.action.manager.ActionResult;
import de.shiro.system.action.manager.builder.AbstractAction;
import de.shiro.utlits.Log;

import java.util.concurrent.Future;

public class ActionListAction extends AbstractAction<Boolean, ActionActionConfig> {

    public ActionListAction(ActionActionConfig config) {
        super(config);
    }

    @Override
    public ActionResult<Boolean> execute() {
        Log.info(getISession().getPlayerAction().getActionFutureQueue().size() + " Actions in Queue");
        for (ActionFuture<?, ?> future : getISession().getPlayerAction().getActionFutureQueue()) {
            System.out.println(future.getAction().getClass().getSimpleName());
        }
        return ActionResult.of(true);
    }

}
