package de.shiro.system.config;

import de.shiro.Record;
import de.shiro.api.blocks.Area;
import de.shiro.api.blocks.Point3;
import de.shiro.manager.manager.RecordManager;
import lombok.Getter;
import lombok.Setter;

public abstract class AbstractRecordActionConfig extends AbstractActionConfig {
    @Getter
    private final RecordManager recordManager;

    public AbstractRecordActionConfig(ISession iSession) {
        super(iSession);
        this.recordManager = Record.getManager().getRecordManager();
    }

}
