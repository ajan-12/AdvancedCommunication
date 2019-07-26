package me.ajan12.advancedcommunication.Commands.IgnoreCommand;

import me.ajan12.advancedcommunication.Utilities.DataStorage;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

class HelpCommand {

    static boolean execute(final CommandSender sender) {
        //Easing my work in the next lines by creating a local variable for plugin tag.
        final String pluginTag = DataStorage.pluginTag;

        //Pasting the help page header on the chat.
        sender.sendMessage(pluginTag + ChatColor.DARK_AQUA + " ---------- " + ChatColor.GOLD + "/ignore HELP" + ChatColor.DARK_AQUA + " ---------- " + pluginTag);
        // -- "/ignore help" command.
        sender.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/ignore help" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Shows this page.");
        // -- "/ignore <player>" command.
        sender.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/ignore <player>" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Ignores/Unignores a specific player.");
        sender.sendMessage(pluginTag + " --- " + ChatColor.RED + "When you ignore a player, you will no longer see their messages.");
        sender.sendMessage(pluginTag + " --- " + ChatColor.RED + "Using this command twice causes it to unignore the player.");
        // -- "/ignore list" command.
        sender.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/ignore list" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Shows a list of ignored players.");
        return true;
    }
}
