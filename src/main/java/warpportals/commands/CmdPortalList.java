package warpportals.commands;

import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import warpportals.nukkit.CommandHandler;
import warpportals.objects.PortalInfo;

public class CmdPortalList extends CommandHandlerObject {

    private static final String[] ALIASES = { "wp-portal-list", "wppl", "plist" };
    private static final String PERMISSION = "warpportals.admin.destination.list";
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
        StringBuilder sblist = new StringBuilder();
        sblist.append("Portals:");
        for (String portalName : main.iPortalManager.getPortalNames()) {
            // Retrieve Portal Info
            PortalInfo portalInfo = main.iPortalManager.getPortalInfo(portalName);
            // Retrieve linked Teleport Destination
            String destText = main.iPortalManager.getDestinationName(portalInfo.tpCoords);
            /*
             * If the teleport destination does not have a name, show the
             * coordinates
             */
            if (destText == null)
                destText = portalInfo.tpCoords.toNiceString();
            try {
                sblist.append(TextFormat.WHITE + "\n - " + TextFormat.RED + portalName + TextFormat.YELLOW + " ("
                        + portalInfo.blockCoordArray.get(0).level.getName() + ") " + TextFormat.WHITE + "-> " + TextFormat.AQUA + destText);
            } catch (Exception e) {
                // Catches exceptions when blockCoordArray is 0 in length
            }
        }
        sender.sendMessage(sblist.toString());
        return true;
    }
}
