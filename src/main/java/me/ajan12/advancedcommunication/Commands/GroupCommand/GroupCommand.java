package me.ajan12.advancedcommunication.Commands.GroupCommand;

import me.ajan12.advancedcommunication.Commands.GroupCommand.GroupSetCommand.GroupSetCommand;
import me.ajan12.advancedcommunication.Enums.Feedbacks;
import me.ajan12.advancedcommunication.Objects.Console;
import me.ajan12.advancedcommunication.Objects.Group;
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

public class GroupCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        //Getting the pluginTag to ease my work.
        final String pluginTag = DataStorage.pluginTag;

        //Checking if the argument amount is 1.
        if (args.length == 1) {
            //Shorter version of if-else's.
            switch (args[0]) {

                //Checking if 1st argument equals to "help".
                case "help":
                    //Sending the help page to the player.
                    return HelpCommand.execute(sender, 1);

                //Checking if the 1st argument equals to "list".
                case "list":
                    //Sending the list of groups they are in to the player.
                    return ListCommand.execute(sender, args);

                //Checking if the 1st argument equals to "accept".
                case "accept":
                    return AcceptDenyCommand.execute(sender, true);
                //Checking if the 1st argument equals to "deny".
                case "deny":
                    return AcceptDenyCommand.execute(sender, false);

                //This is for focusing/un-focusing.
                default:
                    //Checking if the sender is a Player.
                    if (sender instanceof Player) {

                        //Getting the User of the sender.
                        final User senderUser = UserUtils.getUser((Player) sender);

                        //Checking if the User is in the specified group.
                        if (!senderUser.getGroups().containsKey(args[0])) {

                            //Feedbacking the player.
                            sender.sendMessage(Feedbacks.GROUP_NOT_FOUND.toString());
                            return true;
                        }

                        //Getting the group they specified.
                        final Group group = DataStorage.groups.get(senderUser.getGroup(args[0]));

                        //Checking if the senderUser is already focused.
                        if (senderUser.isFocused()) {

                            //Checking if the user is trying to un-focus from the group were focusing on.
                            if (senderUser.getFocused().getUUID().equals(group.getUUID())) {

                                //un-focusing the player.
                                senderUser.setFocused(null);

                                //Informing the player.
                                sender.sendMessage(pluginTag + ChatColor.GREEN + " Successfully un-focused on " + ChatColor.YELLOW + group.getName() + ChatColor.GREEN + ".");
                                return true;
                            } else {

                                //Informing the player.
                                sender.sendMessage(pluginTag + ChatColor.GREEN + " Successfully focused on " + ChatColor.YELLOW + group.getName() + ChatColor.GREEN + " and un-focused on " + ChatColor.YELLOW + senderUser.getFocused().getName() + ChatColor.GREEN + ".");

                                //focusing the player.
                                senderUser.setFocused(group);
                                return true;
                            }
                        } else {

                            //focusing on the group.
                            senderUser.setFocused(group);

                            //Informing the player.
                            sender.sendMessage(pluginTag + ChatColor.GREEN + " Successfully focused on " + ChatColor.YELLOW + group.getName() + ChatColor.GREEN + ".");
                            return true;
                        }
                    }

            }
        }
        //Checking if the sender is a Player.
        if (sender instanceof Player) {

            //Getting the User of the sender.
            final User senderUser = UserUtils.getUser((Player) sender);

            //Shorter version of if-else's.
            switch (args.length) {

                //Un-focusing.
                case 0:
                    //Checking if the senderUser is focused.
                    if (senderUser.isFocused()) {

                        //Getting the name of the player/group the player was focusing on.
                        final String name = senderUser.getFocused().getName();

                        //Un-focusing the player.
                        senderUser.setFocused(null);

                        //Informing the player.
                        sender.sendMessage(pluginTag + ChatColor.AQUA + " Successfully un-focused on " + ChatColor.YELLOW + name + ChatColor.AQUA + ".");
                        return true;
                    } else {

                        //Informing the player.
                        sender.sendMessage(Feedbacks.FOCUSABLE_NOT_FOUND.toString());
                        return true;
                    }

                //We're passing on "case 1" because we already did it before.
                case 2:
                    //Checking if the 1st argument equals to "list".
                    if (args[0].equalsIgnoreCase("list")) {

                        //Executing the ListCommand.
                        return ListCommand.execute(sender, args);
                    //Checking if the 1st argument equals to "help".
                    } else if (args[0].equalsIgnoreCase("help")) {

                        //Checking if the 2nd argument is a number.
                        if (args[1].matches("[0-9]+")) {
                            //Executing the HelpCommand.
                            return HelpCommand.execute(sender, Integer.parseInt(args[1]));
                        } else {
                            //Executing the HelpCommand.
                            return HelpCommand.execute(sender, 1);
                        }
                    //Checking if the 1st argument equals to "create".
                    } else if (args[0].equalsIgnoreCase("create")) {

                        //Executing the GroupCreateCommand.
                        return GroupCreateCommand.execute(sender, args);
                    //Checking if the 2nd argument equals to "leave".
                    } else if (args[1].equalsIgnoreCase("leave")) {

                        //Executing the GroupLeaveCommand.
                        return GroupLeaveCommand.execute(sender, args);
                    //Checking if the 2nd argument equals to "disband".
                    } else if (args[1].equalsIgnoreCase("disband")) {

                        //Executing the GroupDisbandCommand.
                        return GroupDisbandCommand.execute(sender, args);
                    //Checking if the 2nd argument equals to "set".
                    } else if (args[1].equalsIgnoreCase("set")) {

                        //Executing the GroupSetCommand.
                        return GroupSetCommand.execute(sender, args);
                    //Checking if the 2nd argument equals to "info".
                    } else if (args[1].equalsIgnoreCase("info")) {

                        //Executing the GroupInfoCommand.
                        return GroupInfoCommand.execute(sender, args[0]);
                    }

                case 3:
                    //Checking if the 2nd argument equals to "invite" or "kick".
                    if (args[1].equalsIgnoreCase("invite") || args[1].equalsIgnoreCase("kick")) {

                        //Executing the group invite/kick command.
                        return GroupInviteKickCommand.execute(sender, args);
                    //Checking if the 2nd argument equals to "set".
                    } else if (args[1].equalsIgnoreCase("set")) {

                        //Executing the GroupSetCommand.
                        return GroupSetCommand.execute(sender, args);
                    }

                //Sending a message to a group.
                default:
                    //Checking if the 2nd argument equals to "set".
                    if (args[1].equalsIgnoreCase("set")) {

                        //Executing the GroupSetCommand.
                        return GroupSetCommand.execute(sender, args);
                    }

                    //Checking if the senderUser is in the group they specified.
                    if (senderUser.getGroups().containsKey(args[0])) {

                        //Creating the message from the args.
                        final StringBuilder message = new StringBuilder(args[1]);
                        //Iterating over the words sender entered.
                        for (int i = 2; i < args.length; i++) {

                            //Adding the word to the message.
                            message.append(" ").append(args[i]);
                        }

                        //Getting the group player specified.
                        final Group group = DataStorage.groups.get(senderUser.getGroup(args[0]));
                        //Sending the message to the group.
                        group.sendMessage(senderUser, message.toString());
                        return true;
                    } else {

                        //Feedbacking the player.
                        sender.sendMessage(Feedbacks.NOT_MEMBER.toString());
                        return true;
                    }

            }
        } else {
            //Shorter version of if-else's.
            switch (args.length) {

                //We're passing on "case 0" as console can't use it.
                //We're passing on "case 1" because we already did it before.
                case 2:
                    //Checking if the 1st argument equals to "list".
                    if (args[0].equalsIgnoreCase("list")) {

                        //Executing the ListCommand.
                        return ListCommand.execute(sender, args);
                        //Checking if the 1st argument equals to "create".
                    } else if (args[0].equalsIgnoreCase("create")) {

                        //Executing the GroupCreateCommand.
                        return GroupCreateCommand.execute(sender, args);
                    //Checking if the 2nd argument equals to "set".
                    } else if (args[1].equalsIgnoreCase("set")) {

                        //Executing the GroupSetCommand.
                        return GroupSetCommand.execute(sender, args);
                    }

                case 3:
                    //Checking if the 2nd argument equals to "invite" or "kick".
                    if (args[1].equalsIgnoreCase("invite") || args[1].equalsIgnoreCase("kick")) {

                        //Executing the group invite/kick command.
                        return GroupInviteKickCommand.execute(sender, args);
                    //Checking if the 2nd argument equals to "set".
                    } else if (args[1].equalsIgnoreCase("set")) {

                        //Executing the GroupSetCommand.
                        return GroupSetCommand.execute(sender, args);
                    }

                //Sending a message to a group.
                default:

                    //Checking if the 2nd argument equals to "set".
                    if (args[1].equalsIgnoreCase("set")) {

                        //Executing the GroupSetCommand.
                        return GroupSetCommand.execute(sender, args);
                    }

                    //Getting the group console specified.
                    final Group group = GroupUtils.findGroup(args[0]);

                    //Checking if the senderUser is in the group they specified.
                    if (group != null) {

                        //Creating the message from the args.
                        final StringBuilder message = new StringBuilder(args[1]);
                        //Iterating over the words sender entered.
                        for (int i = 2; i < args.length; i++) {

                            //Adding the word to the message.
                            message.append(" ").append(args[i]);
                        }

                        //Sending the message to the group.
                        group.sendMessage(new Console(), message.toString());
                        return true;
                    } else {

                        //Feedbacking the console.
                        sender.sendMessage(Feedbacks.NOT_MEMBER.toString());
                        return true;
                    }

            }
        }
    }
}
