package de.shiro.record.records;

import de.shiro.api.blocks.GameBlock;
import de.shiro.record.RecordTyp;
import de.shiro.utlits.MethodPrinter;
import lombok.Getter;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.ItemTag;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Item;
import net.minecraft.network.chat.IChatBaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.io.Serial;
import java.util.UUID;


public class PlayerChatRecord extends RecordPosData {
    @Serial
    private static final long serialVersionUID = -1;
    @Getter
    private final String blockData;

    public PlayerChatRecord(UUID causeUserID, String worldName, boolean cancelled, GameBlock gameBlock) {
        super(causeUserID,worldName, cancelled, RecordTyp.PLAYER_BREAK_BLOCK, gameBlock.getPosition());
        this.blockData = gameBlock.getBlockData();
    }

    @Override
    public TextComponent getChatMessage() {
        TextComponent textComponent = new TextComponent();
        textComponent.addExtra(getTeleportTo());
        textComponent.addExtra(getPlayerText());
        textComponent.addExtra(setDefaultText("broke a block of"));
        BlockData data = Bukkit.createBlockData(blockData);
        String blockMaterial = data.getMaterial().name();
        TextComponent action = setActionText(blockMaterial.charAt(0) + blockMaterial.substring(1));
        Material material = data.getMaterial();
        material.getTranslationKey();

        ItemStack itemStack = new ItemStack(data.getMaterial());
        System.out.println(itemStack);


        net.minecraft.world.item.ItemStack  nms = CraftItemStack.asNMSCopy(itemStack);
        System.out.println(nms);
        net.minecraft.world.item.Item items = nms.d();
        System.out.println("?___?");
        MethodPrinter.printMethods(items);
        System.out.println("!___!");
        MethodPrinter.printMethods(nms);

        IChatBaseComponent compound = (  items.p() != null? items.p() : null);

        System.out.println(compound);
        assert compound != null;
        Item item = new Item(itemStack.getTranslationKey(), 1, ItemTag.ofNbt(compound.getString()));
        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_ITEM, item);
        action.setHoverEvent(hoverEvent);


        textComponent.addExtra(action);
        textComponent.addExtra(getTimeStamp());
        return textComponent;
    }

    @Override
    public String toString() {
        return "BlockBreakRecord{" +
                "blockData='" + blockData + '\'' +
                '}';
    }

}
