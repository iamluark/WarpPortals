package warpportals.objects;

import cn.nukkit.block.Block;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import cn.nukkit.block.BlockID;

public class PortalCreate {

    public ItemID toolType;
    public String portalName;
    public CoordsPY tpCoords;
    public Block blockType;

    public PortalCreate(String portalName, Item toolType, CoordsPY tpCoords, Block blockType) {
        this.portalName = portalName;
        this.toolType = toolType;
        this.tpCoords = tpCoords;
        this.blockType = blockType;
    }

    public PortalCreate() {

    }
}
