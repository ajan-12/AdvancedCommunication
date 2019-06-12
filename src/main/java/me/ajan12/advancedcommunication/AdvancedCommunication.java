package me.ajan12.advancedcommunication;

import me.ajan12.advancedcommunication.Commands.MainCommand.MainCommand;
import me.ajan12.advancedcommunication.Commands.MessageCommand;
import me.ajan12.advancedcommunication.Listeners.PlayerChatEvent;
import me.ajan12.advancedcommunication.Listeners.PlayerJoinEvent;
import me.ajan12.advancedcommunication.Listeners.PlayerQuitEvent;
import me.ajan12.advancedcommunication.Utilities.DataStorage;

import org.bukkit.plugin.java.JavaPlugin;

public final class AdvancedCommunication extends JavaPlugin {
    //The field to get the inner methods of this class from other classes.
    private static AdvancedCommunication instance;

    @Override
    public void onEnable() {
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
