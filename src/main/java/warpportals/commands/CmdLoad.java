package warpportals.commands;

import cn.nukkit.command.CommandSender;
import warpportals.nukkit.CommandHandler;
import warpportals.objects.CoordsPY;

public class CmdLoad extends CommandHandlerObject {

    private static final String[] ALIASES = { "wp-load", "wpl", "pload" };
    private static final String PERMISSION = "warpportals.admin.op.load";
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
        main.iPortalManager.iPortalDataManager.clearPortalMap();
        main.iPortalManager.iPortalDestManager.mPortalDestMap = new HashMap<String, CoordsPY>();
        main.iPortalManager.loadData();
        sender.sendMessage("Portal data loaded from portals.yml.");
        return true;
    }
}
