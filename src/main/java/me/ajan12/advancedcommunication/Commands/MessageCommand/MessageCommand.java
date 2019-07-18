package me.ajan12.advancedcommunication.Commands.MessageCommand;

import me.ajan12.advancedcommunication.Objects.Console;
import me.ajan12.advancedcommunication.Objects.Focusable;
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

        //Getting the pluginTag to ease my work.
        final String pluginTag = DataStorage.pluginTag;

        //Shorter version of if-else's.
        switch (args.length) {

            //Checking if the args length is 0. This is used to un-focus.
            case 0:
                //Checking if the sender is a Player.
                if (sender instanceof Player) {

                    //Getting the User of the sender.
                    final User user = GeneralUtils.getUser((Player) sender);

                    //Checking if the user was found.
                    if (user == null) {

                        sender.sendMessage(pluginTag + ChatColor.DARK_RED + " Please re-log to the server and try again.");
                        return true;

                    }

                    //Checking if the user is focused.
                    if (user.isFocused()) {

                        //Getting the name of the focused to use in the message.
                        final String name = user.getFocused().getName();

                        //Removing the focused.
                        user.setFocused(null);

                        //Feedbacking the player.
                        sender.sendMessage(pluginTag + ChatColor.GREEN + " Successfully un-focused on " + ChatColor.YELLOW + name + ChatColor.GREEN + ".");

                        return true;

                    } else {

                        sender.sendMessage(pluginTag + ChatColor.DARK_RED + " You aren't focused on any player or group.");
                        return true;

                    }

                } else {

                    sender.sendMessage(ChatColor.DARK_RED + "This command can only be applied in-game!");
                    return true;

                }

            //Checking if the args length is 1. This is used to focus/un-focus.
            case 1:

                //Checking if the sender is a Player.
                if (sender instanceof Player) {

                    //Getting the User of the sender.
                    final User user = GeneralUtils.getUser((Player) sender);

                    //Checking if the user was found.
                    if (user == null) {

                        sender.sendMessage(pluginTag + ChatColor.DARK_RED + " Please re-log to the server and try again.");
                        return true;

                    }

                    //Checking if the user is trying to focus on a group.
                    final Focusable focusable = user.getGroups().containsKey(args[0]) ? DataStorage.groups.get(user.getGroup(args[0])) : new me.ajan12.advancedcommunication.Objects.Player(Bukkit.getPlayer(args[0]));

                    if (focusable.getName() == null) {

                        sender.sendMessage(pluginTag + ChatColor.DARK_RED + " Couldn't find the specified target. Please try again.");
                        return true;

                    }

                    //Checking if the user was focused already.
                    if (user.isFocused()) {

                        //Checking if the user is trying to un-focus on who they were focusing on.
                        if (user.getFocused().equals(focusable)) {

                            //un-focusing the player.
                            user.setFocused(null);

                            //Informing the player.
                            sender.sendMessage(pluginTag + ChatColor.GREEN + " Successfully un-focused on " + ChatColor.YELLOW + focusable.getName() + ChatColor.GREEN + ".");
                            return true;

                        } else {

                            //Informing the player.
                            sender.sendMessage(pluginTag + ChatColor.GREEN + " Successfully focused on " + ChatColor.YELLOW + focusable.getName() + ChatColor.GREEN + " and un-focused on " + ChatColor.YELLOW + user.getFocused().getName() + ChatColor.GREEN + ".");

                            //focusing the player.
                            user.setFocused(focusable);
                            return true;
                        }

                    } else {

                        //focusing the player.
                        user.setFocused(focusable);

                        //Informing the player.
                        sender.sendMessage(pluginTag + ChatColor.GREEN + " Successfully focused on " + ChatColor.YELLOW + focusable.getName() + ChatColor.GREEN + ".");
                        return true;

                    }
                } else {

                    sender.sendMessage(pluginTag + ChatColor.DARK_RED + " This command can only be applied in-game!");
                    return true;

                }

            case 2:

                //Checking if the sender is a Player.
                if (sender instanceof Player) {

                    //Getting the User of the sender.
                    final User user = GeneralUtils.getUser((Player) sender);

                    //Checking if the user was found.
                    if (user == null) {

                        sender.sendMessage(pluginTag + ChatColor.DARK_RED + " Please re-log to the server and try again.");
                        return true;

                    }

                    //Checking if the user is trying to send a message to a group.
                    final Focusable focusable = user.getGroups().containsKey(args[0]) ? DataStorage.groups.get(user.getGroup(args[0])) : new me.ajan12.advancedcommunication.Objects.Player(Bukkit.getPlayer(args[0]));

                    //Checking if the focusable is null.
                    if (focusable.getName() == null) {

                        //Informing the player.
                        sender.sendMessage(pluginTag + ChatColor.DARK_RED + " Couldn't find the specified target. Please try again.");
                        return true;

                    }

                    //Sending the message.
                    focusable.sendMessage(new me.ajan12.advancedcommunication.Objects.Player((Player) sender), args[1]);

                } else {

                    //Getting the target player as a focusable.
                    final Focusable focusable = new me.ajan12.advancedcommunication.Objects.Player(Bukkit.getPlayer(args[0]));

                    //Checking if the focusable is null.
                    if (focusable.getName() == null) {

                        //Informing the player.
                        sender.sendMessage(pluginTag + ChatColor.DARK_RED + " Couldn't find the specified target. Please try again.");
                        return true;

                    }

                    //Sending the message.
                    focusable.sendMessage(new Console(), args[1]);

                }

                //Informing the player.
                sender.sendMessage(pluginTag + ChatColor.GREEN + " Successfully sent the message.");
                return true;

            default:

                //Sending the help page to the sender.
                HelpCommand.execute(sender);
                return true;
        }
    }

}
