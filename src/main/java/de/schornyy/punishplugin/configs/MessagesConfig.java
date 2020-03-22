package de.schornyy.punishplugin.configs;

import de.schornyy.punishplugin.PunishPlugin;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessagesConfig {

    private File file;
    private FileConfiguration cfg;
    private Config config;
    private PunishPlugin plugin = PunishPlugin.getPlugin(PunishPlugin.class);
    private String path;

    public String prefix, noPermissions, canNotPunishUrSelf, playerDosntExists, playerIsBannend, reasonDosntExists
            , playerIsNotBanned, playerUnbanned, bannedPlayer, playerGotBanned, playerGetWarned, warnedPlayer, warnDosntExists
            , deleteWarn, playerMutedPlayer, playerGotMuted, playerIsAlreadyMuted, playerUnmutedPlayer, playerIsNotMuted, playerGotUnmuted
            , playerGotMutedNotification, playerGotBannedNotification, playerTryToChat;
    public List<String> banPlayer;
    public List<String> playerBannedbyMaxWarns;

    public MessagesConfig(Config config) {
        this.config = config;
        path = "Messages.";
        file = config.getFile();
        cfg = config.getCfg();
        load();
    }

    private void load(){
        if(!getCfg().isSet(path)) {

            getCfg().set(path + "prefix", "&cPunishment &f>> ");
            getCfg().set(path + "playerTryToChat", "&aDU wurdest gemutet von &e%player% &cfür &e%reason% &cdu kannst erst wieder am &e%date% &cschreiben!");
            getCfg().set(path + "playerUnmutedPlayer", "&aDu hast den Spieler &e%player% &aunmuted");
            getCfg().set(path + "playerGotBannedNotification", "&eDer Spieler &6%player% &ewurde gebannt von &6%punisher% &efür &6%reason%");
            getCfg().set(path + "playerGotMutedNotification", "&eDer Spieler &6%player% &ewurde gemutet von &6%punisher% &efür &6%reason%");
            getCfg().set(path + "playerIsNotMuted", "&aDer Spieler ist nicht gemutet");
            getCfg().set(path + "playerGotUnmuted", "&aDu wurdest unmuted");
            getCfg().set(path + "playerMutedPlayer", "&aDu hast &e%player% &agemuted für &e%reason%");
            getCfg().set(path + "playerGotMuted", "&cDu wurdest von &e%player% &cgemutet für &e%reason%");
            getCfg().set(path + "playerIsAlreadyMuted", "&cDer Spieler ist bereits gemutet!");
            getCfg().set(path + "deleteWarn", "&aDu hast die Warnung &6%warn% &aerfolgreich gelöscht!");
            getCfg().set(path + "playerGetWarned", "&aDu hast den Spieler &e%player% &agewarnt für &c%reason%");
            getCfg().set(path + "warnedPlayer", "&cDu wurdest von &6%player% &cgewarnt für &e%reason%");
            getCfg().set(path + "warnDosntExists", "&cDiese Warnung existiert nicht!");
            getCfg().set(path + "noPermissions", "&cDazu hast du keine Rechte!");
            getCfg().set(path + "bannedPlayer", "&cDu hast den Spieler gebannt!");
            getCfg().set(path + "playerIsNotBanned", "&cDer Spieler ist nicht gebannt!");
            getCfg().set(path + "playerUnbanned", "&aDu hast den Spieler erfolgreich entbannt!");
            getCfg().set(path + "canNotPunishUrSelf", "&cDu kannst dich nicht selber Bannen!");
            getCfg().set(path + "playerDosntExists", "&cDer Spieler existiert nicht!");
            getCfg().set(path + "playerIsBannend", "&cDer Spieler ist bereits gebannt!");
            getCfg().set(path + "reasonDosntExists", "&cDer angegebene Grund existiert nicht!");
            getCfg().set(path + "playerGotBanned", "&fDer Spieler &c%player% &fwurde gebannt!");

            banPlayer = new ArrayList<>();
            banPlayer.add("&cDu wurdest gebannt von &6%punisher%");
            banPlayer.add("&fGrund: &6%reason%");
            banPlayer.add("&fEntbannung: &6%date%");
            getCfg().set(path + "bannedPlayerMessage", banPlayer);

            playerBannedbyMaxWarns = new ArrayList<>();
            playerBannedbyMaxWarns.add("&cDu wurdest permanent gebannt!");
            playerBannedbyMaxWarns.add("&cDu hast die maximale anzahl an Warnungen erhalten!");
            getCfg().set(path + "playerBannedbyMaxWarns", playerBannedbyMaxWarns);

            try {
                getCfg().save(getFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        prefix = getCfg().getString(path + "prefix").replaceAll("&", "§");
        playerTryToChat = prefix + getCfg().getString(path + "playerTryToChat").replaceAll("&", "§");
        playerUnmutedPlayer = prefix + getCfg().getString(path + "playerUnmutedPlayer").replaceAll("&", "§");
        playerIsNotMuted = prefix + getCfg().getString(path + "playerIsNotMuted").replaceAll("&", "§");
        playerGotUnmuted = prefix + getCfg().getString(path + "playerGotUnmuted").replaceAll("&", "§");
        playerGotMutedNotification = prefix + getCfg().getString(path + "playerGotMutedNotification").replaceAll("&", "§");
        playerGotBannedNotification = prefix + getCfg().getString(path + "playerGotBannedNotification").replaceAll("&", "§");
        playerMutedPlayer = prefix + getCfg().getString(path + "playerMutedPlayer").replaceAll("&", "§");
        playerGotMuted = prefix + getCfg().getString(path + "playerGotMuted").replaceAll("&", "§");
        playerIsAlreadyMuted = prefix + getCfg().getString(path + "playerIsAlreadyMuted").replaceAll("&", "§");
        deleteWarn = prefix + getCfg().getString(path + "deleteWarn").replaceAll("&", "§");
        warnDosntExists = prefix + getCfg().getString(path + "warnDosntExists").replaceAll("&", "§");
        warnedPlayer = prefix + getCfg().getString(path + "warnedPlayer").replaceAll("&", "§");
        playerGetWarned = prefix + getCfg().getString(path + "playerGetWarned").replaceAll("&", "§");
        playerGotBanned = prefix + getCfg().getString(path + "playerGotBanned").replaceAll("&", "§");
        bannedPlayer = prefix + getCfg().getString(path + "bannedPlayer").replaceAll("&", "§");
        playerIsNotBanned = prefix + getCfg().getString(path + "playerIsNotBanned").replaceAll("&", "§");
        playerUnbanned = prefix + getCfg().getString(path + "playerUnbanned").replaceAll("&", "§");
        noPermissions = prefix + getCfg().getString(path + "noPermissions").replaceAll("&", "§");
        canNotPunishUrSelf = prefix + getCfg().getString(path + "canNotPunishUrSelf").replaceAll("&", "§");
        playerDosntExists = prefix + getCfg().getString(path + "playerDosntExists").replaceAll("&", "§");
        playerIsBannend = prefix + getCfg().getString(path + "playerIsBannend").replaceAll("&", "§");
        reasonDosntExists = prefix + getCfg().getString(path + "reasonDosntExists").replaceAll("&", "§");

        banPlayer = new ArrayList<>();
        for(String s : getCfg().getStringList(path + "bannedPlayerMessage")) {
            if(s == null) return;
            banPlayer.add(s.replaceAll("&", "§"));
        }

        playerBannedbyMaxWarns = new ArrayList<>();
        for(String s : getCfg().getStringList(path + "playerBannedbyMaxWarns")) {
            if(s == null) return;
            playerBannedbyMaxWarns.add(s.replaceAll("&", "§"));
        }

    }

    public FileConfiguration getCfg() {
        return cfg;
    }

    public File getFile() {
        return file;
    }
}
