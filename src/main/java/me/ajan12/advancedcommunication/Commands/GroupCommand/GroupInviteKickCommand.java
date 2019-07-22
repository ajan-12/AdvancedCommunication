package me.ajan12.advancedcommunication.Commands.GroupCommand;

import me.ajan12.advancedcommunication.Enums.Feedbacks;
import me.ajan12.advancedcommunication.Objects.Console;
import me.ajan12.advancedcommunication.Objects.Group;
import me.ajan12.advancedcommunication.Objects.User;
import me.ajan12.advancedcommunication.Utilities.DataStorage;
import me.ajan12.advancedcommunication.Utilities.GroupUtils;
import me.ajan12.advancedcommunication.Utilities.UserUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class GroupInviteKickCommand {

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

            //Getting the target player.
            final Player target = Bukkit.getPlayer(args[2]);
            //Checking if the target player was found.
            if (target == null) {

                //Feedbacking the player.
                sender.sendMessage(Feedbacks.PLAYER_NOT_FOUND.toString());
                return true;
            }

            //Getting the User of the target.
            final User targetUser = UserUtils.getUser(target);

            //Checking if the user was found.
            if (targetUser == null) {

                //Feedbacking the players.
                target.sendMessage(Feedbacks.TARGET_USER_NOT_FOUND_TARGET.toString());
                sender.sendMessage(Feedbacks.TARGET_USER_NOT_FOUND_SENDER.toString());
                return true;
            }

            //Getting the User of the sender.
            final User senderUser = UserUtils.getUser((Player) sender);

            //Checking if the user was found.
            if (senderUser == null) {

                //Feedbacking the sender.
                sender.sendMessage(Feedbacks.SENDER_USER_NOT_FOUND.toString());
                return true;
            }

            //Checking if the sender is trying to invite a player.
            if (args[1].equalsIgnoreCase("invite")) {

                //Checking if the sender is trying to invite themselves.
                if (target.getUniqueId().equals(((Player) sender).getUniqueId())) {

                    //Feedbacking the player.
                    sender.sendMessage(Feedbacks.INVITE_SELF.toString());
                    return true;
                }

                //Checking if the target user is already in the group.
                if (targetUser.getGroups().containsKey(args[0])) {

                    //Feedbacking the player.
                    sender.sendMessage(Feedbacks.IN_GROUP_ALREADY.toString());
                    return true;
                }

                //Inviting the targetUser.
                targetUser.inviteToGroup(group, sender.getName());
                //Feedbacking the player.
                sender.sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Successfully invited " + ChatColor.YELLOW + args[2] + ChatColor.GREEN + " to group " + ChatColor.YELLOW + args[0] + ChatColor.GREEN + ".");
                return true;
            //This will happen if the sender is trying to kick a player.
            } else {

                //Checking if the sender is trying to kick themselves.
                if (target.getUniqueId().equals(((Player) sender).getUniqueId())) {

                    //Feedbacking the player.
                    sender.sendMessage(Feedbacks.KICK_SELF.toString());
                    return true;
                }

                //Checking if the target user is in the group.
                if (targetUser.getGroups().containsKey(args[0])) {

                    //Feedbacking the player.
                    sender.sendMessage(Feedbacks.TARGET_NOT_MEMBER.toString());
                    return true;
                }

                //Kicking the target.
                group.kickMember(senderUser, target);
                //Feedbacking the player.
                sender.sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Successfully kicked " + ChatColor.YELLOW + args[2] + ChatColor.GREEN + " from group " + ChatColor.YELLOW + args[0] + ChatColor.GREEN + ".");
                return true;
            }
        } else {

            //Getting the group console specified.
            final Group group = GroupUtils.findGroup(args[0]);
            //Checking if the group exists.
            if (group == null) {

                //Feedbacking the console.
                sender.sendMessage(Feedbacks.GROUP_NOT_FOUND.toString());
                return true;
            }

            //Getting the target player.
            final Player target = Bukkit.getPlayer(args[2]);
            //Checking if the target player was found.
            if (target == null) {

                //Feedbacking the console.
                sender.sendMessage(Feedbacks.PLAYER_NOT_FOUND.toString());
                return true;
            }

            //Getting the User of the target.
            final User targetUser = UserUtils.getUser(target);

            //Checking if the user was found.
            if (targetUser == null) {

                //Feedbacking the player and console.
                target.sendMessage(Feedbacks.TARGET_USER_NOT_FOUND_TARGET.toString());
                sender.sendMessage(Feedbacks.TARGET_USER_NOT_FOUND_SENDER.toString());
                return true;

            }

            //Checking if the sender is trying to invite a player.
            if (args[1].equalsIgnoreCase("invite")) {

                //Checking if the target user is already in the group.
                if (targetUser.getGroups().containsKey(args[0])) {

                    //Feedbacking the console.
                    sender.sendMessage(Feedbacks.IN_GROUP_ALREADY.toString());
                    return true;
                }

                //Inviting the targetUser.
                targetUser.inviteToGroup(group, new Console().getName());
                //Feedbacking the console.
                sender.sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Successfully invited " + ChatColor.YELLOW + args[2] + ChatColor.GREEN + " to group " + ChatColor.YELLOW + args[0] + ChatColor.GREEN + ".");
                return true;
            //This will happen if the sender is trying to kick a player.
            } else {

                //Checking if the target user is in the group.
                if (targetUser.getGroups().containsKey(args[0])) {

                    //Feedbacking the player.
                    sender.sendMessage(Feedbacks.TARGET_NOT_MEMBER.toString());
                    return true;
                }

                //Kicking the target.
                group.kickMember(new Console(), target);
                //Feedbacking the console.
                sender.sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Successfully kicked " + ChatColor.YELLOW + args[2] + ChatColor.GREEN + " from group " + ChatColor.YELLOW + args[0] + ChatColor.GREEN + ".");
                return true;
            }
        }
    }
}
