package de.schornyy.punishplugin.listener;

import de.schornyy.punishplugin.PunishPlugin;
import de.schornyy.punishplugin.player.PunishPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private PunishPlugin plugin = PunishPlugin.getPlugin(PunishPlugin.class);

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        PunishPlayer punishPlayer = plugin.getPunishPlayerManager().getStoredPunishPlayer().get(p.getName());
        punishPlayer.save();

        if(punishPlayer.isBanned()) {
            e.setQuitMessage(null);
        }
    }
}
