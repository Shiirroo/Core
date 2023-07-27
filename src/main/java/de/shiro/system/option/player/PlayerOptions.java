package de.shiro.system.option.player;

import de.shiro.system.option.Option;
import de.shiro.system.option.Options;
import de.shiro.system.option.StringOption;

public class PlayerOptions extends Options<PlayerOption> {

    public final Option<PlayerOption, String> Nichname = register(new StringOption<>(PlayerOption.Nickname, null));



}
