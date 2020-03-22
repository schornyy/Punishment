package de.schornyy.punishplugin.listener;

import de.schornyy.punishplugin.PunishPlugin;
import de.schornyy.punishplugin.player.PunishPlayer;
import de.schornyy.punishplugin.utils.Formatting;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private PunishPlugin plugin = PunishPlugin.getPlugin(PunishPlugin.class);

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        PunishPlayer punishPlayer = null;
        if(plugin.getPunishPlayerManager().getStoredPunishPlayer().get(p.getName()) == null) {
             punishPlayer = new PunishPlayer(p.getName());
            plugin.getPunishPlayerManager().getStoredPunishPlayer().put(p.getName(), punishPlayer);
        }
        punishPlayer = plugin.getPunishPlayerManager().getStoredPunishPlayer().get(p.getName());

        if(punishPlayer.isBanned()) {
            if(plugin.getPunishPlayerManager().isUnbannend(punishPlayer)) {
                punishPlayer.setBanned(false);
                punishPlayer.setBanReason(null);
                punishPlayer.setUnBannedDate(null);
                punishPlayer.setBannedFrom(null);
                punishPlayer.save();
            } else {
                if(punishPlayer.getCfg().isSet("Banned.")) {
                    String kickMessage = "";
                    for (String s : plugin.getPunishmentConfig().getMessagesConfig().banPlayer) {
                        if (s.contains("%punisher%")) {
                            kickMessage = kickMessage + s.replaceAll("%punisher%", punishPlayer.getBannedFrom()) + "\n";
                        } else if (s.contains("%reason%")) {
                            kickMessage = kickMessage + s.replaceAll("%reason%", punishPlayer.getBanReason()) + "\n";
                        } else if (s.contains("%date%")) {
                            kickMessage = kickMessage + s.replaceAll("%date%", punishPlayer.getUnBannedDate()) + "\n";
                        } else {
                            kickMessage = kickMessage + s + "\n";
                        }
                    }
                    p.kickPlayer(kickMessage);
                } else {
                    String kickMessage = "";
                    for(String s : plugin.getPunishmentConfig().getMessagesConfig().playerBannedbyMaxWarns) {
                        if(s == null) return;
                        kickMessage = kickMessage + s + "\n";
                    }
                    Player player = Bukkit.getPlayer(punishPlayer.getPlayer());
                    if(player.isOnline()) {
                        player.kickPlayer(kickMessage);
                    }
                }
            }
            e.setJoinMessage(null);
        }

    }

}
