package me.ajan12.advancedcommunication.Commands.MainCommand;

import me.ajan12.advancedcommunication.Utilities.DataStorage;

import org.bukkit.ChatColor;
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

                //Setting a String because we want the command to work even without a target as 2nd argument.
                String target = sender.getName();
                //If there's a target player, we set the target variable to that player.
                if (args.length == 2) target = args[1];

                //Checking if the sender is a Player, since this command can only be applied in-game.
                if (sender instanceof Player) {
                    //Toggling the spy status and getting if it succeeded.
                    final SpyCommand.CommandSuccess success = SpyCommand.execute((Player) sender, target);

                    //To shorten some if-else's.
                    switch (success) {
                        //if the target wasn't found.
                        case TARGET_NOT_FOUND:
                            sender.sendMessage(DataStorage.pluginTag + ChatColor.DARK_RED + " The target player cannot be found.");
                            break;

                        //If the sender didn't have permission for self-toggling or other-toggling.
                        case NO_PERMISSION_SELF:
                        case NO_PERMISSION_OTHER:
                            sender.sendMessage(DataStorage.pluginTag + ChatColor.DARK_RED + " You do not have permission to do this!");
                            break;
                    }

                    //Returning true because we don't want to display the command usage.
                    return true;
                //If the sender wasn't a Player. This command is special for players.
                } else {
                    //Pasting an error message to the sender.
                    sender.sendMessage(DataStorage.pluginTag + ChatColor.DARK_RED + " This command can only be applied in-game!");
                    //Returning true because we don't want to display the command usage.
                    return true;
                }

            }

        }
        //If nothing was returned above we want to show the help page or the command usage(nothing).
        return HelpCommand.execute(sender);
    }

}
