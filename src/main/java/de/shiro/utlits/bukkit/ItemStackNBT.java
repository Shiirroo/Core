package de.shiro.utlits.bukkit;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ItemTag;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStackNBT {

    @Getter
    public final ItemStack is;


    public ItemStackNBT(ItemStack is){
        this.is = is;
    }




    public String getTags(){
        StringBuilder builder;
        builder = new StringBuilder("{Item:{id:\"minecraft:");
        builder.append(is.getType().name().toLowerCase()).append("\",");
        builder.append("Count:1b").append(",");
        builder.append("tag:").append(getNBT());
        builder.append("}}");
        return builder.toString();
    }


    public String getNBT(){
        ItemMeta im = is.getItemMeta();
        ItemTag nbt = im != null ?  ItemTag.ofNbt(im.getAsString()) : ItemTag.ofNbt("");
        return nbt.getNbt();
    }


    public Item toTextItem(){
        ItemMeta im = is.getItemMeta();
        ItemTag nbt = im != null ?  ItemTag.ofNbt(im.getAsString()) : ItemTag.ofNbt("");
        TextComponent textComponent = new TextComponent();
        textComponent.setText("item" + (is.getAmount()>1? "s": "") + " ");
        textComponent.setColor(ChatColor.AQUA);
        return new Item(getFormatKey(is.getTranslationKey()), is.getAmount(), nbt);
    }


    public String getFormatKey(String key){
        return key.replace("block.minecraft.", "minecraft:").replace("item.minecraft.", "minecraft:").toLowerCase();
    }


}
