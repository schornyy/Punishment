package de.schornyy.punishplugin.player;

import de.schornyy.punishplugin.PunishPlugin;
import de.schornyy.punishplugin.punishment.Reason;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class PunishPlayer {

    private File file;
    private FileConfiguration cfg;
    private boolean muted;
    private boolean banned;
    private String unBannedDate, banReason, bannedDate, mutedDate, mutedFrom, bannedFrom, mutedReason, unMutedDate;
    private String player;
    private PunishPlugin plugin = PunishPlugin.getPlugin(PunishPlugin.class);

    public PunishPlayer(String playerName) {
        player = playerName;
        file = new File(plugin.getDataFolder() + "/Player/" + playerName + ".yml");
        cfg = YamlConfiguration.loadConfiguration(file);
        load();
    }

    public void save() {

        getCfg().set("Banned", isBanned());
        getCfg().set("Muted", isMuted());

        if(isMuted()) {
            getCfg().set("Muted.MutedDate", getMutedDate());
            getCfg().set("Muted.Reason", getMutedReason());
            getCfg().set("Muted.From", getMutedFrom());
            getCfg().set("Muted.UnMutedDate", getUnBannedDate());
        }
        if(isBanned()) {
            getCfg().set("Banned.BannaedDate", getBannedDate());
            getCfg().set("Banned.UnbannedDate", getUnBannedDate());
            getCfg().set("Banned.From", getBannedFrom());
            getCfg().set("Banned.Reason", getBanReason());
        }

        try {
            getCfg().save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load(){
        if(!getFile().exists()) {

            setBanned(false);
            setMuted(false);

            try {
                getCfg().save(getFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        setBanned(getCfg().getBoolean("Banned"));
        setMuted(getCfg().getBoolean("Muted"));

        if(isBanned()) {
            setBannedDate(getCfg().getString("Banned.BannedDate"));
            setUnBannedDate(getCfg().getString("Banned.UnbannedDate"));
            setBannedFrom(getCfg().getString("Banned.From"));
            setBanReason(getCfg().getString("Banned.Reason"));
        }

        if(isMuted()) {
            setMutedDate(getCfg().getString("Muted.MutedDate"));
            setMutedFrom(getCfg().getString("Muted.From"));
            setMutedReason(getCfg().getString("Muted.Reason"));
            setUnMutedDate(getCfg().getString("Muted.UnMutedDate"));
        }

    }

    public String getUnMutedDate() {
        return unMutedDate;
    }

    public void setUnMutedDate(String unMutedDate) {
        this.unMutedDate = unMutedDate;
    }

    public void setBannedFrom(String bannedFrom) {
        this.bannedFrom = bannedFrom;
    }

    public void setBanReason(String banReason) {
        this.banReason = banReason;
    }

    public void setMutedDate(String mutedDate) {
        this.mutedDate = mutedDate;
    }

    public void setMutedFrom(String mutedFrom) {
        this.mutedFrom = mutedFrom;
    }

    public void setMutedReason(String mutedReason) {
        this.mutedReason = mutedReason;
    }

    public boolean isBanned() {
        return banned;
    }

    public boolean isMuted() {
        return muted;
    }

    public String getBannedFrom() {
        return bannedFrom;
    }

    public String getBanReason() {
        return banReason;
    }

    public String getMutedDate() {
        return mutedDate;
    }

    public String getMutedFrom() {
        return mutedFrom;
    }

    public String getMutedReason() {
        return mutedReason;
    }

    public String getBannedDate() {
        return bannedDate;
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getCfg() {
        return cfg;
    }

    public String getPlayer() {
        return player;
    }

    public String getUnBannedDate() {
        return unBannedDate;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public void setBannedDate(String bannedDate) {
        this.bannedDate = bannedDate;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public void setUnBannedDate(String unBannedDate) {
        this.unBannedDate = unBannedDate;
    }
}
