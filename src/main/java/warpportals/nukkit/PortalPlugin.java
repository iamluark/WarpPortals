package warpportals.nukkit;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import warpportals.helpers.Utils;
import warpportals.manager.PortalManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PortalPlugin extends PluginBase implements Listener {

    CommandHandler iCommandHandler;
    public PortalManager iPortalManager;

    public File iPortalDataFile;
    File iPortalConfigFile;

    private Config config;
    private Config portals;

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
//        config = new File(getDataFolder(), "config.yml");
//        portals = new File(getDataFolder(), "portals.yml");
        this.config = new Config(new File(getDataFolder(), "config.yml"), Config.YAML);
        this.portals = new Config(new File(getDataFolder(), "Portals.yml"), Config.YAML);

        this.saveDefaultConfig();
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
//        try {
//
//        } catch (InvalidConfigurationException e) {
//            getLogger().error("The WarpPortal config file has invalid markup.");
//        } catch (FileNotFoundException e) {
//            getLogger().error("No config file found for WarpPortals!");
//        } catch (IOException e) {
//            getLogger().error("Can't load Portal's config file!");
//            e.printStackTrace();
//        }
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
