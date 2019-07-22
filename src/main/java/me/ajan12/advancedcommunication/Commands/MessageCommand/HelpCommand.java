package me.ajan12.advancedcommunication.Commands.MessageCommand;

import me.ajan12.advancedcommunication.Utilities.DataStorage;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

class HelpCommand {

    static boolean execute(final CommandSender player) {
        //Easing my work in the next lines by creating a local variable for plugin tag.
        final String pluginTag = DataStorage.pluginTag;

        //Pasting the help page header on the chat.
        player.sendMessage(pluginTag + ChatColor.DARK_AQUA + " ---------- " + ChatColor.GOLD + "/message HELP" + ChatColor.DARK_AQUA + " ---------- " + pluginTag);
        // -- "/msg" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/msg" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Un-focuses if you are focused on a player or a group.");
        // -- "/msg help" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/msg help" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Shows this page.");
        // -- "/msg <player>" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/msg <player>" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Focuses/Un-focuses on a player.");
        // -- "/msg <player> <message>" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/msg <player> <message>" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Sends a one-time message to a player.");
        player.sendMessage(pluginTag + " --- " + ChatColor.RED + "This doesn't affect your focus status.");
        // -- Command aliases info.
        player.sendMessage(pluginTag + " - " + ChatColor.AQUA + "/advancedcommunication command aliases: " + ChatColor.YELLOW + "message, privatemessage, private-message, pmsg, dm, dmsg, directmessage, direct-message" + ChatColor.AQUA + ".");
        //Pasting the help page footer on the chat.
        player.sendMessage(pluginTag + ChatColor.DARK_AQUA + " ----------+---------------------------+---------- " + pluginTag);

        return true;
    }
}
