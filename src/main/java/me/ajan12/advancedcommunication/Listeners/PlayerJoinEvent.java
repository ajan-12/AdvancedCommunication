package me.ajan12.advancedcommunication.Listeners;

import me.ajan12.advancedcommunication.Objects.User;
import me.ajan12.advancedcommunication.Utilities.DataStorage;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PlayerJoinEvent implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent e) {

        //Creating a new User for the joining Player.
        final User user = new User(e.getPlayer());
        //Saving the new User to DataStorage.
        DataStorage.addUser(user);

    }
}
