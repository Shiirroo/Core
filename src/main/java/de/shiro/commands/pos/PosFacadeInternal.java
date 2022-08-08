package de.shiro.commands.pos;

import de.shiro.actionregister.pos.action.DeletePosAction;
import de.shiro.actionregister.pos.action.FollowPosAction;
import de.shiro.actionregister.pos.action.ListPosAction;
import de.shiro.actionregister.pos.action.SavePosAction;
import de.shiro.actionregister.pos.config.PosWithPointConfig;
import de.shiro.system.action.manager.facede.FacadeAction;
import de.shiro.system.action.manager.facede.FacedInternal;
import de.shiro.system.action.manager.builder.AbstractAction;
import de.shiro.system.action.manager.ActionFuture;
import de.shiro.actionregister.pos.config.AbstractPosActionConfig;

public interface PosFacadeInternal extends FacedInternal {

    @FacadeAction(action = SavePosAction.class)
     <R, U extends AbstractAction<R, ?>> ActionFuture<R, U> save(PosWithPointConfig config);

    @FacadeAction(action = ListPosAction.class)
    <R, U extends AbstractAction<R, ?>> ActionFuture<R, U> list(AbstractPosActionConfig config);

    @FacadeAction(action = FollowPosAction.class)
    <R, U extends AbstractAction<R, ?>> ActionFuture<R, U> follow(AbstractPosActionConfig config);

    @FacadeAction(action = DeletePosAction.class)
    <R, U extends AbstractAction<R, ?>> ActionFuture<R, U> delete(AbstractPosActionConfig config);

}
