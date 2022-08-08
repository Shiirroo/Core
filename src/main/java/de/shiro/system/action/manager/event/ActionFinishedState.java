package de.shiro.system.action.manager.event;

import lombok.Getter;

public enum ActionFinishedState {
    Timeout,
    Cancelled,
    Success,
    Failed,
    Unknown;

    @Getter
    public Object message;

    public ActionFinishedState setMessage(Object message) {
        this.message = message;
        return this;
    }


}
