package de.shiro.actionregister.chunk;

import de.shiro.system.action.manager.ActionType;
import de.shiro.api.blocks.Area;
import de.shiro.system.config.AbstractChunkActionConfig;
import de.shiro.system.config.ISession;
import lombok.Getter;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class ChunkSelectConfig extends AbstractChunkActionConfig {

    @Getter
    private final Area area;
    @Getter
    private final List<Entity> entities = new ArrayList<>();

    public ChunkSelectConfig(ISession iSession, Area area) {
        super(iSession);
        this.area = area;
        checkChunkSelected();
    }

    @Override
    public ActionType getActionType() {
        return ActionType.SYNC;
    }

    public synchronized void addEntity(Entity entity) {
        entities.add(entity);
    }

    public synchronized void delete(Entity entity) {
        entities.remove(entity);
        entity.remove();
    }


}
