package de.schornyy.punishplugin.player;

import de.schornyy.punishplugin.PunishPlugin;
import de.schornyy.punishplugin.configs.MessagesConfig;
import de.schornyy.punishplugin.punishment.Reason;
import de.schornyy.punishplugin.utils.Formatting;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class PunishPlayerManager {

    private HashMap<String, PunishPlayer> storedPunishPlayer;
    private PunishPlugin plugin = PunishPlugin.getPlugin(PunishPlugin.class);
    private MessagesConfig config = plugin.getPunishmentConfig().getMessagesConfig();

    public PunishPlayerManager() {
        storedPunishPlayer = new HashMap<>();
    }

    public boolean playerExistsAtDatabase(String player) {
        boolean exists = false;
        File file = new File(plugin.getDataFolder() + "/Player/");

        if(file.exists()) {
            for (File files : file.listFiles()) {
                if (files == null) break;
                if (files.getName().replaceAll(".yml", "").equalsIgnoreCase(player)) {
                    exists = true;
                }
            }
        }

        return exists;
    }

    public void saveAllPlayerDatas() {
        if(getStoredPunishPlayer().size() != 0) {
            for(PunishPlayer all : getStoredPunishPlayer().values()) {
                if(all == null) return;
                all.save();
            }
        }
     }

    public void loadAllPlayerDatas() {
        File file = new File(plugin.getDataFolder() + "/Player/");

        if(file.exists()) {
            if(file.listFiles() != null) {
                for(File files : file.listFiles()) {
                    if(files == null) return;
                    String playerName = files.getName().replaceAll(".yml", "");
                    PunishPlayer punishPlayer = new PunishPlayer(playerName);
                    storedPunishPlayer.put(playerName, punishPlayer);
                }
            }
        }
    }

    public void unban(PunishPlayer punishPlayer) {
        punishPlayer.setBanned(false);
        if(punishPlayer.getBanReason() != null) {
            punishPlayer.setBannedFrom(null);
            punishPlayer.setBanReason(null);
            punishPlayer.setBannedDate(null);
        }
        punishPlayer.setWarns(new ArrayList<>());
        punishPlayer.save();
    }

    public boolean isUnbannend(PunishPlayer punishPlayer) {
        boolean isUnbannend = false;
        if(punishPlayer.getUnBannedDate() != null) {
            String unBannedDate = punishPlayer.getUnBannedDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
            LocalDateTime unBannDateTime =  LocalDateTime.parse(unBannedDate, formatter);
            LocalDateTime now = LocalDateTime.now();

            if(now.isAfter(unBannDateTime)) {
                isUnbannend = true;
            }
        }

        return isUnbannend;
    }

    public boolean isUnmuted(PunishPlayer punishPlayer) {
        boolean isUnmuted = false;
        if(punishPlayer.getUnBannedDate() != null) {
            String unMutedDate = punishPlayer.getUnMutedDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
            LocalDateTime unBannDateTime =  LocalDateTime.parse(unMutedDate, formatter);
            LocalDateTime now = LocalDateTime.now();

            if(now.isAfter(unBannDateTime)) {
                isUnmuted = true;
            }
        }

        return isUnmuted;
    }

    public void mutePlayer(PunishPlayer punishPlayer, Reason reason, String punisher) {
        punishPlayer.setMuted(true);
        punishPlayer.setMutedReason(reason.getReason());
        punishPlayer.setMutedFrom(punisher);
        punishPlayer.setMutedDate(Formatting.getFormattetDateTime());
        punishPlayer.setUnMutedDate(Formatting.realeaseDate(reason));
        punishPlayer.save();
    }

    public void unMutePlayer(PunishPlayer player) {
        player.setMuted(false);
        player.setMutedReason(null);
        player.setMutedFrom(null);
        player.setMutedDate(null);
        player.setUnMutedDate(null);
        player.save();
    }

    public void banPlayer(PunishPlayer punishPlayer, Reason reason, String punisher) {
        punishPlayer.setBanned(true);
        punishPlayer.setBanReason(reason.getReason());
        punishPlayer.setBannedFrom(punisher);
        punishPlayer.setBannedDate(Formatting.getFormattetDateTime());
        punishPlayer.setUnBannedDate(Formatting.realeaseDate(reason));
        punishPlayer.save();

        String kickMessage = "";
        for(String s : config.banPlayer) {
            if(s.contains("%punisher%")) {
                kickMessage =  kickMessage + s.replaceAll("%punisher%", punisher) + "\n";
            }else if(s.contains("%reason%")) {
                kickMessage =  kickMessage + s.replaceAll("%reason%", reason.getReason()) + "\n";
            }else if(s.contains("%date%")) {
                kickMessage = kickMessage + s.replaceAll("%date%", Formatting.realeaseDate(reason)) + "\n";
            } else {
                kickMessage = kickMessage + s;
            }
        }
        Player player = Bukkit.getPlayer(punishPlayer.getPlayer());
        if(player.isOnline()) {
            player.kickPlayer(kickMessage);
        }
    }

    public void permaBanPlayer(PunishPlayer punishPlayer) {
        punishPlayer.setBanned(true);
        punishPlayer.save();
        String kickMessage = "";
        for(String s : config.playerBannedbyMaxWarns) {
           if(s == null) return;
           kickMessage = kickMessage + s + "\n";
        }
        Player player = Bukkit.getPlayer(punishPlayer.getPlayer());
        if(player.isOnline()) {
            player.kickPlayer(kickMessage);
        }
    }

    public HashMap<String, PunishPlayer> getStoredPunishPlayer() {
        return storedPunishPlayer;
    }
}
