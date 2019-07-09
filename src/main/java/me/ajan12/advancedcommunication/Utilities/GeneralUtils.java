package me.ajan12.advancedcommunication.Utilities;

import me.ajan12.advancedcommunication.Objects.Group;
import me.ajan12.advancedcommunication.Objects.User;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

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

    /**
     * Purges the groups according to the inactivity of its members, group admins and overall group inactivity.
     */
    public static void purgeGroups() {

        //Creating a TreeMap with the likelinesses and Group's.
        final Map<Float, Group> likelinesses = new TreeMap<>(Comparator.reverseOrder());

        //Getting the amount of groups to purge.
        final int groupAmountToPurge = DataStorage.groups.size() - 10;
        //Checking if the amount is negative.
        if (groupAmountToPurge < 0) return;

        //Iterating over all of the groups.
        for (final Group group : DataStorage.groups) {
            //likeliness of this group to purge.
            float likeliness = 0.0F;

            //denominator and partition to use for groupInactiveness.
            float denominator = 0.0F;
            float partition = 0.0F;

            //Iterating over all the members this group has.
            for (final Map.Entry<Player, Boolean> member : group.getMembers().entrySet()) {
                //a temporary value.
                float temp = 0.0F;

                //Getting the player of this member.
                final Player player = member.getKey();

                //Getting the time that passed since this player last played.
                long timePassed = System.currentTimeMillis() - player.getLastPlayed();
                //If the timePassed is bigger than 12 months.
                if (timePassed >= 31_104_000_000L) {
                    temp += 1.0F;
                //If the timePassed is bigger than 6 months.
                } else if (timePassed >= 15_552_000_000L) {
                    temp += 0.5F;
                //If the timePassed is bigger than 3 months.
                } else if (timePassed >= 7_776_000_000L) {
                    temp += 0.25F;
                }

                //Doubling the effect of temp by 2 for group admins. The 2.0 should be a value between 1.5 to 3.0
                if (member.getValue()) temp *= 2.0F;

                //Increasing the partition and denominator.
                partition += temp;
                denominator += 1.0F;
            }

            //Getting the groupInactiveness.
            final float groupInactiveness = partition / denominator;

            //Increasing the likeliness to purge this group by the groupInactiveness.
            likeliness += groupInactiveness;

            //Checking if the group wasn't updated in a month.
            if (System.currentTimeMillis() - group.getLastUpdate() >= 2_592_000_000L) {
                //Increasing the likeliness to purge this group by 1.
                likeliness++;
            }

            //Saving the likeliness of this group to the main array.
            likelinesses.put(likeliness, group);
        }

        //The integer to use while iteration, this helps us in purging the right amount of groups.
        int i = 0;
        //Iterating over groups to remove the groups with the highest likelinesses.
        for (final Map.Entry<Float, Group> entry : likelinesses.entrySet()) {

            //If i exceeds the amount of groups to purge, we break the loop.
            if (i >= groupAmountToPurge) break;

            //Purging the group.
            entry.getValue().purge();

            //Increasing the iteration integer.
            i++;
        }
    }

    /**
     * Saves all the groups to the disc.
     */
    public static void saveGroups() {

        //Creating a hashset that contains the groups to be removed after saving to prevent any exceptions.
        final HashSet<Group> groupsToRemove = new HashSet<>();
        //Iterating over all of the groups.
        for (final Group group : DataStorage.groups) {

            //Checking if the group wasn't updated in 2 months.
            if (System.currentTimeMillis() - group.getLastUpdate() >= 5_184_000_000L) {
                //Purging the group because it wasn't updated in 2 months.
                group.purge();
                //Adding the group to the hashset to remove it later on.
                groupsToRemove.add(group);
                //Passing this group.
                continue;
            }

            //Saving this group to the disc.
            group.save();

        }

        //Iterating over the groups that will be removed from memory.
        for (final Group group : groupsToRemove) {
            //Removing the group from memory.
            DataStorage.removeGroup(group);
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
        final String newMessage = ChatColor.YELLOW + message.replace(toHighlight, ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + toHighlight);

        //Sending the player the highlighted message.
        p.sendMessage(sender.getDisplayName() + ChatColor.RED + ">>" + newMessage);

        //Sending the message as a subtitle so we make sure that the player sees the title.
        p.sendTitle(sender.getDisplayName() + ChatColor.RED + " has mentioned you.", newMessage, 10, 110, 20);
    }
}