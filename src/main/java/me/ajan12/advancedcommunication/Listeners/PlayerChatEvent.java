package me.ajan12.advancedcommunication.Listeners;

import me.ajan12.advancedcommunication.Objects.User;
import me.ajan12.advancedcommunication.Utilities.GeneralUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatEvent implements Listener {

    //Priority is LOW because we want the other plugins to do their jobs first
    @EventHandler(priority = EventPriority.LOW)
    public void onChat(AsyncPlayerChatEvent e) {

        //Checking if any other plugin cancels this
        if (e.isCancelled()) return;
        //Checking if the message is empty
        if (e.getMessage().equals("")) return;

        //Getting the User the message sender corresponds to
        final User user = GeneralUtils.getUser(e.getPlayer());
        //Checking if the User is null
        if (user == null) return;

    }

}