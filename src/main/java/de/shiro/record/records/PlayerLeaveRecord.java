package de.shiro.record.records;

import com.google.gson.annotations.Expose;
import de.shiro.api.blocks.Point3;
import de.shiro.api.blocks.Point3D;
import de.shiro.record.IRecordData;
import de.shiro.record.RecordData;
import de.shiro.record.RecordTyp;
import de.shiro.record.Records;
import lombok.Getter;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;

import java.io.Serial;
import java.util.Objects;
import java.util.UUID;

public class PlayerLeaveRecord extends RecordPosData {

    @Serial
    private static final long serialVersionUID = -1;

    public PlayerLeaveRecord(UUID causeUserID, String worldName, Point3 pos) {
        super(causeUserID, worldName, false, RecordTyp.PLAYER_LEAVE, pos);
    }

    @Override
    public TextComponent getChatMessage() {
        TextComponent textComponent = new TextComponent();
        textComponent.addExtra(getTeleportTo());
        textComponent.addExtra(getPlayerText());
        textComponent.addExtra(setDefaultText("leaved the server"));
        textComponent.addExtra(getTimeStamp());
        return textComponent;
    }
}