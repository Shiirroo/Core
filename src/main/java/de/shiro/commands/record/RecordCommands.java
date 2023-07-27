package de.shiro.commands.record;

import de.shiro.actions.recods.action.RecordGetPosAction;
import de.shiro.actions.recods.action.RecordGetTypeAction;
import de.shiro.actions.recods.config.*;
import de.shiro.api.blocks.Point3;
import de.shiro.commands.commandbuilder.CommandArguments;
import de.shiro.commands.commandbuilder.ckey.CKey;
import de.shiro.record.RecordTyp;
import de.shiro.system.config.ISession;
import de.shiro.utlits.Utlits;
import de.shiro.utlits.log.LogState;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public class RecordCommands implements RecordCommandsInternal {

    private static final RecordFacade facade = new RecordFacade();


    @Override
    public void lookup(ISession iSession, CommandSender sender, CommandArguments args) {
        String value = args.getIfExists(CKey.TimeTo, "15m");
        String value2 = args.getIfExists(CKey.TimeFrom, "0s");
        Integer range = args.getIfExists(CKey.RANGE, 15, 1,50);
        Integer page = args.getIfExists(CKey.Amount, 1, 1, 10000);
        long time = Utlits.parseDuration(value);
        long time2 = Utlits.parseDuration(value2);
        if(time <= 0 || time > 2419200000L) time = 15000;
        if(time2 < 0 || time2 > time) time2 = 0;
        Location location = iSession.getSessionLocation();
        if(location == null)  {
            iSession.sendSessionMessage("Location cant be null");
            return;
        }
        if(location.getWorld() == null)  {
            iSession.sendSessionMessage("World cant be null");

            return;
        }
        RecordGetPosActionConfig config = new RecordGetPosActionConfig(iSession,
                location.getWorld().getName(), new Point3(location),
                System.currentTimeMillis() - time, System.currentTimeMillis() - time2, range, page);
        RecordShowActionConfig showAction = new RecordShowActionConfig(iSession, config, RecordGetPosAction.class);
        facade.lookup(showAction);
    }

    @Override
    public void type(ISession iSession, CommandSender sender, CommandArguments args) {
        RecordTyp recordTyp =args.getIfExists(CKey.RECORDTYP, RecordTyp.PLAYER_JOIN, RecordTyp.values());
        String value = args.getIfExists(CKey.TimeTo, "15m");
        String value2 = args.getIfExists(CKey.TimeFrom, "0s");
        Integer range = args.getIfExists(CKey.RANGE, 15, 1,50);
        Integer page = args.getIfExists(CKey.Amount, 1, 1, 10000);
        long time = Utlits.parseDuration(value);
        long time2 = Utlits.parseDuration(value2);
        if(time <= 0 || time > 2419200000L) time = 15000;
        if(time2 < 0 || time2 > time) time2 = 0;
        Location location = iSession.getSessionLocation();

        if(location == null)  {
            iSession.sendSessionMessage("Location cant be null");
            return;
        }
        if(location.getWorld() == null)  {
            iSession.sendSessionMessage("World cant be null");

            return;
        }



        RecordGetTypePosActionConfig config = new RecordGetTypePosActionConfig(iSession, System.currentTimeMillis() - time, System.currentTimeMillis() - time2, page, recordTyp);
        RecordShowActionConfig showAction = new RecordShowActionConfig(iSession, config, RecordGetTypeAction.class);
        facade.type(showAction);
    }

    @Override
    public void config(ISession iSession, CommandSender sender, CommandArguments args) {
        LogState state = args.getIfExists(CKey.LOGCONFIG, LogState.values());
        if(state == null) {
            iSession.sendSessionMessage("state cant be null");
            return;
        }
        var config = new RecordChangeConfigActionConfig(iSession, state);
        facade.config(config);

    }
}
