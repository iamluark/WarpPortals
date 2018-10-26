package warpportals.commands;

import cn.nukkit.Player;
import cn.nukkit.level.Location;
import warpportals.helpers.Regex;
import warpportals.helpers.Utils;
import warpportals.nukkit.CommandHandler;
import warpportals.objects.Coords;
import warpportals.objects.CoordsPY;
import warpportals.objects.PortalInfo;

public class CmdPortalTeleport extends CommandHandlerObject {

    private static final String[] ALIASES = { "wp-portal-teleport", "wp-portal-tp", "wpptp" };
    private static final String PERMISSION = "warpportals.admin.portal.teleport";
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
                    CoordsPY tpCoords = null;
                    PortalInfo portal = main.iPortalManager.getPortalInfo(args[0]);
                    if (portal != null) {
                        Coords midCrds = portal.blockCoordArray.get(portal.blockCoordArray.size() > 1 ? portal.blockCoordArray.size() / 2 : 0);
                        tpCoords = new CoordsPY(new Coords(midCrds.level, midCrds.x, midCrds.y, midCrds.z));
                        tpCoords.z += 10;
                        for (Coords crd : portal.blockCoordArray) {
                            if (tpCoords.equals(crd)) {
                                tpCoords.z += 100;
                                tpCoords.y += 10;
                            }
                        }
                    } else
                        player.sendMessage("\"" + args[0] + "\" is not a valid Portal.");
                    if (tpCoords != null) {
                        Location loc = player.getLocation();
                        Utils.coordsToLoc(tpCoords, loc);
                        player.teleport(loc);
                        player.sendMessage("Teleported to \"" + args[0] + "\" @ " + tpCoords.toNiceString());
                    } else
                        throw new Exception();
                } catch (Exception e) {
                    player.sendMessage("Error teleporting to WarpPortal \"" + args[0] + "\"");
                }
            } else
                player.sendMessage("Command argument must be a valid alpha-numeric WarpPortal name.");
        } else
            return false;
        return true;
    }
}
