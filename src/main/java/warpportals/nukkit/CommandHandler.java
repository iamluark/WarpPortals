package warpportals.nukkit;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import warpportals.commands.*;
import warpportals.helpers.Defaults;
import warpportals.manager.PortalManager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    public PortalPlugin iPortalPlugin;
    public PortalManager iPortalManager;
    public Config iPortalConfig;
    public TextFormat iCC;

    Map<String, CommandHandlerObject> iCmdHandlerMap;

    public CommandHandler (PortalPlugin mainPlugin, PortalManager portalManager, Config portalConfig) {
        iPortalPlugin = mainPlugin;
        iPortalManager = portalManager;
        iPortalConfig = portalConfig;

        iCC = TextFormat.getByChar(iPortalConfig.getString("portals.general.textColor", Defaults.CHAT_COLOR));

        iCmdHandlerMap = new HashMap<String, CommandHandlerObject>();
        (new CmdBackup()).populate(iCmdHandlerMap);
        (new CmdDestCreate()).populate(iCmdHandlerMap);
        (new CmdDestDelete()).populate(iCmdHandlerMap);
        (new CmdDestList()).populate(iCmdHandlerMap);
        (new CmdDestTeleport()).populate(iCmdHandlerMap);
        (new CmdHelp()).populate(iCmdHandlerMap);
        (new CmdLoad()).populate(iCmdHandlerMap);
        (new CmdPortalCreate()).populate(iCmdHandlerMap);
        (new CmdPortalDelete()).populate(iCmdHandlerMap);
        (new CmdPortalDelTool()).populate(iCmdHandlerMap);
        (new CmdPortalIDTool()).populate(iCmdHandlerMap);
        (new CmdPortalList()).populate(iCmdHandlerMap);
        (new CmdPortalMaterial()).populate(iCmdHandlerMap);
        (new CmdPortalTeleport()).populate(iCmdHandlerMap);
        (new CmdSave()).populate(iCmdHandlerMap);
        (new CmdVersion()).populate(iCmdHandlerMap);
    }

    public boolean handleCommand(CommandSender sender, Command command, String label, String[] args) {

        CommandHandlerObject cmdHandler = iCmdHandlerMap.get(command.getName().toLowerCase());

        if(cmdHandler != null)
            return cmdHandler.handle(sender, args, this);

        return false;
    }
}
