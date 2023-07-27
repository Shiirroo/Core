package de.shiro.bukkitevents;

import de.shiro.api.blocks.Point3;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class OnInventoryClick implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInventoryClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        assert holder != null;
        System.out.println(holder.getInventory().getType() + " " + event.getAction());
        if (event.getWhoClicked() instanceof Player player && (holder instanceof BlockState || holder instanceof DoubleChest)) {
            Point3 pos = new Point3(player.getLocation());
            Pair<ItemStack, Integer> pair = switch (event.getAction()) {
                case PICKUP_ONE, DROP_ONE_SLOT -> {
                    if(event.getRawSlot() >= event.getView().getTopInventory().getSize()) yield null;
                    if (event.getCurrentItem() == null) yield null;
                    yield Pair.of(event.getCurrentItem(), -1);
                }
                case PLACE_ONE -> {
                    if(event.getRawSlot() >= event.getView().getTopInventory().getSize()) yield null;
                    if (event.getCurrentItem() == null) yield null;
                    yield Pair.of(event.getCursor(), 1);
                }
                case PICKUP_HALF -> {
                    if(event.getRawSlot() >= event.getView().getTopInventory().getSize()) yield null;
                    if (event.getCurrentItem() == null) yield null;
                    yield Pair.of(event.getCurrentItem(), -(event.getCurrentItem().getAmount() + 1) / 2);
                }
                case PICKUP_SOME -> {
                    if(event.getRawSlot() >= event.getView().getTopInventory().getSize()) yield null;
                    if (event.getCurrentItem() == null) yield null;
                    int taken = event.getCurrentItem().getAmount() - event.getCurrentItem().getMaxStackSize();
                    yield Pair.of(event.getCursor(), taken);
                }
                case PICKUP_ALL, DROP_ALL_SLOT -> {
                    if(event.getRawSlot() >= event.getView().getTopInventory().getSize()) yield null;
                    if (event.getCurrentItem() == null) yield null;
                    yield Pair.of(event.getCurrentItem(), -event.getCurrentItem().getAmount());
                }
                case PLACE_SOME -> {
                    if(event.getRawSlot() >= event.getView().getTopInventory().getSize()) yield null;
                    if (event.getCurrentItem() == null) yield null;
                    int placeable = event.getCurrentItem().getMaxStackSize() - event.getCurrentItem().getAmount();
                    ;
                    yield Pair.of(event.getCursor(), placeable);
                }
                case PLACE_ALL -> {
                    if(event.getRawSlot() >= event.getView().getTopInventory().getSize()) yield null;
                    if (event.getCursor() == null) yield null;
                    yield Pair.of(event.getCursor(), event.getCursor().getAmount());
                }

                case SWAP_WITH_CURSOR -> {
                    throw new RuntimeException("NOT IMPLEMENTED");
                    /*
                       if (event.getRawSlot() < event.getView().getTopInventory().getSize()) {
                            modifications.addModification(event.getCursor(), event.getCursor().getAmount());
                            modifications.addModification(event.getCurrentItem(), -event.getCurrentItem().getAmount());
                        }

                    */
                }
                case MOVE_TO_OTHER_INVENTORY -> {
                    System.out.println("Cursor" + event.getCursor());
                    System.out.println("getCurrentItem" + event.getCurrentItem());
                    if (event.getCurrentItem() == null) yield null;
                    boolean removed = event.getRawSlot() < event.getView().getTopInventory().getSize();
                    yield Pair.of(event.getCurrentItem(), event.getCurrentItem().getAmount() * (removed ? -1 : 1));
                }
                case COLLECT_TO_CURSOR -> {
                    if(event.getRawSlot() >= event.getView().getTopInventory().getSize()) yield null;
                    ItemStack cursor = event.getCursor();
                    if (cursor == null) yield null;
                    int toPickUp = cursor.getMaxStackSize() - cursor.getAmount();
                    int takenFromContainer = 0;
                    boolean takeFromFullStacks = false;
                    Inventory top = event.getView().getTopInventory();
                    Inventory bottom = event.getView().getBottomInventory();
                    while (toPickUp > 0) {
                        for (ItemStack stack : top.getStorageContents()) {
                            if (cursor.isSimilar(stack)) {
                                if (takeFromFullStacks == (stack.getAmount() == stack.getMaxStackSize())) {
                                    int take = Math.min(toPickUp, stack.getAmount());
                                    toPickUp -= take;
                                    takenFromContainer += take;
                                    if (toPickUp <= 0) {
                                        break;
                                    }
                                }
                            }
                        }
                        if (toPickUp <= 0) {
                            break;
                        }
                        for (ItemStack stack : bottom.getStorageContents()) {
                            if (cursor.isSimilar(stack)) {
                                if (takeFromFullStacks == (stack.getAmount() == stack.getMaxStackSize())) {
                                    int take = Math.min(toPickUp, stack.getAmount());
                                    toPickUp -= take;
                                    if (toPickUp <= 0) {
                                        break;
                                    }
                                }
                            }
                        }
                        if (takeFromFullStacks) {
                            break;
                        } else {
                            takeFromFullStacks = true;
                        }
                    }
                    if (takenFromContainer > 0) {
                        yield Pair.of(event.getCursor(), -takenFromContainer);
                    }
                    throw new RuntimeException("NOT IMPEMENTET");
                }
                case HOTBAR_SWAP, DROP_ALL_CURSOR, DROP_ONE_CURSOR -> {
                    System.out.println("STATE: " + event.getAction());
                    System.out.println("Cursor" + event.getCursor());
                    System.out.println("Cursor" + event.getCurrentItem());

                    throw new RuntimeException("NOT IMPEMENTET");
                }
                case HOTBAR_MOVE_AND_READD -> {
                    if (event.getRawSlot() < event.getView().getTopInventory().getSize()) {
                        ItemStack otherSlot = (event.getClick() == ClickType.SWAP_OFFHAND) ? event.getWhoClicked().getInventory().getItemInOffHand() : event.getWhoClicked().getInventory().getItem(event.getHotbarButton());
                        if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
                            yield Pair.of(event.getCurrentItem(), -event.getCurrentItem().getAmount());
                        }
                        if (otherSlot != null && otherSlot.getType() != Material.AIR) {
                            yield Pair.of(otherSlot, otherSlot.getAmount());
                        }
                    }
                    throw new RuntimeException("NOT IMPEMENTET");
                }
                default -> null;
            };
            if(pair != null){
                System.out.println(pair.getKey() + " :" + pair.getValue());

            }
        }
    }
}