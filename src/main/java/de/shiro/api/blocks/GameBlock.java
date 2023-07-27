package de.shiro.api.blocks;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.Objects;

public class GameBlock implements Serializable {

    public static final String BLOCK_DATA_STRING_AIR = Bukkit.createBlockData(Material.AIR).toString();

    @Getter @Expose
    private final Point3 position;

    @Getter @Expose
    private final String blockData;

    public GameBlock() {
        this(0,0,0, BLOCK_DATA_STRING_AIR);
    }

    public GameBlock(int x, int y, int z , String blockData) {
        this.position = new Point3(x, y, z);
        this.blockData = blockData;
    }

    public GameBlock(Point3 position, String blockData) {
        this.position = position;
        this.blockData = blockData;
    }

    public GameBlock(Point3 position, Block block) {
        this.position = position;
        this.blockData = block.getBlockData().getAsString();
    }

    public static GameBlock of(int x, int y, int z, String blockData) {
        return new GameBlock(x, y, z, blockData);
    }

    public static GameBlock of(Point3 position, String blockData) {
        return new GameBlock(position, blockData);
    }

    public BlockData createBlockData(){
        return Bukkit.createBlockData(blockData);
    }

    public void setBlock(Chunk chunk){
        chunk.getBlock(position.getX(), position.getY(), position.getZ()).setBlockData(createBlockData());
    }

    public boolean sameCoordinates(GameBlock gameBlock){
        return gameBlock.getPosition().sameAs(position);
    }

    public boolean sameCoordinates(Point3 position){
        return this.position.sameAs(position);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameBlock gameBlock)) return false;
        return Objects.equals(getPosition(), gameBlock.getPosition()) && Objects.equals(getBlockData(), gameBlock.getBlockData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPosition(), getBlockData());
    }

    public String toString(){
        return "GameBlock{" +
                "position=" + position +
                ", blockData='" + blockData + '\'' +
                '}';
    }
}
