package de.shiro.actionregister.chunk;

import de.shiro.system.action.manager.ActionType;
import de.shiro.api.blocks.Area;
import de.shiro.system.config.AbstractActionConfig;
import de.shiro.system.config.ISession;
import lombok.Getter;
import lombok.Setter;

public class SaveChunkConfig extends AbstractActionConfig {

    @Getter @Setter
    private final Area area;
    @Getter
    private final SaveChunkCallBack chunkBlockCallback = new SaveChunkCallBack();


    public SaveChunkConfig(ISession iSession, Area area) {
        super(iSession);
        this.area = area;
    }


    @Override
    public ActionType getActionType() {
        return ActionType.ASYNC;
    }


}
