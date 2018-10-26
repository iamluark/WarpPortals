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

    public HashMap<UUID, PortalCreate> mPlayerPortalCreateMap = new HashMap<UUID, PortalCreate>();
    public HashMap<UUID, PortalTool> mPlayerPortalToolMap = new HashMap<UUID, PortalTool>();

    public PortalToolManager(PortalManager pm, Config portalConfig) {
        iPM = pm;

        iConfig = portalConfig;
    }

    public void addCreating(UUID playerUUID, PortalCreate portalCreate) {
        mPlayerPortalCreateMap.put(playerUUID, portalCreate);
    }

    public void removeCreating(UUID playerUUID) {
        mPlayerPortalCreateMap.remove(playerUUID);
    }

    public void addTool(UUID playerUUID, PortalTool tool) {
        mPlayerPortalToolMap.put(playerUUID, tool);
    }

    public void removeTool(UUID playerUUID) {
        mPlayerPortalToolMap.remove(playerUUID);
    }

    public PortalTool getTool(UUID playerUUID) {
        return mPlayerPortalToolMap.get(playerUUID);
    }

//    public void playerItemRightClick(PlayerInteractEvent e) {
//        Player player = e.getPlayer();
//        PortalCreate portalCreate = mPlayerPortalCreateMap.get(player.getUniqueId());
//        if (portalCreate != null && portalCreate.toolType == player.getItemInHand().getType()) {
//            iPM.iPortalCDManager.possibleCreatePortal(e.getClickedBlock(), player, portalCreate);
//        } else {
//            PortalTool tool = mPlayerPortalToolMap.get(player.getUniqueId());
//            if (tool != null && tool.toolType == player.getItemInHand().getType()) {
//                if (tool.action == PortalTool.Action.DELETE) {
//                    iPM.iPortalCDManager.possibleDeletePortal(e.getClickedBlock(), player);
//                } else if (tool.action == PortalTool.Action.IDENTIFY) {
//                    identifyPortal(e);
//                }
//            }
//        }
//    }

//    private void identifyPortal(PlayerInteractEvent e) {
//        Player player = e.getPlayer();
//        String portalName = iPM.getPortalName(new Coords(e.getClickedBlock()));
//        if (portalName != null) {
//            player.sendMessage(portalName);
//        } else {
//            player.sendMessage("That is not a WarpPortal.");
//        }
//    }
}
