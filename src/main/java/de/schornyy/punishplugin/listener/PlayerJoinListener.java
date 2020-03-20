package de.schornyy.punishplugin.listener;

import de.schornyy.punishplugin.PunishPlugin;
import de.schornyy.punishplugin.player.PunishPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private PunishPlugin plugin = PunishPlugin.getPlugin(PunishPlugin.class);

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();

        if(plugin.getPunishPlayerManager().getStoredPunishPlayer().get(p.getName()) == null) {
            PunishPlayer punishPlayer = new PunishPlayer(p.getName());
            plugin.getPunishPlayerManager().getStoredPunishPlayer().put(p.getName(), punishPlayer);
        }
    }

}
