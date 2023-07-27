package de.shiro.actions.recods.config;

import de.shiro.Record;
import de.shiro.record.RecordData;
import de.shiro.system.action.manager.ActionType;
import de.shiro.system.config.AbstractActionConfig;
import de.shiro.system.config.AbstractRecordActionConfig;
import de.shiro.system.config.ISession;
import lombok.Getter;

public class RecordAddActionConfig extends AbstractRecordActionConfig {

    @Getter
    private final RecordData record;

    public RecordAddActionConfig(ISession iSession, RecordData record) {
        super(iSession);
        this.record = record;
    }
}
