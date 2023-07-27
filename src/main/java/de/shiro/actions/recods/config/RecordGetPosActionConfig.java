package de.shiro.actions.recods.config;

import de.shiro.Record;
import de.shiro.api.blocks.Area;
import de.shiro.api.blocks.Point3;
import de.shiro.manager.manager.RecordManager;
import de.shiro.system.config.AbstractPosActionConfig;
import de.shiro.system.config.ISession;
import lombok.Getter;
import lombok.Setter;

public class RecordGetPosActionConfig extends RecordGetActionConfig {

    @Getter @Setter
    private final int range;
    @Getter
    private final String worldName;
    @Getter
    private final Point3 pos;
    @Getter
    private final Area area;


  public RecordGetPosActionConfig(ISession iSession, String worldName, Point3 point3, long from, long to, int range, int page) {
        super(iSession, from, to, page);
        this.worldName = worldName;
        this.pos = point3;
        this.range = range;
        this.area = new Area(point3, range);
  }

}
