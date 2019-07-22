package me.ajan12.advancedcommunication.Commands.GroupCommand;

import me.ajan12.advancedcommunication.Enums.Feedbacks;
import me.ajan12.advancedcommunication.Objects.Console;
import me.ajan12.advancedcommunication.Objects.Group;
import me.ajan12.advancedcommunication.Objects.User;
import me.ajan12.advancedcommunication.Utilities.DataStorage;
import me.ajan12.advancedcommunication.Utilities.UserUtils;
import me.ajan12.advancedcommunication.Utilities.GroupUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

class GroupDisbandCommand {

    static boolean execute(final CommandSender sender, final String[] args) {
        //Checking if the sender is a Player.
        if (sender instanceof Player) {

            //Getting the group player specified.
            final Group group = GroupUtils.findGroup(args[0]);
            //Checking if the group exists.
            if (group == null) {

                //Feedbacking the player.
                sender.sendMessage(Feedbacks.GROUP_NOT_FOUND.toString());
                return true;
            }

            //Checking if the sender is a member of the group.
            if (!group.getMembers().containsKey(((Player) sender).getUniqueId())) {

                //Feedbacking the player.
                sender.sendMessage(Feedbacks.NOT_MEMBER.toString());
                return true;
            }

            //Checking if the sender is a group admin in the group.
            if (!group.getMembers().get(((Player) sender).getUniqueId())) {

                //Feedbacking the player.
                sender.sendMessage(Feedbacks.NOT_ADMIN.toString());
                return true;
            }

            //Feedbacking the group members.
            group.broadcast(ChatColor.DARK_RED + "The group is being disbanded by " + ChatColor.YELLOW + sender.getName() + ChatColor.DARK_RED + ".");

            //Removing the group from DataStorage.
            DataStorage.removeGroup(group);
            //Removing the group from the users' lists.
            for (final Map.Entry<UUID, Boolean> member : group.getMembers().entrySet()) {

                //Getting the User of the member.
                final User targetUser = UserUtils.getUser(member.getKey());
                //Checking if the user was found.
                if (targetUser == null) {

                    //Feedbacking the member.
                    sender.sendMessage(Feedbacks.SENDER_USER_NOT_FOUND.toString());
                    continue;
                }

                //Removing the group from member's list.
                targetUser.removeGroup(group.getName());
            }

            //Feedbacking the sender.
            sender.sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Successfully disbanded the group " + ChatColor.YELLOW + sender.getName() + ChatColor.GREEN + ".");
            return true;
        } else {

            //Getting the group player specified.
            final Group group = GroupUtils.findGroup(args[0]);
            //Checking if the group exists.
            if (group == null) {

                //Feedbacking the player.
                sender.sendMessage(Feedbacks.GROUP_NOT_FOUND.toString());
                return true;
            }

            //Feedbacking the group members.
            group.broadcast(ChatColor.DARK_RED + "The group is being disbanded by " + ChatColor.YELLOW + new Console().getName() + ChatColor.DARK_RED + ".");

            //Removing the group from DataStorage.
            DataStorage.removeGroup(group);
            //Removing the group from the users' lists.
            for (final Map.Entry<UUID, Boolean> member : group.getMembers().entrySet()) {

                //Getting the User of the member.
                final User targetUser = UserUtils.getUser(member.getKey());
                //Checking if the user was found.
                if (targetUser == null) {

                    //Feedbacking the member.
                    sender.sendMessage(Feedbacks.SENDER_USER_NOT_FOUND.toString());
                    continue;
                }

                //Removing the group from member's list.
                targetUser.removeGroup(group.getName());
            }

            //Feedbacking the sender.
            sender.sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Successfully disbanded the group " + ChatColor.YELLOW + new Console().getName() + ChatColor.GREEN + ".");
            return true;
        }
    }
}
