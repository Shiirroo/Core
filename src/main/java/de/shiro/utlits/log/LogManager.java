package de.shiro.utlits.log;

import de.shiro.manager.config.ConfigSaver;
import de.shiro.manager.config.IConfigBuilder;
import de.shiro.manager.manager.IManager;
import lombok.Getter;

import java.util.HashMap;

public class LogManager extends IConfigBuilder implements IManager {

    @Getter
    public HashMap<LogState, Boolean> logConfig = new HashMap<>();

    public LogManager(){
        for(LogState state: LogState.values()){
            logConfig.put(state, state.getDefaultState());
        }
    }

    public Boolean getLogState(LogState state){
        return logConfig.getOrDefault(state, state.getDefaultState());
    }

    public void changeState(LogState state){
        boolean bool = logConfig.getOrDefault(state, state.getDefaultState());
        if(bool) logConfig.put(state, false);
        else logConfig.put(state, true);
    }


    @Override
    public IManager init() {
        return this;
    }

    @Override
    public IManager close() {
        return new ConfigSaver<>(this).saveConfig();
    }
}
