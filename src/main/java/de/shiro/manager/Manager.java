package de.shiro.manager;

import de.shiro.Record;
import de.shiro.manager.config.ConfigManager;
import de.shiro.manager.manager.*;
import de.shiro.manager.mongo.MongoManager;
import de.shiro.manager.manager.RecordManager;
import de.shiro.system.action.manager.CommandManager;
import de.shiro.system.action.manager.event.ActionEventManager;
import de.shiro.utlits.log.LogManager;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.List;

public class Manager {

    @Getter
    private final MongoManager mongoManager;
    @Getter
    private final EventManager eventManager;
    @Getter
    private final ConfigManager configManager;
    @Getter
    private final RecordManager recordManager;
    @Getter
    private final ISessionManager iSessionManager;
    @Getter
    private final ActionEventManager actionEventManager;
    @Getter
    private final PlayerActionManager playerActionManager;
    @Getter
    private final CommandManager commandManager;
    @Getter
    private final List<IManager> managers;
    @Getter
    private final LogManager logManager;

    public Manager(Record plugin){
        this.configManager = new ConfigManager().init();
        this.mongoManager = new MongoManager(configManager.getMongoConfigConfigLoader().loadConfig());
        this.eventManager = new EventManager(plugin);
        this.recordManager = new RecordManager();
        this.iSessionManager = new ISessionManager();
        this.actionEventManager = new ActionEventManager();
        this.playerActionManager = new PlayerActionManager();
        this.commandManager = new CommandManager(plugin);
        this.logManager = configManager.getLogConfigConfigLoader().loadConfig();
        this.managers = List.of(configManager, mongoManager, eventManager, recordManager, iSessionManager, actionEventManager, playerActionManager, commandManager);
    }


    public void init() {
        managers.forEach(IManager::init);
    }

    public void close() {
        Bukkit.getScheduler().cancelTasks(Record.getInstance());
        managers.forEach(IManager::close);
    }
}
