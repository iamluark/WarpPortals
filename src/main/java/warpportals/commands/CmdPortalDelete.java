package warpportals.commands;

import cn.nukkit.command.CommandSender;
import warpportals.nukkit.CommandHandler;
import warpportals.objects.PortalInfo;

public class CmdPortalDelete extends CommandHandlerObject {

    private static final String[] ALIASES = { "wp-portal-delete", "wppd", "pdelete" };
    private static final String PERMISSION = "warpportals.admin.portal.delete.command";
    private static final boolean REQUIRES_PLAYER = false;

    @Override
    public String getPermission() {
        return PERMISSION;
    }

    @Override
    public String[] getAliases() {
        return ALIASES;
    }

    @Override
    public boolean doesRequirePlayer() {
        return REQUIRES_PLAYER;
    }

    @Override
    boolean command(CommandSender sender, String[] args, CommandHandler main) {
        if (args.length == 1) {
            try {
                PortalInfo portal = main.iPortalManager.getPortalInfo(args[0]);
                if (portal != null) {
                    main.iPortalManager.deletePortal(args[0]);
                } else {
                    sender.sendMessage(" is not a valid Portal name.");
                }
            } catch (Exception e) {
                sender.sendMessage("Error saving Portal destination");
            }
        } else
            return false;
        return true;
    }
}
