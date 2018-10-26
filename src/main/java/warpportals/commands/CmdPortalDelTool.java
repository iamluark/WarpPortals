package warpportals.commands;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import warpportals.nukkit.CommandHandler;
import warpportals.objects.PortalTool;

public class CmdPortalDelTool extends CommandHandlerObject {

    private static final String[] ALIASES = { "wp-portal-deltool", "wppdt", "pdeltool" };
    private static final String PERMISSION = "warpportals.admin.portal.delete.tool";
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
            Item curItem = player.getInventory().getItemInHand();
            if (!curItem.getNamedTag().equals("block")) {
                PortalTool tool = new PortalTool(curItem.getName(), PortalTool.Action.DELETE);
                main.iPortalManager.addTool(player.getUniqueId(), tool);
                player.sendMessage("Portal delete tool equipped to \"" + curItem.getName() + "\"");
            } else
                player.sendMessage("You can't use a block for that! Try using something like the fishing rod.");
        } catch (Exception e) {
            player.sendMessage("Error deleting Portal");
        }
        return true;
    }
}
