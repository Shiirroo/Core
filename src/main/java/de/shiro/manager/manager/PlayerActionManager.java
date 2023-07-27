package de.shiro.manager.manager;

import de.shiro.system.action.PlayerActionHotbar;
import lombok.Getter;

import java.util.Timer;

public class PlayerActionManager implements IManager {

    @Getter
    private final PlayerActionHotbar playerActionHotbar;
    @Getter
    private final Timer timer;


    public PlayerActionManager(){
        this.playerActionHotbar = new PlayerActionHotbar();
        this.timer = new Timer();
    }
    @Override
    public IManager init(){
        timer.scheduleAtFixedRate(playerActionHotbar, 0, 500);
        return this;
    }

    @Override
    public IManager close() {
        timer.cancel();
        return this;
    }


}
