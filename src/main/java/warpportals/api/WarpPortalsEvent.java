package warpportals.api;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;
import cn.nukkit.level.Location;
import warpportals.objects.CoordsPY;
import warpportals.objects.PortalInfo;

public class WarpPortalsEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;

    // Event data
    private Player player;
    private PortalInfo portal;
    private boolean hasPermission;

    // Modifiable event data
    private CoordsPY tpCoords;

    /**
     * Constructor of a WarpPortalsEvent, triggered when a player is teleported
     * by a WarpPortal
     *
     * @param p
     *            - Player that is getting teleported
     * @param po
     *            - PortalInfo for the WarpPortal that the player has entered.
     *            Contains the WarpPortal's name, blocks, destination coords.
     * @param hp
     *            - boolean if the player has permission to be teleported or
     *            not.
     */
    public WarpPortalsEvent(Player p, PortalInfo po, boolean hp) {
        this.cancelled = !hp;

        player = p;
        portal = po.clone();
        hasPermission = hp;

        tpCoords = portal.tpCoords.clone();
    }

    /* Bukkit Event API methods */

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    /* WarpPortal Event API methods */

    /* Get event data */
    /**
     * Get the player that is entering the WarpPortal.
     *
     * @return Player associated with the event
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get a copy of the WarpPortal that the player is entering. PortalInfo
     * contains portal location data and teleportation data.
     *
     * @return PortalInfo - WarpPortal associated with the event
     */
    public PortalInfo getPortal() {
        return portal;
    }

    /**
     * Does the player have permission to enter the portal? Permission according
     * to player.hasPermission("warpportal.enter")
     *
     * @return boolean
     */
    public boolean hasPermission() {
        return hasPermission;
    }

    /**
     * Get a copy of the Teleport Coordinates that the player is scheduled for.
     * CoordsPY contains world, x, y, z, pitch and yaw data.
     *
     * @return CoordsPY - Teleport CoordsPY associated with the event
     */
    public CoordsPY getTeleportCoordsPY() {
        return tpCoords;
    }

    /* Set event data */

    /**
     * Modify the player's teleport destination. The CoordsPY supplied to this
     * method will override the original teleport destination, and WarpPortals
     * will teleport the player to this new location.
     *
     * @param newTPC
     *            - CoordsPY of the new destination
     */
    public void setTeleportCoordsPY(CoordsPY newTPC) {
        tpCoords = newTPC;
    }

    /**
     * Modify the player's teleport destination. The Location supplied to this
     * method will override the original teleport destination, and WarpPortals
     * will teleport the player to this new location.
     *
     * @param newLoc
     *            - CoordsPY of the new destination
     */
    public void setTeleportCoordsPY(Location newLoc) {
        setTeleportCoordsPY(new CoordsPY(newLoc));
    }
}
