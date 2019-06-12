package me.ajan12.advancedcommunication.Commands.MainCommand;

import me.ajan12.advancedcommunication.AdvancedCommunication;
import me.ajan12.advancedcommunication.Utilities.DataStorage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

class SpyCommand {

    /**
     * Toggles the spy status/permission of the player.
     *
     * @param sender    : The player that toggles the spy status.
     * @param targetName: The player to toggle the spy status on.
     * @return          : True if the target is found, false if the target isn't found.
     */
    static CommandSuccess execute(final Player sender, final String targetName) {

        //Checking if the player is toggling their own spy status.
        if (targetName.equals(sender.getName())) {
            //Checking if the player has the permission to toggle their own spy status.
            if (!sender.hasPermission("advancedcommunication.command.spy.self")) return CommandSuccess.NO_PERMISSION_SELF;
        //The player is toggling someone else's spy status.
        } else {
            //Checking if the player has the permission to toggle someone else's spy status.
            if (!sender.hasPermission("advancedcommunication.command.spy.other")) return CommandSuccess.NO_PERMISSION_OTHER;
        }

        //Getting the player to toggle the spy status on.
        final Player target = Bukkit.getPlayer(targetName);
        //Checking if the target is null, if it's we return false.
        if (target == null) return CommandSuccess.TARGET_NOT_FOUND;

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
        //If no problems occurred we return success.
        return CommandSuccess.SUCCESS;

    }

    //To make the above method return the problem that occurred or to show that the command applied successfully.
    public enum CommandSuccess {

        TARGET_NOT_FOUND(),
        NO_PERMISSION_SELF(),
        NO_PERMISSION_OTHER(),

        SUCCESS();

        CommandSuccess() { }
    }

}
