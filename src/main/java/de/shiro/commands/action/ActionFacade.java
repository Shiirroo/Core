package de.shiro.commands.action;

import de.shiro.actions.action.config.ActionActionConfig;
import de.shiro.system.action.manager.ActionFuture;
import de.shiro.system.action.manager.builder.AbstractAction;
import de.shiro.system.action.manager.facede.FacadeManager;
import de.shiro.system.config.AbstractActionConfig;

public class ActionFacade implements ActionFacadeInternal {

    private final FacadeManager helper = new FacadeManager(this.getClass());

    @Override
    public <R, U extends AbstractAction<R, ? extends AbstractActionConfig>> ActionFuture<R, U> list(ActionActionConfig config) {
        return helper.addAction(config, ActionActionName.list);
    }

    @Override
    public <R, U extends AbstractAction<R, ? extends AbstractActionConfig>> ActionFuture<R, U> clear(ActionActionConfig config) {
        return helper.addAction(config, ActionActionName.clear);
    }



}
