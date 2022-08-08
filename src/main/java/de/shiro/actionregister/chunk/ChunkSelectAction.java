package de.shiro.actionregister.chunk;

import de.shiro.Core;
import de.shiro.system.action.manager.ActionResult;
import de.shiro.system.action.manager.builder.AbstractAction;
import de.shiro.api.blocks.Area;
import de.shiro.api.blocks.ChunkPoint;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ChunkSelectAction extends AbstractAction<Void, ChunkSelectConfig> {

    public ChunkSelectAction(ChunkSelectConfig config) {
        super(config);
    }


    @Override
    public ActionResult<Void> execute() throws ExecutionException, InterruptedException {
        Area area = getConfig().getArea();
        getConfig().setChunkSelected();
        spawnSelection(area, getConfig().getISession().getSessionWorld());


        return ActionResult.SuccessVoid();
    }

    public void spawnSelection(Area area, World world) {
        area.getChunkPoints().forEach(chunkPoint -> {
            spawnSlime(world, chunkPoint);
        });



        Bukkit.getScheduler().runTaskTimer(Core.getInstance(), () -> {
            List<Entity> l = getConfig().getEntities().stream().filter(Entity::isValid).toList();
        }, 20,20);
    }

    public void spawnSlime(World world, ChunkPoint chunkPoint) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Chunk chunk =chunkPoint.getChunk(world);
                chunk.load();
                Entity entity = world.spawnEntity(chunkPoint.getMiddleLocation(world,130), EntityType.SLIME);
                Slime slime = (Slime) entity;
                if(chunkPoint.isBorder()) {
                    slime.setSize(5);
                } else {
                    slime.setSize(2);
                }

                slime.setAI(false);
                slime.setCustomName("-");
                getConfig().addEntity(entity);
            }
        }.runTask(Core.getInstance());
    }
}
