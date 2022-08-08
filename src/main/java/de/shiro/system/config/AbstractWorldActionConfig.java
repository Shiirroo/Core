package de.shiro.system.config;

import lombok.Getter;
import org.bukkit.persistence.PersistentDataType;

public class AbstractWorldActionConfig extends AbstractActionConfig {

    @Getter
    private final String worldName;


    public AbstractWorldActionConfig(ISession iSession, String worldName) {
        super(iSession);
        this.worldName = worldName;
    }





}
