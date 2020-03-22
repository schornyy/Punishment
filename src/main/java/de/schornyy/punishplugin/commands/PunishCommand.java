package de.schornyy.punishplugin.commands;

import de.schornyy.punishplugin.PunishPlugin;
import de.schornyy.punishplugin.configs.MessagesConfig;
import de.schornyy.punishplugin.player.PunishPlayer;
import de.schornyy.punishplugin.punishment.Reason;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PunishCommand implements CommandExecutor, TabCompleter {

    //punish <Player> <Reason>
    private PunishPlugin plugin = PunishPlugin.getPlugin(PunishPlugin.class);
    private MessagesConfig config = plugin.getPunishmentConfig().getMessagesConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;

        if(args.length == 2) {
            if(player.hasPermission("pa.punish")) {
                if(player.getName().equalsIgnoreCase(player.getName())) {
                    if(Bukkit.getPlayer(player.getName()) != null) {
                        PunishPlayer targetPunishPlayer;
                        if(plugin.getPunishPlayerManager().getStoredPunishPlayer().get(args[0]) != null) {
                            targetPunishPlayer = plugin.getPunishPlayerManager().getStoredPunishPlayer().get(args[0]);
                        } else {
                            targetPunishPlayer = new PunishPlayer(args[0]);
                        }
                        Reason r = null;
                        if(targetPunishPlayer.isBanned() == false) {
                            for(Reason reason : plugin.getReasonManager().getStoredReasons()){
                                if(reason == null) break;
                                if(reason.getReason().equalsIgnoreCase(args[1])) {
                                    r = reason;
                                }
                            }

                            if(r != null) {
                                plugin.getPunishPlayerManager().banPlayer(targetPunishPlayer, r, player.getName());
                                targetPunishPlayer.save();
                                player.sendMessage(config.bannedPlayer);
                                Bukkit.getOnlinePlayers().forEach(all -> {
                                    if(all.hasPermission("pa.bannNotification")) {
                                        all.sendMessage(config.playerGotBannedNotification
                                                .replaceAll("%player%", args[0])
                                                .replaceAll("%reason%", args[1])
                                                .replaceAll("%punisher%", player.getName()));
                                    }
                                });
                            } else {
                                player.sendMessage(config.reasonDosntExists);
                            }
                        } else {
                            player.sendMessage(config.playerIsBannend);
                        }
                    } else {
                       player.sendMessage(config.playerDosntExists);
                    }
                } else {
                    player.sendMessage(config.canNotPunishUrSelf);
                }
            } else {
                player.sendMessage(config.noPermissions);
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        Player p = (Player)sender;
        List<String> tabs = new ArrayList<>();

        if(p.hasPermission("pa.punish")) {
            switch (args.length) {
                case 1:
                    plugin.getPunishPlayerManager().getStoredPunishPlayer().values().forEach(all -> {
                        tabs.add(all.getPlayer());
                    });
                    break;
                case 2:
                    plugin.getReasonManager().getStoredReasons().forEach(r -> {
                        tabs.add(r.getReason());
                    });
                    break;
            }
        }


        return tabs;
    }
}
