package de.shiro.actions.recods.config;

import de.shiro.Record;
import de.shiro.api.blocks.Area;
import de.shiro.api.blocks.Point3;
import de.shiro.manager.manager.RecordManager;
import de.shiro.system.config.*;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public abstract class RecordActionConfig extends AbstractPosActionConfig {

    @Getter @Setter
    private final int page;

    @Getter @Setter
    private final long timefrom;
    @Getter @Setter
    private final long timeto;
    @Getter @Setter
    private final int range;
    @Getter
    private final Area area;
    @Getter
    private final RecordManager recordManager;


  public RecordActionConfig(ISession iSession, String worldName, Point3 point3, long from, long to, int range, int page) {
        super(iSession, worldName, point3);
        this.timefrom = from;
        this.timeto = to;
        this.range = range;
        this.area = new Area(point3, range);
        this.recordManager = Record.getManager().getRecordManager();
        this.page = page;
    }

    public Long between(){
        return getTimeto() - getTimefrom();
    }
}
