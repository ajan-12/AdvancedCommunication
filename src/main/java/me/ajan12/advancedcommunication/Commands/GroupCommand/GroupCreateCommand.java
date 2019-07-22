package me.ajan12.advancedcommunication.Commands.GroupCommand;

import me.ajan12.advancedcommunication.Enums.BannedGroupNames;
import me.ajan12.advancedcommunication.Enums.Feedbacks;
import me.ajan12.advancedcommunication.Objects.Group;
import me.ajan12.advancedcommunication.Objects.User;
import me.ajan12.advancedcommunication.Utilities.DataStorage;
import me.ajan12.advancedcommunication.Utilities.GroupUtils;
import me.ajan12.advancedcommunication.Utilities.UserUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

class GroupCreateCommand {

    static boolean execute(final CommandSender sender, final String[] args) {

        //Getting the pluginTag to ease my work.
        final String pluginTag = DataStorage.pluginTag;

        //Checking if the sender is a Player.
        if (sender instanceof Player) {

            //The casted version of the sender.
            final Player player = (Player) sender;

            //Checking if the player is permitted to create a group.
            if (!player.hasPermission(" advancedcommunication.command.group.create")) {

                //Feedbacking the sender.
                sender.sendMessage(Feedbacks.PERMISSION_ERROR.toString());
                return true;
            }

            //Checking if the group name is legit.
            if (!isLegit(sender, args[1])) return true;

            //Preparing the members map.
            final HashMap<UUID, Boolean> members = new HashMap<>();
            members.put(player.getUniqueId(), true);

            //Creating the group.
            final Group group = new Group(player.getName(), args[1], members);

            //Getting the User of the sender.
            final User user = UserUtils.getUser((Player) sender);

            //Checking if the user was found.
            if (user == null) {

                //Feedbacking the player.
                sender.sendMessage(Feedbacks.SENDER_USER_NOT_FOUND.toString());
                return true;
            }

            //Adding the group to the player's groups list.
            user.addGroup(args[1], group.getUUID());
            //Adding the new group to DataStorage.
            DataStorage.addGroup(group);

            //Feedbacking the sender.
            sender.sendMessage(pluginTag + ChatColor.GREEN + " You have successfully created a group named: " + ChatColor.YELLOW + args[1] + ChatColor.GREEN + ".");
            return true;

        } else {

            //Feedbacking the console.
            sender.sendMessage(Feedbacks.CONSOLE_MESSAGE_ERROR.toString());
            return true;
        }
    }

    /**
     * Checks if the group name for this player is legit.
     *
     * @param sender: The CommandSender that will receive feedback messages.
     * @param name  : The group name to check if it's legit.
     * @return      : true if the name is legit, false otherwise.
     */
    private static boolean isLegit(final CommandSender sender, final String name) {

        //Getting the pluginTag to ease my work.
        final String pluginTag = DataStorage.pluginTag;

        //Checking if the given group name is banned.
        if (BannedGroupNames.containsName(name)) {

            //Feedbacking the sender.
            sender.sendMessage(Feedbacks.BANNED_NAME.toString());
            return false;
        }

        //Finding if there is a group with this name already.
        final Group group = GroupUtils.findGroup(name);
        if (group != null) {

            //Feedbacking the sender.
            sender.sendMessage(Feedbacks.USED_NAME.toString());
            return false;
        }

        //Checking if the group name is too long or too short.
        if (name.length() > 32) {

            //Feedbacking the sender.
            sender.sendMessage(Feedbacks.NAME_TOO_LONG.toString());
            return false;
        } else if (name.length() < 4) {

            //Feedbacking the sender.
            sender.sendMessage(Feedbacks.NAME_TOO_SHORT.toString());
            return false;
        }

        //This group name is legit.
        return true;
    }
}
