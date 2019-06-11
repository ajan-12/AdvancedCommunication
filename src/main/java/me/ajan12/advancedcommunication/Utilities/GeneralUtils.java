package me.ajan12.advancedcommunication.Utilities;

import me.ajan12.advancedcommunication.Objects.User;

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
}
