package me.ajan12.advancedcommunication.Commands.ReplyCommand;

import me.ajan12.advancedcommunication.Enums.Feedbacks;
import me.ajan12.advancedcommunication.Objects.User;
import me.ajan12.advancedcommunication.Utilities.UserUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReplyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        //Checking if the argument amount is 0 or the 1st argument is "help".
        if (args.length == 0 || args[0].equalsIgnoreCase("help"))  return HelpCommand.execute(sender);

        //Checking if the sender is a Player.
        if (sender instanceof Player) {
            //Getting the User of the sender.
            final User user = UserUtils.getUser((Player) sender);

            //Checking if the user was found.
            if (user == null) {

                //Feedbacking the player.
                sender.sendMessage(Feedbacks.SENDER_USER_NOT_FOUND.toString());
                return true;
            }

            //Checking if the User was messaged by someone else.
            if (user.isMessagedBy()) {

                //Creating the message StringBuilder.
                final StringBuilder message = new StringBuilder(args[0]);

                //Iterating over the words.
                for (int i = 1; i < args.length; i++) {
                    //Adding the word to the message.
                    message.append(" ").append(args[i]);
                }

                //Sending the message to the messagedBy.
                user.getMessagedBy().sendMessage(user, message.toString());
                return true;
            } else {

                //Feedbacking the player.
                sender.sendMessage(Feedbacks.NOT_MESSAGED_BY.toString());
                return true;
            }
        } else {

            //Feedbacking the console.
            sender.sendMessage(Feedbacks.CONSOLE_MESSAGE_ERROR.toString());
            return true;
        }
    }
}
