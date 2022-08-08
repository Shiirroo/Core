package de.shiro.system.config;

import lombok.Getter;

import java.util.UUID;

public class IdActionConfig extends AbstractActionConfig {
    @Getter
    private final UUID id;

    public IdActionConfig(ISession iSession, UUID id) {
        super(iSession);
        this.id = id;
    }

    public IdActionConfig(ISession iSession) {
        super(iSession);
        this.id = iSession.getExecutorID();
    }


    public static IdActionConfig of(ISession ISession, UUID id) {
        return new IdActionConfig(ISession, id);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +"{" +
                "id=" + getId() +
                ", executerId=" + getISession() +
                ", actionType=" + getActionType() +
                '}';
    }
}
