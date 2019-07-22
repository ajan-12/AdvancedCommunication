package me.ajan12.advancedcommunication.Commands.MainCommand;

import me.ajan12.advancedcommunication.Enums.Feedbacks;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Checking if the argument amount is 1 or 2.
        if (!(args.length > 2) && args.length != 0) {
            //Checking if the 1st argument is "spy".
            if (args[0].equals("spy")) {

                //Setting this as sender's name because we want the command to work even without a target as 2nd argument.
                String target = sender.getName();
                //If there's a target player, we set the target variable to that player.
                if (args.length == 2) target = args[1];

                //Checking if the sender is a Player, since this command can only be applied in-game.
                if (sender instanceof Player) {

                    //Toggling the spy status and getting if it succeeded.
                    return SpyCommand.execute((Player) sender, target);
                //If the sender wasn't a Player. This command is special for players.
                } else {

                    //Pasting an error message to the sender.
                    sender.sendMessage(Feedbacks.CONSOLE_MESSAGE_ERROR.toString());
                    //Returning true because we don't want to display the command usage.
                    return true;
                }
            //Checking if the 1st argument is "group".
            } else if (args[0].equalsIgnoreCase("groups")) {
                //Checking if the 2nd argument is "save".
                if (args[1].equalsIgnoreCase("save")) {

                    //Executing the SavePurgeBulkCommand.
                    return SavePurgeCommand.SavePurgeBulkCommand.execute(sender, true, true);
                //Checking if the 1st argument is "group".
                } else if (args[1].equalsIgnoreCase("purge")) {

                    //Executing the SavePurgeBulkCommand.
                    return SavePurgeCommand.SavePurgeBulkCommand.execute(sender, true, false);
                }
            //Checking if the 1st argument is "group".
            } else if (args[0].equalsIgnoreCase("users")) {

                //Checking if the 2nd argument is "save".
                if (args[1].equalsIgnoreCase("save")) {

                    //Executing the SavePurgeBulkCommand.
                    return SavePurgeCommand.SavePurgeBulkCommand.execute(sender, false, true);
                //Checking if the 1st argument is "group".
                } else if (args[1].equalsIgnoreCase("purge")) {

                    //Executing the SavePurgeBulkCommand.
                    return SavePurgeCommand.SavePurgeBulkCommand.execute(sender, false, false);
                }
            //Checking if the 1st argument is "save".
            } else if (args[0].equalsIgnoreCase("save")) {

                //Executing the SavePurgeSingleCommand.
                return SavePurgeCommand.SavePurgeSingleCommand.execute(sender, args[1], true);
            //Checking if the 1st argument is "purge".
            } else if (args[0].equalsIgnoreCase("purge")) {

                //Executing the SavePurgeSingleCommand.
                return SavePurgeCommand.SavePurgeSingleCommand.execute(sender, args[1], true);
            }
        }
        //If nothing was returned above we want to show the help page.
        return HelpCommand.execute(sender);
    }
}
