package me.ajan12.advancedcommunication.Utilities;

import me.ajan12.advancedcommunication.Objects.User;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

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
        final Player focusedPlayer = sender.getFocusedPlayer();
        //Sending the message to both target player and sender player.
        focusedPlayer.sendMessage(
                ChatColor.GOLD + "[" + ChatColor.AQUA + senderPlayer.getDisplayName() +
                ChatColor.GOLD + "] >> [" + ChatColor.RED + "You" + ChatColor.GOLD + "] » " +
                ChatColor.RESET + message);
        senderPlayer.sendMessage(
                ChatColor.GOLD + "[" + ChatColor.RED + "You" +
                ChatColor.GOLD + "] >> [" + ChatColor.AQUA + focusedPlayer.getDisplayName() + ChatColor.GOLD + "] » " +
                ChatColor.RESET + message);

        //Iterating over all the players online.
        for (final Player player : Bukkit.getOnlinePlayers()) {
            //Checking if the player is spying.
            if (player.hasPermission("advancedcommunication.spy")) {

                //Checking if the player iterating on is the sender, if so we're passing them as they already got the message.
                if (player.getUniqueId().equals(senderPlayer.getUniqueId())) continue;
                //Checking if the player iterating on is the focusedPlayer, if so we're passing them as they already got the message.
                if (player.getUniqueId().equals(focusedPlayer.getUniqueId())) continue;

                //Sending the message to the player as they are spying.
                player.sendMessage(
                        ChatColor.GOLD + "[" + ChatColor.RED + "SPY" + ChatColor.GOLD + "] " +
                        ChatColor.GOLD + "[" + ChatColor.AQUA + senderPlayer.getDisplayName() +
                        ChatColor.GOLD + "] >> [" + ChatColor.AQUA + focusedPlayer.getDisplayName() + ChatColor.GOLD + "] » " +
                        ChatColor.RESET + message);
            }
        }
    }

    /**
     * Sends a private message to a player from a player.
     *
     * @param sender : The sender player of this message.
     * @param target : The target player of this message.
     * @param message: The message to send.
     */
    public static void sendMessagePlayer(final Player sender, final Player target, final String message) {
        //Sending the message to both target player and sender player.
        target.sendMessage(
                ChatColor.GOLD + "[" + ChatColor.AQUA + sender.getDisplayName() +
                ChatColor.GOLD + "] >> [" + ChatColor.RED + "You" + ChatColor.GOLD + "] » " +
                ChatColor.RESET + message);
        sender.sendMessage(
                ChatColor.GOLD + "[" + ChatColor.RED + "You" +
                ChatColor.GOLD + "] >> [" + ChatColor.AQUA + target.getDisplayName() + ChatColor.GOLD + "] » " +
                ChatColor.RESET + message);

        //Iterating over all the players online.
        for (final Player player : Bukkit.getOnlinePlayers()) {
            //Checking if the player is spying.
            if (player.hasPermission("advancedcommunication.spy")) {

                //Checking if the player iterating on is the sender, if so we're passing them as they already got the message.
                if (player.getUniqueId().equals(sender.getUniqueId())) continue;
                //Checking if the player iterating on is the focusedPlayer, if so we're passing them as they already got the message.
                if (player.getUniqueId().equals(target.getUniqueId())) continue;

                //Sending the message to the player as they are spying.
                player.sendMessage(
                        ChatColor.GOLD + "[" + ChatColor.RED + "SPY" + ChatColor.GOLD + "] " +
                        ChatColor.GOLD + "[" + ChatColor.AQUA + sender.getDisplayName() +
                        ChatColor.GOLD + "] >> [" + ChatColor.AQUA + target.getDisplayName() + ChatColor.GOLD + "] » " +
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
}
