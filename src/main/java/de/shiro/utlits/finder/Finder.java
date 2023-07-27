package de.shiro.utlits.finder;

import de.shiro.Record;
import de.shiro.record.RecordTyp;
import de.shiro.system.config.ISession;
import de.shiro.utlits.log.LogState;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;

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
    public static AbstractSuffixSearch<LogState> logStateFinder = new TypFinderEnum<>(LogState.class);
    public static AbstractSuffixSearch<RecordTyp> recordTypeFinder = new TypFinderEnum<>(RecordTyp.class);



}
