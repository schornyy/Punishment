package de.schornyy.punishplugin.commands;

import de.schornyy.punishplugin.PunishPlugin;
import de.schornyy.punishplugin.configs.MessagesConfig;
import de.schornyy.punishplugin.player.PunishPlayer;
import de.schornyy.punishplugin.punishment.Warn;
import de.schornyy.punishplugin.utils.Formatting;
import de.schornyy.punishplugin.utils.MessageBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarnCommand implements CommandExecutor {

    private PunishPlugin plugin =PunishPlugin.getPlugin(PunishPlugin.class);
    private MessagesConfig config = plugin.getPunishmentConfig().getMessagesConfig();

    /*
    warn <player> <grund> - X
    warn list | <player> - X
    warn delete <player> <index>
     */

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;
        PunishPlayer punishPlayer = plugin.getPunishPlayerManager().getStoredPunishPlayer().get(player.getName());

        switch (args.length) {
            case 1:
                if(args[0].equalsIgnoreCase("list")) {
                    if(player.hasPermission("pa.list")) {
                        int i = 1;
                        for (Warn warn : punishPlayer.getWarns()) {
                            if(warn == null) break;
                            TextComponent msg = new MessageBuilder("§fWarn §e" + i + "§f: ").getTextComponent();
                            TextComponent info = new MessageBuilder("§f[§eInfo§f]").addHover("§fGrund: §6" + warn.getReason()
                                    + "\n"
                                    + "§fVon: §6" +  warn.getWarnfrom()
                                    + "\n"
                                    + "§fDatum: §6" + warn.getWarnDate()).getTextComponent();
                            i++;
                            player.spigot().sendMessage(msg, info);
                        }
                    } else {
                        player.sendMessage(config.noPermissions);
                    }
                }
            break;
            case 2:
                if(args[0].equalsIgnoreCase("list")) {
                    if(player.hasPermission("pa.list.other")) {
                        if(Bukkit.getPlayer(args[1]) != null) {
                            if(plugin.getPunishPlayerManager().getStoredPunishPlayer().get(args[1]) != null) {
                                PunishPlayer tagretPunishPlayer = plugin.getPunishPlayerManager().getStoredPunishPlayer().get(args[1]);

                                int i = 1;
                                player.sendMessage(config.prefix + "§6" + args[1] + " §fWarns:");
                                for (Warn warn : tagretPunishPlayer.getWarns()) {
                                    if(warn == null) break;
                                    TextComponent msg = new MessageBuilder("§fWarn §e" + i + "§f: ").getTextComponent();
                                    TextComponent info = new MessageBuilder("§f[§eInfo§f]").addHover("§fGrund: §6" + warn.getReason()
                                            + "\n"
                                            + "§fVon: §6" +  warn.getWarnfrom()
                                            + "\n"
                                            + "§fDatum: §6" + warn.getWarnDate()).getTextComponent();
                                    TextComponent delete = new MessageBuilder("§§f[§cDelete§f]")
                                            .addHover("§fKlicken um zu löschen")
                                            .addClick(ClickEvent.Action.RUN_COMMAND, "/warn delete " + tagretPunishPlayer.getPlayer() + " " + (i - 1))
                                            .getTextComponent();
                                    i++;
                                    player.spigot().sendMessage(msg, info, delete);
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
                } else {
                    if(player.hasPermission("pa.warn")) {
                        if(Bukkit.getPlayer(args[0]) != null) {
                            if(plugin.getPunishPlayerManager().getStoredPunishPlayer().get(args[0]) != null) {
                                PunishPlayer targetPunishPlayer = plugin.getPunishPlayerManager().getStoredPunishPlayer().get(args[0]);
                                Player target = Bukkit.getPlayer(targetPunishPlayer.getPlayer());
                                target.sendMessage(config.warnedPlayer.replaceAll("%player%", player.getName()).replaceAll("%reason%", args[1]));
                                player.sendMessage(config.playerGetWarned.replaceAll("%player%", player.getName()).replaceAll("%reason%", args[1]));

                                targetPunishPlayer.getWarns().add(new Warn(args[1], player.getName(), Formatting.getFormattetDateTime()));

                                if(targetPunishPlayer.getWarns().size() >= plugin.getPunishmentConfig().getSettingsConfig().maxWarns) {
                                    plugin.getPunishPlayerManager().permaBanPlayer(targetPunishPlayer);
                                }
                            } else {
                                player.sendMessage(config.playerDosntExists);
                            }
                        } else {
                            player.sendMessage(config.playerDosntExists);
                        }
                    }
                }

                break;
            case 3:
                if(args[0].equalsIgnoreCase("delete")) {
                    if(player.hasPermission("pa.warn.delete")) {
                        if(Bukkit.getPlayer(args[1]) != null) {
                            if(plugin.getPunishPlayerManager().getStoredPunishPlayer().get(args[1]) != null) {
                                int i = Integer.valueOf(args[2]);
                                PunishPlayer targetPunishPlayer = plugin.getPunishPlayerManager().getStoredPunishPlayer().get(args[1]);
                                if(targetPunishPlayer.getWarns().get(i) != null) {
                                    targetPunishPlayer.getWarns().remove(i);
                                    player.sendMessage(config.deleteWarn.replaceAll("%warn%", "" +(i +1)));
                                } else {
                                    player.sendMessage(config.warnDosntExists);
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
                break;
        }

        return false;
    }
}
