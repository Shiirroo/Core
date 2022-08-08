package de.shiro.system.permission;

import de.shiro.utlits.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class PermManager {

    private final  Plugin plugin;
    private final ConfigManager configManager;
    private static final ArrayList<PlayerPermissions> playerPermissions = new ArrayList<>();


    public PermManager(Plugin plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
    }




    public void setupPermissions(Player player) {
        PermissionAttachment attachment = player.addAttachment(plugin);
        Optional<PlayerPermissions> optionalPermissionAttachment = playerPermissions.stream().filter(perm -> perm.getUuid().equals(player.getUniqueId())).findFirst();
        if(optionalPermissionAttachment.isPresent()) playerPermissions.removeIf(perm -> perm.getUuid().equals(player.getUniqueId()));
        playerPermissions.add(new PlayerPermissions(attachment, player.getUniqueId()));
        String player_rank = configManager.getConfig("profiles").getString("Users." + player.getName() + ".rank");
        for (String perm : configManager.getConfig("permissions").getStringList("Ranks." + player_rank + ".permissions")) {
            if (perm.contains("*")) {
                for (Permission perms : getPerms()) {
                    attachment.setPermission(perms.getName(), true);
                }
            } else if (perm.contains("-*")) {
                unsetPermissions(player);
            }

            attachment.setPermission(perm, true);

            if (perm.startsWith("-")) {
                attachment.setPermission(perm.split("-")[1], false);
            }
        }

        player.recalculatePermissions();
    }

    public List<Permission> getPerms() {
        List<Permission> perms = new ArrayList<>();
        for (Permission perm : Bukkit.getPluginManager().getPermissions()) {
            if (!perms.contains(perm)) {
                perms.add(perm);
            }
        }

        return perms;
    }


    public void unsetPermissions(Player player) {
        Optional<PlayerPermissions> optionalPermissionAttachment = playerPermissions.stream().filter(perm -> perm.getUuid().equals(player.getUniqueId())).findFirst();
        if(optionalPermissionAttachment.isPresent()) {
            PlayerPermissions playerPermission = optionalPermissionAttachment.get();
            PermissionAttachment attachment = playerPermission.getPermissionAttachment();
            for (PermissionAttachmentInfo permissionAttachmentInfo : player.getEffectivePermissions()) {
                attachment.setPermission(permissionAttachmentInfo.getPermission(), false);
            }
            player.removeAttachment(attachment);
            playerPermissions.removeIf(perm -> perm.getUuid().equals(player.getUniqueId()));
        }
    }
}
