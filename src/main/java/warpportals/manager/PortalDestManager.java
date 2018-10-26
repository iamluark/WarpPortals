package warpportals.manager;

import warpportals.objects.CoordsPY;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class PortalDestManager {

    Logger iLogger;

    private PortalManager iPM;

    public HashMap<String, CoordsPY> iPortalDestMap = new HashMap<String, CoordsPY>();

    public PortalDestManager(PortalManager pm, Logger logger) {
        iPM = pm;
        iLogger = logger;
    }

    public void addDestination(String destname, CoordsPY coords) {
        iPortalDestMap.put(destname, coords);
        iPM.saveDataFile();
    }

    public void removeDestination(String destName) {
        iPortalDestMap.remove(destName);
        iPM.saveDataFile();
    }

    public CoordsPY getDestCoords(String destname) {
        return iPortalDestMap.get(destname);
    }

    public Set<String> getDestinations() {
        return iPortalDestMap.keySet();
    }

    public String getDestinationName(CoordsPY coords) {
        for (Map.Entry<String, CoordsPY> dest : iPortalDestMap.entrySet()) {
            if (dest.getValue().equals(coords)) {
                return dest.getKey();
            }
        }
        return null;
    }
}
