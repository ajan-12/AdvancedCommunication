package me.ajan12.advancedcommunication.Commands.GroupCommand;

import me.ajan12.advancedcommunication.Utilities.DataStorage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

class HelpCommand {

    static boolean execute(final CommandSender player) {
        //Easing my work in the next lines by creating a local variable for plugin tag.
        final String pluginTag = DataStorage.pluginTag;

        //Pasting the help page header on the chat.
        player.sendMessage(pluginTag + ChatColor.DARK_AQUA + " ----*---------*---- " + ChatColor.GOLD + "/group HELP" + ChatColor.DARK_AQUA + " ----*---------*---- " + pluginTag);
        // -- "/gr help" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/group help" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Shows this page.");

        // -- "/gr" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/group" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Un-focuses if you are focused on a player or a group.");

        // -- "/gr list" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/group list" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Lists down the groups you are currently in.");
        // -- "/gr list <player>" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/group list <player>" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Lists down the groups the player is currently in.");

        // -- "/gr create <name>" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/group create <name>" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Creates a new group with the given name.");
        player.sendMessage(pluginTag + " --- " + ChatColor.RED + "The group name must be between 4 and 32 characters!");
        // -- "/gr leave <name>" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/group leave <name>" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Leaves the specified group.");

        //Creating a space.
        player.sendMessage(" ");

        // -- "/gr accept" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/group accept" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Accepts an incoming group invitation.");
        player.sendMessage(pluginTag + " --- " + ChatColor.RED + "If you don't accept an incoming invite it'll expire in 5 minutes.");
        // -- "/gr deny" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/group deny" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Denies an incoming group invitation.");
        player.sendMessage(pluginTag + " --- " + ChatColor.RED + "If you don't deny an incoming invite it'll expire in 5 minutes.");

        //Creating a space.
        player.sendMessage(" ");

        // -- "/gr <group>" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/group <group>" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Toggles the focus on the group chat.");
        player.sendMessage(pluginTag + " --- " + ChatColor.RED + "You can only focus on 1 group/player at a time!");
        player.sendMessage(pluginTag + " --- " + ChatColor.RED + "Focusing on a different group causes the focus to change.");

        // -- "/gr <group> <message>" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/group <group> <message>" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Sends a message to the given group.");
        player.sendMessage(pluginTag + " --- " + ChatColor.RED + "This doesn't affect your focus status.");

        // -- "/gr <group> info" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/group <group> info" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Shows the information about the group.");

        // -- "/gr <group> invite <player>" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/group <group> invite <player>" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Invites a player to the group.");
        player.sendMessage(pluginTag + " --- " + ChatColor.DARK_RED + "Group Admin command.");
        // -- "/gr <group> kick <player>" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/group <group> kick <player>" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Kicks a player from the group.");
        player.sendMessage(pluginTag + " --- " + ChatColor.DARK_RED + "Group Admin command.");

        //Creating a space.
        player.sendMessage(" ");

        // -- "/gr <group> disband" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/group <group> disband" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Disbands a group.");
        player.sendMessage(pluginTag + " --- " + ChatColor.DARK_RED + "Group Admin command.");

        // -- Command aliases info.
        player.sendMessage(pluginTag + " - " + ChatColor.AQUA + "'/group' command aliases: " + ChatColor.YELLOW + "gr, groups" + ChatColor.AQUA + ".");
        //Pasting the help page footer on the chat.
        player.sendMessage(pluginTag + ChatColor.DARK_AQUA + " ----------+---------------------------+---------- " + pluginTag);

        return true;
    }
}
