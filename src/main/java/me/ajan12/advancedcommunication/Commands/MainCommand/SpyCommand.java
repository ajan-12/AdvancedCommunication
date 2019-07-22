package me.ajan12.advancedcommunication.Commands.MainCommand;

import me.ajan12.advancedcommunication.AdvancedCommunication;
import me.ajan12.advancedcommunication.Enums.Feedbacks;
import me.ajan12.advancedcommunication.Utilities.DataStorage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

class SpyCommand {

    static boolean execute(final Player sender, final String targetName) {

        //Checking if the player is toggling their own spy status.
        if (targetName.equals(sender.getName())) {
            //Checking if the player has the permission to toggle their own spy status.
            if (!sender.hasPermission("advancedcommunication.command.spy.self")) {
                sender.sendMessage(Feedbacks.PERMISSION_ERROR.toString());
                return true;
            }
        //The player is toggling someone else's spy status.
        } else {
            //Checking if the player has the permission to toggle someone else's spy status.
            if (!sender.hasPermission("advancedcommunication.command.spy.other")) {
                sender.sendMessage(Feedbacks.PERMISSION_OTHERS_ERROR.toString());
                return true;
            }
        }

        //Getting the player to toggle the spy status on.
        final Player target = Bukkit.getPlayer(targetName);
        //Checking if the target is null, if it's we return false.
        if (target == null) {
            sender.sendMessage(Feedbacks.PLAYER_NOT_FOUND.toString());
            return true;
        }

        //Checking if the player was already spying.
        final boolean wasSpying = target.hasPermission("advancedcommunication.spy");

        //Toggling the spy status of the player.
        //      AdvancedCommunication.getInstance() returns the plugin.
        //      "advancedcommunication.spy" is our permission that defines the spy status.
        //      !wasSpying to toggle off the spying if it was on or vice-versa.
        target.addAttachment(AdvancedCommunication.getInstance(), "advancedcommunication.spy", !wasSpying);

        //If the player WASN'T spying.
        if (!wasSpying) {

            //Sending messages to both console and player to log.
            target.sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Now spying on private messages.");
            sender.sendMessage(DataStorage.pluginTag + " " + ChatColor.YELLOW + targetName + ChatColor.GREEN + " is now spying on private messages.");
            Bukkit.getConsoleSender().sendMessage(
                    DataStorage.pluginTag + ChatColor.YELLOW + target.getName() +
                    ChatColor.AQUA + "/" + ChatColor.YELLOW + target.getUniqueId().toString() +
                    ChatColor.AQUA + " is now spying on private messages by the command of player " +
                    ChatColor.YELLOW + sender.getName() + ChatColor.AQUA + "/" +
                    ChatColor.YELLOW + sender.getUniqueId().toString() + ChatColor.AQUA + ".");
        //If the player was already spying
        } else {

            //Sending messages to both console and player to log.
            target.sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " No longer spying on private messages.");
            sender.sendMessage(DataStorage.pluginTag + " " + ChatColor.YELLOW + targetName + ChatColor.GREEN + " is no longer spying on private messages.");
            Bukkit.getConsoleSender().sendMessage(
                    DataStorage.pluginTag + ChatColor.YELLOW + target.getName() +
                    ChatColor.AQUA + "/" + ChatColor.YELLOW + target.getUniqueId().toString() +
                    ChatColor.AQUA + " is no longer spying on private messages by the command of player " +
                    ChatColor.YELLOW + sender.getName() + ChatColor.AQUA + "/" +
                    ChatColor.YELLOW + sender.getUniqueId().toString() + ChatColor.AQUA + ".");
        }
        return true;
    }
}
