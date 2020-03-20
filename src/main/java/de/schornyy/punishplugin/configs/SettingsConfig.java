package de.schornyy.punishplugin.configs;

import de.schornyy.punishplugin.PunishPlugin;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;

public class SettingsConfig {

    private File file;
    private FileConfiguration cfg;
    private Config config;
    private PunishPlugin plugin = PunishPlugin.getPlugin(PunishPlugin.class);
    private String path;

    public int maxWarns;

    public SettingsConfig(Config config) {
        this.config = config;
        path = "Settings.";
        file = config.getFile();
        cfg = config.getCfg();
        load();
    }

    private void load(){
        if(!getCfg().isSet(path)) {

            getCfg().set(path + "maxWarns", 3);

            try {
                getCfg().save(getFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        maxWarns = getCfg().getInt(path + "maxWarns");
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getCfg() {
        return cfg;
    }

}
