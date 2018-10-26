package warpportals.objects;

import cn.nukkit.item.ItemID;
import cn.nukkit.block.BlockID;

public class PortalCreate {

    public ItemID toolType;
    public String portalName;
    public CoordsPY tpCoords;
    public BlockID blockType;

    public PortalCreate(String portalName, ItemID toolType, CoordsPY tpCoords, BlockID blockType) {
        this.portalName = portalName;
        this.toolType = toolType;
        this.tpCoords = tpCoords;
        this.blockType = blockType;
    }

    public PortalCreate() {

    }
}
