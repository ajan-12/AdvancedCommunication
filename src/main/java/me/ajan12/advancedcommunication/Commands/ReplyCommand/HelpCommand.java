package me.ajan12.advancedcommunication.Commands.ReplyCommand;

import me.ajan12.advancedcommunication.Utilities.DataStorage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

class HelpCommand {

    static boolean execute(final CommandSender sender) {
        //Easing my work in the next lines by creating a local variable for plugin tag.
        final String pluginTag = DataStorage.pluginTag;

        //Pasting the help page header on the chat.
        sender.sendMessage(pluginTag + ChatColor.DARK_AQUA + " ---------- " + ChatColor.GOLD + "/reply HELP" + ChatColor.DARK_AQUA + " ---------- " + pluginTag);
        // -- "/r help" command.
        sender.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/r help" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Shows this page.");
        // -- "/r <message>" command.
        sender.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/r <message>" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Sends a one-time message to the last person messaged you.");
        sender.sendMessage(pluginTag + " --- " + ChatColor.RED + "This doesn't affect your focus status.");
        // -- Command aliases info.
        sender.sendMessage(pluginTag + " - " + ChatColor.AQUA + "/reply command aliases: " + ChatColor.YELLOW + "r" + ChatColor.AQUA + ".");
        return true;
    }
}
