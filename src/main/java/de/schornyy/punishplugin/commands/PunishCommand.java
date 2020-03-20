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

public class PunishCommand implements CommandExecutor {

    //punish <Player> <Reason>
    private PunishPlugin plugin = PunishPlugin.getPlugin(PunishPlugin.class);
    private MessagesConfig config = plugin.getPunishmentConfig().getMessagesConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;

        if(player.hasPermission("pa.punish")) {
            if(player.getName().equalsIgnoreCase(player.getName())) {
                if(Bukkit.getPlayer(player.getName()) != null) {
                    PunishPlayer targetPunishPlayer;
                    if(plugin.getPunishPlayerManager().getStoredPunishPlayer().get(args[0]) != null) {
                        targetPunishPlayer = plugin.getPunishPlayerManager().getStoredPunishPlayer().get(args[0]);
                    } else {
                        targetPunishPlayer = new PunishPlayer(args[0]);
                    }

                    if(targetPunishPlayer.isBanned() == false) {
                        for(Reason reason : plugin.getReasonManager().getStoredReasons()){
                            if(reason == null) break;

                        }
                    } else {
                        //PlayerisBanned
                    }


                } else {
                    //Spieler exestiert nicht
                }
            } else {
                //cannotPuinish u self
            }
        } else {
            player.sendMessage(config.noPermissions);
        }

        return false;
    }
}
