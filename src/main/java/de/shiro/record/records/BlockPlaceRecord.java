package de.shiro.record.records;

import de.shiro.api.blocks.GameBlock;
import de.shiro.record.RecordTyp;
import de.shiro.utlits.bukkit.ItemStackConverter;
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


public class BlockPlaceRecord extends BlockRecord {
    @Serial
    private static final long serialVersionUID = -1;

    public BlockPlaceRecord(UUID causeUserID, String worldName, boolean cancelled, GameBlock gameBlock, ItemStack itemStack) {
        super(causeUserID, worldName, cancelled,  RecordTyp.PLAYER_PLACE_BLOCK,  gameBlock.getPosition(), gameBlock.getBlockData(), itemStack, ItemSource.BLOCK);
    }
    @Override
    public TextComponent getChatMessage() {
        TextComponent textComponent = new TextComponent();
        textComponent.addExtra(getTeleportTo());
        ItemStack item = ItemStackConverter.itemStackFromBytes(getItemBytes());
        if(item != null){
            textComponent.addExtra(getBlockSetEvent(item));
            textComponent.addExtra(getGiveItem(item));
        }
        textComponent.addExtra(getPlayerText());
        textComponent.addExtra(setDefaultText("placed a"));
        textComponent.addExtra(getBlockText());
        textComponent.addExtra(getTimeStamp());
        return textComponent;
    }


    @Override
    public String toString() {
        return "BlockBreakRecord{" +
                "blockData='" + getBlockData() + '\'' +
                '}';
    }

}
