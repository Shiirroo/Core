package de.shiro.manager.config;


import de.shiro.manager.manager.IManager;
import de.shiro.manager.mongo.MongoConfig;
import de.shiro.utlits.log.LogManager;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class ConfigManager implements IManager {
    private final Logger logger = LoggerFactory.getLogger(ConfigManager.class);

    @Getter
    private ConfigLoader<MongoConfig> mongoConfigConfigLoader;
    @Getter
    private ConfigLoader<LogManager> logConfigConfigLoader;



    public ConfigManager(){
        createConfigFolder();

    }




    public void createConfigFolder(){
        File file = new File("./configs");
        if(file.exists() && file.isDirectory()) return;
        if(file.mkdirs()){
            logger.info("Configs Folder created!");
        } else {
            logger.info("Configs Folder can´t created!");
        }
    }


    @Override
    public ConfigManager init() {
        this.mongoConfigConfigLoader = new ConfigLoader<>(MongoConfig.class);
        this.logConfigConfigLoader = new ConfigLoader<>(LogManager.class);
        return this;
    }

    @Override
    public ConfigManager close() {
        return this;
    }
}
