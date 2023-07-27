package de.shiro.system.option;

public class StringOption<E extends Enum<E>, R> extends Option<E, R> {


    public StringOption(E type, R defaultValue) {
        super(type, defaultValue);
    }


}
