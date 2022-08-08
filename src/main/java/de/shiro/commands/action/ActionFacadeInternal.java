package de.shiro.commands.action;

import de.shiro.actionregister.action.action.ActionClearAction;
import de.shiro.actionregister.action.action.ActionListAction;
import de.shiro.actionregister.action.config.ActionActionConfig;
import de.shiro.actionregister.chunk.ChunkSelectAction;
import de.shiro.actionregister.chunk.ChunkSelectConfig;
import de.shiro.actionregister.chunk.SaveChunkAction;
import de.shiro.actionregister.chunk.SaveChunkConfig;
import de.shiro.system.action.manager.ActionFuture;
import de.shiro.system.action.manager.builder.AbstractAction;
import de.shiro.system.action.manager.facede.FacadeAction;
import de.shiro.system.action.manager.facede.FacedInternal;

public interface ActionFacadeInternal extends FacedInternal {

    @FacadeAction(action = ActionListAction.class)
     <R, U extends AbstractAction<R, ?>> ActionFuture<R, U> list(ActionActionConfig config);

    @FacadeAction(action = ActionClearAction.class)
    <R, U extends AbstractAction<R, ?>> ActionFuture<R, U> clear(ActionActionConfig config);

}
