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

    //Priority is MONITOR because we don't want the other plugins to do their jobs first. ESPECIALLY DISCORDSRV
    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent e) {

        //Checking if any other plugin cancels this.
        if (e.isCancelled()) return;
        //Checking if the message is empty.
        if (e.getMessage().equals("")) return;

        //Getting the User the message sender corresponds to.
        final User user = GeneralUtils.getUser(e.getPlayer());
        //Checking if the User is null.
        if (user == null) return;

        //Getting the focused player.
        final Player target = user.getFocusedPlayer();
        //Checking the focused player is null, if so we don't need to do anything because player isn't focused on anyone.
        if (target == null) return;

        //Sending the message to both target player and sender player
        target.sendMessage(
                ChatColor.GOLD + "[" + ChatColor.AQUA + e.getPlayer().getDisplayName() +
                ChatColor.GOLD + "] >> [" + ChatColor.RED + "You" + ChatColor.GOLD + "] » " +
                ChatColor.RESET + e.getMessage());
        e.getPlayer().sendMessage(
                ChatColor.GOLD + "[" + ChatColor.RED + "You" +
                ChatColor.GOLD + "] >> [" + ChatColor.AQUA + target.getDisplayName() + ChatColor.GOLD + "] » " +
                ChatColor.RESET + e.getMessage());

        //Setting the message to null because we don't want to send multiple messages on chat.
        e.setMessage(null);
        //Cancelling the event because we don't want to send multiple messages on chat.
        e.setCancelled(true);
    }

}
