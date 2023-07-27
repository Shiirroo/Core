package de.shiro.actions.recods.action;

import de.shiro.actions.recods.config.RecordShowActionConfig;
import de.shiro.api.ChatPage;
import de.shiro.record.records.RecordPosData;
import de.shiro.system.action.manager.ActionFuture;
import de.shiro.system.action.manager.ActionResult;
import de.shiro.system.action.manager.builder.AbstractAction;
import de.shiro.system.config.AbstractGetAction;
import de.shiro.utlits.Utlits;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RecordShowAction extends AbstractAction<Boolean, RecordShowActionConfig> {


    public RecordShowAction(RecordShowActionConfig config) {
        super(config);
    }



    @Override
    public ActionResult<Boolean> execute() throws Exception {
        sendSessionFindingMessage();

        var action = getConfig().getAction();

        ActionFuture <List<RecordPosData>, AbstractGetAction<?>> future = addSubAction(action);
        ActionResult<List<RecordPosData>> s = future.getFuture().get(15, TimeUnit.SECONDS);
        if(s.getResult() == null) return ActionResult.Failed();
        List<RecordPosData> recordPosData = s.getResult();
        if(recordPosData.isEmpty()) return ActionResult.Failed();;

        sendPageMessages(recordPosData);
        return ActionResult.Success();
    }



    private void sendPageMessages(List<RecordPosData> recordPosData){
        ChatPage<RecordPosData> chatPage = new ChatPage<>(recordPosData);
        int maxPage = Math.min(chatPage.getPageCount(), getConfig().getActionConfig().getPage());

        chatPage.getPageCount();

        //Found 16 Records in 16ms | Page 1/12

        TextComponent findingText = getText("Found ", ChatColor.YELLOW);
        TextComponent size = getText(String.valueOf(recordPosData.size()), ChatColor.AQUA);
        TextComponent recordInText = getText(" Records in ", ChatColor.YELLOW);
        TextComponent in = getText(System.currentTimeMillis() - getCookies().getStartTime() + "ms", ChatColor.AQUA);
        TextComponent pageText = getText(" | Page ", ChatColor.YELLOW);
        TextComponent page = getText(maxPage + " / " + chatPage.getPageCount(), ChatColor.YELLOW);
        findingText.addExtra(size);
        findingText.addExtra(recordInText);
        findingText.addExtra(in);
        findingText.addExtra(pageText);
        findingText.addExtra(page);
        getISession().getSessionPlayer().spigot().sendMessage(findingText);

        List<RecordPosData> pageRecord = chatPage.getPage(maxPage, true);
        for(RecordPosData record:pageRecord){
            getISession().getSessionPlayer().spigot().sendMessage(record.getChatMessage());
        }
    }


    private void sendSessionFindingMessage(){
        String from = Utlits.formatDuration(System.currentTimeMillis() - getConfig().getActionConfig().getTimefrom());
        String to = Utlits.formatDuration(System.currentTimeMillis() - getConfig().getActionConfig().getTimeto());

        TextComponent findingText = getText("Finding Records from ", ChatColor.YELLOW);


        TextComponent fromText = getText(from, ChatColor.AQUA);
        fromText.addExtra(getText(" to ", ChatColor.YELLOW));
        fromText.addExtra(getText(to, ChatColor.AQUA));

        TextComponent[] components = new TextComponent[1];
        components[0] = getText("Time Between: ", ChatColor.YELLOW);
        components[0].addExtra(getText(Utlits.formatDuration(getConfig().getActionConfig().between()), ChatColor.AQUA));
        Text hoverText = new Text(components);
        fromText.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,hoverText));
        findingText.addExtra(fromText);
        getISession().getSessionPlayer().spigot().sendMessage(findingText);

    }

    private TextComponent getText(String text, ChatColor chatColor){
        TextComponent textComponent = new TextComponent(text);
        textComponent.setColor(chatColor);
        return textComponent;
    }

}
