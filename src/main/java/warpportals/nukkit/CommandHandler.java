package warpportals.nukkit;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.Config;
import warpportals.commands.*;
import warpportals.manager.PortalManager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    public PortalPlugin iPortalPlugin;
    public PortalManager iPortalManager;

    Map<String, CommandHandlerObject> iCmdHandlerMap;

    public CommandHandler (PortalPlugin mainPlugin, PortalManager portalManager, Config config) {
        iPortalPlugin = mainPlugin;
        iPortalManager = portalManager;

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
