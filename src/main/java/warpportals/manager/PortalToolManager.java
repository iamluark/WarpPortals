package warpportals.manager;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.utils.Config;
import warpportals.objects.Coords;
import warpportals.objects.PortalCreate;
import warpportals.objects.PortalTool;

import java.util.HashMap;
import java.util.UUID;

public class PortalToolManager {

    PortalManager iPM;

    Config iConfig;

    public HashMap<UUID, PortalCreate> iPlayerPortalCreateMap = new HashMap<UUID, PortalCreate>();
    public HashMap<UUID, PortalTool> iPlayerPortalToolMap = new HashMap<UUID, PortalTool>();

    public PortalToolManager(PortalManager pm, Config portalConfig) {
        iPM = pm;

        iConfig = portalConfig;
    }

    public void addCreating(UUID playerUUID, PortalCreate portalCreate) {
        iPlayerPortalCreateMap.put(playerUUID, portalCreate);
    }

    public void removeCreating(UUID playerUUID) {
        iPlayerPortalCreateMap.remove(playerUUID);
    }

    public void addTool(UUID playerUUID, PortalTool tool) {
        iPlayerPortalToolMap.put(playerUUID, tool);
    }

    public void removeTool(UUID playerUUID) {
        iPlayerPortalToolMap.remove(playerUUID);
    }

    public PortalTool getTool(UUID playerUUID) {
        return iPlayerPortalToolMap.get(playerUUID);
    }

    public void playerItemRightClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        PortalCreate portalCreate = iPlayerPortalCreateMap.get(player.getUniqueId());
        if (portalCreate != null && portalCreate.toolType == player.getInventory().getItemInHand().getNamedTag()) {
            iPM.iPortalCDManager.possibleCreatePortal(e.getBlock(), player, portalCreate);
        } else {
            PortalTool tool = iPlayerPortalToolMap.get(player.getUniqueId());
            if (tool != null && tool.toolType == player.getInventory().getItemInHand().getNamedTag()) {
                if (tool.action == PortalTool.Action.DELETE) {
                    iPM.iPortalCDManager.possibleDeletePortal(e.getBlock(), player);
                } else if (tool.action == PortalTool.Action.IDENTIFY) {
                    identifyPortal(e);
                }
            }
        }
    }

    private void identifyPortal(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        String portalName = iPM.getPortalName(new Coords(e.getBlock()));
        if (portalName != null) {
            player.sendMessage(portalName);
        } else {
            player.sendMessage("That is not a WarpPortal.");
        }
    }
}
