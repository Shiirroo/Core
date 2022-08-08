package de.shiro.commands.chunk;

import de.shiro.actionregister.chunk.ChunkSelectAction;
import de.shiro.actionregister.chunk.ChunkSelectConfig;
import de.shiro.actionregister.chunk.SaveChunkAction;
import de.shiro.actionregister.chunk.SaveChunkConfig;
import de.shiro.actionregister.pos.action.DeletePosAction;
import de.shiro.actionregister.pos.action.FollowPosAction;
import de.shiro.actionregister.pos.action.ListPosAction;
import de.shiro.actionregister.pos.action.SavePosAction;
import de.shiro.actionregister.pos.config.AbstractPosActionConfig;
import de.shiro.actionregister.pos.config.PosWithPointConfig;
import de.shiro.system.action.manager.ActionFuture;
import de.shiro.system.action.manager.builder.AbstractAction;
import de.shiro.system.action.manager.facede.FacadeAction;
import de.shiro.system.action.manager.facede.FacedInternal;

public interface ChunkFacadeInternal extends FacedInternal {

    @FacadeAction(action = SaveChunkAction.class)
     <R, U extends AbstractAction<R, ?>> ActionFuture<R, U> save(SaveChunkConfig config);

    @FacadeAction(action = ChunkSelectAction.class)
    <R, U extends AbstractAction<R, ?>> ActionFuture<R, U> select(ChunkSelectConfig config);

}
