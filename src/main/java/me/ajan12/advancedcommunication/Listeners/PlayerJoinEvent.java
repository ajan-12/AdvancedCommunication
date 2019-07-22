package me.ajan12.advancedcommunication.Listeners;

import me.ajan12.advancedcommunication.Objects.User;
import me.ajan12.advancedcommunication.Utilities.DataStorage;
import me.ajan12.advancedcommunication.Utilities.UserUtils;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinEvent implements Listener {

    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent e) {

        //Getting the User of the joining Player.
        User user = UserUtils.getUser(e.getPlayer());
        //Checking if the user was found.
        if (user == null) {

            //Creating a new User for the joining Player.
            user = new User(e.getPlayer().getDisplayName(), e.getPlayer().getUniqueId());
            //Saving the user to DataStorage.
            DataStorage.addUser(user);
        }

        //Checking if the user has a name set.
        if (user.getName() == null) {

            //Setting the user's name.
            user.setName(e.getPlayer().getDisplayName());
        }
    }
}
