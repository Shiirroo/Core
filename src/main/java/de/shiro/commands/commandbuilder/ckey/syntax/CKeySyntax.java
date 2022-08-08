package de.shiro.commands.commandbuilder.ckey.syntax;

import de.shiro.api.ChatPage;
import de.shiro.api.blocks.Point3;
import de.shiro.commands.commandbuilder.CommandArguments;
import de.shiro.commands.commandbuilder.ckey.CKey;
import de.shiro.system.action.manager.ActionFuture;
import de.shiro.system.config.ISession;
import de.shiro.utlits.finder.AbstractSuffixSearch;
import de.shiro.utlits.finder.Finder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.generator.WorldInfo;

import java.util.*;
import java.util.stream.Collectors;

public enum CKeySyntax {

    CUSTOM(getCustom(), false),
    CHUNKCOORDINATES(getChunkCoordinates(),false),
    WORLDS(getWorlds(), false),
    MATERIAL( getMaterial(), false),
    WORLDTYPE( getWorldType(), false),
    MANAGEPOS( getManagePos(), false),
    POSLIST( getPosList(), true),
    VISIBILITY(getVisibility(),false),
    POINT3(getPoint3(),false),
    PAGE(getPage(),false),
    ACTIONS(getActions(),true);



    @Getter
    private final CKeySyntaxBuilder syntax;

    @Getter
    private final Boolean pageable;




    CKeySyntax(CKeySyntaxBuilder syntax, Boolean pageable) {
        this.syntax = syntax;
        this.pageable = pageable;
    }


    public static CKeySyntaxBuilder getCustom(){
        return (iSession, arguments) -> new ArrayList<>();
    }

    public static CKeySyntaxBuilder getChunkCoordinates(){
        return (iSession,arguments) -> List.of("10,10","20,20","100,100","25,25","-5,-5","-10,-10");
    }
    public static CKeySyntaxBuilder getWorlds(){
        return (iSession, arguments) -> Bukkit.getWorlds().stream().map(WorldInfo::getName).collect(Collectors.toList());
    }

    public static CKeySyntaxBuilder getMaterial(){
        return (iSession, arguments) -> findList(Finder.materialFinder, arguments.getTypingCommand());
    }

    public static CKeySyntaxBuilder getWorldType(){
        return (iSession, arguments) -> findList(Finder.worldTypeFinder, arguments.getTypingCommand());
    }

    public static CKeySyntaxBuilder getManagePos(){
        return (iSession, arguments) ->  findList(Finder.manageList(iSession), arguments.getTypingCommand());
    }

    public static CKeySyntaxBuilder getPosList(){
        return (iSession, arguments) ->  findList(Finder.posList(iSession), arguments.getTypingCommand());
    }

    public static CKeySyntaxBuilder getVisibility(){
        return (iSession, arguments) -> formatEnum(de.shiro.api.types.Visibility.values());
    }
    public static CKeySyntaxBuilder getPoint3(){
        return (iSession, arguments) -> List.of(new Point3(iSession.getSessionLocation()).toChatString());
    }

    public static CKeySyntaxBuilder getActions(){
        return (iSession, arguments) -> iSession.getPlayerAction().getActionFutureQueue().stream().map(ActionFuture::getAction).map(a -> a.getClass().getSimpleName()).collect(Collectors.toList());
    }


    public static CKeySyntaxBuilder getPage(){
        return CKeySyntax::pageList;
    }


    public static List<String> pageList(ISession iSession, CommandArguments commandArguments){
        List<String> pages = new ArrayList<>();
        CKey key = commandArguments.getPageKeyIndicator(CKey.Page);
        if(key == null) return pages;
        String pos = commandArguments.getIfExists(iSession, key);
        List<String> list = key.getCKeySyntax().getSyntax().perform(iSession, commandArguments.removeLast());
        if(pos == null) return pages;
        ChatPage<String> chatPage = new ChatPage<>(list);
        for(int i = 0; i < chatPage.getPageCount(); i++){
            pages.add(String.valueOf(i + 1));
        }
        return pages;
    }

    public static <T> List<String> findList(AbstractSuffixSearch<T> finder, String command){
        if(command.isEmpty()) return finder.getSuffixes();
        if(command.equalsIgnoreCase("*")) return finder.getSuffixes();
        else return finder.getSuffixContains(command);
    }



    private static List<String> formatEnum(Enum<?>[] enumTypes){
        return Arrays.stream(enumTypes).map(CKeySyntax::formatString).collect(Collectors.toList());
    }

    private static String formatString( Enum<?> enumType){
        return enumType.name().charAt(0) + enumType.name().substring(1).toLowerCase();
    }

}
