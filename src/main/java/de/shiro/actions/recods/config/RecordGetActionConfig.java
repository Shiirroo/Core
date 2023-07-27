package de.shiro.actions.recods.config;

import de.shiro.Record;
import de.shiro.api.blocks.Area;
import de.shiro.api.blocks.Point3;
import de.shiro.manager.manager.RecordManager;
import de.shiro.system.config.AbstractActionConfig;
import de.shiro.system.config.AbstractPosActionConfig;
import de.shiro.system.config.AbstractRecordActionConfig;
import de.shiro.system.config.ISession;
import lombok.Getter;
import lombok.Setter;

public class RecordGetActionConfig extends AbstractRecordActionConfig {

    @Getter @Setter
    private final int page;
    @Getter @Setter
    private final long timefrom;
    @Getter @Setter
    private final long timeto;

  public RecordGetActionConfig(ISession iSession, long from, long to,int page) {
        super(iSession);
        this.timefrom = from;
        this.timeto = to;
        this.page = page;
  }

    public Long between(){
        return getTimeto() - getTimefrom();
    }
}
