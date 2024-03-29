package de.shiro.commands.commandbuilder;

import de.shiro.commands.commandbuilder.ckey.CKey;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String aliases() default "";
    CKey[] syntax() default {};

}
