package warpportals.commands;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Location;
import cn.nukkit.utils.TextFormat;
import warpportals.nukkit.CommandHandler;
import warpportals.objects.PortalInfo;

public class CmdPortalMaterial extends CommandHandlerObject {

    private static final String[] ALIASES = { "wp-portal-material", "wppm" };
    private static final String PERMISSION = "warpportals.admin.portal.material";
    private static final boolean REQUIRES_PLAYER = false;

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
    boolean command(CommandSender sender, String[] args, CommandHandler main) {
        if (args.length == 2) {
            try {
                PortalInfo portal = main.iPortalManager.getPortalInfo(args[0].trim());
                if (portal != null) {
                    /*
                     * Get the block type specified as the 3rd argument for the
                     * portal's material type
                     */
                    Block blockType = Block.get(BlockID.GOLD_BLOCK);
                    // Test to see if that is a valid material type
                    if (blockType != null) {
                        /*
                         * Test to see if it is a valid block type (not a
                         * fishing rod for example)
                         */
                        if (blockType.isSolid()) {
                            /*
                             * Test to see if the block is solid, recommend to
                             * the player that they don't use it
                             */
                            if (blockType.isSolid()) {
                                sender.sendMessage(main.iCC + "" + blockType
                                        + " is solid. You can create a WarpPortal using it but that may not be the best idea.");
                            }
                            main.iPortalManager.changeMaterial(blockType, portal.blockCoordArray, new Location(0, 0, 0, portal.tpCoords.level));
                            sender.sendMessage(TextFormat.RED + portal.name + TextFormat.WHITE + " portal material changed to " + TextFormat.AQUA + blockType);
                        } else
                            sender.sendMessage(main.iCC + "WarpPortals can only be created out of blocks, you can't use other items.");
                    } else
                        sender.sendMessage(main.iCC + "You have to provide a valid BLOCK_NAME to create the WarpPortal out of.");
                } else
                    sender.sendMessage(main.iCC + "\"" + args[0].trim() + "\" is not a WarpPortal!");
            } catch (Exception e) {
                sender.sendMessage(main.iCC + "Error modifying WarpPortal type");
            }
        } else
            return false;
        return true;
    }
}
