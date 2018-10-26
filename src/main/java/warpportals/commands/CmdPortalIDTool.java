package warpportals.commands;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.item.Item;
import warpportals.nukkit.CommandHandler;
import warpportals.objects.PortalTool;

public class CmdPortalIDTool extends CommandHandlerObject {

    private static final String[] ALIASES = { "wp-portal-idtool", "wppid", "wppidt" };
    private static final String PERMISSION = "warpportals.admin.portal.list.tool";
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
    boolean command(Player player, String[] args, CommandHandler main) {
        try {
            Item item = player.getInventory().getItemInHand();
            if (!item.getNamedTag().equals("block")) {
                if (main.iPortalManager.getTool(player.getUniqueId()) != null) {
                    main.iPortalManager.removeTool(player.getUniqueId());
                    player.sendMessage("Portal identify tool dequipped from \"" + item.getName() + "\"");
                } else {
//                    PortalTool tool = new PortalTool(item.getNamedTag(), PortalTool.Action.IDENTIFY);
//                    main.iPortalManager.addTool(player.getUniqueId(), tool);
                    player.sendMessage("Portal identify tool equipped to \"" + item.getName() + "\"");
                }
            } else
                player.sendMessage("You can't use a block for that! Try using something like the fishing rod.");
        } catch (Exception e) {
            player.sendMessage("Error identifying Portal");
        }
        return true;
    }
}
