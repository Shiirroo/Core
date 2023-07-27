package de.shiro.record.records;

import de.shiro.api.blocks.GameBlock;
import de.shiro.api.blocks.Point3;
import de.shiro.record.RecordTyp;
import de.shiro.utlits.Utlits;
import de.shiro.utlits.bukkit.ItemStackNBT;
import lombok.Getter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.ItemTag;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Item;
import org.bukkit.Bukkit;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;

import java.io.Serial;
import java.util.UUID;


public abstract class BlockRecord extends ItemRecord {
    @Serial
    private static final long serialVersionUID = -1;
    @Getter
    private final String blockData;

    public BlockRecord(UUID causeUserID, String worldName, boolean cancelled, RecordTyp recordTyp, Point3 point3, String data, ItemStack itemStack, ItemSource dropSource) {
        super(causeUserID, worldName, cancelled, recordTyp, point3, itemStack, dropSource);
        this.blockData = data;
    }


    public TextComponent getBlockSetEvent(ItemStack item){
        ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/setblock "+ getPos().getX() + " " + getPos().getY() + " "+ getPos().getZ() +" " + Utlits.getFormatKey(item.getTranslationKey()));
        return getPerformAction(clickEvent);
    }


    public TextComponent getBlockText(){
        String[] s = blockData.split("\\[");
        ItemTag nbt = s.length > 1 ?  ItemTag.ofNbt("["+ s[1]) : ItemTag.ofNbt("");
        TextComponent action = setActionText("block");
        Item item = new Item(s[0], 1, nbt);
        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_ITEM, item);
        action.setHoverEvent(hoverEvent);
        return action;
    }

    public String getKey(){
        String[] s = blockData.split("\\[");
        return s[0];
    }

    @Override
    public String toString() {
        return "BlockRecord{" +
                "blockData='" + blockData + '\'' +
                "} " + getPos().toString() + " " + getRecordTime();
    }
}
