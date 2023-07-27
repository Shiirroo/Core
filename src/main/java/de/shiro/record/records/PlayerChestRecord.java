package de.shiro.record.records;

import de.shiro.api.blocks.Point3;
import de.shiro.record.RecordTyp;
import lombok.Getter;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import java.io.Serial;
import java.util.UUID;


public class PlayerChestRecord extends ItemRecord {
    @Serial
    private static final long serialVersionUID = -1;
    @Getter
    private final InventoryAction inventoryAction;
    @Getter
    private final int size;

    public PlayerChestRecord(UUID causeUserID, String worldName, boolean cancelled, Point3 point3, ItemStack itemStack, int size, InventoryAction action) {
        super(causeUserID, worldName, cancelled, RecordTyp.PLAYER_PLACE_BLOCK, point3, itemStack, ItemSource.CHEST);
        this.inventoryAction = action;
        this.size = size;
    }

    @Override
    public TextComponent getChatMessage() {
        TextComponent textComponent = new TextComponent();
        textComponent.addExtra(getTeleportTo());
        return textComponent;
    }


}
