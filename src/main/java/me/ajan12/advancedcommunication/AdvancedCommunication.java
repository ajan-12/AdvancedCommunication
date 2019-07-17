package me.ajan12.advancedcommunication;

import me.ajan12.advancedcommunication.Commands.MainCommand.MainCommand;
import me.ajan12.advancedcommunication.Commands.MessageCommand.MessageCommand;
import me.ajan12.advancedcommunication.Listeners.PlayerChatEvent;
import me.ajan12.advancedcommunication.Listeners.PlayerJoinEvent;
import me.ajan12.advancedcommunication.Listeners.PlayerQuitEvent;
import me.ajan12.advancedcommunication.Listeners.ProtocolLibListeners.TabCompleteEvent;
import me.ajan12.advancedcommunication.Utilities.DataStorage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class AdvancedCommunication extends JavaPlugin {

    //The field to get the inner methods of this class from other classes.
    private static AdvancedCommunication instance;

    @Override
    public void onEnable() {

        //Checking if the server has ProtocolLib
        if (getServer().getPluginManager().getPlugin("ProtocolLib") == null || !getServer().getPluginManager().getPlugin("ProtocolLib").isEnabled()) {

            //Sending a message to console indicating that this plugin needs ProtocolLib.
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "The plugin " + ChatColor.YELLOW + "ProtocolLib" + ChatColor.DARK_RED + " is needed for this plugin!");
            //Sending a message to console indication that this plugin isn't being enabled.
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Disabling this plugin.");

            //Aborting the plugin initialization.
            return;
        }

        //Setting-up the plugin instance.
        instance = this;
        //Setting-up the DataStorage.
        DataStorage.setup();

        //Registering all the event listeners.
        getServer().getPluginManager().registerEvents(new PlayerChatEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitEvent(), this);

        //Registering the commands.
        getCommand("message").setExecutor(new MessageCommand());
        getCommand("advancedcommunication").setExecutor(new MainCommand());

        //Registering packet listeners.
        TabCompleteEvent.register();
    }

    @Override
    public void onDisable() {
        //Purging DataStorage to remove any leftovers.
        DataStorage.purge();
        //Removing instance to prevent data corruption and memory leak.
        instance = null;
    }

    //The Getter for the plugin instance.
    public static AdvancedCommunication getInstance() { return instance; }
}
