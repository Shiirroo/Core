package de.shiro.commands.record;

import de.shiro.actions.recods.action.RecordAddAction;
import de.shiro.actions.recods.action.RecordChangeConfigActionAction;
import de.shiro.actions.recods.action.RecordShowAction;
import de.shiro.actions.recods.config.*;
import de.shiro.system.action.manager.ActionFuture;
import de.shiro.system.action.manager.builder.AbstractAction;
import de.shiro.system.action.manager.facede.FacadeAction;
import de.shiro.system.action.manager.facede.FacedInternal;
import de.shiro.system.config.AbstractActionConfig;

public interface RecordFacadeInternal extends FacedInternal {


    @FacadeAction(action = RecordShowAction.class)
    <R, U extends AbstractAction<R, ? extends AbstractActionConfig>> ActionFuture<R, U> lookup(RecordShowActionConfig config);

    @FacadeAction(action = RecordAddAction.class)
    <R, U extends AbstractAction<R, ? extends AbstractActionConfig>> ActionFuture<R, U> add(RecordAddActionConfig config);

    @FacadeAction(action = RecordChangeConfigActionAction.class)
    <R, U extends AbstractAction<R, ? extends AbstractActionConfig>> ActionFuture<R, U> config(RecordChangeConfigActionConfig config);


    @FacadeAction(action = RecordShowAction.class)
    <R, U extends AbstractAction<R, ? extends AbstractActionConfig>> ActionFuture<R, U> type(RecordShowActionConfig config);


}
