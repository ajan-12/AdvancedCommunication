package me.ajan12.advancedcommunication.Objects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public abstract class Focusable {

    public abstract String getName();
    public abstract void sendMessage(final Focusable sender, final String message);
    public abstract UUID getUUID();

    void sendMessageSpies(final Focusable sender, final Focusable receiver, final String message) {

        //Iterating over all the players online.
        for (final Player player : Bukkit.getOnlinePlayers()) {
            //Checking if the player is spying.
            if (player.hasPermission("advancedcommunication.spy")) {

                //Checking if the player iterating on is the receiver, if so we're passing them as they already got the message.
                if (player.getUniqueId().equals(receiver.getUUID())) continue;

                //Checking if the player iterating on is the sender, if so we're passing them as they already got the message.
                if (player.getUniqueId().equals(sender.getUUID())) continue;

                //Sending the message to the player as they are spying.
                player.sendMessage(ChatColor.GOLD + "[" + ChatColor.RED + "SPY" + ChatColor.GOLD + "] " + message);
            }
        }

    }
}
