package me.ajan12.advancedcommunication.Commands.HardMuteCommand;

import me.ajan12.advancedcommunication.Utilities.DataStorage;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

class HelpCommand {

    static boolean execute(final CommandSender sender) {
        //Easing my work in the next lines by creating a local variable for plugin tag.
        final String pluginTag = DataStorage.pluginTag;

        //Pasting the help page header on the chat.
        sender.sendMessage(pluginTag + ChatColor.DARK_AQUA + " ---------- " + ChatColor.GOLD + "/hardmute HELP" + ChatColor.DARK_AQUA + " ---------- " + pluginTag);

        // -- "/hm help" command.
        sender.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/hm help" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Shows this page.");

        // -- "/hm <player> [length] [-s] [reason]" command.
        sender.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/hm <player> [length] [-s] [reason]" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Hardmutes a player.");
        sender.sendMessage(pluginTag + " --- " + ChatColor.RED + "Enter the length in seconds.");
        sender.sendMessage(pluginTag + " --- " + ChatColor.RED + "\"-s\" tag blocks the mute announcement.");
        sender.sendMessage(pluginTag + " --- " + ChatColor.RED + "The order of the arguments are important.");

        // -- Detailed description.
        sender.sendMessage(pluginTag + " - " + ChatColor.DARK_RED + "Hardmute blocks the mute altogether and tells the muted player");
        sender.sendMessage(pluginTag + " - " + ChatColor.DARK_RED + "that they have been muted and so they can't send any messages.");

        // -- Command aliases info.
        sender.sendMessage(pluginTag + " - " + ChatColor.AQUA + "/hardmute command aliases: " + ChatColor.YELLOW + "hm, hardm, hmute, mute" + ChatColor.AQUA + ".");
        return true;
    }
}
