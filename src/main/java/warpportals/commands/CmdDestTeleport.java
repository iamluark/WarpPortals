package warpportals.commands;

import cn.nukkit.Player;
import cn.nukkit.level.Location;
import warpportals.helpers.Regex;
import warpportals.helpers.Utils;
import warpportals.nukkit.CommandHandler;
import warpportals.objects.CoordsPY;

public class CmdDestTeleport extends CommandHandlerObject {

    private static final String[] ALIASES = { "wp-destination-teleport", "wp-destination-tp", "wp-dest-teleport", "wp-dest-tp", "wpdtp" };
    private static final String PERMISSION = "warpportals.admin.destination.teleport";
    private static final boolean REQUIRES_PLAYER = true;

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
    boolean command(Player player, String[] args, CommandHandler main) {
        if (args.length == 1) {
            if (args[0].matches(Regex.PORTAL_DEST_NAME)) {
                try {
                    CoordsPY tpCoords = main.iPortalManager.getDestCoords(args[0]);
                    if (tpCoords == null) {
                        player.sendMessage("\"" + args[0] + "\" is not a valid Destination.");
                    } else {
                        Location loc = player.getLocation();
                        Utils.coordsToLoc(tpCoords, loc);
                        player.teleport(loc);
                        player.sendMessage("Teleported to \"" + args[0] + "\" @ " + tpCoords.toNiceString());
                    }
                } catch (Exception e) {
                    player.sendMessage("Error teleporting to WarpPortal Location \"" + args[0] + "\"");
                }
            } else
                player.sendMessage("Command argument must be a valid alpha-numeric WarpPortal Location name.");
        } else
            return false;
        return true;
    }
}
