package de.shiro.commands.pos;

import de.shiro.actionregister.pos.config.PosWithPointConfig;
import de.shiro.api.blocks.Point3D;
import de.shiro.api.types.Visibility;
import de.shiro.commands.commandbuilder.ckey.CKey;
import de.shiro.commands.commandbuilder.CommandArguments;
import de.shiro.system.action.manager.facede.Facede;
import de.shiro.actionregister.pos.config.AbstractPosActionConfig;
import de.shiro.system.config.ISession;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PosCommands implements PosCommandsInternal {

    @Facede @Getter
    private static final PosFacade facade = new PosFacade();


    @Override
    public void save(ISession iSession, CommandSender sender, CommandArguments args) {
        Player player = (Player) sender;
        String name = args.getIfExists(iSession, CKey.PosManage);
        Visibility visibility = args.getIfExists(iSession, CKey.Visibility, Visibility.PUBLIC);
        if(name == null) {
            sender.sendMessage("Kein Name angegeben");
            return;
        }

        PosWithPointConfig config = new PosWithPointConfig(iSession, player.getWorld().getName(), new Point3D(player.getLocation()), name, visibility);
        facade.save(config);
    }

    @Override
    public void list(ISession iSession, CommandSender sender, CommandArguments args) {
        Integer page = args.getIfExists(iSession, CKey.Page, 1);
        String name = args.getIfExists(iSession, CKey.PosList);
        if(page == 0) page = 1;
        AbstractPosActionConfig config = new AbstractPosActionConfig(iSession,name,page);
        facade.list(config);
    }

    @Override
    public void follow(ISession iSession, CommandSender sender, CommandArguments args) {
        String name = args.getIfExists(iSession, CKey.PosManage);
        if (name == null) {
            sender.sendMessage("Kein Name angegeben");
            return;
        }
        AbstractPosActionConfig config = new AbstractPosActionConfig(iSession,  name);
        facade.follow(config);
    }

    @Override
    public void delete(ISession iSession, CommandSender sender, CommandArguments args) {
        String name = args.getIfExists(iSession, CKey.PosManage);
        if (name == null) {
            sender.sendMessage("Kein Name angegeben");
            return;
        }
        AbstractPosActionConfig config = new AbstractPosActionConfig(iSession,name,((Player) sender).getWorld().getName());
        facade.delete(config);
    }


}
