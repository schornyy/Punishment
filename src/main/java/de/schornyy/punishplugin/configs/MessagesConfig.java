package de.schornyy.punishplugin.configs;

import de.schornyy.punishplugin.PunishPlugin;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MessagesConfig {

    private File file;
    private FileConfiguration cfg;
    private Config config;
    private PunishPlugin plugin = PunishPlugin.getPlugin(PunishPlugin.class);
    private String path;

    public String prefix, noPermissions;
    public List<String> banPlayer;

    public MessagesConfig(Config config) {
        this.config = config;
        path = "Messages.";
        file = config.getFile();
        cfg = config.getCfg();
        load();
    }

    private void load(){
        if(!getCfg().isSet(path)) {

            getCfg().set(path + "prefix", "&cPunishment &f>> ");
            getCfg().set(path + "noPermissions", "&cDazu hast du keine Rechte!");

            banPlayer.add("&cDu wurdest gebannt von &6%punisher%");
            banPlayer.add("&fGrund: &6%reason%");
            banPlayer.add("&fEntbannung: &6%date%");
            getCfg().set(path + "bannedPlayerMessage", banPlayer);

            try {
                getCfg().save(getFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        prefix = getCfg().getString(path + "prefix").replaceAll("&", "§");
        noPermissions = prefix + getCfg().getString(path + "noPermissions").replaceAll("&", "§");

        for(String s : getCfg().getStringList(path + "bannedPlayerMessage")) {
            if(s == null) return;
            banPlayer.add(s.replaceAll("&", "§"));
        }
    }

    public FileConfiguration getCfg() {
        return cfg;
    }

    public File getFile() {
        return file;
    }
}
