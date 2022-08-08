package de.shiro.actionregister.pos.config;

import de.shiro.actionregister.pos.PosManager;
import de.shiro.api.blocks.Point3D;
import de.shiro.system.config.AbstractActionConfig;
import de.shiro.system.config.ISession;
import lombok.Getter;
import lombok.Setter;

public class AbstractPosActionConfig extends AbstractActionConfig {

    @Getter @Setter
    private String worldName = "";
    @Getter @Setter
    private String posName = "";

    @Getter
    private int page = 1;

    @Getter
    private final PosManager posManager;

    public AbstractPosActionConfig(ISession iSession) {
        super(iSession);
        this.posManager = new PosManager(iSession);

    }

    public AbstractPosActionConfig(ISession iSession,String getPosName, int page) {
        super(iSession);
        if(getPosName != null) this.posName = getPosName;
        this.posManager = new PosManager(iSession);
        this.page = page;
    }

    public AbstractPosActionConfig(ISession iSession,String getPosName, String worldName) {
        super(iSession);
        if(getPosName != null) this.posName = getPosName;
        if(worldName != null) this.worldName = worldName;
        this.posManager = new PosManager(iSession);

    }

    public AbstractPosActionConfig(ISession iSession,String getPosName) {
        super(iSession);
        if(getPosName != null) this.posName = getPosName;
        this.posManager = new PosManager(iSession);

    }

    @Override
    public String toString() {
        return "PosManager{" +
                "worldName='" + worldName + '\'' +
                ", posName='" + posName + '\'' +
                ", page=" + page +
                '}';
    }



}
