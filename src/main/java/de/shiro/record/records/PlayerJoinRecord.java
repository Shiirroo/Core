package de.shiro.record.records;

import de.shiro.api.blocks.GameBlock;
import de.shiro.api.blocks.Point3;
import de.shiro.api.blocks.Point3D;
import de.shiro.record.RecordData;
import de.shiro.record.RecordTyp;
import lombok.Getter;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

import java.io.Serial;
import java.util.Objects;
import java.util.UUID;

public class PlayerJoinRecord extends RecordPosData {

    @Serial
    private static final long serialVersionUID = -1;

    public PlayerJoinRecord(UUID causeUserID, Point3 pos, String worldName) {
        super(causeUserID,  worldName, false, RecordTyp.PLAYER_JOIN, pos);
    }

    @Override
    public TextComponent getChatMessage() {
        TextComponent textComponent = new TextComponent();
        textComponent.addExtra(getTeleportTo());
        textComponent.addExtra(getPlayerText());
        textComponent.addExtra(setDefaultText("joined the server"));
        textComponent.addExtra(getTimeStamp());
        return textComponent;
    }
}
