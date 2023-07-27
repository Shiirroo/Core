package de.shiro.record.records;

import de.shiro.api.blocks.GameBlock;
import de.shiro.record.RecordData;
import de.shiro.record.RecordTyp;
import de.shiro.utlits.Config;
import de.shiro.utlits.MethodPrinter;
import de.shiro.utlits.bukkit.ItemStackConverter;
import de.shiro.utlits.bukkit.ItemStackNBT;
import lombok.Getter;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.Item;
import net.md_5.bungee.api.chat.hover.content.ItemSerializer;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.Serial;
import java.util.UUID;


public class BlockBreakRecord extends BlockRecord {
    @Serial
    private static final long serialVersionUID = -1;

    public BlockBreakRecord(UUID causeUserID, String worldName, boolean cancelled,GameBlock gameBlock, ItemStack itemStack) {
        super(causeUserID, worldName, cancelled, RecordTyp.PLAYER_BREAK_BLOCK, gameBlock.getPosition(),  gameBlock.getBlockData(), itemStack, ItemSource.BLOCK);
    }

    @Override
    public TextComponent getChatMessage() {
        TextComponent textComponent = new TextComponent();
        textComponent.addExtra(getTeleportTo());
        ItemStack item = ItemStackConverter.itemStackFromBytes(getItemBytes());
        if(item != null){
            textComponent.addExtra(getBlockSetEvent(Config.getAIR()));
            textComponent.addExtra(getGiveItem(item));
        }
        textComponent.addExtra(getPlayerText());
        textComponent.addExtra(setDefaultText("broke a"));
        textComponent.addExtra(getBlockText());
        textComponent.addExtra(getTimeStamp());
        return textComponent;
    }


    @Override
    public String toString() {
        return "BlockBreakRecord{" +
                "blockData='" + super.getBlockData() + '\'' +
                '}';
    }

}
