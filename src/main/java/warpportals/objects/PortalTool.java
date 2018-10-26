package warpportals.objects;

import cn.nukkit.item.ItemID;

public class PortalTool {

    public ItemID toolType;
    public Action action;

    public PortalTool(ItemID toolType, Action action) {
        this.toolType = toolType;
        this.action = action;
    }

    public static enum Action {
        DELETE, IDENTIFY;
    }
}
