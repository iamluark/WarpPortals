package warpportals.api;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.player.PlayerEvent;
import cn.nukkit.event.HandlerList;
import cn.nukkit.level.Location;

public class WarpPortalsPlayerBlockMoveEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private Location from;
    private Location to;

    private boolean cancel;

    public WarpPortalsPlayerBlockMoveEvent(Player who, Location from, Location to) {
        super(who);

        this.from = from;
        this.to = to;
    }

    /**
     * Get the block-course location that the player was previously at.
     *
     * @return Location
     */
    public Location getFrom() {
        return from;
    }

    /**
     * Get the block-course location that the player is now on.
     *
     * @return Location
     */
    public Location getTo() {
        return to;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
