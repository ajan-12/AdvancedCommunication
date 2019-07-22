package me.ajan12.advancedcommunication;

import me.ajan12.advancedcommunication.Commands.GroupCommand.GroupCommand;
import me.ajan12.advancedcommunication.Commands.MainCommand.MainCommand;
import me.ajan12.advancedcommunication.Commands.MessageCommand.MessageCommand;
import me.ajan12.advancedcommunication.Listeners.PlayerChatEvent;
import me.ajan12.advancedcommunication.Listeners.PlayerJoinEvent;
import me.ajan12.advancedcommunication.Listeners.ProtocolLibEvents;
import me.ajan12.advancedcommunication.Utilities.DataStorage;
import me.ajan12.advancedcommunication.Utilities.DatabaseUtils.SQLiteUtils;
import me.ajan12.advancedcommunication.Utilities.GroupUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class AdvancedCommunication extends JavaPlugin {

    //The field to get the inner methods of this class from other classes.
    private static AdvancedCommunication instance;

    @Override
    public void onEnable() {

        //Checking if the server has ProtocolLib
        if (getServer().getPluginManager().getPlugin("ProtocolLib") == null || !getServer().getPluginManager().getPlugin("ProtocolLib").isEnabled()) {

            //Sending a message to console indicating that this plugin needs ProtocolLib.
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[" + ChatColor.RED + "AC" + ChatColor.AQUA + "]" + ChatColor.DARK_RED + " The plugin " + ChatColor.YELLOW + "ProtocolLib" + ChatColor.DARK_RED + " is needed for this plugin!");
            //Sending a message to console indication that this plugin isn't being enabled.
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[" + ChatColor.RED + "AC" + ChatColor.AQUA + "]" + ChatColor.DARK_RED + " Disabling this plugin.");

            //Aborting the plugin initialization.
            return;
        }

        //Checking if the server has Vault
        if (getServer().getPluginManager().getPlugin("Vault") == null || !getServer().getPluginManager().getPlugin("Vault").isEnabled()) {

            //Sending a message to console indicating that this plugin needs Vault.
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[" + ChatColor.RED + "AC" + ChatColor.AQUA + "]" + ChatColor.DARK_RED + " The plugin " + ChatColor.YELLOW + "Vault" + ChatColor.DARK_RED + " is needed for this plugin!");
            //Sending a message to console indication that this plugin isn't being enabled.
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[" + ChatColor.RED + "AC" + ChatColor.AQUA + "]" + ChatColor.DARK_RED + " Disabling this plugin.");

            //Aborting the plugin initialization.
            return;
        }

        //Setting-up the plugin instance.
        instance = this;
        //Saving the config file.
        saveDefaultConfig();
        //Setting-up the DataStorage.
        DataStorage.setup();
        //Setting-up the SQLiteUtils.
        SQLiteUtils.setup(getDataFolder().getAbsolutePath() + File.separator + "AdvancedCommunication.db");

        //Registering all the event listeners.
        getServer().getPluginManager().registerEvents(new PlayerChatEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinEvent(), this);

        //Registering the commands.
        getCommand("message").setExecutor(new MessageCommand());
        getCommand("group").setExecutor(new GroupCommand());
        getCommand("advancedcommunication").setExecutor(new MainCommand());

        //Registering packet listeners.
        ProtocolLibEvents.registerListeners();

        //Importing the groups.
        SQLiteUtils.importData();

        //Purging the groups.
        GroupUtils.purgeGroups();
    }

    @Override
    public void onDisable() {

        //Saving the groups.
        GroupUtils.saveGroups();

        //Purging SQLiteUtils to remove any leftovers.
        SQLiteUtils.purgeLocalCache();

        //Purging DataStorage to remove any leftovers.
        DataStorage.purge();

        //Removing instance to prevent data corruption and memory leak.
        instance = null;

    }

    //The Getter for the plugin instance.
    public static AdvancedCommunication getInstance() { return instance; }
}
