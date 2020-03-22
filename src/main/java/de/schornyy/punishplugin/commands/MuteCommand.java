package de.schornyy.punishplugin.commands;

import de.schornyy.punishplugin.PunishPlugin;
import de.schornyy.punishplugin.configs.MessagesConfig;
import de.schornyy.punishplugin.player.PunishPlayer;
import de.schornyy.punishplugin.punishment.Reason;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MuteCommand implements CommandExecutor {

    private PunishPlugin plugin = PunishPlugin.getPlugin(PunishPlugin.class);
    private MessagesConfig config = plugin.getPunishmentConfig().getMessagesConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;

        if(args.length == 2) {
            if(player.hasPermission("pa.mute")) {
                if(Bukkit.getPlayer(args[0]) != null) {
                    if(plugin.getPunishPlayerManager().getStoredPunishPlayer().get(args[0]) != null) {
                        PunishPlayer targetPunishPlayer = plugin.getPunishPlayerManager().getStoredPunishPlayer().get(args[0]);
                        if(plugin.getReasonManager().raesonExists(args[1])) {
                            if(targetPunishPlayer.isMuted() == false){
                                Reason reason = plugin.getReasonManager().getReasonByName(args[1]);
                                Player target = Bukkit.getPlayer(args[0]);
                                plugin.getPunishPlayerManager().mutePlayer(targetPunishPlayer, reason, player.getName());
                                player.sendMessage(config.playerMutedPlayer.replaceAll("%player%", args[0]).replaceAll("%reason%", args[1]));
                                target.sendMessage(config.playerGotMuted.replaceAll("%player%", player.getName()).replaceAll("%reason%", args[1]));

                                Bukkit.getOnlinePlayers().forEach(all -> {
                                    if(all.hasPermission("pa.mutenotification")) {
                                        all.sendMessage(config.playerGotMutedNotification
                                                .replaceAll("%player%", args[0])
                                                .replaceAll("%reason%", args[1])
                                                .replaceAll("%punisher%", player.getName()));
                                    }
                                });

                            } else {
                                player.sendMessage(config.playerIsAlreadyMuted);
                            }
                        } else {
                         player.sendMessage(config.reasonDosntExists);
                        }
                    } else {
                        player.sendMessage(config.playerDosntExists);
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
