package de.shiro.system.action.manager.builder;

import de.shiro.Record;
import de.shiro.manager.Manager;
import de.shiro.system.action.ActionCookies;
import de.shiro.system.action.manager.ActionFuture;
import de.shiro.system.action.manager.ActionResult;
import de.shiro.system.action.manager.facede.FacadeAction;
import de.shiro.system.config.AbstractActionConfig;
import de.shiro.system.config.ISession;
import lombok.Getter;
import lombok.Setter;

public abstract class AbstractAction<R,C extends AbstractActionConfig> {


    @Getter
    private final C config;

    @Getter @Setter
    private final ActionCookies cookies;


    public AbstractAction(C config) {
        this.config = config;
        this.cookies = new ActionCookies();

    }

    public AbstractAction(C config, boolean skipQue) {
        this.config = config;
        this.cookies =  new ActionCookies(skipQue);
    }


    public abstract ActionResult<R> execute() throws Exception;



    protected <RR, AA extends AbstractAction<RR,? extends AbstractActionConfig>> ActionFuture<RR,AA> newActionFuture(AA action) {
        if(action == null) return null;
        return new ActionFuture<>(action);
    }

    protected <RR, AA extends AbstractAction<RR,? extends AbstractActionConfig>> ActionFuture<RR,AA> addSubAction(AA action) {
        if (action == null) return null;
        if (!action.getConfig().getActionType().equals(this.getConfig().getActionType())) return null;
        action.getCookies().setSubAction(true);
        ActionFuture<RR, AA> actionFuture = ActionFuture.of(action);
        getCookies().getSubActions().add(actionFuture);
        actionFuture.execute();
        return actionFuture;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof AbstractAction<?, ?> abstractAction) {
            return abstractAction.getCookies().equals(this.getCookies()) && abstractAction.getConfig().equals(this.getConfig());
        }
        return false;
    }

    public Manager getManager(){
        return Record.getManager();
    }

    public ISession getISession() {
        return config.getISession();
    }

    public String getExecutorName() {
        return config.getISession().getExecutorName();
    }

    @Override
    public String toString() {
        return "AbstractAction{" +
                "config=" + config +
                ", cookies=" + cookies +
                '}';
    }

}
