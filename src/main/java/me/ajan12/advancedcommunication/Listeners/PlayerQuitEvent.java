package me.ajan12.advancedcommunication.Listeners;

import me.ajan12.advancedcommunication.Objects.User;
import me.ajan12.advancedcommunication.Utilities.DataStorage;
import me.ajan12.advancedcommunication.Utilities.GeneralUtils;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerQuitEvent implements Listener {

    @EventHandler
    public void onQuit(org.bukkit.event.player.PlayerQuitEvent e) {

        //Getting the User of the player that left
        final User user = GeneralUtils.getUser(e.getPlayer());
        //Checking if the User is null and if so no action needs to be done so returning.
        if (user == null) return;
        //Removing the User from DataStorage.
        DataStorage.removeUser(user);

    }
}
