package de.shiro.actionregister.pos.config;

import de.shiro.api.blocks.Point3D;
import de.shiro.api.types.Visibility;
import de.shiro.system.action.manager.ActionType;
import de.shiro.system.config.ISession;
import lombok.Getter;

public class PosWithPointConfig extends AbstractPosActionConfig {

    @Getter
    private final Point3D pos;

    @Getter
    private final String posName;

    @Getter
    private Visibility visibility = Visibility.PUBLIC;

    @Override
    public ActionType getActionType() {
        return ActionType.ASYNC;
    }

    public PosWithPointConfig(ISession iSession, String world, Point3D pos, String posName, Visibility visibility) {
        super(iSession);
        setWorldName(world);
        this.pos = pos;
        this.posName = posName;
        this.visibility = visibility;
    }

    public PosWithPointConfig(ISession iSession, String world, Point3D pos, String posName) {
        super(iSession);
        setWorldName(world);
        this.pos = pos;
        this.posName = posName;
    }

    public PosWithPointConfig of(ISession iSession, String world, Point3D pos, String posName) {
        return new PosWithPointConfig(iSession, world, pos, posName);
    }




}
