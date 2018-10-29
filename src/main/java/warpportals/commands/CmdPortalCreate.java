package warpportals.commands;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import warpportals.helpers.Regex;
import warpportals.nukkit.CommandHandler;
import warpportals.objects.Coords;
import warpportals.objects.CoordsPY;
import warpportals.objects.NullWorldException;
import warpportals.objects.PortalCreate;

public class CmdPortalCreate extends CommandHandlerObject {

    private static final String[] ALIASES = { "wp-portal-create", "wppc", "pcreate" };
    private static final String PERMISSION = "warpportals.admin.portal.create";
    private static final boolean REQUIRES_PLAYER = true;

    @Override
    public String getPermission() {
        return PERMISSION;
    }

    @Override
    public String[] getAliases() {
        return ALIASES;
    }

    @Override
    public boolean doesRequirePlayer() {
        return REQUIRES_PLAYER;
    }

    @Override
    boolean command(Player sender, String[] args, CommandHandler main) {
        if (args.length == 3) {
            try {
                if (args[0].matches(Regex.PORTAL_DEST_NAME)) {
                    if (main.iPortalManager.getPortalInfo(args[0].trim()) == null) {
                        try {
                            CoordsPY tpCoords = null;
                            if (args[1].matches("\\([0-9]+,[0-9]+,[0-9]+\\)")) {
                                tpCoords = new CoordsPY(new Coords(args[1]));
                            } else if (args[1].matches(Regex.PORTAL_DEST_NAME)) {
                                tpCoords = main.iPortalManager.getDestCoords(args[1]);
                                if (tpCoords == null)
                                    sender.sendMessage(main.iCC + "Couldn't find the WarpPortal Destination \"" + args[1] + "\"");
                            }
                            if (tpCoords != null) {
                                /*
                                 * Get the block type specified as the 3rd
                                 * argument for the portal's material type
                                 */
                                //Block blockType = Block.equals(args[2]);
                                // Test to see if that is a valid material
                                // type
//                                if (blockType != null) {
//                                    /*
//                                     * Test to see if it is a valid block type
//                                     * (not a fishing rod for example)
//                                     */
//                                    if (blockType.isSolid()) {
//                                        /*
//                                         * Test to see if the block is solid,
//                                         * recommend to the player that they
//                                         * don't use it
//                                         */
//                                        if (blockType.isSolid()) {
//                                            sender.sendMessage(main.iCC + "" + blockType
//                                                    + " is solid. You can create a WarpPortal using it but that may not be the best idea.");
//                                        }
//                                        // Get current item in the player's
//                                        // hand
//                                        Item curItem = sender.getInventory().getItemInHand();
//                                        /*
//                                         * Test if curItem is a tool or other
//                                         * non-block item
//                                         */
//                                        if (!curItem.canBePlaced()) {
//                                            PortalCreate portalCreate = new PortalCreate();
//                                            portalCreate.toolType = Item.get(curItem.getId());
//                                            portalCreate.portalName = args[0];
//                                            portalCreate.tpCoords = tpCoords;
//                                            portalCreate.blockType = blockType;
//                                            main.iPortalManager.addCreating(sender.getUniqueId(), portalCreate);
//                                            sender.sendMessage(TextFormat.AQUA + "Right-click on a Gold Block wall\n - Tool: \"" + curItem.getName()
//                                                    + "\"\n " + TextFormat.WHITE + "-" + TextFormat.AQUA + " WarpPortal Name: " + TextFormat.RED
//                                                    + portalCreate.portalName + TextFormat.WHITE + "\n - " + TextFormat.AQUA + "WarpPortal Dest: "
//                                                    + TextFormat.YELLOW + args[1]);
//                                        } else
//                                            sender.sendMessage(main.iCC + "You can't use a block for that! Try using something like the fishing rod.");
//                                    } else
//                                        sender.sendMessage(main.iCC + "WarpPortals can only be created out of blocks, you can't use other items.");
//                                } else
//                                    sender.sendMessage(main.iCC + "You have to provide a valid BLOCK_NAME to create the WarpPortal out of.");
                            } else
                                sender.sendMessage(main.iCC
                                        + "The 2nd param is the WarpPortal Destination. It must be in the format (x,y,z) or the name of a WarpPortal Destination set with /wp-destination-create [name]");
                        } catch (NullWorldException e) {
                            sender.sendMessage(main.iCC + "The world specified in the destination is invalid. \"" + e.getWorldName() + "\" does not exist.");
                        }
                    } else
                        sender.sendMessage(main.iCC + "\"" + args[0].trim() + "\" is already a Portal!");
                } else
                    sender.sendMessage(main.iCC + "The first param is the Portal Name. You can only use a-z, A-Z, 0-9.");
            } catch (Exception e) {
                sender.sendMessage(main.iCC + "Error creating Portal");
            }
        } else
            return false;
        return true;
    }
}
