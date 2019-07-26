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
     * Finds an User with the given UUID.
     *
     * @param uuid: The UUID of the User.
     * @return    : True if the User is found, false otherwise.
     */
    public static boolean findUser(final UUID uuid) {

        //Iterating over all of the User's in the DataStorage to find the correct one.
        for (final User user : DataStorage.users) {
            //Comparing UUIDs because it's more reliable.
            if (user.getUUID().equals(uuid)) {
                //Returning true as we found the user.
                return true;
            }
        }
        //Returning false as we couldn't find the user.
        return false;
    }

    /**
     * Creates a new User for the given player.
     *
     * @param player: The player to create the User of.
     * @return      : The User created.
     */
    public static User handleUser(final UUID player) {

        //Creating a new User for the player.
        final User user = new User(player);
        //Saving the user to DataStorage.
        DataStorage.addUser(user);
        return user;
    }

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
     * @param player: The OfflinePlayer to use while searching for the User.
     * @return      : If any User is found then that User but if not then null.
     */
    public static User getUser(final OfflinePlayer player) {
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
            //Comparing UUIDs because it's more reliable.
            if (user.getUUID().equals(uuid)) {
                //Returning the User with the same UUID with the given player.
                return user;
            }
        }
        //Creating a new user and returning it.
        return handleUser(uuid);
    }

    /**
     * Imports the users from the Database to DataStorage.
     */
    public static void importUsers() {

        //Checking if the plugin is busy.
        if (DataStorage.pluginState != PluginState.IDLE) return;

        //Changing to PluginState to IMPORTING_USERS as we're doing that in this method.
        DataStorage.changePluginState(PluginState.IMPORTING_USERS);

        //Importing the users.
        SQLiteUtils.importUsers();

        //Changing to PluginState to IDLE as we're done with importing.
        DataStorage.changePluginState(PluginState.IDLE);
    }

    /**
     * Purges the users according to their inactivity, groups' inactivity.
     */
    public static void purgeUsers() {

        //Checking if the plugin is busy.
        if (DataStorage.pluginState != PluginState.IDLE) return;

        //Changing to PluginState to PURGING_USERS as we're doing that in this method.
        DataStorage.changePluginState(PluginState.PURGING_USERS);

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

        //Changing to PluginState to IDLE as we're done with purging.
        DataStorage.changePluginState(PluginState.IDLE);
    }

    /**
     * Saves all the users to the disc.
     */
    public static void saveUsers() {

        //Checking if the plugin is busy.
        if (DataStorage.pluginState != PluginState.IDLE) return;

        //Changing to PluginState to SAVING_USERS as we're doing that in this method.
        DataStorage.changePluginState(PluginState.SAVING_USERS);

        //Saving the users.
        SQLiteUtils.saveUsers(DataStorage.users);

        //Changing to PluginState to IDLE as we're done with saving.
        DataStorage.changePluginState(PluginState.IDLE);
    }
}
