package de.schornyy.punishplugin.player;

import de.schornyy.punishplugin.PunishPlugin;
import de.schornyy.punishplugin.configs.MessagesConfig;
import de.schornyy.punishplugin.punishment.Reason;
import de.schornyy.punishplugin.utils.Formatting;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PunishPlayerManager {

    private HashMap<String, PunishPlayer> storedPunishPlayer;
    private PunishPlugin plugin = PunishPlugin.getPlugin(PunishPlugin.class);
    private MessagesConfig config = plugin.getPunishmentConfig().getMessagesConfig();

    public PunishPlayerManager() {
        storedPunishPlayer = new HashMap<>();
    }

    public void banPlayer(PunishPlayer punishPlayer, Reason reason, String punisher) {
        punishPlayer.setBanned(true);
        punishPlayer.setBannedFrom(punisher);
        punishPlayer.setBannedDate(Formatting.getFormattetDateTime());
        punishPlayer.setUnBannedDate(Formatting.realeaseDate(reason));
        punishPlayer.save();

        String kickMessage = "";
        for(String s : config.banPlayer) {
            if(s.contains("%punisher%")) {
                kickMessage += s.replaceAll("%punisher%", punisher) + "\n";
            }
            if(s.contains("%reason%")) {
                kickMessage += s.replaceAll("%reason%", reason.getReason()) + "\n";
            }
            if(s.contains("%date%")) {
                kickMessage += s.replaceAll("%date%", Formatting.realeaseDate(reason)) + "\n";
            }
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
