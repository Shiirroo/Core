package de.shiro.utlits.bukkit;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ItemStackConverter {

    public static ItemStack[] itemStackArrayFromBase64(String data)  {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];
            for (int i = 0; i < items.length; i++) items[i] = (ItemStack) dataInput.readObject();
            dataInput.close();
            return items;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static ItemStack itemStackFromBytes(byte[] bytes)  {
        return  itemStackFromBytes(bytes, Material.AIR);
    }

    public static ItemStack itemStackFromBytes(byte[] bytes, Material defaultItem)  {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        try {
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            dataInput.close();
            if(dataInput.readObject() instanceof ItemStack itemStack){
                return itemStack;
            }
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
        return new ItemStack(defaultItem);
    }

    public static byte[] itemStackToBytes(ItemStack itemStack)  {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeObject(itemStack);
            dataOutput.close();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return outputStream.toByteArray();
    }

    public static String itemStackArrayToBase64(ItemStack[] items)   {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeInt(items.length);
            for (ItemStack item : items) dataOutput.writeObject(item);
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        }  catch (IOException e ) {
            throw new RuntimeException(e);
        }
    }



}