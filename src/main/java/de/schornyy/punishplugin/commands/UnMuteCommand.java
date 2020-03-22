package de.schornyy.punishplugin.commands;

import de.schornyy.punishplugin.PunishPlugin;
import de.schornyy.punishplugin.configs.MessagesConfig;
import de.schornyy.punishplugin.player.PunishPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnMuteCommand implements CommandExecutor {

    private PunishPlugin plugin = PunishPlugin.getPlugin(PunishPlugin.class);
    private MessagesConfig config = plugin.getPunishmentConfig().getMessagesConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;

        if(args.length == 1) {
            if(player.hasPermission("pa.unmute")) {
                if(Bukkit.getPlayer(args[0]) != null || plugin.getPunishPlayerManager().getStoredPunishPlayer().get(args[0]) != null) {
                    PunishPlayer targetPunishPlayer = plugin.getPunishPlayerManager().getStoredPunishPlayer().get(args[0]);

                    if(targetPunishPlayer.isMuted()) {
                        Player target = Bukkit.getPlayer(args[0]);
                        plugin.getPunishPlayerManager().unMutePlayer(targetPunishPlayer);
                        //playerUnmutetPlayer
                        player.sendMessage(config.playerUnmutedPlayer.replaceAll("%player%", args[0]));
                        //playerGotUnMuted
                        target.sendMessage(config.playerGotUnmuted);
                    }else {
                        player.sendMessage(config.playerIsNotMuted);
                    }

                } else {
                    player.sendMessage(config.playerDosntExists);
                }
            } else {
                player.sendMessage(config.noPermissions);
            }
        }

        return false;
    }
}
