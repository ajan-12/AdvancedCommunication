package me.ajan12.advancedcommunication.Commands.MainCommand;

import me.ajan12.advancedcommunication.Enums.Feedbacks;
import me.ajan12.advancedcommunication.Utilities.DataStorage;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
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
            //Checking if the 1st argument is "save".
            } else if (args[0].equalsIgnoreCase("save")) {

                //Executing the SaveCommand.
                return SaveCommand.execute(sender, args[1]);
            //Checking if the 1st argument is "purge".
            } else if (args[0].equalsIgnoreCase("purge")) {

                //Executing the PurgeCommand.
                return PurgeCommand.execute(sender, args[1]);
            //Shows the current pluginState.
            } else if (args[0].equalsIgnoreCase("state")) {

                //Checking if there are more than 1 argument.
                if (args.length > 1) return HelpCommand.execute(sender);

                //Feedbacking the sender.
                sender.sendMessage(DataStorage.pluginTag + ChatColor.AQUA + " Currently plugin is " + ChatColor.YELLOW + DataStorage.pluginState.toString() + ChatColor.AQUA + ".");
                return true;
            }
        }
        //If nothing was returned above we want to show the help page.
        return HelpCommand.execute(sender);
    }
}
