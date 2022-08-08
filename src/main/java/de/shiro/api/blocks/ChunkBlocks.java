package de.shiro.api.blocks;

import lombok.Getter;
import org.bukkit.block.data.BlockData;

import java.util.*;

public class ChunkBlocks {

    @Getter
    private final List<GameBlock> blocks = new ArrayList<>();
    @Getter
    private ChunkPoint chunkPoint = new ChunkPoint();

    public ChunkBlocks(Set<GameBlock> blocks, ChunkPoint chunkPoint) {
        if(blocks != null) this.blocks.addAll(blocks);
        if(chunkPoint != null) this.chunkPoint = chunkPoint;
    }

    public ChunkBlocks(ChunkPoint chunkPoint) {
        this(null, chunkPoint);
    }


    public synchronized void addBlock(GameBlock block) {
        blocks.add(block);
    }

    public HashMap<String, Integer> getBlockSize() {
        HashMap<String, Integer> blockSize = new HashMap<>();
        for (GameBlock block : blocks) {
            if(blockSize.containsKey(block.getBlockData())) {
                blockSize.put(block.getBlockData(), blockSize.get(block.getBlockData()) + 1);
            } else {
                blockSize.put(block.getBlockData(), 1);
            }
        }
        return blockSize;
    }

    public int getBlockSize(GameBlock block) {
        return (int) blocks.stream().filter(b -> b.equals(block)).count();
    }

    public int getBlockSize(BlockData blockData) {
        return (int) blocks.stream().filter(b -> b.getBlockData().equalsIgnoreCase(blockData.getAsString())).count();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChunkBlocks that)) return false;
        return Objects.equals(getBlocks(), that.getBlocks()) && Objects.equals(getChunkPoint(), that.getChunkPoint());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBlocks(), getChunkPoint());
    }

    public String toString() {
        return "ChunkBlocks{" +
                "blocks=" + blocks +
                ", chunkPoint=" + chunkPoint +
                '}';
    }








}
