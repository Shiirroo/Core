package de.shiro.record.records;

import de.shiro.api.blocks.Point3;
import de.shiro.record.RecordData;
import de.shiro.record.RecordTyp;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

import java.io.Serial;
import java.util.Objects;
import java.util.UUID;

@Getter
public abstract class RecordPosData extends RecordData {

    @Serial
    private static final long serialVersionUID = -1;

    private final Point3 pos;

    public RecordPosData(UUID causeUserID,String worldName, boolean cancelled, RecordTyp typ, Point3 pos) {
        super(causeUserID, worldName,cancelled, typ);
        this.pos = pos;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        RecordPosData that = (RecordPosData) object;
        return Objects.equals(pos, that.pos);
    }

    public TextComponent getTeleportTo(){
        TextComponent textComponent = new TextComponent();
        textComponent.setColor(ChatColor.YELLOW);
        textComponent.setText("[֍]");
        ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp @s " + pos.getX() + " " +pos.getY() + " " + pos.getZ());
        textComponent.setClickEvent(clickEvent);
        TextComponent hoverText = new TextComponent();
        hoverText.setText("Teleport to: " +  "X:" + pos.getX() + " Y:" +pos.getY() + " Z:" + pos.getX());
        TextComponent[] array = new TextComponent[1];
        array[0] = hoverText;
        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(array));
        textComponent.setHoverEvent(hoverEvent);
        return textComponent;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), pos);
    }

    @Override
    public String toString() {
        return "RecordPosData{" +
                "pos=" + pos.toString() +
                '}';
    }
}
