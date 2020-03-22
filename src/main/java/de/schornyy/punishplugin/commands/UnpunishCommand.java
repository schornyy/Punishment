package de.schornyy.punishplugin.commands;

import de.schornyy.punishplugin.PunishPlugin;
import de.schornyy.punishplugin.configs.MessagesConfig;
import de.schornyy.punishplugin.player.PunishPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class UnpunishCommand implements CommandExecutor {

    private PunishPlugin plugin = PunishPlugin.getPlugin(PunishPlugin.class);
    private MessagesConfig config = plugin.getPunishmentConfig().getMessagesConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;

        if(args.length == 1) {
            if(player.hasPermission("pa.unpanish")) {
                    if(plugin.getPunishPlayerManager().playerExistsAtDatabase(args[0])) {
                        PunishPlayer targetPunishPlayer = null;
                        if(plugin.getPunishPlayerManager().getStoredPunishPlayer().get(args[0]) == null) {
                            targetPunishPlayer = new PunishPlayer(args[0]);
                        } else {
                            targetPunishPlayer = plugin.getPunishPlayerManager().getStoredPunishPlayer().get(args[0]);
                        }

                        if(targetPunishPlayer.isBanned()) {
                            plugin.getPunishPlayerManager().unban(targetPunishPlayer);
                            player.sendMessage(config.playerUnbanned);
                        } else {
                            player.sendMessage(config.playerIsNotBanned);
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
