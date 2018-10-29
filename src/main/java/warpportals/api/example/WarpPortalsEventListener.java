package warpportals.api.example;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.utils.TextFormat;
import warpportals.api.WarpPortalsTeleportEvent;
import warpportals.helpers.Utils;

public class WarpPortalsEventListener implements Listener {

    /* Class variables */
    // Message to send to player upon teleport
    String mTPMessage;
    // ChatColor of message
    TextFormat iTPC;

    // Array of messages that denote 'No message to be sent'
    static final String[] NO_MESSAGE = new String[]{"&none", "none", ""};

    /**
     * Create an Event Listener for the WarpPortals Event API. This listener listens for the WarpPortalsTeleportEvent
     * and sends a colored chat message to the player when the event is triggered.
     *
     * @param tpMessage - Message to send
     * @param tpCharColor - ChatColor of the message
     */
    public WarpPortalsEventListener(String tpMessage, TextFormat tpCharColor) {
        mTPMessage = tpMessage;
        iTPC = tpCharColor;

        if (Utils.arrayContains(NO_MESSAGE, mTPMessage))
            mTPMessage = null;
    }

    @EventHandler
    public void onTeleport(WarpPortalsTeleportEvent event) {
        Player player = event.getPlayer();
        if (mTPMessage != null)
            player.sendMessage(iTPC + mTPMessage);
    }

}
