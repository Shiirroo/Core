package de.shiro.utlits.finder;

import de.shiro.actionregister.pos.PosManager;
import de.shiro.api.blocks.WorldPos;
import de.shiro.system.config.ISession;
import de.shiro.utlits.Config;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;

import java.util.Comparator;
import java.util.stream.Collectors;

public class Finder {

    public static AbstractSuffixSearch<Material> materialFinder = new TypFinderEnum<>(Material.class);

    public static AbstractSuffixSearch<WorldType> worldTypeFinder = new TypFinderEnum<>(WorldType.class);

    public static AbstractSuffixSearch<GameMode> gamemodeFinder = new TypFinderEnum<>(GameMode.class);

    public static AbstractSuffixSearch<EntityType> entityFinder = new TypFinderEnum<>(EntityType.class);

    public static AbstractSuffixSearch<TreeType> treeFinder = new TypFinderEnum<>(TreeType.class);

    public static AbstractSuffixSearch<ItemFlag> itemFlagFinder = new TypFinderEnum<>(ItemFlag.class);

    public static AbstractSuffixSearch<Biome> biomeFinder = new TypFinderEnum<>(Biome.class);

    public static AbstractSuffixSearch<Difficulty> difficultyFinder = new TypFinderEnum<>(Difficulty.class);

    public static AbstractSuffixSearch<World.Environment> environmentFinder = new TypFinderEnum<>(World.Environment.class);

    public static AbstractSuffixSearch<String> posList(ISession iSession){
       return new TypFinderString<>(new PosManager(iSession).getWorldPosList().stream().map(WorldPos::getPosName).toList());
    };

    public static AbstractSuffixSearch<String> manageList(ISession iSession){
        return new TypFinderString<>(new PosManager(iSession).hasManagePerm());
    };


}
