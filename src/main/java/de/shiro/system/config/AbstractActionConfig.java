package de.shiro.system.config;

import de.shiro.system.action.manager.ActionType;
import lombok.Getter;

public class AbstractActionConfig {

    @Getter
    private final ISession iSession;

    @Getter
    private final ActionType actionType;

    public AbstractActionConfig(ISession iSession) {
        this.iSession = iSession;
        this.actionType = ActionType.ASYNC;
    }

    public AbstractActionConfig(ISession iSession, ActionType actionType) {
        this.iSession = iSession;
        this.actionType = actionType;
    }

    public static AbstractActionConfig of(ISession iSession) {
        return new AbstractActionConfig(iSession);
    }

    public String toString() {
        return this.getClass().getSimpleName() +"{" +
                "executerId=" + getISession() +
                ", actionType=" + getActionType() +
                '}';
    }



    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AbstractActionConfig) {
            return ((AbstractActionConfig) obj).iSession.equals(this.iSession);
        }
        return false;
    }
}
