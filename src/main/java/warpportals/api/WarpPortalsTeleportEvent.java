package warpportals.api;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.Event;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.HandlerList;
import cn.nukkit.level.Location;

public class WarpPortalsTeleportEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;

    // Event data
    private Player player;
    private Location prevLocation;

    /**
     * Constructor of a WarpPortalsEnterEvent. Triggered when a player has entered a WarpPortal managed portal.
     *
     * @param p
     *            - Player that is entering the portal
     * @param prevLoc
     *            - Player's pre-teleport location
     */
    public WarpPortalsTeleportEvent(Player p, Location prevLoc) {
        this.cancelled = false;

        player = p;
        prevLocation = prevLoc;
    }


    /* Bukkit Event API methods */

    @EventHandler
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

    /* WarpPortal API methods */

    /**
     * Get the player that is entering the portal.
     *
     * @return Player associated with the event
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get the player's pre-teleport location.
     *
     * @return Location
     */
    public Location getPreviousLocation() {
        return prevLocation;
    }
}
