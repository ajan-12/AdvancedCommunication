package me.ajan12.advancedcommunication.Commands.GroupCommand;

import me.ajan12.advancedcommunication.Enums.Feedbacks;
import me.ajan12.advancedcommunication.Objects.Group;
import me.ajan12.advancedcommunication.Objects.User;
import me.ajan12.advancedcommunication.Utilities.UserUtils;
import me.ajan12.advancedcommunication.Utilities.GroupUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class GroupLeaveCommand {

    static boolean execute(final CommandSender sender, final String[] args) {
        //Checking if the sender is a Player.
        if (!(sender instanceof Player)) {

            //Feedbacking the console.
            sender.sendMessage(Feedbacks.CONSOLE_MESSAGE_ERROR.toString());
            return true;
        }

        //Getting the group player specified.
        final Group group = GroupUtils.findGroup(args[1]);
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

        //Getting the User of the sender.
        final User user = UserUtils.getUser((Player) sender);

        //Checking if the user was found.
        if (user == null) {

            //Feedbacking the player.
            sender.sendMessage(Feedbacks.SENDER_USER_NOT_FOUND.toString());
            return true;
        }

        //Removing the user from the group.
        group.memberLeave((Player) sender);
        return true;
    }
}
