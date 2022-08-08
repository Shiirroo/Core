package de.shiro.commands.action;

import de.shiro.actionregister.action.config.ActionActionConfig;
import de.shiro.actionregister.chunk.ChunkSelectConfig;
import de.shiro.actionregister.chunk.SaveChunkConfig;
import de.shiro.system.action.manager.ActionFuture;
import de.shiro.system.action.manager.builder.AbstractAction;
import de.shiro.system.action.manager.facede.FacadeManager;

public class ActionFacade implements ActionFacadeInternal {

    private final FacadeManager helper = new FacadeManager(this.getClass());

    @Override
    public <R, U extends AbstractAction<R, ?>> ActionFuture<R, U> list(ActionActionConfig config) {
        return helper.addAsyncAction(config, ActionActionName.list);
    }

    @Override
    public <R, U extends AbstractAction<R, ?>> ActionFuture<R, U> clear(ActionActionConfig config) {
        return helper.addAsyncAction(config, ActionActionName.clear);
    }



}
