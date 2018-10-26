package warpportals.commands;

import cn.nukkit.command.CommandSender;
import warpportals.nukkit.CommandHandler;

public class CmdBackup extends CommandHandlerObject {

    private static final String[] ALIASES = { "wp-backup", "wpb", "pbackup" };
    private static final String PERMISSION = "warpportals.admin.op.backup";
    private static final boolean REQUIRES_PLAYER = false;

    public String getPermission() {
        return PERMISSION;
    }

    public String[] getAliases() {
        return ALIASES;
    }

    public boolean doesRequirePlayer() {
        return REQUIRES_PLAYER;
    }

    boolean command(CommandSender sender, String[] args, CommandHandler main) {
        try {
            if(main.iPortalManager.backupDataFile())
                sender.sendMessage("WarpPortal data backed up to WarpPortals plugin folder");
            else
                sender.sendMessage("Error backing up WarpPortal data to plugin folder");
        } catch (Exception e) {
            sender.sendMessage("Error backing up WarpPortal data");
        }
        return true;
    }
}
