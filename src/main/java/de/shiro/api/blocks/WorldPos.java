package de.shiro.api.blocks;

import de.shiro.actionregister.pos.config.PosWithPointConfig;
import de.shiro.api.types.Visibility;
import lombok.Getter;

public class WorldPos {

    @Getter
    private String worldName;

    @Getter
    private Point3D point3d;

    @Getter
    private String posName;

    @Getter
    private Visibility visibility = Visibility.PUBLIC;


    //JACKSON
    public WorldPos() {
    }

    public WorldPos(String worldName, Point3D point3d, String posName) {
        this.worldName = worldName;
        this.point3d = point3d;
        this.posName = posName;
    }

    public WorldPos(String worldName, Point3D point3d, String posName, Visibility visibility) {
        this.worldName = worldName;
        this.point3d = point3d;
        this.posName = posName;
        this.visibility = visibility;
    }

    public WorldPos(PosWithPointConfig savePosConfig) {
        this.worldName = savePosConfig.getWorldName();
        this.point3d = savePosConfig.getPos();
        this.posName = savePosConfig.getPosName();
        this.visibility = savePosConfig.getVisibility();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof WorldPos other) {
            return other.getWorldName().equals(worldName) && other.getPoint3d().equals(point3d) && other.getPosName().equals(posName);
        }
        return false;
    }
}
