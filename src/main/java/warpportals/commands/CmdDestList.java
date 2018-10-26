package warpportals.commands;

import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import warpportals.nukkit.CommandHandler;
import warpportals.objects.CoordsPY;

public class CmdDestList extends CommandHandlerObject {

    private static final String[] ALIASES = { "wp-destination-list", "wp-dest-list", "wp-dests", "wpdl", "pdestlist" };
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
        StringBuilder sbdest = new StringBuilder();
        sbdest.append("Destionations:");
        for (String destName : main.iPortalManager.getDestinations()) {
            CoordsPY destCoords = main.iPortalManager.getDestCoords(destName);
            try {
                sbdest.append(TextFormat.WHITE + "\n - " + TextFormat.AQUA + destName + TextFormat.WHITE + " in " + TextFormat.YELLOW + destCoords.getWorldName());
            } catch (Exception e) {
                // Catch when destCoords == null
            }
        }
        sender.sendMessage(sbdest.toString());
        return true;
    }

}
