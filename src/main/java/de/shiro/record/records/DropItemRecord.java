package de.shiro.record.records;

import de.shiro.api.blocks.Point3;
import de.shiro.record.RecordTyp;
import de.shiro.utlits.bukkit.ItemStackConverter;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.inventory.ItemStack;

import java.io.Serial;
import java.util.UUID;


public class DropItemRecord extends ItemRecord {


    @Serial
    private static final long serialVersionUID = -1;

    public DropItemRecord(UUID causeUserID, String worldName, boolean cancelled, Point3 point3, ItemStack itemStack, ItemSource itemSource) {
        super(causeUserID,worldName, cancelled,RecordTyp.DROP_ITEM, point3,itemStack, itemSource);
    }

    @Override
    public TextComponent getChatMessage() {
        ItemStack item = ItemStackConverter.itemStackFromBytes(getItemBytes());
        TextComponent textComponent = new TextComponent();
        textComponent.addExtra(getTeleportTo());
        if(item != null)  textComponent.addExtra(getSpawnItemEvent(item));
        if(item != null)  textComponent.addExtra(getGiveItem(item));
        textComponent.addExtra(getPlayerText());
        if(item != null)  textComponent.addExtra(setDefaultText("drop " + item.getAmount() + " of"));
        if(item != null)  textComponent.addExtra(getItemText(item));
        else textComponent.addExtra(setDefaultText(" item"));
        textComponent.addExtra(getTimeStamp());
        return textComponent;
    }



}
