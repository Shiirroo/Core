package de.shiro.record.records;

import com.google.gson.annotations.Expose;
import de.shiro.api.blocks.Point3;
import de.shiro.record.RecordTyp;
import de.shiro.utlits.bukkit.ItemStackConverter;
import de.shiro.utlits.bukkit.ItemStackNBT;
import de.shiro.utlits.Utlits;
import lombok.Getter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.ItemTag;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.Serial;
import java.util.UUID;


public abstract class ItemRecord extends RecordPosData {
    @Serial
    private static final long serialVersionUID = -1;
    @Getter @Expose
    private final ItemSource source;
    @Getter @Expose
    private final byte[] itemBytes;

    public ItemRecord(UUID causeUserID, String worldName, boolean cancelled, RecordTyp recordTyp, Point3 point3, ItemStack itemStack, ItemSource dropSource) {
        super(causeUserID, worldName, cancelled,recordTyp,  point3);
        this.source = dropSource;
        itemBytes = ItemStackConverter.itemStackToBytes(itemStack);

    }

    public TextComponent getSpawnItemEvent(ItemStack itemStack){
        ItemStackNBT itemStackNBT = new ItemStackNBT(itemStack);
        String builder = "/summon minecraft:item " + getPos().getX() + " " +
                getPos().getY() + " " +
                getPos().getZ() + " " +
                itemStackNBT.getTags();
        ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, builder) ;
        return getPerformAction(clickEvent);
    }

    public TextComponent getGiveItem(ItemStack item){
        StringBuilder builder = new StringBuilder(Utlits.getFormatKey(item.getTranslationKey()));
        if (item.hasItemMeta() && item.getItemMeta() != null) builder.append(item.getItemMeta().getAsString());
        ClickEvent clickEvent  = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/give @s " + builder);
        return getItem(clickEvent);
    }

    public TextComponent getItemText(ItemStack itemStack){
        ItemMeta im = itemStack.getItemMeta();
        ItemTag nbt = im != null ?  ItemTag.ofNbt(im.getAsString()) : ItemTag.ofNbt("");
        TextComponent action = setActionText("item" + (itemStack.getAmount()>1? "s": ""));
        Item item = new Item(Utlits.getFormatKey(itemStack.getTranslationKey()), itemStack.getAmount(), nbt);
        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_ITEM, item);
        action.setHoverEvent(hoverEvent);
        return action;
    }

    @Override
    public String toString() {
        return "ItemRecord{" +
                "source=" + source +
                ", itemBytes=" + (itemBytes != null? itemBytes.length : "0") +
                '}';
    }
}
