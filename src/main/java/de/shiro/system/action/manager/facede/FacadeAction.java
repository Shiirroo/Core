package de.shiro.system.action.manager.facede;

import de.shiro.actions.action.action.ActionClearAction;
import de.shiro.system.action.manager.builder.AbstractAction;
import de.shiro.system.config.AbstractActionConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FacadeAction {
    Class<? extends AbstractAction<?,? extends AbstractActionConfig>> action();
}

