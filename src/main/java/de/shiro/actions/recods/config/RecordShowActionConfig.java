package de.shiro.actions.recods.config;

import de.shiro.system.config.AbstractActionConfig;
import de.shiro.system.config.AbstractGetAction;
import de.shiro.system.config.AbstractRecordActionConfig;
import de.shiro.system.config.ISession;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class RecordShowActionConfig extends AbstractActionConfig {

    @Getter
    private final RecordGetActionConfig actionConfig;

    @Getter
    private final AbstractGetAction<? extends RecordGetActionConfig> action;


    public <C extends RecordGetActionConfig, A extends AbstractGetAction<C> > RecordShowActionConfig(ISession iSession, C config, Class<A> actionClass) {
        super(iSession);
        this.actionConfig = config;
        try {
            System.out.println(Arrays.toString(actionClass.getConstructors()));
            System.out.println(config.getClass());
            System.out.println(config);
            this.action = actionClass.getConstructor(config.getClass()).newInstance(config);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            System.out.println("TEST ERROR");
            throw new RuntimeException(e);
        }
    }
}
