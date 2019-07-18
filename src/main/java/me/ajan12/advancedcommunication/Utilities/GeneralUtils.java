package me.ajan12.advancedcommunication.Utilities;

import me.ajan12.advancedcommunication.Objects.User;
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

        //Sending a hotbar message.
        PacketUtils.sendHotbarMessage(p, newMessage);

        //Playing a sound effect to notify the player.
        p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, SoundCategory.PLAYERS, 100F, 0F);
    }
}