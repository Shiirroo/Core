package de.shiro.system.permission;

import lombok.Getter;
import org.bukkit.permissions.PermissionAttachment;

import java.util.UUID;

public class PlayerPermissions {

    @Getter
    private final PermissionAttachment permissionAttachment;

    @Getter
    private final UUID uuid;


    public PlayerPermissions(PermissionAttachment permissionAttachment, UUID uuid) {
        this.permissionAttachment = permissionAttachment;
        this.uuid = uuid;
    }




}
