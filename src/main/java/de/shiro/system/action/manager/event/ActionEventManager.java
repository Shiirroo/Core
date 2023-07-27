package de.shiro.system.action.manager.event;

import de.shiro.manager.manager.IManager;
import lombok.Getter;

public class ActionEventManager implements IManager {

    @Getter
    private final ActionStartListener actionStartListener = new ActionStartListener();
    @Getter
    private final ActionFinishedListener actionFinishedListener = new ActionFinishedListener();


    @Override
    public IManager init() {
        return this;
    }

    @Override
    public IManager close() {
        return this;
    }
}
