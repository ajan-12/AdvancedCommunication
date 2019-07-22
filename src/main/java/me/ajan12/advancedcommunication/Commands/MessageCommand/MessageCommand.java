package me.ajan12.advancedcommunication.Commands.MessageCommand;

import me.ajan12.advancedcommunication.Enums.Feedbacks;
import me.ajan12.advancedcommunication.Objects.Console;
import me.ajan12.advancedcommunication.Objects.Focusable;
import me.ajan12.advancedcommunication.Objects.User;
import me.ajan12.advancedcommunication.Utilities.DataStorage;
import me.ajan12.advancedcommunication.Utilities.GroupUtils;
import me.ajan12.advancedcommunication.Utilities.UserUtils;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

public class MessageCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        //Getting the pluginTag to ease my work.
        final String pluginTag = DataStorage.pluginTag;

        //Shorter version of if-else's.
        switch (args.length) {

            //Checking if the args length is 0. This is used to un-focus.
            case 0:
                //Checking if the sender is a Player.
                if (sender instanceof Player) {

                    //Getting the User of the sender.
                    final User user = UserUtils.getUser((Player) sender);

                    //Checking if the user was found.
                    if (user == null) {

                        sender.sendMessage(Feedbacks.SENDER_USER_NOT_FOUND.toString());
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

                        //Feedbacking the player.
                        sender.sendMessage(Feedbacks.FOCUSABLE_NOT_FOUND.toString());
                        return true;
                    }

                } else {

                    //Feedbacking the console.
                    sender.sendMessage(Feedbacks.CONSOLE_MESSAGE_ERROR.toString());
                    return true;
                }

            //Checking if the args length is 1. This is used to focus/un-focus.
            case 1:

                //Checking if the sender requested the help page.
                if (args[0].equalsIgnoreCase("help")) {

                    //Sending the help page to the sender.
                    return HelpCommand.execute(sender);
                }

                //Checking if the sender is a Player.
                if (sender instanceof Player) {

                    //Getting the User of the sender.
                    final User user = UserUtils.getUser((Player) sender);

                    //Checking if the user was found.
                    if (user == null) {

                        //Feedbacking the player.
                        sender.sendMessage(Feedbacks.SENDER_USER_NOT_FOUND.toString());
                        return true;
                    }

                    //Checking if the user is trying to focus on a group.
                    final Focusable focusable = user.getGroups().containsKey(args[0]) ? DataStorage.groups.get(user.getGroup(args[0])) : UserUtils.getUser(args[0]);

                    //Checking if we successfully found the focusable.
                    if (focusable == null) {

                        //Feedbacking the player.
                        sender.sendMessage(Feedbacks.TARGET_FOCUSABLE_NOT_FOUND.toString());
                        return true;
                    }

                    //Checking if the user was focused already.
                    if (user.isFocused()) {

                        //Checking if the user is trying to un-focus on who they were focusing on.
                        if (user.getFocused().getUUID().equals(focusable.getUUID())) {

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

                    //Feedbacking the player.
                    sender.sendMessage(Feedbacks.CONSOLE_MESSAGE_ERROR.toString());
                    return true;
                }

            default:

                //Checking if the sender is a Player.
                if (sender instanceof Player) {

                    //Getting the User of the sender.
                    final User user = UserUtils.getUser((Player) sender);

                    //Checking if the user was found.
                    if (user == null) {

                        sender.sendMessage(Feedbacks.SENDER_USER_NOT_FOUND.toString());
                        return true;
                    }

                    //Checking if the user is trying to send a message to a group.
                    final Focusable focusable = user.getGroups().containsKey(args[0]) ? DataStorage.groups.get(user.getGroup(args[0])) : UserUtils.getUser(args[0]);

                    //Checking if the focusable is null.
                    if (focusable == null) {

                        //Informing the player.
                        sender.sendMessage(Feedbacks.TARGET_FOCUSABLE_NOT_FOUND.toString());
                        return true;
                    }

                    //Creating the message StringBuilder.
                    final StringBuilder message = new StringBuilder(args[1]);

                    //Iterating over the words.
                    for (int i = 2; i < args.length; i++) {
                        //Adding the word to the message.
                        message.append(" ").append(args[i]);
                    }

                    //Sending the message to the target.
                    focusable.sendMessage(user, message.toString());

                } else {

                    //Getting the target User.
                    Focusable focusable = UserUtils.getUser(args[0]);
                    //Checking if the user is null.
                    if (focusable == null) {

                        //Getting the target Group.
                        focusable = GroupUtils.findGroup(args[0]);
                        if (focusable == null) {

                            //Informing the console.
                            sender.sendMessage(Feedbacks.TARGET_FOCUSABLE_NOT_FOUND.toString());
                            return true;
                        }
                    }

                    //Creating the message StringBuilder.
                    final StringBuilder message = new StringBuilder(args[1]);

                    //Iterating over the words.
                    for (int i = 2; i < args.length; i++) {
                        //Adding the word to the message.
                        message.append(" ").append(args[i]);
                    }

                    //Sending the message.
                    focusable.sendMessage(new Console(), message.toString());
                }
                return true;
        }
    }
}
