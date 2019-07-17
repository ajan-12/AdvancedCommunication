package me.ajan12.advancedcommunication.Utilities;

import me.ajan12.advancedcommunication.Objects.Focusable;
import me.ajan12.advancedcommunication.Objects.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

/**
 * This class has varius methods for many features of this plugin.
 */
public class GeneralUtils {

    /**
     * Searches for all of the stored User's in the DataStorage and finds the User that this player corresponds to.
     *
     * @param player: The player to use while searching for the User.
     * @return      : If any User is found then that User but if not then null.
     */
    public static User getUser(final Player player) {

        //Iterating over all of the User's in the DataStorage to find the correct one
        for (final User user : DataStorage.users) {
            //Comparing UUIDs because I'm more confident with it
            if (user.getPlayer().getUniqueId().equals(player.getUniqueId())) {
                //Returning the User with the same UUID with the given player
                return user;
            }
        }
        //Returning null if no user is found(nothing was returned before)
        return null;
    }

    /**
     * Sends a private message to the focused player of an User.
     *
     * @param sender : The User of the sender of this message.
     * @param message: The message to send.
     */
    public static void sendMessageFocused(final User sender, final String message) {

        //Getting the sender player.
        final Player senderPlayer = sender.getPlayer();
        //Getting the focused player.
        final Focusable focused = sender.getFocused();

        //Sending the message.
        sendMessagePlayer(senderPlayer, focused, message);
    }

    /**
     * Sends a private message to a player from a player.
     *
     * @param sender : The sender player of this message.
     * @param target : The target player of this message.
     * @param message: The message to send.
     */
    public static void sendMessagePlayer(final Player sender, final Focusable target, final String message) {

        //Sending the message to both target player and sender player.
        target.sendMessage(sender,
                ChatColor.GOLD + "[" + ChatColor.AQUA + sender.getDisplayName() +
                ChatColor.GOLD + "] >> [" + ChatColor.RED + "You" + ChatColor.GOLD + "] » " +
                ChatColor.RESET + message);
        sender.sendMessage(
                ChatColor.GOLD + "[" + ChatColor.RED + "You" +
                ChatColor.GOLD + "] >> [" + ChatColor.AQUA + target.getName() + ChatColor.GOLD + "] » " +
                ChatColor.RESET + message);

        //Iterating over all the players online.
        for (final Player player : Bukkit.getOnlinePlayers()) {
            //Checking if the player is spying.
            if (player.hasPermission("advancedcommunication.spy")) {

                //Checking if the player iterating on is the sender, if so we're passing them as they already got the message.
                if (player.getUniqueId().equals(sender.getUniqueId())) continue;

                //Checking if the target is a Player.
                if (target instanceof me.ajan12.advancedcommunication.Objects.Player) {

                    //Checking if the player iterating on is the focusedPlayer, if so we're passing them as they already got the message.
                    if (player.getUniqueId().equals(((me.ajan12.advancedcommunication.Objects.Player) target).getPlayer().getUniqueId())) continue;

                }

                //Sending the message to the player as they are spying.
                player.sendMessage(
                        ChatColor.GOLD + "[" + ChatColor.RED + "SPY" + ChatColor.GOLD + "] " +
                        ChatColor.GOLD + "[" + ChatColor.AQUA + sender.getDisplayName() +
                        ChatColor.GOLD + "] >> [" + ChatColor.AQUA + target.getName() + ChatColor.GOLD + "] » " +
                        ChatColor.RESET + message);
            }
        }
    }

    /**
     * Sends a private message to a player from console.
     *
     * @param target : The target player of this message.
     * @param message: The message to send.
     */
    public static void sendMessageConsole(final Player target, final String message) {
        //Sending the message to both target player and sender player.
        target.sendMessage(
                ChatColor.GOLD + "[" + ChatColor.DARK_RED + "CONSOLE" +
                        ChatColor.GOLD + "] >> [" + ChatColor.RED + "You" + ChatColor.GOLD + "] » " +
                        ChatColor.RESET + message);
        Bukkit.getConsoleSender().sendMessage(
                ChatColor.GOLD + "[" + ChatColor.RED + "You" +
                        ChatColor.GOLD + "] >> [" + ChatColor.DARK_RED + "CONSOLE" + ChatColor.GOLD + "] » " +
                        ChatColor.RESET + message);

        //Iterating over all the players online.
        for (final Player player : Bukkit.getOnlinePlayers()) {
            //Checking if the player is spying.
            if (player.hasPermission("advancedcommunication.spy")) {

                //Checking if the player iterating on is the focusedPlayer, if so we're passing them as they already got the message.
                if (player.getUniqueId().equals(target.getUniqueId())) continue;

                //Sending the message to the player as they are spying.
                player.sendMessage(
                        ChatColor.GOLD + "[" + ChatColor.RED + "SPY" + ChatColor.GOLD + "] " +
                        ChatColor.GOLD + "[" + ChatColor.DARK_RED + "CONSOLE" +
                        ChatColor.GOLD + "] >> [" + ChatColor.AQUA + target.getDisplayName() + ChatColor.GOLD + "] » " +
                        ChatColor.RESET + message);
            }
        }
    }

    /**
     * Highlights the given message and sends it to the given player.
     *
     * @param p          : The player to get the message.
     * @param sender     : The sender of the message.
     * @param message    : The message to be edited on.
     * @param toHighlight: The part where the player is mentioned on.
     */
    public static void highlightAndSend(final Player p, final Player sender, final String message, final String toHighlight) {

        //The message to be sent to the player
        final String newMessage = "§e" + message.replace(toHighlight, "§6" + "§l" + toHighlight);

        //Sending the player the highlighted message.
        p.sendMessage(sender.getDisplayName() + ChatColor.RED + ">>" + newMessage);

        //Sending the message as a subtitle so we make sure that the player sees the title.
        p.sendTitle(sender.getDisplayName() + ChatColor.RED + " has mentioned you.", newMessage, 10, 110, 20);

        PacketUtils.sendHotbarMessage(p, newMessage);

        //Playing a sound effect to notify the player.
        p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, SoundCategory.PLAYERS, 100F, 0F);
    }
}