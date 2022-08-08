package de.shiro.system.config;

import de.shiro.Core;
import de.shiro.system.action.PlayerAction;
import de.shiro.system.action.manager.ActionFuture;
import de.shiro.system.action.manager.builder.AbstractAction;
import lombok.Getter;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class ISession {

    @Getter
    private final UUID executorID;

    @Getter
    private final String executorName;

    @Getter
    private List<String> permissions;

    @Getter
    private final PlayerAction playerAction = new PlayerAction();

    public ISession(UUID executorID,String executorName) {
        this.executorID = executorID;
        this.executorName = executorName;
    }

    public static ISession of(UUID executerId, String executerName) {
        return new ISession(executerId, executerName);
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public void addPermission(String permission) {
        this.permissions.add(permission);
    }

    public World getSessionWorld() {
        Player player = getSessionPlayer();
        return player != null ? player.getWorld() : null;
    }

    public Location getSessionLocation() {
        Player player = getSessionPlayer();
        return player != null ? player.getLocation() : null;
    }

    public void setSessionLocation(Location location) {
        Player player = getSessionPlayer();
        if(player != null) player.teleport(location);
    }


    public void sendSessionMessage(String message) {
        Player player = getSessionPlayer();
        if(player != null) player.sendMessage(message);
    }

    public void sendSessionTextComponent(TextComponent message) {
        Player player = getSessionPlayer();
        if(player != null) player.sendMessage(message);
    }



    public Player getSessionPlayer() {
        return Bukkit.getPlayer(this.executorID);
    }

    public OfflinePlayer getSessionOfflinePlayer() {
        return Bukkit.getOfflinePlayer(this.executorID);
    }

    public PersistentDataContainer getSessionDataContainer() {
        return getSessionPlayer().getPersistentDataContainer();
    }

    public Object getSessionDataContainer(DataContainer dataContainer, PersistentDataType<?,?> type) {
        return getSessionDataContainer().get(getSessionNamespacedKey(dataContainer), type);
    }

    public NamespacedKey getSessionNamespacedKey(DataContainer dataContainer) {
        return new NamespacedKey(dataContainer.name().toLowerCase(), executorID.toString());
    }

    public synchronized static ISession getOrAddISession(UUID uuid, String executorName){
        Optional<ISession> optionalISession = Core.getISessions().stream().filter(iSession -> iSession.getExecutorID().equals(uuid)).findFirst();
        if(optionalISession.isPresent()){
            return optionalISession.get();
        }else{
            ISession iSession = new ISession(uuid,executorName);
            Core.getISessions().add(iSession);
            return iSession;
        }
    }

    public synchronized static void checkSessionOrCreate(UUID uuid, String executorName) {
        if(Core.getISessions().stream().noneMatch(iSession -> iSession.getExecutorID().equals(uuid))){
            ISession iSession = new ISession(uuid,executorName);
            Core.getISessions().add(iSession);
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ISession) {
            return ((ISession) obj).executorID.equals(this.executorID);
        }
        return false;
    }


    @Override
    public String toString() {
        return this.getClass().getSimpleName() +"{" +
                "executorID=" + getExecutorID() +
                '}';
    }
}
