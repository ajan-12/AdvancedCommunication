package me.ajan12.advancedcommunication.Commands.MainCommand;

import me.ajan12.advancedcommunication.Utilities.DataStorage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

class HelpCommand {

    static boolean execute(final CommandSender player) {
        //Easing my work in the next lines by creating a local variable for plugin tag.
        final String pluginTag = DataStorage.pluginTag;
        //Checking if the player is able to view help page.
        if (!player.hasPermission("advancedcommunication.command.help")) return true;

        //Pasting the help page header on the chat.
        player.sendMessage(pluginTag + ChatColor.DARK_AQUA + " ---------- " + ChatColor.GOLD + "/advancedcommunication HELP" + ChatColor.DARK_AQUA + " ---------- " + pluginTag);
        // -- "/ac help" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/ac help" + ChatColor.GREEN + " : " + ChatColor.AQUA + "Shows this page.");

        //Creating a space.
        player.sendMessage(" ");

        // -- "/ac spy" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/ac spy" + ChatColor.GREEN + " : " + ChatColor.AQUA + "Toggles the spy status of the executor of the command.");
        player.sendMessage(pluginTag + " --- " + ChatColor.RED + " This command requires some permissions.");
        player.sendMessage(pluginTag + " --- " + ChatColor.RED + " Spy status is the ability to view all private messages sent within the server.");
        player.sendMessage(pluginTag + " --- " + ChatColor.YELLOW + "/ac spy <target>" + ChatColor.RED + " To toggle spy status of someone else.");

        //Creating a space.
        player.sendMessage(" ");

        // -- "/ac groups save" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/ac groups save" + ChatColor.GREEN + " : " + ChatColor.AQUA + "Makes a request to save the groups to database.");
        // -- "/ac groups purge" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/ac groups purge" + ChatColor.GREEN + " : " + ChatColor.AQUA + "Makes a request to purge the groups.");

        // -- "/ac users save" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/ac users save" + ChatColor.GREEN + " : " + ChatColor.AQUA + "Makes a request to save the users to database.");
        // -- "/ac users purge" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/ac users purge" + ChatColor.GREEN + " : " + ChatColor.AQUA + "Makes a request to purge the users.");

        //Creating a space.
        player.sendMessage(" ");

        // -- "/ac save user" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/ac save <user>" + ChatColor.GREEN + " : " + ChatColor.AQUA + "Saves a specific user. Enter the user name/uuid to <user>.");
        // -- "/ac purge user" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/ac purge <user>" + ChatColor.GREEN + " : " + ChatColor.AQUA + "Saves a specific user. Enter the user name/uuid to <user>.");

        // -- "/ac save group" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/ac save <group>" + ChatColor.GREEN + " : " + ChatColor.AQUA + "Saves a specific group. Enter the group name to <group>.");
        // -- "/ac purge group" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/ac purge <group>" + ChatColor.GREEN + " : " + ChatColor.AQUA + "Saves a specific group. Enter the group name to <group>.");

        //Creating a space.
        player.sendMessage(" ");

        // -- Command aliases info.
        player.sendMessage(pluginTag + " - " + ChatColor.AQUA + "/advancedcommunication command aliases: " + ChatColor.YELLOW + "ac, advc, acomm, advcomm" + ChatColor.AQUA + ".");
        //Pasting the help page footer on the chat.
        player.sendMessage(pluginTag + ChatColor.DARK_AQUA + " ----------+---------------------------+---------- " + pluginTag);

        return true;
    }

}
