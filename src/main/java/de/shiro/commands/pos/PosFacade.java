package de.shiro.commands.pos;

import de.shiro.actionregister.pos.config.PosWithPointConfig;
import de.shiro.system.action.manager.builder.AbstractAction;
import de.shiro.system.action.manager.ActionFuture;
import de.shiro.system.action.manager.facede.FacadeManager;
import de.shiro.actionregister.pos.config.AbstractPosActionConfig;

public class PosFacade implements PosFacadeInternal {

    private final FacadeManager helper = new FacadeManager(this.getClass());

    @Override
    public <R, U extends AbstractAction<R, ?>> ActionFuture<R, U> save(PosWithPointConfig config) {
        return helper.addAsyncAction(config, PosActionName.save);
    }

    @Override
    public <R, U extends AbstractAction<R, ?>> ActionFuture<R, U> list(AbstractPosActionConfig config) {
        return helper.addAsyncAction(config, PosActionName.list);
    }

    @Override
    public <R, U extends AbstractAction<R, ?>> ActionFuture<R, U> follow(AbstractPosActionConfig config) {
        return helper.addAsyncAction(config, PosActionName.follow);
    }

    @Override
    public <R, U extends AbstractAction<R, ?>> ActionFuture<R, U> delete(AbstractPosActionConfig config) {
        return helper.addAsyncAction(config, PosActionName.delete);
    }


}
