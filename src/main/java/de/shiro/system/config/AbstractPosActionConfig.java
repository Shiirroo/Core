package de.shiro.system.config;

import de.shiro.api.blocks.Point3;
import de.shiro.system.action.manager.ActionType;
import lombok.Getter;

public class AbstractPosActionConfig extends AbstractActionConfig {

    @Getter
    private final String worldName;
    @Getter
    private final Point3 pos;

    public AbstractPosActionConfig(ISession iSession, String worldName, Point3 point3) {
        super(iSession, ActionType.ASYNC);
        this.worldName = worldName;
        this.pos = point3;
    }





}
