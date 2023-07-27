package de.shiro.commands.record;

import de.shiro.actions.recods.config.*;
import de.shiro.system.action.manager.ActionFuture;
import de.shiro.system.action.manager.builder.AbstractAction;
import de.shiro.system.action.manager.facede.FacadeManager;
import de.shiro.system.config.AbstractActionConfig;

public class RecordFacade implements RecordFacadeInternal {


    private static final FacadeManager helper = new FacadeManager(RecordFacade.class);

    @Override
    public <R, U extends AbstractAction<R, ? extends AbstractActionConfig>> ActionFuture<R, U> lookup(RecordShowActionConfig config) {
        return helper.addAction(config, RecordActionName.lookup);
    }

    @Override
    public <R, U extends AbstractAction<R, ? extends AbstractActionConfig>> ActionFuture<R, U> add(RecordAddActionConfig config) {
        return helper.addAction(config, RecordActionName.add);
    }

    @Override
    public <R, U extends AbstractAction<R, ? extends AbstractActionConfig>> ActionFuture<R, U> config(RecordChangeConfigActionConfig config) {
        return helper.addAction(config, RecordActionName.config);
    }

    @Override
    public <R, U extends AbstractAction<R, ? extends AbstractActionConfig>> ActionFuture<R, U> type(RecordShowActionConfig config) {
        return helper.addAction(config, RecordActionName.type);
    }


    public static <R, U extends AbstractAction<R, ? extends AbstractActionConfig>> ActionFuture<R, U> lookupHelper(RecordAddActionConfig config) {
        return helper.addAction(config, RecordActionName.add);
    }


}
