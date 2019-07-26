package me.ajan12.advancedcommunication.Commands.GroupCommand;

import me.ajan12.advancedcommunication.Enums.Feedbacks;
import me.ajan12.advancedcommunication.Objects.Group;
import me.ajan12.advancedcommunication.Utilities.DataStorage;
import me.ajan12.advancedcommunication.Utilities.GroupUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

class GroupInfoCommand {

    static boolean execute(final CommandSender sender, final String target) {

        //Easing my work in the next lines.
        final String pluginTag = DataStorage.pluginTag;

        //Getting the targeting Group.
        final Group group = GroupUtils.findGroup(target);
        //Checking if the group exists.
        if (group == null) {

            //Feedbacking the sender.
            sender.sendMessage(Feedbacks.GROUP_NOT_FOUND.toString());
            return true;
        }

        //Constructing the info parts about members.
        final StringBuilder groupAdmins = new StringBuilder();
        final StringBuilder members = new StringBuilder();
        //Iterating over the members of the group.
        for (Map.Entry<UUID, Boolean> member : group.getMembers().entrySet()) {

            //Getting the OfflinePlayer of the member.
            final OfflinePlayer player = Bukkit.getOfflinePlayer(member.getKey());
            //The color of the playername to add.
            final ChatColor color = player.isOnline() ? ChatColor.GREEN : ChatColor.RED;

            //Checking if the member is a group admin.
            if (member.getValue()) {

                //Adding the member to groupAdmins.
                groupAdmins.append(color).append(player.getName());
            } else {

                //Adding the member to members.
                members.append(color).append(player.getName());
            }
        }

        //Info header.
        sender.sendMessage(pluginTag + ChatColor.DARK_AQUA + " ----------- " + ChatColor.YELLOW + group.getName() + ChatColor.DARK_AQUA + " ----------- " + pluginTag);
        //Group description.
        sender.sendMessage(ChatColor.DARK_GREEN + "Group Description: " + ChatColor.AQUA + group.getDescription());
        //Group creation time.
        sender.sendMessage(ChatColor.DARK_GREEN + "Created on: " + new SimpleDateFormat("dd/MM/yyyy @ mm:HH").format(new Date(group.getCreatedTime())));

        //Creating a space.
        sender.sendMessage(" ");

        //Sending the sender the info about members.
        sender.sendMessage(ChatColor.DARK_GREEN + "Group Admins: " + groupAdmins.toString());
        sender.sendMessage(ChatColor.DARK_GREEN + "Group Members: " + members.toString());

        //Creating a space.
        sender.sendMessage(" ");

        //Sending the sender the info about group settings.
        sender.sendMessage(ChatColor.DARK_GREEN + "Group Settings: " + ChatColor.AQUA + "Send-Messages: " + ChatColor.GRAY + group.isSendMessages() + ChatColor.AQUA + " Edit-Info: " + ChatColor.GRAY + group.isEditInfo() + ChatColor.AQUA + " Slowdown: " + ChatColor.GRAY + group.isInSlowdown());
        return true;
    }
}
