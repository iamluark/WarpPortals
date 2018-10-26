package warpportals.manager;

import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.level.Location;
import cn.nukkit.utils.Config;
import warpportals.nukkit.PortalPlugin;
import warpportals.objects.*;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

public class PortalManager {

    PortalPlugin iPortalPlugin;
    Logger iLogger;
    // Yaml  Config

    public PortalDestManager iPortalDestManager;
    public PersistanceManager iPersistanceManager;
    public PortalCDManager iPortalCDManager;
    public PortalDataManager iPortalDataManager;
    public PortalInteractManager iPortalInteractManager;
    public PortalToolManager iPortalToolManager;

    public PortalManager(Logger logger, Config config, File dataFile, PortalPlugin plugin) {
        iPortalPlugin = plugin;
        iLogger = logger;

        iPersistanceManager = new PersistanceManager(iLogger, dataFile, iPortalPlugin);
        iPortalDataManager = new PortalDataManager(this, iLogger);
        iPortalToolManager = new PortalToolManager(this, iPortalConfig);
        iPortalCDManager = new PortalCDManager(iPortalDataManager, iPortalToolManager, iPortalConfig);
        iPortalDestManager = new PortalDestManager(this, iLogger);
        iPortalInteractManager = new PortalInteractManager(this);

        loadData();
    }

    public void onDisable() {
        saveDataFile();
    }

    public void loadData() {
        iPersistanceManager.loadDataFile(iPortalDataManager, iPortalCDManager, iPortalDestManager.mPortalDestMap);
    }

    public boolean saveDataFile() {
        return iPersistanceManager.saveDataFile(iPortalDataManager.getPortalMap(), iPortalDestManager.mPortalDestMap);
    }

    public boolean saveDataFile(File mPortalDataFile) {
        return iPersistanceManager.saveDataFile(iPortalDataManager.getPortalMap(), iPortalDestManager.mPortalDestMap, mPortalDataFile);
    }

    public boolean backupDataFile() {
        return iPersistanceManager.backupDataFile(iPortalDataManager.getPortalMap(), iPortalDestManager.mPortalDestMap, null);
    }

    public void playerItemRightClick(PlayerInteractEvent e) {
        iPortalToolManager.playerItemRightClick(e);
    }

    public PortalInfo isLocationInsidePortal(Location location) {
        String portalName = iPortalInteractManager.isLocationInsidePortal(location);
        if (portalName != null)
            return getPortalInfo(portalName);
        return null;
    }

    public Set<String> getPortalNames() {
        return iPortalDataManager.getPortalNames();
    }

    public PortalInfo getPortalInfo(String portalName) {
        return iPortalDataManager.getPortalInfo(portalName);
    }

    public String getPortalName(Coords coords) {
        return iPortalDataManager.getPortalName(coords);
    }

    public void addCreating(UUID playerUUID, PortalCreate portalCreate) {
        iPortalToolManager.addCreating(playerUUID, portalCreate);
    }

    public boolean changeMaterial(Material material, List<Coords> blockCoordArray, Location location) {
        return iPortalCDManager.changeMaterial(material, blockCoordArray, location);
    }

    public void deletePortal(String portalName) {
        iPortalCDManager.deletePortal(portalName);
    }

    public void addTool(UUID playerUUID, PortalTool tool) {
        iPortalToolManager.addTool(playerUUID, tool);
    }

    public PortalTool getTool(UUID playerUUID) {
        return iPortalToolManager.getTool(playerUUID);
    }

    public void removeTool(UUID playerUUID) {
        iPortalToolManager.removeTool(playerUUID);
    }

    public void addDestination(String destName, CoordsPY destCoords) {
        iPortalDestManager.addDestination(destName, destCoords);
    }

    public void removeDestination(String destName) {
        iPortalDestManager.removeDestination(destName);
    }

    public CoordsPY getDestCoords(String destName) {
        return iPortalDestManager.getDestCoords(destName);
    }

    public Set<String> getDestinations() {
        return iPortalDestManager.getDestinations();
    }

    public String getDestinationName(CoordsPY coords) {
        return iPortalDestManager.getDestinationName(coords);
    }
}
