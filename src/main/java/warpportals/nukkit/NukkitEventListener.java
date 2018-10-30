package warpportals.nukkit;

import cn.nukkit.Nukkit;
import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.event.Event;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockFormEvent;
import cn.nukkit.event.block.BlockFromToEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.event.player.PlayerTeleportEvent;
import cn.nukkit.event.level.LevelUnloadEvent;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.Logger;
import cn.nukkit.utils.TextFormat;
import warpportals.api.WarpPortalsEvent;
import warpportals.api.WarpPortalsTeleportEvent;
import warpportals.helpers.Defaults;
import warpportals.manager.PortalManager;
import warpportals.objects.CoordsPY;
import warpportals.objects.PortalInfo;

import java.util.Iterator;
import java.util.Map;

public class NukkitEventListener implements Listener {
    PortalPlugin iPlugin;
    PortalManager iPortalManager;
    Config iPortalConfig;

    Logger iLogger;

    final TextFormat iCC;
    final boolean iAllowNormalPortals;
    final boolean iAlertUserAboutPortalPermission;
    final boolean iCheckIndividualPortalPermission;

    public NukkitEventListener(PortalPlugin plugin, PortalManager portalManager, Config portalConfig) {
        iPlugin = plugin;
        iPortalManager = portalManager;
        iPortalConfig = portalConfig;

        iCC = TextFormat.getByChar(iPortalConfig.getString("portals.general.textColor", Defaults.CHAT_COLOR));
        iAllowNormalPortals = iPortalConfig.getBoolean("portals.general.allowNormalPortals", Defaults.ALLOW_NORMAL_PORTALS);
        iAlertUserAboutPortalPermission = iPortalConfig.getBoolean("portals.permission.alertUser", Defaults.ALERT_USER_ABOUT_PORTAL_PERMISSION);
        iCheckIndividualPortalPermission = iPortalConfig.getBoolean("portals.permission.checkIndividualPortalPermissions", Defaults.CHECK_INDIVIDUAL_PORAL_PERMISSIONS);

        iLogger = iPlugin.getLogger();
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        /*
         * Watch all player move events and simplify them down to block move
         * events. Block move events can be listened for as
         * WarpPortalsPlayerBlockMoveEvent
         */
        // Check if player is moving between blocks
        boolean isSameBlock = (e.getFrom().getFloorX() == e.getTo().getFloorX()) && (e.getFrom().getFloorY() == e.getTo().getFloorY()) && (e.getFrom().getFloorZ() == e.getTo().getFloorZ());
        if(!isSameBlock) {
            // Check if player is in a WarpPortal
            PortalInfo portal = iPortalManager.isLocationInsidePortal(e.getTo());
            // If player is in a WarpPortal
            if (portal != null) {
                boolean hasPermission;
                if (iCheckIndividualPortalPermission)
                    // Check player permissions to use portal
                    hasPermission = player.hasPermission("warpportals.enter." + portal.name);
                else
                    hasPermission = player.hasPermission("warpportals.enter");

                // Create WarpPortalsEvent
                WarpPortalsEvent wpEvent = new WarpPortalsEvent(player, portal, hasPermission);
                // Call WarpPortalsEvent
                iPlugin.getServer().getPluginManager().callEvent(wpEvent);

                // Check if event has been cancelled
                // Event status defaults to player permissions to use the portal
                if (!wpEvent.isCancelled()) {
                    // Get (possibly modified) teleportation data
                    CoordsPY tpCoords = wpEvent.getTeleportCoordsPY();

                    // Save player's current location
                    Location preTPLocation = player.getLocation();

                    /* Teleport player */
                    Location tpLoc = new Location(tpCoords.x, tpCoords.y, tpCoords.z, tpCoords.yaw, tpCoords.pitch, iPlugin.getServer().getLevelByName((String) tpCoords.level.getName()));
                    player.teleport(tpLoc);

                    WarpPortalsTeleportEvent wpTPEvent = new WarpPortalsTeleportEvent(player, preTPLocation);
                    // Call WarpPortalsTeleportEvent
                    iPlugin.getServer().getPluginManager().callEvent(wpTPEvent);

                } else {
                    if (!hasPermission) {
                        if (iAlertUserAboutPortalPermission) {
                            player.sendMessage(iCC + "You do not have permission to use that WarpPortal.");
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPortalEnter(PlayerTeleportEvent event) {
        // Get player involved in the event
        Player player = event.getPlayer();
        // Get if player is in a WarpPortal or normal portal
        /*
         * If the player is in a WarpPortal, let the onPlayerBlockMoveEvent
         * listener handle it.
         */
        if (iPortalManager.isLocationInsidePortal(player.getLocation()) == null) {
            // The player did not enter a WarpPortal
            /*
             * Check to see if the server is configured to allow normal portal
             * events
             */
            if (!iAllowNormalPortals)
                // If not allowed, cancel the event
                event.setCancelled(true);
        } else
            // If in a WarpPortal, cancel the event
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        // lacks item and block validations
        if (e.getAction() == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            iPortalManager.playerItemRightClick(e);
        }
    }

    /**
     * Used to allow PORTAL blocks to face any direction, with a contiguous
     * direction as its adjacent portal blocks.
     *
     * @param e
     */
    @EventHandler
    public void onBlockPhysicsEvent(BlockFormEvent e) {
        //The following check is to prevent physics when we initially change the template gold blocks to portals, which
        //causes only two portal blocks to appear and the rest to vanish immediately:
        if((e.getBlock().getId() == BlockID.END_PORTAL && e.getNewState() == Block.get(BlockID.GOLD_BLOCK)) ||
                (e.getBlock().getId() == BlockID.END_PORTAL && iPortalManager.isLocationInsidePortal(e.getBlock().getLocation()) != null)) {
            e.setCancelled(true);
        }
    }

    /**
     * Used to keep liquid blocks from flowing outside of the portal.
     *
     * @param event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockFromTo(BlockFromToEvent event) {
        Block block = event.getBlock();
        if (iPortalManager.isLocationInsidePortal(block.getLocation()) != null) {
            event.setCancelled(true);
        }
    }

    /**
     * Protects portals from getting destroyed.
     *
     * @param event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (iPortalManager.isLocationInsidePortal(block.getLocation()) != null) {
            if (!event.getPlayer().hasPermission("warpportals.admin.breakblock"))
                event.setCancelled(true);
        }
    }

    /**
     * Handles deleted or unloaded world events. Keeps WarpPortals from existing
     * in non-existent worlds.
     *
     * @param e
     */
    @EventHandler
    public void onWorldUnloadEvent(LevelUnloadEvent e) {
        Level unloadedLevel = e.getLevel();

        /* Backup existing data */
        iPortalManager.backupDataFile();

        /* Look for portals in the unloaded world */
        for (Iterator<Map.Entry<String, PortalInfo>> it = iPortalManager.iPortalDataManager.getPortalMap().entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, PortalInfo> entry = it.next();

            PortalInfo portal = entry.getValue();
            /* Test if portal exist in a non-existing or unloaded world */
            Level portalLevel = portal.blockCoordArray.get(0).level;
            if (portalLevel == null || portalLevel.equals(unloadedLevel)) {
                /* Remove portal from map */
                it.remove();
                /* Alert console */
                iLogger.warning("The portal \"" + portal.name + "\" has been deleted because the level \"" + unloadedLevel.getName() + "\" does not exist anymore.");
                continue;
            }
            Level destLevel = portal.tpCoords.level;
            if (destLevel == null || destLevel.equals(unloadedLevel)) {
                /*
                 * Set Portal blocks to default gold state.
                 */
                Location loc = new Location(0, 0, 0, portal.blockCoordArray.get(0).level);
                iPortalManager.iPortalCDManager.changeMaterial(Block.get(BlockID.GOLD_BLOCK), portal.blockCoordArray, loc);

                /* Remove portal from map */
                it.remove();
                /* Alert console */
                iLogger.error("The destination for portal \"" + portal.name + "\" is in a non-existant world \"" + unloadedLevel.getName()
                        + "\". The portal has been deactivated.");
            }
        }
        /* Look for destinations in the unloaded world */
        for (Iterator<Map.Entry<String, CoordsPY>> it = iPortalManager.iPortalDestManager.iPortalDestMap.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, CoordsPY> entry = it.next();

            CoordsPY destCoords = entry.getValue();
            /* Test if destination exist in a non-existent or unloaded level */
            Level destLevel = destCoords.level;
            if (destLevel == null || destLevel.equals(unloadedLevel)) {
                /* Remove destination from map */
                it.remove();
                /* Alert console */
                iLogger.warning("The destination \"" + entry.getKey() + "\" has been deleted because the world \"" + unloadedLevel.getName()
                        + "\" does not exist anymore.");
            }
        }
    }
}
