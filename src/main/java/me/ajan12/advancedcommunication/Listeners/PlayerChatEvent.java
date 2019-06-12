package me.ajan12.advancedcommunication.Listeners;

import me.ajan12.advancedcommunication.Objects.User;
import me.ajan12.advancedcommunication.Utilities.GeneralUtils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatEvent implements Listener {

    //Priority is LOWEST because we don't want the other plugins to do their jobs first. ESPECIALLY DISCORDSRV
    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent e) {

        //Checking if any other plugin cancels this.
        if (e.isCancelled()) return;
        //Checking if the message is empty.
        if (e.getMessage().equals("")) return;

        //Getting the sender player.
        final Player sender = e.getPlayer();
        //Checking if the sender player is null.
        if (sender == null) return;

        //Getting the User the message sender corresponds to.
        final User user = GeneralUtils.getUser(sender);
        //Checking if the User is null.
        if (user == null) return;

        //Getting the focused player.
        final Player target = user.getFocusedPlayer();
        //Checking the focused player is null, if so we don't need to do anything because player isn't focused on anyone.
        if (target == null) return;

        //Sending the message to both target player and sender player
        target.sendMessage(
                ChatColor.GOLD + "[" + ChatColor.AQUA + sender.getDisplayName() +
                ChatColor.GOLD + "] >> [" + ChatColor.RED + "You" + ChatColor.GOLD + "] » " +
                ChatColor.RESET + e.getMessage());
        sender.sendMessage(
                ChatColor.GOLD + "[" + ChatColor.RED + "You" +
                ChatColor.GOLD + "] >> [" + ChatColor.AQUA + target.getDisplayName() + ChatColor.GOLD + "] » " +
                ChatColor.RESET + e.getMessage());

        //Cancelling the event because we don't want to send multiple messages on chat.
        e.setCancelled(true);
    }

}
