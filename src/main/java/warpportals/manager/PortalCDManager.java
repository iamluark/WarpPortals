package warpportals.manager;

import cn.nukkit.Nukkit;
import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Location;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.Config;
import warpportals.api.WarpPortalsCreateEvent;
import warpportals.helpers.BlockCrawler;
import warpportals.helpers.Regex;
import warpportals.objects.Coords;
import warpportals.objects.CoordsPY;
import warpportals.objects.PortalCreate;
import warpportals.objects.PortalInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class PortalCDManager {

    Logger mLogger;
    PortalDataManager iPDM;
    PortalToolManager iPTM;
    Config iConfig;

    public PortalCDManager(PortalDataManager pim, PortalToolManager ptm, Config portalConfig) {
        iPDM = pim;
        iPTM = ptm;
        iConfig = portalConfig;
    }

    void possibleDeletePortal(Block block, Player player) {
        if (block.getId() == BlockID.NETHER_PORTAL || block.getId() == BlockID.END_PORTAL) {
            deletePortal(block.getLocation());
            iPTM.removeTool(player.getUniqueId());
        } else
            player.sendMessage("Right click on the Portal that you want to delete");
    }

    public void deletePortal(String name) {
        try {
            Location loc = new Location(0, 0, 0, iPDM.getPortalInfo(name).tpCoords.level);
            changeMaterial(Block.get(BlockID.GOLD_BLOCK), iPDM.getPortalInfo(name).blockCoordArray, loc);
        } catch (Exception e) {
            // Error changing portal to gold block
        }
        iPDM.removePortal(name);
    }

    private void deletePortal(Location loc) {
        String delPortalName = "~|~";
        for (String portalName : iPDM.getPortalNames()) {
            for (Coords crd : iPDM.getPortalInfo(portalName).blockCoordArray) {
                if (loc.getX() == crd.x && loc.getY() == crd.y && loc.getZ() == crd.z) {
                    delPortalName = portalName;
                    break;
                }
            }
        }
        if (!delPortalName.matches("~|~"))
            deletePortal(delPortalName);
    }

    void possibleCreatePortal(Block block, Player player, PortalCreate portalCreate) {
        if (block.getId() == BlockID.GOLD_BLOCK || (block.getId() == BlockID.NETHER_PORTAL || block.getId() == BlockID.END_PORTAL)) {
            // Check to see if that Portal Name is already in use
            if (iPDM.getPortalInfo(portalCreate.portalName) == null) {
                // Check if Portal Name is valid
                if (portalCreate.portalName.matches(Regex.PORTAL_DEST_NAME)) {
                    /*
                     * Run recursion spider starting at the block the player
                     * clicked
                     */
                    int maxPortalSize = iConfig.getInt("portals.create.maxSize", BlockCrawler.DEFAULT_MAX_SIZE);
                    BlockCrawler blockSpider = new BlockCrawler(maxPortalSize);
                    try {
                        ArrayList<Coords> blockCoordArray = new ArrayList<Coords>();
                        blockSpider.start(block, blockCoordArray);
                        // Test to see if blocks are already in a portal
                        Set<String> existingPortalOverlap = new HashSet<String>();
                        for (Coords coords : blockCoordArray) {
                            String overlapPortalName = iPDM.getPortalName(coords);
                            if (overlapPortalName != null)
                                existingPortalOverlap.add(overlapPortalName);
                        }
                        // If there is no portal-portal overlap
                        if (existingPortalOverlap.size() == 0) {
                            createPortal(player, block, portalCreate.portalName, portalCreate.tpCoords, portalCreate.blockType, blockCoordArray);
                        } else {
                            /*
                             * Alert player that the portal they are trying to
                             * create overlaps "..." portals.
                             */
                            player.sendMessage("Portal \"" + portalCreate.portalName + "\" could not be created because it overlapped existing portals: "
                                    + existingPortalOverlap.toString() + ".");
                        }
                    } catch (BlockCrawler.MaxRecursionException e) {
                        /*
                         * Alert player that the portal they are trying to
                         * create has reached max recursion size
                         */
                        player.sendMessage("Portal \"" + portalCreate.portalName
                                + "\" could not be created because it was larger than the max Portal size of " + String.valueOf(maxPortalSize) + ".");
                    }
                } else {
                    player.sendMessage("There was an error using that Portal name. It wasn't a valid alpha-numeric string.");
                }
            } else {
                player.sendMessage("A Portal with the name \"" + portalCreate.portalName + "\" already exists.");
                iPTM.removeCreating(player.getUniqueId());
            }
        } else {
            player.sendMessage("The Portal should be made out of either Gold/Silver/Ender Portal/Portal Blocks originally");
        }
    }

    public void createPortal(CommandSender sender, Block block, String portalName, CoordsPY tpCoords, Block portalMaterial,
                             ArrayList<Coords> blockCoordsArray) {
        PortalInfo newPortalInfo = new PortalInfo();
        newPortalInfo.name = portalName;
        newPortalInfo.tpCoords = tpCoords;
        newPortalInfo.blockCoordArray = blockCoordsArray;

        /*
         * Trigger WarpPortalCreateEvent so that other plugins can tie in to new
         * WarpPortal creations
         */
        WarpPortalsCreateEvent wpCreateEvent = new WarpPortalsCreateEvent(sender, newPortalInfo);
        // Call WarpPortalsTeleportEvent

        // Check if the WarpPortalCreateEvent has been cancelled
        if (!wpCreateEvent.isCancelled()) {
            PortalInfo createPortalInfo = wpCreateEvent.getPortal();
            Location loc = block.getLocation();
            /*
             * Update the blocks in the Portal to whatever the Player designated
             * them to be.
             */
            changeMaterial(portalMaterial, createPortalInfo.blockCoordArray, loc);
            // Add portal
            iPDM.addPortal(createPortalInfo.name, createPortalInfo);
            // Deactivate portal creation tool
            if (sender instanceof Player) {
                iPTM.removeCreating(((Player) sender).getUniqueId());
            }
            // Alert player of portal creation success
            sender.sendMessage("\"" + createPortalInfo.name + "\" created and linked to " + createPortalInfo.tpCoords.toNiceString());
        } else {
            sender.sendMessage("\"" + portalName + "\" creation has been cancelled by another plugin.");
        }
    }
    public boolean changeMaterial(Block block, List<Coords> blockCoordArray, Location location) {
        if (block.isNormalBlock()) {
            for (Coords crd : blockCoordArray) {
                location.setComponents(crd.x, crd.y, crd.z);
//                location.setY(crd.y);
//                location.setZ(crd.z);
                location.getLevelBlock();
            }
            return true;
        }
        return false;
    }
}
