package me.ajan12.advancedcommunication;

import me.ajan12.advancedcommunication.Commands.MessageCommand;
import me.ajan12.advancedcommunication.Listeners.PlayerChatEvent;
import me.ajan12.advancedcommunication.Listeners.PlayerJoinEvent;
import me.ajan12.advancedcommunication.Listeners.PlayerQuitEvent;
import me.ajan12.advancedcommunication.Utilities.DataStorage;

import org.bukkit.plugin.java.JavaPlugin;

public final class AdvancedCommunication extends JavaPlugin {

    @Override
    public void onEnable() {
        //Setting-up the DataStorage
        DataStorage.setup();

        //Registering all the event listeners
        getServer().getPluginManager().registerEvents(new PlayerChatEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitEvent(), this);

        //Registering the message command.
        getCommand("message").setExecutor(new MessageCommand());
    }

    @Override
    public void onDisable() {
        //Purging DataStorage to remove any leftovers
        DataStorage.purge();
    }
}
