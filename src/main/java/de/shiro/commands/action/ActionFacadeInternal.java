package de.shiro.commands.action;

import de.shiro.actions.action.action.ActionClearAction;
import de.shiro.actions.action.action.ActionListAction;
import de.shiro.actions.action.config.ActionActionConfig;
import de.shiro.system.action.manager.ActionFuture;
import de.shiro.system.action.manager.builder.AbstractAction;
import de.shiro.system.action.manager.facede.FacadeAction;
import de.shiro.system.action.manager.facede.FacedInternal;
import de.shiro.system.config.AbstractActionConfig;

public interface ActionFacadeInternal extends FacedInternal {

    @FacadeAction(action = ActionListAction.class)
     <R, U extends AbstractAction<R, ? extends AbstractActionConfig>> ActionFuture<R, U> list(ActionActionConfig config);

    @FacadeAction(action = ActionClearAction.class)
    <R, U extends AbstractAction<R, ? extends AbstractActionConfig>> ActionFuture<R, U> clear(ActionActionConfig config);

}
