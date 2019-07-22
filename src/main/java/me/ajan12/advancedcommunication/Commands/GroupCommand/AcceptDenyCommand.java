package me.ajan12.advancedcommunication.Commands.GroupCommand;

import me.ajan12.advancedcommunication.Enums.Feedbacks;
import me.ajan12.advancedcommunication.Objects.Group;
import me.ajan12.advancedcommunication.Objects.User;
import me.ajan12.advancedcommunication.Utilities.DataStorage;
import me.ajan12.advancedcommunication.Utilities.UserUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class AcceptDenyCommand {

    static boolean execute(final CommandSender sender, final boolean isAccepting) {

        //Checking if the sender is a Player.
        if (sender instanceof Player) {
            //Checking if the player used "accept".
            if (isAccepting) {

                //Getting the User of the sender.
                final User user = UserUtils.getUser((Player) sender);

                //Checking if the user was found.
                if (user == null) {

                    //Feedbacking the player.
                    sender.sendMessage(Feedbacks.SENDER_USER_NOT_FOUND.toString());
                    return true;
                }

                //Checking if the User is invited to a group.
                if (user.isInvited()) {

                    //Getting the group player accepts the invitation of.
                    final Group group = DataStorage.groups.get(user.getInvitedGroup());

                    //Adding the member to the group.
                    group.addMember((Player) sender);

                    //Removing the invitation.
                    user.setInvited(false);
                    user.setInviter(null);
                    user.setInvitedGroup(null);

                    return true;
                } else {

                    //Feedbacking the player.
                    sender.sendMessage(Feedbacks.NOT_INVITED.toString());
                    return true;
                }
            } else {

                //Getting the User of the sender.
                final User user = UserUtils.getUser((Player) sender);

                //Checking if the user was found.
                if (user == null) {

                    //Feedbacking the player.
                    sender.sendMessage(Feedbacks.SENDER_USER_NOT_FOUND.toString());
                    return true;
                }

                //Checking if the User is invited to a group.
                if (user.isInvited()) {

                    //Removing the invitation.
                    user.setInvited(false);
                    user.setInviter(null);
                    user.setInvitedGroup(null);

                    //Feedbacking the player.
                    sender.sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " You have denied the invitation.");
                    return true;
                } else {

                    //Feedbacking the player.
                    sender.sendMessage(Feedbacks.NOT_INVITED.toString());
                    return true;
                }
            }
        } else {

            //Feedbacking the console.
            sender.sendMessage(Feedbacks.CONSOLE_MESSAGE_ERROR.toString());
            return true;
        }
    }
}
