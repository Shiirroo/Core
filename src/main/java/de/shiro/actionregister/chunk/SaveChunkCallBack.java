package de.shiro.actionregister.chunk;

import de.shiro.api.blocks.ChunkBlocks;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class SaveChunkCallBack {

    @Getter
    private final List<ChunkBlocks> chunkBlocks = new ArrayList<>();
    @Getter
    private int blocksCount = 0;

    public void addChunkBlocks(ChunkBlocks chunkBlocks) {
        this.chunkBlocks.add(chunkBlocks);
    }

    public void addChunkBlocks(List<ChunkBlocks> chunkBlocks) {
        this.chunkBlocks.addAll(chunkBlocks);
    }

    public synchronized void addBlock() {
        this.blocksCount++;
    }



}
