package me.ajan12.advancedcommunication.Commands.GroupCommand.GroupSetCommand;

import me.ajan12.advancedcommunication.Enums.Feedbacks;
import me.ajan12.advancedcommunication.Objects.Group;
import me.ajan12.advancedcommunication.Objects.User;
import me.ajan12.advancedcommunication.Utilities.GroupUtils;
import me.ajan12.advancedcommunication.Utilities.UserUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GroupSetCommand {

    public static boolean execute(final CommandSender sender, final String[] args) {

        //Checking if the argument amount is 2 or 3rd argument equals to "help".
        if (args.length < 4 || args[2].equalsIgnoreCase("help")) return HelpCommand.execute(sender);

        //Getting the targeting group.
        final Group group = GroupUtils.findGroup(args[0]);
        //Checking if the group exists.
        if (group == null) {

            //Feedbacking the sender.
            sender.sendMessage(Feedbacks.GROUP_NOT_FOUND.toString());
            return true;
        }

        //Checking if the sender is a Player.
        if (sender instanceof Player) {
            //Checking if the player is a group admin.
            if (!group.getMembers().get(((Player) sender).getUniqueId())) {

                //Checking if members can edit group info.
                if (!group.isEditInfo()) {
                    //Feedbacking the player.
                    sender.sendMessage(Feedbacks.NOT_ADMIN.toString());
                    return true;
                }
            }
        }

        //Checking if the sender is trying to set the name of a group.
        if (args[2].equalsIgnoreCase("name")) {

            //The old name of the group.
            final String oldName = group.getName();
            //Changing the group name.
            group.setName(args[3]);

            //Iterating over the members of the group.
            for (final UUID userUUID : group.getMembers().keySet()) {

                //Getting the user of the member.
                final User user = UserUtils.getUser(userUUID);
                //Removing and adding the group to change the name in the User data.
                user.removeGroup(oldName);
                user.addGroup(args[3], group.getUUID());
            }

            //Broadcasting the name change to whole group.
            group.broadcast(ChatColor.YELLOW + sender.getName() + ChatColor.AQUA + " has changed the name of this group from " + ChatColor.YELLOW + oldName + ChatColor.AQUA + " to " + ChatColor.YELLOW + args[3] + ChatColor.AQUA + ".");
            return true;
        //Checking if the sender is trying to set the description of a group.
        } else if (args[2].equalsIgnoreCase("description")) {

            //Constructing the new description.
            final StringBuilder newDescription = new StringBuilder(args[3]);
            //Iterating over the args to add them to the description.
            for (int i = 4; i < args.length; i++) {
                //Adding the argument to the description.
                newDescription.append(" ").append(args[i]);
            }

            //Changing the description of the group.
            group.setDescription(newDescription.toString());

            //Broadcasting the description change to whole group.
            group.broadcast(ChatColor.YELLOW + sender.getName() + ChatColor.AQUA + " has changed the description of this group.");
            return true;
        //Checking if 3rd argument equals to "edit-info".
        } else if (args[2].equalsIgnoreCase("edit-info")) {

            final boolean newEditInfo;
            //Checking if 4th argument equals to "on".
            if (args[3].equalsIgnoreCase("on")) {

                newEditInfo = true;
            //Checking if 4th argument equals to "off".
            } else if (args[3].equalsIgnoreCase("off")) {

                newEditInfo = false;
            } else return HelpCommand.execute(sender);

            //Toggling the edit-info.
            group.setEditInfo(newEditInfo);

            //This color will be used in broadcast.
            final ChatColor on_offColor = args[3].equalsIgnoreCase("on") ? ChatColor.GREEN : ChatColor.DARK_RED;

            //Broadcasting the setting change to whole group.
            group.broadcast(ChatColor.YELLOW + sender.getName() + ChatColor.AQUA + " has toggled the ability of members' group info editing " + on_offColor + args[3] + ChatColor.AQUA + ".");
            return true;
        //Checking if 3rd argument equals to "slowdown".
        } else if (args[2].equalsIgnoreCase("slowdown")) {

            final boolean newSlowdown;
            //Checking if 4th argument equals to "on".
            if (args[3].equalsIgnoreCase("on")) {

                newSlowdown = true;
            //Checking if 4th argument equals to "off".
            } else if (args[3].equalsIgnoreCase("off")) {

                newSlowdown = false;
            } else return HelpCommand.execute(sender);

            //Toggling the slowdown.
            group.setInSlowdown(newSlowdown);

            //This color will be used in broadcast.
            final ChatColor on_offColor = args[3].equalsIgnoreCase("on") ? ChatColor.GREEN : ChatColor.DARK_RED;

            //Broadcasting the setting change to whole group.
            group.broadcast(ChatColor.YELLOW + sender.getName() + ChatColor.AQUA + " has toggled the message slowdown " + on_offColor + args[3] + ChatColor.AQUA + ".");
            return true;
        //Checking if 3rd argument equals to "send-messages".
        } else if (args[2].equalsIgnoreCase("send-messages")) {

            final boolean newSendMessages;
            //Checking if 4th argument equals to "on".
            if (args[3].equalsIgnoreCase("on")) {

                newSendMessages = true;
                //Checking if 4th argument equals to "off".
            } else if (args[3].equalsIgnoreCase("off")) {

                newSendMessages = false;
            } else return HelpCommand.execute(sender);

            //Toggling the slowdown.
            group.setSendMessages(newSendMessages);

            //This color will be used in broadcast.
            final ChatColor on_offColor = args[3].equalsIgnoreCase("on") ? ChatColor.GREEN : ChatColor.DARK_RED;

            //Broadcasting the setting change to whole group.
            group.broadcast(ChatColor.YELLOW + sender.getName() + ChatColor.AQUA + " has toggled the ability of members' messages sending " + on_offColor + args[3] + ChatColor.AQUA + ".");
            return true;
        //Checking if the 3rd argument is "admin".
        } else if (args[2].equalsIgnoreCase("admin")) {

            //Checking if the sender is a Player.
            if (sender instanceof Player) {
                //Checking if the player is a group admin.
                if (!group.getMembers().get(((Player) sender).getUniqueId())) {

                    //Feedbacking the player.
                    sender.sendMessage(Feedbacks.NOT_ADMIN.toString());
                    return true;
                }
            }

            //Getting the targeting player.
            final Player player = Bukkit.getPlayer(args[3]);
            //Checking if player exists.
            if (player == null) {

                //Feedbacking the player.
                sender.sendMessage(Feedbacks.PLAYER_NOT_FOUND.toString());
                return true;
            }

            //Getting if the target player is already an admin.
            final boolean isAdmin = group.getMembers().get(player.getUniqueId());
            //Toggling the admin state of the target player.
            group.getMembers().replace(player.getUniqueId(), !isAdmin);

            //We'll use this in the broadcast.
            final String promotedDemoted = isAdmin ? "demoted" : "promoted";

            //Broadcasting the setting change to whole group.
            group.broadcast(ChatColor.YELLOW + sender.getName() + ChatColor.AQUA + " has " + promotedDemoted + ChatColor.YELLOW + args[3] + ChatColor.AQUA + ".");
            return true;
        } else return HelpCommand.execute(sender);
    }
}
