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
        // -- "/ac help" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/group help" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Shows this page.");

        // -- "/gr list" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/group list" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Lists down the groups you are currently in.");
        // -- "/gr list <player>" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/group list <player>" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Lists down the groups the player is currently in.");

        // -- "/gr <group>" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/group <group>" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Toggles the focus on the group chat.");
        player.sendMessage(" --- " + ChatColor.AQUA + "You can only focus on 1 group/player at a time!");
        player.sendMessage(" --- " + ChatColor.AQUA + "Focusing on a different group causes the focus to change.");

        // -- "/gr <group> info" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/group <group> info" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Shows the information about the group.");

        // -- "/gr <group> invite <player>" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/group <group> invite <player>" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Invites a player to the group.");
        player.sendMessage(" --- " + ChatColor.RED + "Group Admin command.");
        // -- "/gr <group> kick <player>" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/group <group> kick <player>" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Kicks a player from the group.");
        player.sendMessage(" --- " + ChatColor.RED + "Group Admin command.");

        // -- "/gr <group> set" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/group <group> kick <player>" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Kicks a player from the group.");
        player.sendMessage(" --- " + ChatColor.RED + "Group Admin command.");

        // -- Command aliases info.
        player.sendMessage(pluginTag + " - " + ChatColor.AQUA + "'/group' command aliases: " + ChatColor.YELLOW + "gr, groups" + ChatColor.AQUA + ".");
        //Pasting the help page footer on the chat.
        player.sendMessage(pluginTag + ChatColor.DARK_AQUA + " ----------+---------------------------+---------- " + pluginTag);

        return true;
    }
}
