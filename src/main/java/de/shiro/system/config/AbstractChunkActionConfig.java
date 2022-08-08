package de.shiro.system.config;

import lombok.Getter;
import org.bukkit.persistence.PersistentDataType;

public class AbstractChunkActionConfig extends AbstractActionConfig {

    @Getter
    private Boolean chunkSelected = false;


    public AbstractChunkActionConfig(ISession iSession) {
        super(iSession);
    }

    protected void checkChunkSelected() {
        String tempc = (String) getISession().getSessionDataContainer(DataContainer.ChunkSelect, PersistentDataType.STRING);
        if (tempc != null && tempc.equalsIgnoreCase("TRUE")) {
            this.chunkSelected = true;
        }
    }

    public void setChunkSelected() {
        getISession().getSessionDataContainer().set(getISession().getSessionNamespacedKey(DataContainer.ChunkSelect), PersistentDataType.STRING, "TRUE");

    }

    public void setChunkSelected(Boolean bool) {
        getISession().getSessionDataContainer().set(getISession().getSessionNamespacedKey(DataContainer.ChunkSelect), PersistentDataType.STRING, bool.toString());
    }





}
