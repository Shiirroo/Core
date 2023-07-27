package de.shiro.record;

import com.google.gson.annotations.Expose;
import de.shiro.record.records.PlayerJoinRecord;
import de.shiro.utlits.Utlits;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Entity;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
public abstract class RecordData implements Serializable {
    @Serial
    private static final long serialVersionUID = -1;
    @Expose
    private final Long recordTime;
    @Expose
    private final UUID causeUserID;
    @Expose
    private final boolean cancelled;
    @Expose
    private final RecordTyp recordTyp;
    @Expose
    private final String worldName;

    public RecordData(UUID causeUserID, String worldName, boolean cancelled, RecordTyp recordTyp){
        this.recordTime = System.currentTimeMillis();
        this.causeUserID = causeUserID;
        this.cancelled = cancelled;
        this.recordTyp = recordTyp;
        this.worldName = worldName;
    }


    @Override
    public String toString() {
        return "RecordData{" +
                ", recordTime=" + recordTime +
                ", causeUserID=" + causeUserID +
                ", cancelled=" + cancelled +
                '}';
    }

    public abstract TextComponent getChatMessage();

    public TextComponent getPerformAction(ClickEvent clickEvent){
        TextComponent textComponent = new TextComponent();
        textComponent.setText("[☠]");
        textComponent.setColor(ChatColor.DARK_RED);
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Performe Action")));
        textComponent.setClickEvent(clickEvent);
        return textComponent;
    }

    public TextComponent getItem(ClickEvent clickEvent){
        TextComponent textComponent = new TextComponent();
        textComponent.setText("[\uD83D\uDCCB]");
        textComponent.setColor(ChatColor.GREEN);
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Give Item")));
        textComponent.setClickEvent(clickEvent);
        return textComponent;
    }



    public TextComponent getTimeStamp(){
        TextComponent endBorder = getEndBorder();
        TextComponent textComponent = new TextComponent();
        long time = ((System.currentTimeMillis() - getRecordTime()) / 1000);
        textComponent.setText(Utlits.convertTimeSingle(time));
        textComponent.setColor(ChatColor.GRAY);
        endBorder.addExtra(textComponent);

        TextComponent[] textComponents = new TextComponent[1];
        textComponents[0] = new TextComponent(Utlits.convertTime(time));
        Text text = new Text(textComponents);
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, text));

        return endBorder;
    }

    public TextComponent getPlayerText(){
        TextComponent textComponent = new TextComponent();
        Player player = Bukkit.getPlayer(getCauseUserID());
        String displayName;
        if(player != null) displayName = player.getDisplayName();
        else {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(getCauseUserID());
            displayName = offlinePlayer.getName();
        }
        textComponent.setText(" " + displayName + " ");
        textComponent.setColor(ChatColor.RED);
        TextComponent displayname = new TextComponent(displayName);
        Entity entity = new Entity("minecraft:player", getCauseUserID().toString(), displayname);
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ENTITY, entity));
        return textComponent;
    }

    public TextComponent setDefaultText(String text){
        TextComponent textComponent = new TextComponent();
        textComponent.setText(text + " ");
        textComponent.setColor(ChatColor.WHITE);
        return textComponent;
    }

    public TextComponent setActionText(String text){
        TextComponent textComponent = new TextComponent();
        textComponent.setText(text + " ");
        textComponent.setColor(ChatColor.AQUA);
        return textComponent;
    }

    public TextComponent getEndBorder(){
        return setDefaultText("|");
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        RecordData that = (RecordData) object;
        return cancelled == that.cancelled && Objects.equals(recordTime, that.recordTime) && Objects.equals(causeUserID, that.causeUserID);
    }

    public RecordData toRecordType(){
        return recordTyp.getRecordDataClass().cast(this);
    }




    @Override
    public int hashCode() {
        return Objects.hash(recordTime, causeUserID, cancelled);
    }
}
