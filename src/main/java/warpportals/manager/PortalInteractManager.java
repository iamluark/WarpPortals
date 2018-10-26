package warpportals.manager;

import cn.nukkit.level.Location;
import warpportals.objects.Coords;

public class PortalInteractManager {

    PortalManager iPortalManager;

    public PortalInteractManager(PortalManager portalManager) {
        iPortalManager = portalManager;
    }

    /** Test whether or not the location is currently inside a portal.
     * @param loc Location to test
     * @return String of the portal's name OR null if the player is not in a portal.
     */
    public String isLocationInsidePortal(Location loc) {
        Coords coords = Coords.createCourse(loc);
        return iPortalManager.getPortalName(coords);
    }
}
