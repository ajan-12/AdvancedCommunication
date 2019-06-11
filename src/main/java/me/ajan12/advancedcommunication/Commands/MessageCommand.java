package me.ajan12.advancedcommunication.Commands;

import me.ajan12.advancedcommunication.Objects.User;
import me.ajan12.advancedcommunication.Utilities.DataStorage;
import me.ajan12.advancedcommunication.Utilities.GeneralUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //No arguments can be used to remove the focus.
        if (args.length == 0) {

            //Checking if the sender is a Player
            if (!(sender instanceof Player)) {
                //Sender isn't a Player and so we send an error message.
                sender.sendMessage(DataStorage.pluginTag + ChatColor.DARK_RED + " You can only focus on a player in-game!");
                //Returning true because we don't want the usage to be shown.
                return true;
            }

            //Casting to Player to ease my work in the next lines.
            final Player sendingPlayer = (Player) sender;
            //Getting the User of the sender.
            final User senderUser = GeneralUtils.getUser(sendingPlayer);

            //Checking if the User is null
            if (senderUser == null) {

                //User is null which means a big problem. They need to re-log for the User to be created.
                sendingPlayer.sendMessage(DataStorage.pluginTag + ChatColor.DARK_RED + " Encountered with an error. Please re-log.");
                Bukkit.getConsoleSender().sendMessage(
                        DataStorage.pluginTag + ChatColor.DARK_RED + " Encountered with an error while finding the User of the player " +
                        ChatColor.YELLOW + sendingPlayer.getName() + ChatColor.DARK_RED + "/" + ChatColor.YELLOW + sendingPlayer.getUniqueId().toString() +
                        ChatColor.DARK_RED + "! Asked them to re-log."
                );

                //Returning true because we don't want the usage to be shown.
                return true;

            }

            //Getting the Player sender was focused on to remove it from the User.
            final Player focusedPlayer = senderUser.getFocusedPlayer();

            //Checking if the focusedPlayer is null.
            if (focusedPlayer == null) {

                //focusedPlayer is null which means sender wasn't focused on anyone.
                sendingPlayer.sendMessage(DataStorage.pluginTag + ChatColor.DARK_RED + " You are not focused on anyone!");

                //Returning false because we want the usage to be shown.
                return false;

            } else {

                //focusedPlayer isn't null and so we remove the focusedPlayer.
                senderUser.setFocusedPlayer(null);
                sendingPlayer.sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Successfully un-focused on player " + ChatColor.YELLOW + focusedPlayer.getName() + ChatColor.GREEN + ".");

                //Returning true because we don't want the usage to be shown.
                return true;

            }


        // 1 argument means that they are focusing or un-focusing on someone.
        } else if (args.length == 1) {

            //Checking if the sender is a Player
            if (!(sender instanceof Player)) {
                //Sender isn't a Player and so we send an error message.
                sender.sendMessage(DataStorage.pluginTag + ChatColor.DARK_RED + " You can only focus on a player in-game!");
                //Returning true because we don't want the usage to be shown.
                return true;
            }

            //Casting to Player to ease my work in the next lines.
            final Player sendingPlayer = (Player) sender;
            //Getting the targetingPlayer that was stated in the argument.
            final Player targetingPlayer = Bukkit.getPlayer(args[0]);

            //Checking if the targetingPlayer is null or offline.
            if (targetingPlayer == null || !targetingPlayer.isOnline()) {
                //targetingPlayer is null/offline which means the plugin cannot find it.
                sender.sendMessage(DataStorage.pluginTag + ChatColor.DARK_RED + " Cannot find the targeting player!");
                //Returning true because we don't want the usage to be shown.
                return true;
            }

            //Getting the User of the targetingPlayer
            final User targetUser = GeneralUtils.getUser(targetingPlayer);

            //Checking if targetUser is null
            if (targetUser == null) {

                //User is null which means a big problem. They need to re-log for the User to be created.
                sender.sendMessage(DataStorage.pluginTag + ChatColor.DARK_RED + " Encountered with an error. Please try again later.");
                targetingPlayer.sendMessage(DataStorage.pluginTag + ChatColor.DARK_RED + " Encountered with an error. Please re-log.");
                Bukkit.getConsoleSender().sendMessage(
                        DataStorage.pluginTag + ChatColor.DARK_RED + " Encountered with an error while finding the User of the player " +
                        ChatColor.YELLOW + targetingPlayer.getName() + ChatColor.DARK_RED + "/" + ChatColor.YELLOW + targetingPlayer.getUniqueId().toString() +
                        ChatColor.DARK_RED + "! Asked them to re-log."
                );

                //Returning true because we don't want the usage to be shown.
                return true;
            }

            //Getting the User of the sendingPlayer
            final User senderUser = GeneralUtils.getUser(sendingPlayer);

            //Checking if senderUser is null
            if (senderUser == null) {

                //User is null which means a big problem. They need to re-log for the User to be created.
                sendingPlayer.sendMessage(DataStorage.pluginTag + ChatColor.DARK_RED + " Encountered with an error. Please re-log.");
                Bukkit.getConsoleSender().sendMessage(
                        DataStorage.pluginTag + ChatColor.DARK_RED + " Encountered with an error while finding the User of the player " +
                        ChatColor.YELLOW + sendingPlayer.getName() + ChatColor.DARK_RED + "/" + ChatColor.YELLOW + sendingPlayer.getUniqueId().toString() +
                        ChatColor.DARK_RED + "! Asked them to re-log."
                );

                //Returning true because we don't want the usage to be shown.
                return true;
            }

            //Getting the Player sender was focused on to remove it from the User.
            final Player focusedPlayer = senderUser.getFocusedPlayer();

            //Checking if the User is focused on anyone
            if (focusedPlayer.getUniqueId().equals(targetingPlayer.getUniqueId())) {

                //Setting the focusingPlayer to null meaning the User is no longer focused on anyone
                senderUser.setFocusedPlayer(null);
                sendingPlayer.sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Successfully un-focused on player " + ChatColor.YELLOW + targetingPlayer.getName() + ChatColor.GREEN + ".");

                //Returning true because we don't want the usage to be shown.
                return true;

            } else {

                //Setting the focusingPlayer to the User wants to focus on
                senderUser.setFocusedPlayer(targetingPlayer);
                sendingPlayer.sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Successfully focused on player " + ChatColor.YELLOW + targetingPlayer.getName() + ChatColor.GREEN + ".");

                //Returning true because we don't want the usage to be shown.
                return true;

            }

        //2 arguments means that the player wants to send a message for this time only.
        } else if (args.length == 2) {

            //Getting the targetingPlayer that was stated in the argument.
            final Player targetingPlayer = Bukkit.getPlayer(args[0]);

            //Checking if the targetingPlayer is null or offline.
            if (targetingPlayer == null || !targetingPlayer.isOnline()) {

                //targetingPlayer is null/offline which means the plugin cannot find it.
                sender.sendMessage(DataStorage.pluginTag + ChatColor.DARK_RED + " Cannot find the targeting player!");

                //Returning true because we don't want the usage to be shown.
                return true;

            }

            //Checking if the sender is a Player to add their name into the message.
            if (sender instanceof Player) {
                //Casting to Player to ease my work in the next lines.
                final Player sendingPlayer = (Player) sender;

                //Sending the message to both players.
                targetingPlayer.sendMessage(
                        ChatColor.GOLD + "[ " + ChatColor.AQUA + sendingPlayer.getDisplayName() +
                        ChatColor.GOLD + " ] -> [ " + ChatColor.RED + "YOU" + ChatColor.GOLD + " ] : " +
                        ChatColor.RESET + args[1]);
                sendingPlayer.sendMessage(
                        ChatColor.GOLD + "[ " + ChatColor.RED + "YOU" +
                        ChatColor.GOLD + " ] -> [ " + ChatColor.AQUA + targetingPlayer.getDisplayName() + ChatColor.GOLD + " ] : " +
                        ChatColor.RESET + args[1]);

                //Returning true because we don't want the usage to be shown.
                return true;
            } else {

                //Sending the message to both players.
                targetingPlayer.sendMessage(
                        ChatColor.GOLD + "[ " + ChatColor.DARK_RED + "CONSOLE" +
                        ChatColor.GOLD + " ] -> [ " + ChatColor.RED + "YOU" + ChatColor.GOLD + " ] : " +
                        ChatColor.RESET + args[1]);
                Bukkit.getConsoleSender().sendMessage(
                        ChatColor.GOLD + "[ " + ChatColor.RED + "CONSOLE" +
                        ChatColor.GOLD + " ] -> [ " + ChatColor.AQUA + targetingPlayer.getDisplayName() + ChatColor.GOLD + " ] : " +
                        ChatColor.RESET + args[1]);

                //Returning true because we don't want the usage to be shown.
                return true;
            }

        }

        return false;
    }
}
