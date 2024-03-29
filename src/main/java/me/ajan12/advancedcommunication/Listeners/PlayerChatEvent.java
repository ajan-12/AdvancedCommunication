package me.ajan12.advancedcommunication.Listeners;

import me.ajan12.advancedcommunication.Objects.Focusable;
import me.ajan12.advancedcommunication.Objects.MentionedMessage;
import me.ajan12.advancedcommunication.Objects.User;
import me.ajan12.advancedcommunication.Utilities.DataStorage;
import me.ajan12.advancedcommunication.Utilities.PacketUtils;
import me.ajan12.advancedcommunication.Utilities.UserUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;

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
        //Getting the User the message sender corresponds to.
        final User user = UserUtils.getUser(sender);

        //Getting the focused.
        final Focusable target = user.getFocused();
        //Checking if the focused is null, if so we don't need to do anything because player isn't focused on anyone.
        if (target == null) return;

        //Sending the messages.
        target.sendMessage(user, e.getMessage());

        //Cancelling the event because we don't want to send multiple messages on chat.
        e.setCancelled(true);
    }

    //Priority is HIGH because we want the other plugins to do their jobs first.
    //This listener is for Mentioning feature.
    @EventHandler(priority = EventPriority.HIGH)
    public void onChat_Mentioning(final AsyncPlayerChatEvent e) {

        //We don't want to send any messages if the event is cancelled.
        if (e.isCancelled()) return;

        //Checking if the player has already sent a mention recently.
        if (DataStorage.mentionedPlayers.get(e.getPlayer().getUniqueId()) > System.currentTimeMillis() - 10_000) return;

        //Getting the message that was sent.
        final String message = e.getMessage();
        //Getting the words in the message.
        final String[] words = message.split(" ");

        //Preparing the String that we'll be modifying.
        String highlightedMessage = message;

        //Creating the hashmap that will contain the players mentioned and the amounts.
        final HashMap<Player, Integer> playerMentions = new HashMap<>();

        int total = 0;
        //Iterating over the players.
        for (final Player player : Bukkit.getOnlinePlayers()) {

            //The amount of mentions of this player.
            int amount = 0;
            //Iterating over the words.
            for (final String word : words) {

                //Checking if the player was mentioned in this word.
                if (word.toLowerCase().contains("@" + player.getName().toLowerCase()) || word.toLowerCase().contains("@" + player.getDisplayName().toLowerCase())) {
                    //Checking if the mention has anything else.
                    if (word.replaceAll("(@" + player.getName() + "|" + player.getDisplayName() + ")", "").matches("\\W*")) amount++;
                }
            }

            if (amount > 0) {

                //Highlighting the mention.
                highlightedMessage = highlightedMessage.replaceAll("(@(" + player.getName() + "|" + ChatColor.stripColor(player.getDisplayName()) + "))", "§e@" + player.getDisplayName().replaceAll("(§r|§f)","§e") + "§r");

                //Updating the message.
                e.setMessage(highlightedMessage);

                //Notifications.
                //Notifying the mentioned player by a subtitle.
                player.sendTitle(" ", e.getPlayer().getDisplayName() + ChatColor.GOLD + " has mentioned you.", 10, 60, 20);
                //Sending a hotbar message.
                PacketUtils.sendHotbarMessage(player, highlightedMessage);
                //Playing a sound effect to notify the player.
                player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, SoundCategory.PLAYERS, 100F, 0F);

                //Adding the mention to playerMentions.
                playerMentions.put(player, amount);

                total++;
            }
        }

        //Creating and adding a new MentionedMessage.
        DataStorage.addMention(new MentionedMessage(playerMentions, total));
        //Adding the player to cooldowned ones.
        DataStorage.addMentionedPlayer(e.getPlayer().getUniqueId());
    }

    //Priority is HIGH because we want the other plugins to do their jobs first.
    //This listener is for Ignoring feature.
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat_Ignoring(final AsyncPlayerChatEvent e) {

        //Iterating over the online players to check the ignoring status.
        for (final Player player : Bukkit.getOnlinePlayers()) {

            //Getting the User of the sender.
            final User user = UserUtils.getUser(player);

            //Checking if the user is ignoring the sender.
            if (user.getIgnoredUsers().contains(e.getPlayer().getUniqueId())) e.getRecipients().remove(player);
        }
    }

    //Priority is HIGH because we want the other plugins to do their jobs first.
    //This listener is for SoftMute and HardMute(mute) features.
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat_Mute(final AsyncPlayerChatEvent e) {

        //Getting the User of the sender of the chat message.
        final User user = UserUtils.getUser(e.getPlayer());

        //Checking if the user is hard muted.
        if (user.isHardMuted()) {

            //Cancelling the event.
            e.setCancelled(true);
            //Feedbacking the user about that they cannot talk while muted.
            user.getPlayer().sendMessage(DataStorage.pluginTag + ChatColor.DARK_RED + " You cannot talk while muted!");
        //Checking if the user is soft muted.
        } else if (user.isSoftMuted()) {

            //Removing all the recipients of this chat message.
            for (Player recipient : e.getRecipients()) {
                e.getRecipients().remove(recipient);
            }
        }
    }
}
