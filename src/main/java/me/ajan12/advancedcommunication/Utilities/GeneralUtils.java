package me.ajan12.advancedcommunication.Utilities;

import me.ajan12.advancedcommunication.Objects.MentionedMessage;
import org.bukkit.entity.Player;

/**
 * This class has varius methods for many features of this plugin.
 */
public class GeneralUtils {

    /**
     * Searches for all of the stored MentionedMessage's in the DataStorage and finds the MentionedMessage that this player is mentioned in.
     *
     * @param player: The player to use while searching for the MentionedMessage.
     * @return      : If any MentionedMessage is found then that MentionedMessage but if not then null.
     */
    public static MentionedMessage getMentionedMessage(final Player player) {

        //Iterating over all of the MentionedMessage's in the DataStorage to find the correct one.
        for (final MentionedMessage message : DataStorage.messages) {
            //Checking if this MentionedMessage has the player.
            if (message.getPlayerMentions().containsKey(player)) {
                //Returning the MentionedMessage that has the player.
                return message;
            }
        }
        //Returning null if no MentionedMessage is found(nothing was returned before).
        return null;
    }
}