package me.ajan12.advancedcommunication.Utilities;

import me.ajan12.advancedcommunication.Enums.PluginState;
import me.ajan12.advancedcommunication.Objects.Group;
import me.ajan12.advancedcommunication.Objects.User;
import me.ajan12.advancedcommunication.Utilities.DatabaseUtils.SQLiteUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class GroupUtils {

    /**
     * Finds the group with the given name from the database.
     *
     * @param name: The name of the group.
     * @return    : The group if it was found, null otherwise.
     */
    public static Group findGroup(final String name) {
        //Iterating over all the groups.
        for (final Group group : DataStorage.groups.values()) {

            //Checking if we found our group.
            if (group.getName().equalsIgnoreCase(name)) return group;
        }

        //Returning null as no groups was found.
        return null;
    }

    /**
     * Creates a String with all the groups that the player is in.
     *
     * @param p: The player that we'll search on
     * @return : Returns a String populated with the group names of the player.\n
     *           "none" if the player isn't in any group.
     */
    public static String listGroups(final Player p) {

        //Getting the User of this player.
        final User user = UserUtils.getUser(p);

        //Checking if the user is null.
        if (user == null) {

            //Returning "none" as we couldn't find any groups.
            return "none";

        } else {

            //Getting the groups the user is in.
            final List<String> groups = new ArrayList<>(user.getGroups().keySet());

            //Checking the group size and returning the groups.
            switch (groups.size()) {

                //Checking if the user isn't in any groups.
                case 0:
                    //Returning "none" as we couldn't find any groups.
                    return "none";

                //Checking if the user is only in 1 group.
                case 1:
                    //Returning the only group the player is in.
                    return groups.get(0);

                //This happens only if the user is in more than 1 groups.
                default:

                    //Creating the StringBuilder that we'll populate and return.
                    final StringBuilder message = new StringBuilder(ChatColor.YELLOW + groups.get(1) + ChatColor.AQUA + " and " + ChatColor.YELLOW + groups.get(0) + ChatColor.AQUA + ".");

                    //Populating the StringBuilder if the user is in more than 2 groups.
                    for (int i = 2; i < groups.size(); i++) {

                        //Inserting the group we're iterating over to the beginning.
                        message.insert(0, ChatColor.YELLOW + groups.get(i) + ChatColor.AQUA + ", ");

                    }

                    //Returning the groups the user is in.
                    return message.toString();
            }
        }
    }

    /**
     * Imports the groups from the Database to DataStorage.
     */
    public static void importGroups() {

        //Checking if the plugin is busy.
        if (DataStorage.pluginState != PluginState.IDLE) return;

        //Changing to PluginState to IMPORTING_GROUPS as we're doing that in this method.
        DataStorage.changePluginState(PluginState.IMPORTING_GROUPS);

        //Importing the groups.
        SQLiteUtils.importGroups();

        //Changing to PluginState to IDLE as we're done with importing.
        DataStorage.changePluginState(PluginState.IDLE);
    }

    /**
     * Purges the groups according to the inactivity of its members, group admins and overall group inactivity.
     */
    public static void purgeGroups() {

        //Checking if the plugin is busy.
        if (DataStorage.pluginState != PluginState.IDLE) return;

        //Changing to PluginState to PURGING_GROUPS as we're doing that in this method.
        DataStorage.changePluginState(PluginState.PURGING_GROUPS);

        //Creating a TreeMap with the likelinesses and Group's.
        final TreeMap<Float, Group> likelinesses = new TreeMap<>(Comparator.reverseOrder());

        //Getting the amount of groups to purge.
        final int groupAmountToPurge = DataStorage.groups.size() - 500;
        //Checking if the amount is negative.
        if (groupAmountToPurge < 0) {
            //Changing to PluginState to IDLE as we're done with purging and saving.
            DataStorage.changePluginState(PluginState.IDLE);
            return;
        }

        //Iterating over all of the groups.
        for (final Group group : DataStorage.groups.values()) {
            //likeliness of this group to purge.
            float likeliness = 0.0F;

            //denominator and partition to use for groupInactiveness.
            float denominator = 0.0F;
            float partition = 0.0F;

            //Iterating over all the members this group has.
            for (final Map.Entry<UUID, Boolean> member : group.getMembers().entrySet()) {
                //a temporary value.
                float temp = 0.0F;

                //Getting the player of this member.
                final OfflinePlayer player = Bukkit.getOfflinePlayer(member.getKey());

                //Getting the time passed since this player last played.
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

        //Creating a hashset to store the groups that will be purged.
        final HashSet<Group> groupsToPurge = new HashSet<>();

        //Iterating over groups to remove the groups with the highest likelinesses.
        for (final Map.Entry<Float, Group> entry : likelinesses.entrySet()) {

            //If i exceeds the amount of groups to purge, we break the loop.
            if (i >= groupAmountToPurge) break;

            //Adding the group to the hashset.
            groupsToPurge.add(entry.getValue());

            //Increasing the iteration integer.
            i++;
        }

        //Purging the groups.
        SQLiteUtils.purgeGroups(groupsToPurge);

        //Changing to PluginState to IDLE as we're done with purging.
        DataStorage.changePluginState(PluginState.IDLE);
    }

    /**
     * Saves all the groups to the disc.
     */
    public static void saveGroups() {

        //Checking if the plugin is busy.
        if (DataStorage.pluginState != PluginState.IDLE) return;

        //Changing to PluginState to SAVING_GROUPS as we're doing that in this method.
        DataStorage.changePluginState(PluginState.SAVING_GROUPS);

        //Getting the groups that we're saving.
        final HashSet<Group> groups = new HashSet<>(DataStorage.groups.values());

        //Saving the groups.
        SQLiteUtils.saveGroups(groups);

        //Changing to PluginState to IDLE as we're done with saving.
        DataStorage.changePluginState(PluginState.IDLE);
    }
}
