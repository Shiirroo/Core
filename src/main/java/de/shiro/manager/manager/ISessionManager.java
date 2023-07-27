package de.shiro.manager.manager;

import de.shiro.system.config.ISession;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ISessionManager implements IManager {

    @Getter
    private final List<ISession> iSessions;

    public ISessionManager(){
        this.iSessions = new ArrayList<>();
    }


    public void registerISessions(){
        if(!iSessions.isEmpty()) iSessions.clear();
        Bukkit.getOnlinePlayers().forEach(player -> {
            iSessions.add(ISession.newISession(player.getUniqueId(), player.getName()));
        });
    }

    public Optional<ISession> getISession(UUID uuid){
        return iSessions.stream().filter(iSession -> iSession.getExecutorID().equals(uuid)).findFirst();

    }


    @Override
    public IManager init() {
        registerISessions();
        return this;
    }

    @Override
    public IManager close() {
        return this;
    }
}
