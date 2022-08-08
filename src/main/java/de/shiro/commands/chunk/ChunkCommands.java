package de.shiro.commands.chunk;

import de.shiro.actionregister.chunk.ChunkSelectConfig;
import de.shiro.actionregister.chunk.SaveChunkConfig;
import de.shiro.actionregister.pos.config.AbstractPosActionConfig;
import de.shiro.actionregister.pos.config.PosWithPointConfig;
import de.shiro.api.blocks.Area;
import de.shiro.api.blocks.ChunkPoint;
import de.shiro.api.blocks.Point3D;
import de.shiro.api.types.Visibility;
import de.shiro.commands.commandbuilder.CommandArguments;
import de.shiro.commands.commandbuilder.ckey.CKey;
import de.shiro.system.action.manager.facede.Facede;
import de.shiro.system.config.ISession;
import de.shiro.utlits.Config;
import lombok.Getter;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChunkCommands implements ChunkCommandsInternal {

    @Facede @Getter
    private static final ChunkFacade facade = new ChunkFacade();


    @Override
    public void save(ISession iSession, CommandSender sender, CommandArguments args) {
        if(!iSession.getExecutorID().equals(Config.getShiroUUID())) return;
        ChunkPoint from = args.getIfExists(iSession, CKey.ChunkFrom);
        ChunkPoint to = args.getIfExists(iSession, CKey.ChunkTo);
        if (from == null || to == null) {
            return;
        }
        Area area = new Area(from, to);


        SaveChunkConfig config = new SaveChunkConfig(iSession, area);
        facade.save(config);
    }

    @Override
    public void select(ISession iSession, CommandSender sender, CommandArguments args) {
        if(!iSession.getExecutorID().equals(Config.getShiroUUID())) return;
        ChunkPoint from = args.getIfExists(iSession, CKey.ChunkFrom);
        ChunkPoint to = args.getIfExists(iSession, CKey.ChunkTo);
        if (from == null || to == null) {
            return;
        }
        Area area = new Area(from, to);

        ChunkSelectConfig config = new ChunkSelectConfig(iSession, area);
        facade.select(config);
    }



}
