package de.shiro.system.config;

import com.google.gson.annotations.Expose;
import de.shiro.Record;
import de.shiro.api.blocks.Point3;
import de.shiro.manager.bossbar.BossBarCreator;
import de.shiro.manager.manager.ISessionManager;
import de.shiro.system.action.PlayerAction;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ISession {

    @Getter @Expose
    private final UUID executorID;

    @Getter @Expose
    private final String executorName;

    @Getter
    private List<String> permissions;

    @Getter
    private final PlayerAction playerAction;
    @Getter
    private final List<BossBarCreator> bossBars;
    @Getter @Setter
    private boolean showAction = true;

    public ISession(UUID executorID,String executorName) {
        this.executorID = executorID;
        this.executorName = executorName;
        this.playerAction = new PlayerAction(this);
        this.bossBars = new ArrayList<>();

    }

    public static ISession of(UUID executorId, String executorName) {
        return new ISession(executorId,executorName);
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

    public Point3 getSessionPoint3() {
        Player player = getSessionPlayer();
        return player != null ? new Point3(player.getLocation()) : null;
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
        if(player != null) player.spigot().sendMessage(message);
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

    @Deprecated //FIXME
    public Object getSessionDataContainer(DataContainer dataContainer, PersistentDataType<?,?> type) {
        return getSessionDataContainer().get(getSessionNamespacedKey(dataContainer), type);
    }

    @Deprecated //FIXME
    public NamespacedKey getSessionNamespacedKey(DataContainer dataContainer) {
        return new NamespacedKey(dataContainer.name().toLowerCase(), executorID.toString());
    }

    public synchronized static ISession getOrAddISession(UUID uuid, String executorName){
        Optional<ISession> optionalISession = Record.getManager().getISessionManager().getISessions().stream().filter(iSession -> iSession.getExecutorID().equals(uuid)).findFirst();
        if(optionalISession.isPresent()){
            return optionalISession.get();
        }else{
            ISession iSession = new ISession(uuid,executorName);
            Record.getManager().getISessionManager().getISessions().add(iSession);
            return iSession;
        }
    }

    public synchronized static ISession getOrAddISession(Player player){
        return getOrAddISession(player.getUniqueId(), player.getName());
    }

    public synchronized static ISession newISession(UUID uuid, String executorName){
        return new ISession(uuid,executorName);
    }

    public synchronized static void checkSessionOrCreate(UUID uuid, String executorName) {
        if(Record.getManager().getISessionManager().getISessions().stream().noneMatch(iSession -> iSession.getExecutorID().equals(uuid))){
            ISession iSession = new ISession(uuid,executorName);
            Record.getManager().getISessionManager().getISessions().add(iSession);
        }
    }

    public ISessionManager getISessionManager(){
        return Record.getManager().getISessionManager();
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
