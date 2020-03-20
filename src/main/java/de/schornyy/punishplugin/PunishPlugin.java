package de.schornyy.punishplugin;

import de.schornyy.punishplugin.commands.PunishCommand;
import de.schornyy.punishplugin.configs.Config;
import de.schornyy.punishplugin.listener.PlayerJoinListener;
import de.schornyy.punishplugin.listener.PlayerQuitListener;
import de.schornyy.punishplugin.player.PunishPlayerManager;
import de.schornyy.punishplugin.punishment.ReasonManager;
import de.schornyy.punishplugin.utils.Formatting;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class PunishPlugin extends JavaPlugin {

    private Config punishmentConfig;
    private ReasonManager reasonManager;
    private PunishPlayerManager punishPlayerManager;

    @Override
    public void onEnable() {
        loadInits();
        loadCommands();
        loadListener();

        Bukkit.getConsoleSender().sendMessage(getPunishmentConfig().getMessagesConfig().prefix + "§awurde geladen!");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(getPunishmentConfig().getMessagesConfig().prefix + "§cwurde deaktiviert!");
    }

    private void loadListener() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
    }

    private void loadCommands() {
        getCommand("punish").setExecutor(new PunishCommand());
    }

    private void loadInits() {
        punishmentConfig = new Config();
        reasonManager = new ReasonManager();
        punishPlayerManager = new PunishPlayerManager();
    }

    public Config getPunishmentConfig() {
        return punishmentConfig;
    }

    public PunishPlayerManager getPunishPlayerManager() {
        return punishPlayerManager;
    }

    public ReasonManager getReasonManager() {
        return reasonManager;
    }
}
