package me.ajan12.advancedcommunication.Commands.MessageCommand;

import me.ajan12.advancedcommunication.Utilities.DataStorage;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

class HelpCommand {

    static boolean execute(final CommandSender sender) {
        //Easing my work in the next lines by creating a local variable for plugin tag.
        final String pluginTag = DataStorage.pluginTag;

        //Pasting the help page header on the chat.
        sender.sendMessage(pluginTag + ChatColor.DARK_AQUA + " ---------- " + ChatColor.GOLD + "/message HELP" + ChatColor.DARK_AQUA + " ---------- " + pluginTag);
        // -- "/msg" command.
        sender.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/msg" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Un-focuses if you are focused on a player or a group.");
        // -- "/msg help" command.
        sender.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/msg help" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Shows this page.");
        // -- "/msg <player>" command.
        sender.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/msg <player>" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Focuses/Un-focuses on a player.");
        // -- "/msg <player> <message>" command.
        sender.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/msg <player> <message>" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Sends a one-time message to a player.");
        sender.sendMessage(pluginTag + " --- " + ChatColor.RED + "This doesn't affect your focus status.");
        // -- Command aliases info.
        sender.sendMessage(pluginTag + " - " + ChatColor.AQUA + "/message command aliases: " + ChatColor.YELLOW + "privatemessage, private-message, pmsg, msg, dm, dmsg, directmessage, direct-message" + ChatColor.AQUA + ".");
        return true;
    }
}
