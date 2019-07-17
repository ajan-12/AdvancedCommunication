package me.ajan12.advancedcommunication.Commands.MainCommand;

import me.ajan12.advancedcommunication.Utilities.DataStorage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

class HelpCommand {

    static boolean execute(final CommandSender player) {
        //Easing my work in the next lines by creating a local variable for plugin tag.
        final String pluginTag = DataStorage.pluginTag;
        //Checking if the player is able to view help page. Returning false if they aren't.
        if (!player.hasPermission("advancedcommunication.command.help")) return false;

        //Pasting the help page header on the chat.
        player.sendMessage(pluginTag + ChatColor.DARK_AQUA + " ---------- " + ChatColor.GOLD + "/advancedcommunication HELP" + ChatColor.DARK_AQUA + " ---------- " + pluginTag);
        // -- "/ac help" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/ac help" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Shows this page.");
        // -- "/ac spy" command.
        player.sendMessage(pluginTag + " - " + ChatColor.YELLOW + "/ac spy" + ChatColor.GREEN + " : " + ChatColor.AQUA + " Toggles the spy status of the executor of the command.");
        player.sendMessage(pluginTag + " --- " + ChatColor.AQUA + " This command requires some permissions.");
        player.sendMessage(pluginTag + " --- " + ChatColor.AQUA + " Spy status is the ability to view all private messages sent within the server.");
        player.sendMessage(pluginTag + " --- " + ChatColor.YELLOW + "/ac spy <target>" + ChatColor.AQUA + " To toggle spy status of someone else.");
        // -- Command aliases info.
        player.sendMessage(pluginTag + " - " + ChatColor.AQUA + "/advancedcommunication command aliases: " + ChatColor.YELLOW + "ac, advc, acomm, advcomm" + ChatColor.AQUA + ".");
        //Pasting the help page footer on the chat.
        player.sendMessage(pluginTag + ChatColor.DARK_AQUA + " ----------+---------------------------+---------- " + pluginTag);

        return true;
    }

}
