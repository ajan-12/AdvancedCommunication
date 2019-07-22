package me.ajan12.advancedcommunication.Commands.MainCommand;

import me.ajan12.advancedcommunication.Enums.Feedbacks;
import me.ajan12.advancedcommunication.Objects.Group;
import me.ajan12.advancedcommunication.Objects.User;
import me.ajan12.advancedcommunication.Utilities.DataStorage;
import me.ajan12.advancedcommunication.Utilities.DatabaseUtils.SQLiteUtils;
import me.ajan12.advancedcommunication.Utilities.GroupUtils;
import me.ajan12.advancedcommunication.Utilities.UserUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

public class SavePurgeCommand {

    static class SavePurgeBulkCommand {

        static boolean execute(final CommandSender sender, final boolean isGroup, final boolean isSaving) {

            //Checking if the sender is a Player.
            if (sender instanceof Player) {

                //Checking if the player is trying to save/purge the groups.
                if (isGroup) {
                    //Checking if the player is trying to save.
                    if (isSaving) {

                        //Checking if the player has the permission to do so.
                        if (!sender.hasPermission("advancedcommunication.command.save.groups")) {

                            //Feedbacking the player.
                            sender.sendMessage(Feedbacks.PERMISSION_ERROR.toString());
                            return true;
                        }

                        //Requesting the plugin to save the groups.
                        GroupUtils.saveGroups();

                        //Feedbacking the player.
                        sender.sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Successfully requested the plugin to save the groups.");
                        return true;
                    } else {

                        //Checking if the player has the permission to do so.
                        if (!sender.hasPermission("advancedcommunication.command.purge.groups")) {

                            //Feedbacking the player.
                            sender.sendMessage(Feedbacks.PERMISSION_ERROR.toString());
                            return true;
                        }

                        //Requesting the plugin to save the groups.
                        GroupUtils.purgeGroups();

                        //Feedbacking the player.
                        sender.sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Successfully requested the plugin to purge the groups.");
                        return true;
                    }
                } else {
                    //Checking if the player is trying to save.
                    if (isSaving) {

                        //Checking if the player has the permission to do so.
                        if (!sender.hasPermission("advancedcommunication.command.save.users")) {

                            //Feedbacking the player.
                            sender.sendMessage(Feedbacks.PERMISSION_ERROR.toString());
                            return true;
                        }

                        //Requesting the plugin to save the users.
                        UserUtils.saveUsers();

                        //Feedbacking the player.
                        sender.sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Successfully requested the plugin to save the users.");
                        return true;
                    } else {

                        //Checking if the player has the permission to do so.
                        if (!sender.hasPermission("advancedcommunication.command.purge.users")) {

                            //Feedbacking the player.
                            sender.sendMessage(Feedbacks.PERMISSION_ERROR.toString());
                            return true;
                        }

                        //Requesting the plugin to save the groups.
                        UserUtils.purgeUsers();

                        //Feedbacking the player.
                        sender.sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Successfully requested the plugin to purge the groups.");
                        return true;
                    }
                }
            } else {

                //Checking if the console is trying to save the groups.
                if (isGroup) {
                    //Checking if the console is trying to save.
                    if (isSaving) {

                        //Requesting the plugin to save the groups.
                        GroupUtils.saveGroups();

                        //Feedbacking the console.
                        sender.sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Successfully requested the plugin to save the groups.");
                        return true;
                    } else {

                        //Requesting the plugin to save the groups.
                        GroupUtils.purgeGroups();

                        //Feedbacking the console.
                        sender.sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Successfully requested the plugin to purge the groups.");
                        return true;
                    }
                } else {
                    //Checking if the player is trying to save.
                    if (isSaving) {

                        //Requesting the plugin to save the users.
                        UserUtils.saveUsers();

                        //Feedbacking the player.
                        sender.sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Successfully requested the plugin to save the users.");
                        return true;
                    } else {

                        //Requesting the plugin to save the groups.
                        UserUtils.purgeUsers();

                        //Feedbacking the player.
                        sender.sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Successfully requested the plugin to purge the groups.");
                        return true;
                    }
                }
            }
        }
    }

    static class SavePurgeSingleCommand {

        static boolean execute(final CommandSender sender, final String target, final boolean isSaving) {

            //Getting the target Player via UUID.
            Object targetObj = Bukkit.getPlayer(UUID.fromString(target));
            //Checking if the player is found.
            if (targetObj == null) {

                //Getting the target Player via name.
                targetObj = Bukkit.getPlayer(target);
                //Checking if the player is found.
                if (targetObj == null) {

                    //Getting the group.
                    targetObj = GroupUtils.findGroup(target);
                    if (targetObj == null) {

                        //Feedbacking the sender.
                        sender.sendMessage(Feedbacks.TARGET_FOCUSABLE_NOT_FOUND.toString());
                        return true;
                    }
                }
            }

            //Checking if the sender is a Player.
            if (sender instanceof Player) {

                //Checking if the player is trying to save/purge a group.
                if (targetObj instanceof Group) {

                    //Preparing to save the group.
                    final HashSet<Group> groups = new HashSet<>();
                    groups.add((Group) targetObj);

                    //Checking if the player is trying to save.
                    if (isSaving) {

                        //Checking if the player has the permission to do so.
                        if (!sender.hasPermission("advancedcommunication.command.save.group")) {

                            //Feedbacking the player.
                            sender.sendMessage(Feedbacks.PERMISSION_ERROR.toString());
                            return true;
                        }

                        //Requesting the plugin to save the users.
                        SQLiteUtils.saveGroups(groups);

                        //Feedbacking the player.
                        sender.sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Saving the group " + ChatColor.YELLOW + target + ChatColor.GREEN + ".");
                        return true;
                    } else {

                        //Checking if the player has the permission to do so.
                        if (!sender.hasPermission("advancedcommunication.command.purge.group")) {

                            //Feedbacking the player.
                            sender.sendMessage(Feedbacks.PERMISSION_ERROR.toString());
                            return true;
                        }

                        //Requesting the plugin to save the users.
                        SQLiteUtils.purgeGroups(groups);

                        //Feedbacking the player.
                        sender.sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Purging the group " + ChatColor.YELLOW + target + ChatColor.GREEN + ".");
                        return true;
                    }
                } else {

                    //Getting the User of the target.
                    final User user = UserUtils.getUser((Player) targetObj);

                    //Checking if the user was found.
                    if (user == null) {

                        //Feedbacking the player.
                        sender.sendMessage(Feedbacks.SENDER_USER_NOT_FOUND.toString());
                        return true;
                    }

                    //Preparing to save the group.
                    final HashSet<User> users = new HashSet<>();
                    users.add(user);

                    //Checking if the player is trying to save.
                    if (isSaving) {

                        //Checking if the player has the permission to do so.
                        if (!sender.hasPermission("advancedcommunication.command.save.user")) {

                            //Feedbacking the player.
                            sender.sendMessage(Feedbacks.PERMISSION_ERROR.toString());
                            return true;
                        }

                        //Requesting the plugin to save the users.
                        SQLiteUtils.saveUsers(users);

                        //Feedbacking the player.
                        sender.sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Saving the user " + ChatColor.YELLOW + target + ChatColor.GREEN + ".");
                        return true;
                    } else {

                        //Checking if the player has the permission to do so.
                        if (!sender.hasPermission("advancedcommunication.command.purge.user")) {

                            //Feedbacking the player.
                            sender.sendMessage(Feedbacks.PERMISSION_ERROR.toString());
                            return true;
                        }

                        //Requesting the plugin to save the users.
                        SQLiteUtils.purgeUsers(users);

                        //Feedbacking the player.
                        sender.sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Purging the user " + ChatColor.YELLOW + target + ChatColor.GREEN + ".");
                        return true;
                    }
                }
            } else {

                //Checking if the player is trying to save/purge a group.
                if (targetObj instanceof Group) {

                    //Preparing to save the group.
                    final HashSet<Group> groups = new HashSet<>();
                    groups.add((Group) targetObj);

                    //Checking if the player is trying to save.
                    if (isSaving) {

                        //Requesting the plugin to save the users.
                        SQLiteUtils.saveGroups(groups);

                        //Feedbacking the player.
                        sender.sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Saving the group " + ChatColor.YELLOW + target + ChatColor.GREEN + ".");
                        return true;
                    } else {

                        //Requesting the plugin to save the users.
                        SQLiteUtils.purgeGroups(groups);

                        //Feedbacking the player.
                        sender.sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Purging the group " + ChatColor.YELLOW + target + ChatColor.GREEN + ".");
                        return true;
                    }
                } else {

                    //Getting the User of the target.
                    final User user = UserUtils.getUser((Player) targetObj);

                    //Checking if the user was found.
                    if (user == null) {

                        //Feedbacking the player.
                        sender.sendMessage(Feedbacks.SENDER_USER_NOT_FOUND.toString());
                        return true;
                    }

                    //Preparing to save the group.
                    final HashSet<User> users = new HashSet<>();
                    users.add(user);

                    //Checking if the player is trying to save.
                    if (isSaving) {

                        //Requesting the plugin to save the users.
                        SQLiteUtils.saveUsers(users);

                        //Feedbacking the player.
                        sender.sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Saving the user " + ChatColor.YELLOW + target + ChatColor.GREEN + ".");
                        return true;
                    } else {

                        //Requesting the plugin to save the users.
                        SQLiteUtils.purgeUsers(users);

                        //Feedbacking the player.
                        sender.sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Purging the user " + ChatColor.YELLOW + target + ChatColor.GREEN + ".");
                        return true;
                    }
                }
            }
        }
    }
}
