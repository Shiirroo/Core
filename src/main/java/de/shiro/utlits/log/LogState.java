package de.shiro.utlits.log;

import lombok.Getter;

public enum LogState {
    INFO(false),
    DEBUG(false),
    ERROR(true)



    ;

    private final boolean defaultState ;

    LogState(boolean defaultState) {
        this.defaultState = defaultState;
    }


    public boolean getDefaultState(){
        return this.defaultState;
    }
}
