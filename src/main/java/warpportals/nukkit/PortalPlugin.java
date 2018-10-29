package warpportals.nukkit;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import warpportals.api.example.WarpPortalsEventListener;
import warpportals.helpers.Defaults;
import warpportals.helpers.Utils;
import warpportals.manager.PortalManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.logging.Logger;

public class PortalPlugin extends PluginBase {

    CommandHandler iCommandHandler;
    public PortalManager iPortalManager;

    public File iPortalDataFile;
    File iPortalConfigFile;
    public Config iPortalConfig;

    @Override
    public void onEnable() {
        iPortalConfigFile = new File(getDataFolder(), "config.yml");
        iPortalDataFile = new File(getDataFolder(), "portals.yml");
        iPortalConfig = new Config();
        initiateConfigFiles();
        loadConfigs();
        iPortalManager = new PortalManager(iPortalConfig, iPortalDataFile, this);
        iCommandHandler = new CommandHandler(this, iPortalManager, iPortalConfig);
        getServer().getPluginManager().registerEvents(new NukkitEventListener(this, iPortalManager, iPortalConfig), this);

        // Register example WarpPortals Event API Listener
//        String tpMessage = iPortalConfig.getString("portal.teleport.messageColor", Defaults.TP_MESSAGE);
//        TextFormat tpChatColor = TextFormat.getByChar(iPortalConfig.getString("portals.teleport.messageColor", Defaults.TP_MSG_COLOR));
//        getServer().getPluginManager().registerEvents(new WarpPortalsEventListener(tpMessage, tpChatColor), this);
    }

    private void initiateConfigFiles() {
        if (!iPortalConfigFile.exists()) {
            iPortalConfigFile.getParentFile().mkdirs();
            try {
                iPortalConfigFile.createNewFile();
                Utils.copy(getResource("config.yml"), iPortalConfigFile);
            } catch (IOException e) {
                getLogger().error("Error creating the default Portal config file!");
                e.printStackTrace();
            }
        }

        if (!iPortalDataFile.exists()) {
            iPortalDataFile.getParentFile().mkdirs();
            try {
                iPortalDataFile.createNewFile();
            } catch (IOException e) {
                getLogger().error("Error creating Portal's save file!");
                e.printStackTrace();
            }
        }
    }

    private void loadConfigs() {
        saveDefaultConfig();
    }

    private void saveConfigs() {

    }

    @Override
    public void onDisable() {
        iPortalManager.onDisable();
        saveConfigs();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return iCommandHandler.handleCommand(sender, command, label, args);
    }
}
