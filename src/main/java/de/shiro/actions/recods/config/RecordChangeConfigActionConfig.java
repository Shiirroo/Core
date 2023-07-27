package de.shiro.actions.recods.config;

import de.shiro.Record;
import de.shiro.api.blocks.Area;
import de.shiro.api.blocks.Point3;
import de.shiro.manager.manager.RecordManager;
import de.shiro.system.config.AbstractActionConfig;
import de.shiro.system.config.AbstractPosActionConfig;
import de.shiro.system.config.ISession;
import de.shiro.utlits.log.LogState;
import lombok.Getter;
import lombok.Setter;

public class RecordChangeConfigActionConfig extends AbstractActionConfig {

    @Getter
    private final LogState newState;

    public RecordChangeConfigActionConfig(ISession iSession, LogState logState) {
        super(iSession);
        this.newState = logState;
    }


}
