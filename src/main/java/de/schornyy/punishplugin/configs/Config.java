package de.schornyy.punishplugin.configs;

import com.sun.scenario.Settings;
import de.schornyy.punishplugin.PunishPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Config {

    private File file;
    private FileConfiguration cfg;
    private PunishPlugin plugin = PunishPlugin.getPlugin(PunishPlugin.class);
    private MessagesConfig messagesConfig;
    private SettingsConfig settingsConfig;

    public Config() {
        file = new File(plugin.getDataFolder() + "/Config.yml");
        cfg = YamlConfiguration.loadConfiguration(file);
        loadConfigs();
    }

    private void loadConfigs() {
        messagesConfig = new MessagesConfig(this);
        settingsConfig = new SettingsConfig(this);
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getCfg() {
        return cfg;
    }

    public SettingsConfig getSettingsConfig() {
        return settingsConfig;
    }

    public MessagesConfig getMessagesConfig() {
        return messagesConfig;
    }
}
