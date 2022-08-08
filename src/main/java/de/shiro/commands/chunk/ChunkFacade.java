package de.shiro.commands.chunk;

import de.shiro.actionregister.chunk.ChunkSelectConfig;
import de.shiro.actionregister.chunk.SaveChunkConfig;
import de.shiro.actionregister.pos.config.AbstractPosActionConfig;
import de.shiro.actionregister.pos.config.PosWithPointConfig;
import de.shiro.system.action.manager.ActionFuture;
import de.shiro.system.action.manager.builder.AbstractAction;
import de.shiro.system.action.manager.facede.FacadeManager;

public class ChunkFacade implements ChunkFacadeInternal {

    private final FacadeManager helper = new FacadeManager(this.getClass());

    @Override
    public <R, U extends AbstractAction<R, ?>> ActionFuture<R, U> save(SaveChunkConfig config) {
        return helper.addAsyncAction(config, ChunkActionName.save);
    }

    @Override
    public <R, U extends AbstractAction<R, ?>> ActionFuture<R, U> select(ChunkSelectConfig config) {
        return helper.addAsyncAction(config, ChunkActionName.select);
    }



}
