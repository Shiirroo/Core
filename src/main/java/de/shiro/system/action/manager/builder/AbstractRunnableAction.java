package de.shiro.system.action.manager.builder;

import de.shiro.system.config.AbstractActionConfig;

public class AbstractRunnableAction<A, C extends AbstractActionConfig> extends AbstractAction<A, C> {

    public AbstractRunnableAction(C config) {
        super(config);
    }

}
