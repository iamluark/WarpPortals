package warpportals.commands;

import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import warpportals.nukkit.CommandHandler;

public class CmdVersion extends CommandHandlerObject {

    private static final String[] ALIASES = { "wp-version", "wpv" };
    private static final String PERMISSION = "warpportals.version";
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
        sender.sendMessage(TextFormat.RED + "WarpPortals " + TextFormat.YELLOW + "v" + main.iPortalPlugin.getDescription().getVersion());
        return true;
    }
}

