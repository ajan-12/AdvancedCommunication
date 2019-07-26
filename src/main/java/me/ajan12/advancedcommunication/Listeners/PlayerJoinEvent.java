package me.ajan12.advancedcommunication.Listeners;

import me.ajan12.advancedcommunication.Utilities.UserUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinEvent implements Listener {

    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent e) {

        //Handling the joining player's User.
        UserUtils.getUser(e.getPlayer());
    }
}
