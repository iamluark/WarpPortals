package warpportals.commands;

import cn.nukkit.command.CommandSender;
import warpportals.nukkit.CommandHandler;

public class CmdSave extends CommandHandlerObject {

    private static final String[] ALIASES = { "wp-save", "wps", "psave" };
    private static final String PERMISSION = "warpportals.admin.op.save";
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
        try {
            main.iPortalManager.saveDataFile(main.iPortalPlugin.iPortalDataFile);
            sender.sendMessage("Force saved current Portal data.");
        } catch (Exception e) {
            sender.sendMessage("Error saving Portal data");
        }
        return true;
    }
}
