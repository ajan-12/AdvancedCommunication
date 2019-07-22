package me.ajan12.advancedcommunication.Utilities;

import me.ajan12.advancedcommunication.Enums.PluginState;
import me.ajan12.advancedcommunication.Objects.Group;
import me.ajan12.advancedcommunication.Objects.User;
import me.ajan12.advancedcommunication.Utilities.DatabaseUtils.SQLiteUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class UserUtils {

    /**
     * Searches for all of the stored User's in the DataStorage and finds the User that this player corresponds to.
     *
     * @param playerName: The name of the player to use while searching for the User.
     * @return          : If any User is found then that User but if not then null.
     */
    public static User getUser(final String playerName) {

        //Getting the Player.
        final Player player = Bukkit.getPlayer(playerName);
        //Checking if the player was found.
        if (player == null) return null;

        //Getting and returning the user.
        return getUser(player.getUniqueId());
    }

    /**
     * Searches for all of the stored User's in the DataStorage and finds the User that this player corresponds to.
     *
     * @param player: The player to use while searching for the User.
     * @return      : If any User is found then that User but if not then null.
     */
    public static User getUser(final Player player) {
        //Getting and returning the user.
        return getUser(player.getUniqueId());
    }

    /**
     * Searches for all of the stored User's in the DataStorage and finds the User that this player corresponds to.
     *
     * @param uuid: The UUID of the player to use while searching for the User.
     * @return    : If any User is found then that User but if not then null.
     */
    public static User getUser(final UUID uuid) {

        //Iterating over all of the User's in the DataStorage to find the correct one.
        for (final User user : DataStorage.users) {
            //Comparing UUIDs because I'm more confident with it.
            if (user.getUUID().equals(uuid)) {
                //Returning the User with the same UUID with the given player.
                return user;
            }
        }
        //Returning null if no user is found(nothing was returned before).
        return null;
    }

    /**
     * Purges the users according to their inactivity, groups' inactivity.
     */
    public static void purgeUsers() {

        //Checking if the plugin is busy.
        if (DataStorage.pluginState != PluginState.IDLE) return;

        //Changing to PluginState to PURGING_AND_SAVING_GROUPS as we're doing that in this method.
        DataStorage.changePluginState(PluginState.PURGING_AND_SAVING_USERS);

        //Creating a hashset with the users to purge.
        final HashSet<User> usersToPurge = new HashSet<>();

        //Iterating over the users.
        for (final User user : DataStorage.users) {
            //The likeliness to never come back to the server.
            float likelinessToNeverComeBack = 0.0F;

            //Getting the OfflinePlayer of this User.
            final OfflinePlayer player = Bukkit.getOfflinePlayer(user.getUUID());

            //Checking if the user is op.
            if (player.isOp()) continue;
            //Checking if the user has the bypass permission.
            if (DataStorage.perms.playerHas(null, player, "advancedcommunication.user.purge.bypass")) continue;

            //Getting the time passed since this player last played.
            long timePassed = System.currentTimeMillis() - player.getLastPlayed();

            //Checking if the time that player hasn't been playing is greater than the time the player was active
            if (timePassed >= (player.getLastPlayed() - player.getFirstPlayed())) {
                likelinessToNeverComeBack += 2.0F;
            //If the timePassed is bigger than 12 months.
            } else if (timePassed >= 31_104_000_000L) {
                likelinessToNeverComeBack += 1.0F;
            //If the timePassed is bigger than 6 months.
            } else if (timePassed >= 15_552_000_000L) {
                likelinessToNeverComeBack += 0.5F;
            //If the timePassed is bigger than 3 months.
            } else if (timePassed >= 7_776_000_000L) {
                likelinessToNeverComeBack += 0.25F;
            }

            //The likeliness to come back to the server.
            float likelinessToComeBack = 0.0F;
            //Iterating over the user's groups.
            for (final Map.Entry<String, UUID> entry : user.getGroups().entrySet()) {
                //Getting the Group.
                final Group group = DataStorage.groups.get(entry.getValue());

                //Checking if the user is a group admin in this group.
                if (group.getMembers().get(user.getUUID())) {
                    likelinessToComeBack += 0.1F * (group.getMembers().size() / 2.0F);
                } else {
                    likelinessToComeBack += 0.2F;
                }
            }

            //Checking if the user isn't likely to come back.
            if (likelinessToComeBack >= likelinessToNeverComeBack / 2.0F) {
                //Adding the user to the users to purge.
                usersToPurge.add(user);
            }
        }

        //Purging the users.
        SQLiteUtils.purgeUsers(usersToPurge);

        //Changing to PluginState to IDLE as we're done with purging and saving.
        DataStorage.changePluginState(PluginState.IDLE);
    }

    /**
     * Saves all the users to the disc.
     */
    public static void saveUsers() {

        //Checking if the plugin is busy.
        if (DataStorage.pluginState != PluginState.IDLE) return;

        //Changing to PluginState to PURGING_AND_SAVING_GROUPS as we're doing that in this method.
        DataStorage.changePluginState(PluginState.PURGING_AND_SAVING_USERS);

        //Saving the users.
        SQLiteUtils.saveUsers(DataStorage.users);

        //Changing to PluginState to IDLE as we're done with purging and saving.
        DataStorage.changePluginState(PluginState.IDLE);
    }
}
