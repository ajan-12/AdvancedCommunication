package me.ajan12.advancedcommunication.Commands.MainCommand;

import me.ajan12.advancedcommunication.Enums.Feedbacks;
import me.ajan12.advancedcommunication.Objects.Focusable;
import me.ajan12.advancedcommunication.Objects.Group;
import me.ajan12.advancedcommunication.Objects.User;
import me.ajan12.advancedcommunication.Utilities.DataStorage;
import me.ajan12.advancedcommunication.Utilities.DatabaseUtils.SQLiteUtils;
import me.ajan12.advancedcommunication.Utilities.GroupUtils;
import me.ajan12.advancedcommunication.Utilities.UserUtils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

class SaveCommand {

    static boolean execute(final CommandSender sender, final String target) {

        //Easing my work.
        final String pluginTag = DataStorage.pluginTag;

        //Checking if the sender is a Player.
        if (sender instanceof Player) {
            //Checking if the player is trying to save the users.
            if (target.equalsIgnoreCase("users")) {

                //Checking if the player has the permission.
                if (!sender.hasPermission("advancedcommunication.command.save.users")) {

                    //Feedbacking the player.
                    sender.sendMessage(Feedbacks.PERMISSION_ERROR.toString());
                    return true;
                }

                //Saving the users.
                UserUtils.saveUsers();

                //Feedbacking the player.
                sender.sendMessage(pluginTag + ChatColor.GREEN + " Successfully requested the server to save the users.");
                return true;
            //Checking if the player is trying to save the groups.
            } else if (target.equalsIgnoreCase("groups")) {

                //Checking if the player has the permission.
                if (!sender.hasPermission("advancedcommunication.command.save.groups")) {

                    //Feedbacking the player.
                    sender.sendMessage(Feedbacks.PERMISSION_ERROR.toString());
                    return true;
                }

                //Saving the groups.
                GroupUtils.saveGroups();

                //Feedbacking the player.
                sender.sendMessage(pluginTag + ChatColor.GREEN + " Successfully requested the server to save the groups.");
                return true;
            } else {

                //The target to save.
                final Focusable targetFocusable;
                //Checking if the target is an UUID.
                if (target.matches("([a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12})")) {

                    //Getting the UUID of target.
                    final UUID uuid = UUID.fromString(target);
                    //Checking if the target is a Group.
                    if (DataStorage.groups.containsKey(uuid)) {

                        //Getting the Group and setting it to targetFocusable.
                        targetFocusable = DataStorage.groups.get(uuid);
                    } else {

                        //Getting the User and setting it to targetFocusable.
                        targetFocusable = UserUtils.getUser(uuid);
                    }
                } else {

                    //Getting the group.
                    final Group group = GroupUtils.findGroup(target);
                    //Checking if getting the group was successful.
                    if (group == null) {

                        //Getting the user.
                        final User user = UserUtils.getUser(target);
                        //Checking if getting the user was successful.
                        if (user == null) {

                            //Feedbacking the player.
                            sender.sendMessage(Feedbacks.TARGET_FOCUSABLE_NOT_FOUND.toString());
                            return true;
                        } else targetFocusable = user;
                    } else targetFocusable = group;
                }

                //Checking if the targetFocusable is a Group.
                if (targetFocusable instanceof Group) {

                    //Checking if the player has the permission.
                    if (!sender.hasPermission("advancedcommunication.command.save.group")) {

                        //Feedbacking the player.
                        sender.sendMessage(Feedbacks.PERMISSION_ERROR.toString());
                        return true;
                    }

                    //Preparing to save the group.
                    final HashSet<Group> groupsToSave = new HashSet<>();
                    groupsToSave.add((Group) targetFocusable);

                    //Saving the group.
                    SQLiteUtils.saveGroups(groupsToSave);

                    //Feedbacking the player.
                    sender.sendMessage(pluginTag + ChatColor.GREEN + " Successfully requested the server to save the groups.");
                    return true;
                } else {

                    //Checking if the player has the permission.
                    if (!sender.hasPermission("advancedcommunication.command.save.user")) {

                        //Feedbacking the player.
                        sender.sendMessage(Feedbacks.PERMISSION_ERROR.toString());
                        return true;
                    }

                    //Preparing to save the user.
                    final HashSet<User> usersToSave = new HashSet<>();
                    usersToSave.add((User) targetFocusable);

                    //Saving the user.
                    SQLiteUtils.saveUsers(usersToSave);

                    //Feedbacking the player.
                    sender.sendMessage(pluginTag + ChatColor.GREEN + " Successfully requested the server to save the users.");
                    return true;
                }
            }
        } else {
            //Checking if the player is trying to save the users.
            if (target.equalsIgnoreCase("users")) {

                //Saving the users.
                UserUtils.saveUsers();

                //Feedbacking the player.
                sender.sendMessage(pluginTag + ChatColor.GREEN + " Successfully requested the server to save the users.");
                return true;
            //Checking if the player is trying to save the groups.
            } else if (target.equalsIgnoreCase("groups")) {

                //Saving the groups.
                GroupUtils.saveGroups();

                //Feedbacking the player.
                sender.sendMessage(pluginTag + ChatColor.GREEN + " Successfully requested the server to save the groups.");
                return true;
            } else {

                //The target to save.
                final Focusable targetFocusable;
                //Checking if the target is an UUID.
                if (target.matches("([a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12})")) {

                    //Getting the UUID of target.
                    final UUID uuid = UUID.fromString(target);
                    //Checking if the target is a Group.
                    if (DataStorage.groups.containsKey(uuid)) {

                        //Getting the Group and setting it to targetFocusable.
                        targetFocusable = DataStorage.groups.get(uuid);
                    } else {

                        //Getting the User and setting it to targetFocusable.
                        targetFocusable = UserUtils.getUser(uuid);
                    }
                } else {

                    //Getting the group.
                    final Group group = GroupUtils.findGroup(target);
                    //Checking if getting the group was successful.
                    if (group == null) {

                        //Getting the user.
                        final User user = UserUtils.getUser(target);
                        //Checking if getting the user was successful.
                        if (user == null) {

                            //Feedbacking the player.
                            sender.sendMessage(Feedbacks.TARGET_FOCUSABLE_NOT_FOUND.toString());
                            return true;
                        } else targetFocusable = user;
                    } else targetFocusable = group;
                }

                //Checking if the targetFocusable is a Group.
                if (targetFocusable instanceof Group) {

                    //Preparing to save the group.
                    final HashSet<Group> groupsToSave = new HashSet<>();
                    groupsToSave.add((Group) targetFocusable);

                    //Saving the group.
                    SQLiteUtils.saveGroups(groupsToSave);

                    //Feedbacking the player.
                    sender.sendMessage(pluginTag + ChatColor.GREEN + " Successfully requested the server to save the groups.");
                    return true;
                } else {

                    //Preparing to save the user.
                    final HashSet<User> usersToSave = new HashSet<>();
                    usersToSave.add((User) targetFocusable);

                    //Saving the user.
                    SQLiteUtils.saveUsers(usersToSave);

                    //Feedbacking the player.
                    sender.sendMessage(pluginTag + ChatColor.GREEN + " Successfully requested the server to save the users.");
                    return true;
                }
            }
        }
    }
}
