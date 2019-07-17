package me.ajan12.advancedcommunication.Commands.GroupCommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GroupCommand implements CommandExecutor {
    //TODO: /group <help|list> -> Help page and lists the groups player is in. DONE
    // ||||| /group list <player> -> Lists the groups player is in. DONE
    // ||||| /group <name> -> Focuses on a group.
    // ||||| /group <name> info -> Informs the player about the group(players, description, name, settings).
    // ||||| /group <name> invite|kick <player> -> Invites/kicks a player in the group.
    // ||||| /group <name> mute <time> -> Mutes the group for some time for the player.
    // ||||| /group <name> set admin <player> -> Sets the admin status of a player.
    // ||||| /group <name> set <send-messages|edit-info> <all|admins> -> Sets the group settings.

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //Checking if the first argument is "help".
        if (args[0].equalsIgnoreCase("help")) {

            //Showing the help page to the sender.
            return HelpCommand.execute(sender);

        //Checking if the first argument is "list".
        } else if (args[0].equalsIgnoreCase("list")) {

            //Listing down the groups the sender/target is in.
            return ListCommand.execute(sender, args);

        } else {
            return false;
        }
    }
}
