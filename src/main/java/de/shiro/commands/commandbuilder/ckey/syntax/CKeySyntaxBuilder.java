package de.shiro.commands.commandbuilder.ckey.syntax;

import de.shiro.commands.commandbuilder.CommandArguments;
import de.shiro.system.config.ISession;

import java.util.List;

public interface CKeySyntaxBuilder {

    List<String> defaultSyntax = List.of();

    List<String> perform(ISession iSession, CommandArguments arguments);




}
