package de.shiro.actions.action.config;

import de.shiro.actions.action.action.ActionListAction;
import de.shiro.system.action.manager.ActionType;
import de.shiro.system.config.AbstractActionConfig;
import de.shiro.system.config.ISession;
import lombok.Getter;
import lombok.Setter;

public class ActionActionConfig extends AbstractActionConfig {

    @Getter @Setter
    private int page;

    public ActionActionConfig(ISession iSession) {
        super(iSession, ActionType.SYNC);
    }


}
