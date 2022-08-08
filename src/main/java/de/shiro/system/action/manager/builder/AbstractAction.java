package de.shiro.system.action.manager.builder;

import de.shiro.Core;
import de.shiro.system.action.ActionCookies;
import de.shiro.system.action.manager.ActionFuture;
import de.shiro.system.action.manager.ActionResult;
import de.shiro.system.config.AbstractActionConfig;
import de.shiro.system.config.ISession;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.units.qual.A;

import java.util.concurrent.FutureTask;

public class AbstractAction<R,C extends AbstractActionConfig>  {


    @Getter
    private final C config;

    @Getter @Setter
    private ActionCookies cookies = new ActionCookies();


    public AbstractAction(C config) {
        this.config = config;
    }



    public ActionResult<R> execute() throws Exception {
        return null;
    }


    public static <R,C extends AbstractActionConfig> AbstractAction<R,C> of(C config) {
        return new AbstractAction<>(config);
    }

    protected <RR, AA extends AbstractAction<RR,?>> ActionFuture<RR,AA> newActionFuture(AA action) {
        if(action == null) return null;
        return new ActionFuture<>(action);
    }

    protected <RR, AA extends AbstractAction<RR,?>> ActionFuture<RR,AA> addSubAction(AA action) {
        if(action == null) return null;
        action.getCookies().setSubAction(true);
        ActionFuture<RR,AA> actionFuture = ActionFuture.of(action);
        getCookies().getSubActions().add(actionFuture);
        return actionFuture;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof AbstractAction<?, ?> abstractAction) {
            return abstractAction.getCookies().equals(this.getCookies()) && abstractAction.getConfig().equals(this.getConfig());
        }
        return false;
    }

    public ISession getISession() {
        return config.getISession();
    }

    public String getExecutorName() {
        return config.getISession().getExecutorName();
    }

}
