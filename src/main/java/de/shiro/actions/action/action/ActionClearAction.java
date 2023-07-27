package de.shiro.actions.action.action;

import de.shiro.actions.action.config.ActionActionConfig;
import de.shiro.system.action.manager.ActionFuture;
import de.shiro.system.action.manager.ActionResult;
import de.shiro.system.action.manager.builder.AbstractAction;

public class ActionClearAction extends AbstractAction<Boolean, ActionActionConfig> {

    public ActionClearAction(ActionActionConfig config) {
        super(config, true);
    }

    @Override
    public ActionResult<Boolean> execute() {
        getISession().getPlayerAction().reset();
        return ActionResult.of(true);
    }

}
