package de.shiro.system.option;

import lombok.Getter;
import org.checkerframework.checker.units.qual.A;

public class Option<E extends Enum<E>, R> {

    @Getter
    private final E type;
    @Getter
    private final R defaultValue;

    private R value;

    public Option(E type, R defaultValue){
        this.type = type;
        this.defaultValue = defaultValue;
    }

    public void setValue(R value) {
        this.value = value;
    }

    public String getName() {
        return type.name();
    }

    public R getValue(){
        if(value == null) return defaultValue;
        return value;
    }

    public E getType() {
        return this.type;
    }
}
