package me.ajan12.advancedcommunication.Commands.MainCommand;

import me.ajan12.advancedcommunication.Utilities.DataStorage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class HelpCommand {

    static boolean execute(final CommandSender sender) {

        //Easing my work in the next lines by creating a local variable for plugin tag.
        final String pluginTag = DataStorage.pluginTag;

        //Checking if the sender is a Player.
        if (sender instanceof Player) {
            //Checking if the player is able to view help page.
            if (!sender.hasPermission("advancedcommunication.command.help")) return true;
        }

        //Pasting the help page header on the chat.
        sender.sendMessage(pluginTag + ChatColor.DARK_AQUA + " ---------- " + ChatColor.GOLD + "/advancedcommunication HELP" + ChatColor.DARK_AQUA + " ---------- " + pluginTag);
        // -- "/ac help" command.
        sender.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/ac help" + ChatColor.GREEN + " : " + ChatColor.AQUA + "Shows this page.");

        //Creating a space.
        sender.sendMessage(" ");

        // -- "/ac spy" command.
        sender.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/ac spy" + ChatColor.GREEN + " : " + ChatColor.AQUA + "Toggles the spy status of the executor of the command.");
        sender.sendMessage(pluginTag + " --- " + ChatColor.RED + " This command requires some permissions.");
        sender.sendMessage(pluginTag + " --- " + ChatColor.RED + " Spy status is the ability to view all private messages sent within the server.");
        sender.sendMessage(pluginTag + " --- " + ChatColor.YELLOW + "/ac spy <target>" + ChatColor.RED + " To toggle spy status of someone else.");

        //Creating a space.
        sender.sendMessage(" ");

        // -- "/ac save groups|users|<group>|<user>" command.
        sender.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/ac save groups|users|<group>|<user>" + ChatColor.GREEN + " : " + ChatColor.AQUA + "Saves a specific group/user by their UUIDs/names or all the groups/users.");
        // -- "/ac purge groups|users|<group>|<user>" command.
        sender.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/ac purge groups|users|<group>|<user>" + ChatColor.GREEN + " : " + ChatColor.AQUA + "Purges a specific group/user by their UUIDs/names or all the groups/users.");

        //Creating a space.
        sender.sendMessage(" ");

        // -- "/ac handle-user <player>" command.
        sender.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/ac handle-user <player>" + ChatColor.GREEN + " : " + ChatColor.AQUA + "Handles the user database of a specific player.");
        // -- "/ac state" command.
        sender.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/ac state" + ChatColor.GREEN + " : " + ChatColor.AQUA + "Tells what the plugin is currently doing.");

        //Creating a space.
        sender.sendMessage(" ");

        // -- Command aliases info.
        sender.sendMessage(pluginTag + " - " + ChatColor.AQUA + "/advancedcommunication command aliases: " + ChatColor.YELLOW + "ac, advc, acomm, advcomm" + ChatColor.AQUA + ".");
        return true;
    }

}
