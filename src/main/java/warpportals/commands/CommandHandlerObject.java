package warpportals.commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import warpportals.nukkit.CommandHandler;

import java.util.Map;

public abstract class CommandHandlerObject {

    public abstract String getPermission();

    public abstract String[] getAliases();

    public abstract boolean doesRequirePlayer();

    boolean command(CommandSender sender, String[] args, CommandHandler main) {
        sender.sendMessage("This command has not yet been implemented");
        return false;
    }

    boolean command(Player player, String[] args, CommandHandler main) {
        player.sendMessage("This command has not yet been implemented");
        return false;
    }

    public boolean handle(CommandSender sender, String[] args, CommandHandler main) {
        if (doesRequirePlayer() && !(sender instanceof Player)) {
            sender.sendMessage("This command must be run from an active player");
            return true;
        }
        if (sender.hasPermission(getPermission())) {
            if (doesRequirePlayer())
                return command((Player) sender, args, main);
            else
                return command(sender, args, main);
        } else {
            sender.sendMessage("You are lacking permission \"" + getPermission() + "\" which is required to run that command.");
            return false;
        }
    }

    public void populate(Map<String, CommandHandlerObject> map) {
        for (String a : getAliases()) {
            map.put(a, this);
        }
    }
}
