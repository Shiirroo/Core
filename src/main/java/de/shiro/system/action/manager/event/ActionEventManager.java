package de.shiro.system.action.manager.event;

import lombok.Getter;

public class ActionEventManager {

    @Getter
    private final ActionStartListener actionStartListener = new ActionStartListener();
    @Getter
    private final ActionFinishedListener actionFinishedListener = new ActionFinishedListener();




}
