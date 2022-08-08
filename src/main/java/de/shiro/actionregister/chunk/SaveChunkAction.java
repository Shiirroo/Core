package de.shiro.actionregister.chunk;

import de.shiro.system.action.manager.ActionResult;
import de.shiro.system.action.manager.builder.AbstractRunnableAction;
import de.shiro.api.blocks.*;
import de.shiro.utlits.Config;
import de.shiro.utlits.Log;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SaveChunkAction extends AbstractRunnableAction<SaveChunkCallBack, SaveChunkConfig> {

    public SaveChunkAction(SaveChunkConfig config) {
        super(config);
    }


    @Override
    public ActionResult<SaveChunkCallBack> execute() throws ExecutionException, InterruptedException {
        Area area = getConfig().getArea();
        Player player = Bukkit.getPlayer(getConfig().getISession().getExecutorID());
        if (area == null || player == null) return ActionResult.Failed(getConfig().getChunkBlockCallback());

        List<ChunkBlocks> chunkBlocks = getChunkBlocks(area, player.getWorld());
        if(chunkBlocks.isEmpty()) return ActionResult.Failed(getConfig().getChunkBlockCallback());
        getConfig().getChunkBlockCallback().addChunkBlocks(chunkBlocks);
        Log.send(getConfig().getChunkBlockCallback().getBlocksCount());
        return ActionResult.Success(getConfig().getChunkBlockCallback());
    }




    private List<ChunkBlocks> getChunkBlocks(Area area, World world) throws ExecutionException, InterruptedException {
        List<ChunkBlocks> chunkBlocks;

        chunkBlocks = Config.getService().submit(() -> area.getChunkPoints().stream()
                            .map(point -> getChunkBlocks(point, world))
                            .collect(Collectors.toList())).get();
        return chunkBlocks;
    }

    private ChunkBlocks getChunkBlocks (ChunkPoint chunkPoint, World world) {
        ChunkBlocks chunkBlocks = new ChunkBlocks(chunkPoint);
        IntStream.range(chunkPoint.getMin().getX(), chunkPoint.getMax().getX()).forEach(x ->
                IntStream.range(chunkPoint.getMin().getZ(), chunkPoint.getMax().getZ()).forEach(z ->
                    IntStream.range(world.getMinHeight(), world.getHighestBlockYAt(x, z)).forEach(y -> {
                        chunkBlocks.addBlock(new GameBlock(x, y, z, world.getBlockAt(x, y, z).getBlockData().getAsString()));
                        getConfig().getChunkBlockCallback().addBlock();
                    })
                )
        );
        return chunkBlocks;
    }


}
