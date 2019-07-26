package me.ajan12.advancedcommunication.Commands.GroupCommand.GroupSetCommand;

import me.ajan12.advancedcommunication.Utilities.DataStorage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

class HelpCommand {

    static boolean execute(final CommandSender sender) {
        //Easing my work in the next lines by creating a local variable for plugin tag.
        final String pluginTag = DataStorage.pluginTag;

        //Pasting the help page header on the chat.
        sender.sendMessage(pluginTag + ChatColor.DARK_AQUA + " ---------- " + ChatColor.GOLD + "/group <group> set HELP" + ChatColor.DARK_AQUA + " ---------- " + pluginTag);

        // -- "/group <group> set help" command.
        sender.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/group <group> set help" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Shows this page.");

        // -- "/group <group> set name <name>" command.
        sender.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/group <group> set name <name>" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Changes the name of the group specified.");
        // -- "/group <group> set description <description>" command.
        sender.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/group <group> set description <description>" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Changes the description of the group specified.");

        // -- "/group <group> set edit-info <on|off>" command.
        sender.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/group <group> set edit-info <on|off>" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Changes the ability of members' group info editing.");
        // -- "/group <group> set slowdown <on|off>" command.
        sender.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/group <group> set slowdown <on|off>" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Changes the ability of group message slowdown.");
        // -- "/group <group> set send-messages <on|off>" command.
        sender.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/group <group> set send-messages <on|off>" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Changes the ability of members' message sending.");

        // -- "/group <group> set admin <player>" command.
        sender.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/group <group> set admin <player>" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Promotes/demotes a player to/from group administrate.");
        sender.sendMessage(pluginTag + " --- " + ChatColor.DARK_RED + "Group Admin command.");
        return true;
    }
}
