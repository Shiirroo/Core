package de.shiro.actionregister.pos.action;

import de.shiro.Core;
import de.shiro.actionregister.pos.PosManager;
import de.shiro.api.ChatPage;
import de.shiro.api.blocks.WorldPos;
import de.shiro.api.types.Visibility;
import de.shiro.api.types.WorldTypColor;
import de.shiro.system.action.manager.ActionResult;
import de.shiro.system.action.manager.builder.AbstractAction;
import de.shiro.actionregister.pos.config.AbstractPosActionConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;

import java.util.*;

import static de.shiro.utlits.Utlits.*;

public class ListPosAction extends AbstractAction<Boolean, AbstractPosActionConfig> {

    public  ListPosAction(AbstractPosActionConfig config) {
        super(config);
    }

    @Override
    public ActionResult<Boolean> execute() throws Exception {
        PosManager posManager = getConfig().getPosManager();
        List<WorldPos> worldPosList;
        if(getConfig().getPosName().equalsIgnoreCase("") || getConfig().getPosName().equalsIgnoreCase(" ")) worldPosList = posManager.getWorldPosList();
        else worldPosList =  posManager.getWorldPosListByInput(getConfig().getPosName());
        if(sendPosMessage(worldPosList, getConfig().getPosName())) return ActionResult.Success();
        getConfig().getISession().sendSessionMessage(ChatColor.RED + "Pos "
                + ChatColor.GRAY + "[" + ChatColor.GOLD + getConfig().getPosName() +ChatColor.GRAY  + "] " + ChatColor.RED + "was not found");
        return ActionResult.Failed();
    }

    public Boolean sendPosMessage(List<WorldPos> worldPosList, String worldPosName){
        List<TextComponent> textComponentList = new ArrayList<>();

        worldPosList.forEach(worldPos -> textComponentList.add(getChatMessage(worldPos)));
        ChatPage<TextComponent> chatPage = new ChatPage<>(textComponentList);
        int showPage = getConfig().getPage();


        if(showPage > chatPage.getPageCount()) showPage = chatPage.getPageCount();



        List<TextComponent> page = chatPage.getPage(showPage);

        worldPosList.sort(Comparator.comparing(WorldPos::getPosName));
        getConfig().getISession().sendSessionMessage(Core.getPrefix() + "§7Liste der Positionen: " + (Objects.equals(worldPosName, "") ? Visibility.PUBLIC.getColor() + Visibility.PUBLIC.name(): ChatColor.GOLD + worldPosName) + " §7Page: " + showPage + "§7/" + chatPage.getPageCount());
        page.forEach(textComponent -> getConfig().getISession().sendSessionTextComponent(textComponent));
        return true;
    }


    public TextComponent getChatMessage(WorldPos worldPos){
        World world = Bukkit.getWorld(worldPos.getWorldName());
        if(world != null) {

            TextComponent setCompass = Component.text(ChatColor.GRAY + "[" + (getConfig().getISession().getSessionWorld().equals(world) ? ChatColor.GREEN + "➹" : ChatColor.RED + "➹") + ChatColor.GRAY + "]")
                    .hoverEvent(HoverEvent.showText(Component.text((getConfig().getISession().getSessionWorld().equals(world) ? ChatColor.GREEN + "Set Compass to Pos" : ChatColor.RED + "You are not in this world"))));
            if(getConfig().getISession().getSessionWorld().equals(world)){
                setCompass = setCompass.clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/pos follow "+ worldPos.getPosName()));
            }


            return Component.text("<> ")
                    .append(Component.text(worldPos.getVisibility().getVisibilityIcon())
                            .hoverEvent(HoverEvent.showText(Component.text(worldPos.getVisibility().getColor() + worldPos.getVisibility().toString()))))
                    .append(Component.text( WorldTypColor.getWorldTypColor(world.getEnvironment()).getWorldTypIcon())
                            .hoverEvent(HoverEvent.showText(Component.text(WorldTypColor.getWorldTypColor(world.getEnvironment()).getColor() + worldPos.getWorldName()))))
                    .append(setCompass)
                    .append(Component.text(" " + ChatColor.GOLD + worldPos.getPosName() + ": "
                            + " " + ChatColor.RED + numberToFormat(worldPos.getPoint3d().getX())
                            + " " + ChatColor.GREEN +  numberToFormat(worldPos.getPoint3d().getY())
                            + " " + ChatColor.AQUA +  numberToFormat(worldPos.getPoint3d().getZ())));
        }
        return null;
    }



}
