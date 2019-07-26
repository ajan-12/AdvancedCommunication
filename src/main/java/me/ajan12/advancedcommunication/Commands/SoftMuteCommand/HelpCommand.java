package me.ajan12.advancedcommunication.Commands.SoftMuteCommand;

import me.ajan12.advancedcommunication.Utilities.DataStorage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

class HelpCommand {

    static boolean execute(final CommandSender sender) {
        //Easing my work in the next lines by creating a local variable for plugin tag.
        final String pluginTag = DataStorage.pluginTag;

        //Pasting the help page header on the chat.
        sender.sendMessage(pluginTag + ChatColor.DARK_AQUA + " ---------- " + ChatColor.GOLD + "/softmute HELP" + ChatColor.DARK_AQUA + " ---------- " + pluginTag);
        // -- "/r help" command.
        sender.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/sm help" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Shows this page.");

        // -- "/r <message>" command.
        sender.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/sm <player> [length] [-s] [reason]" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Softmutes a player.");
        sender.sendMessage(pluginTag + " --- " + ChatColor.RED + "Enter the length in seconds.");
        sender.sendMessage(pluginTag + " --- " + ChatColor.RED + "\"-s\" tag blocks the mute announcement.");
        sender.sendMessage(pluginTag + " --- " + ChatColor.RED + "The order of the arguments are important.");

        // -- Detailed description.
        sender.sendMessage(pluginTag + " - " + ChatColor.DARK_RED + "Softmute makes the muted player see their message");
        sender.sendMessage(pluginTag + " - " + ChatColor.DARK_RED + "while others cannot see the muted player's messages.");

        // -- Command aliases info.
        sender.sendMessage(pluginTag + " - " + ChatColor.AQUA + "/softmute command aliases: " + ChatColor.YELLOW + "sm, softm, smute" + ChatColor.AQUA + ".");
        return true;
    }
}
