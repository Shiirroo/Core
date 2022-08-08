package de.shiro.actionregister.action.config;

import de.shiro.actionregister.action.action.ActionListAction;
import de.shiro.system.config.AbstractActionConfig;
import de.shiro.system.config.ISession;
import lombok.Getter;
import lombok.Setter;

public class ActionActionConfig extends AbstractActionConfig {

    @Getter @Setter
    private int page;

    public ActionActionConfig(ISession iSession) {
        super(iSession);
    }


}
