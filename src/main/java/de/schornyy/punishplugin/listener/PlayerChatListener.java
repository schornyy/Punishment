package de.schornyy.punishplugin.listener;

import de.schornyy.punishplugin.PunishPlugin;
import de.schornyy.punishplugin.configs.MessagesConfig;
import de.schornyy.punishplugin.player.PunishPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    private PunishPlugin plugin = PunishPlugin.getPlugin(PunishPlugin.class);
    private MessagesConfig config = plugin.getPunishmentConfig().getMessagesConfig();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        PunishPlayer punishPlayer = plugin.getPunishPlayerManager().getStoredPunishPlayer().get(p.getName());

        if(punishPlayer.isMuted()) {
            if(plugin.getPunishPlayerManager().isUnmuted(punishPlayer)) {
                plugin.getPunishPlayerManager().unMutePlayer(punishPlayer);
            }else {
                e.setCancelled(true);
                p.sendMessage(config.playerTryToChat
                        .replaceAll("%player%", punishPlayer.getMutedFrom())
                        .replaceAll("%reason%", punishPlayer.getMutedReason())
                        .replaceAll("%date%", punishPlayer.getUnMutedDate()));
            }
        }



    }

}
