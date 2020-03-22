package de.schornyy.punishplugin.punishment;

import de.schornyy.punishplugin.player.PunishPlayer;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class Warn {

    private String reason, warnfrom, warnDate;

    public Warn(String reason, String warnfrom, String warnDate) {
        this.reason = reason;
        this.warnfrom = warnfrom;
        this.warnDate = warnDate;
    }

    public String getReason() {
        return reason;
    }

    public String getWarnfrom() {
        return warnfrom;
    }

    public String getWarnDate() {
        return warnDate;
    }
}
