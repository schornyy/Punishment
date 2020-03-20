package de.schornyy.punishplugin.punishment;

import de.schornyy.punishplugin.PunishPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ReasonManager {

    private File file;
    private FileConfiguration cfg;
    private PunishPlugin plugin = PunishPlugin.getPlugin(PunishPlugin.class);
    private ArrayList<Reason> storedReasons;

    public ReasonManager() {
        file = new File(plugin.getDataFolder() + "/Reasons.yml");
        cfg = YamlConfiguration.loadConfiguration(file);
        storedReasons = new ArrayList<>();
        load();
    }

    private void load() {
        if(!getFile().exists()) {

            getCfg().set("Reason.Hacking.Name", "Hacking");
            getCfg().set("Reason.Hacking.Days", 30);

            try {
                getCfg().save(getFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(getCfg().isSet("Reason.")) {
            for(String reason : getCfg().getConfigurationSection("Reason.").getKeys(false)) {
                if(reason == null) return;
                String path = "Reason." + reason;
                Reason r = new Reason(path + ".Name");
                if(getCfg().isSet(path + ".Seconds")) {
                    r.setSeconds(getCfg().getInt(path + ".Seconds"));
                }
                if(getCfg().isSet(path + ".Minutes")) {
                    r.setMinits(getCfg().getInt(path + ".Minutes"));
                }
                if(getCfg().isSet(path + ".Hours")) {
                    r.setHours(getCfg().getInt(path + ".Hours"));
                }
                if(getCfg().isSet(path + ".Days")) {
                    r.setDays(getCfg().getInt(path + ".Days"));
                }
                if(getCfg().isSet(path + ".Months")) {
                    r.setMonth(getCfg().getInt(path + ".Months"));
                }
                if(getCfg().isSet(path + ".Years")) {
                    r.setYears(getCfg().getInt(path + ".Years"));
                }
                storedReasons.add(r);
            }
        }
    }

    public ArrayList<Reason> getStoredReasons() {
        return storedReasons;
    }

    public FileConfiguration getCfg() {
        return cfg;
    }

    public File getFile() {
        return file;
    }
}
