package me.ajan12.advancedcommunication.Listeners;

import me.ajan12.advancedcommunication.Objects.Focusable;
import me.ajan12.advancedcommunication.Objects.User;
import me.ajan12.advancedcommunication.Utilities.GeneralUtils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashSet;

public class PlayerChatEvent implements Listener {

    //Priority is LOWEST because we want to be the first plugin to affect the message.
    //This listener is for Focusing feature.
    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat_Focusing(final AsyncPlayerChatEvent e) {

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

        //Getting the focused.
        final Focusable target = user.getFocused();
        //Checking if the focused is null, if so we don't need to do anything because player isn't focused on anyone.
        if (target == null) return;

        //Sending the messages.
        GeneralUtils.sendMessageFocused(user, e.getMessage());

        //Cancelling the event because we don't want to send multiple messages on chat.
        e.setCancelled(true);
    }

    //Priority is HIGHEST because we want the other plugins to do their jobs first.
    //This listener is for Mentioning feature.
    @EventHandler(priority = EventPriority.HIGH)
    public void onChat_Mentioning(final AsyncPlayerChatEvent e) {

        //We don't want to send any messages if the event is cancelled.
        if (e.isCancelled()) return;

        //Creating a new hashset for the new recipients of the message.
        //These players will get the normal message without highlighting.
        final HashSet<Player> newRecipients = new HashSet<>();
        //The message that was sent by the sender.
        final String message = e.getMessage();

        //Iterating over all of the online players to scan for their nick in the message.
        for (final Player p : Bukkit.getOnlinePlayers()) {

            //Checking if the message contains the player's nick.
            if (message.contains("@" + p.getName())) {

                //Sending the player the highlighted message.
                GeneralUtils.highlightAndSend(p, e.getPlayer(), message, "@" + p.getName());

            //Checking if the message contains the player's display name.
            } else if (message.contains("@" + p.getDisplayName())) {

                //Sending the player the highlighted message.
                GeneralUtils.highlightAndSend(p, e.getPlayer(), message, "@" + p.getDisplayName());

            } else {

                //Adding this player to new recipients since they aren't contained in the message.
                newRecipients.add(p);

            }
        }

        //Iterating over the new recipients.
        for (final Player p : newRecipients) {
            //Sending the normal message to the new recipients.
            p.sendMessage(message);
        }

        //Cancelling the event because we don't want to send multiple messages.
        e.setCancelled(true);
    }
}
