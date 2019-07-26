package me.ajan12.advancedcommunication.Commands.IgnoreCommand;

import me.ajan12.advancedcommunication.Enums.Feedbacks;
import me.ajan12.advancedcommunication.Objects.User;
import me.ajan12.advancedcommunication.Utilities.DataStorage;
import me.ajan12.advancedcommunication.Utilities.UserUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class IgnoreCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        //Checking if the argument amount isn't 1.
        if (args.length != 1) return HelpCommand.execute(sender);

        //Checking if the 1st argument is "help".
        if (args[0].equalsIgnoreCase("help")) {

            //Sending the help page to the sender.
            return HelpCommand.execute(sender);
        //Checking if the 1st argument is "list".
        } else if (args[0].equalsIgnoreCase("list")) {

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

                //Checking if the user is ignoring anyone.
                if (user.getIgnoredUsers().size() == 0) {

                    //Feedbacking the player.
                    sender.sendMessage(Feedbacks.NONE_IGNORED.toString());
                    return true;
                } else {

                    //Constructing the list.
                    final StringBuilder list = new StringBuilder();
                    //Shows the iteration amount.
                    int iteration = 0;
                    //Iterating over the ignored players.
                    for (final UUID ignoredUser : user.getIgnoredUsers()) {

                        //Getting the OfflinePlayer of the ignoredUser.
                        final OfflinePlayer player = Bukkit.getOfflinePlayer(ignoredUser);
                        //Checking if this is the 1st player.
                        if (iteration == 0) {

                            //Adding the player to the list.
                            list.append(ChatColor.YELLOW).append(player.getName());
                        //Checking if this is the 2nd player.
                        } else if (iteration == 1) {

                            //Adding the player to the list.
                            list.append(ChatColor.AQUA).append(" and ").append(ChatColor.YELLOW).append(player.getName());
                        } else {

                            //Adding the player to the list.
                            list.insert(0, ChatColor.YELLOW + player.getName() + ChatColor.AQUA + ", ");
                        }
                        iteration++;
                    }

                    //Feedbacking the player.
                    sender.sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " The players you are ignoring" + ChatColor.AQUA + ": " + list);
                    return true;
                }
            }
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

            //Checking if the person is trying to ignore themselves.
            if (args[0].equalsIgnoreCase(sender.getName()) || args[0].equalsIgnoreCase(((Player) sender).getDisplayName())) {

                //Feedbacking the player.
                sender.sendMessage(Feedbacks.SELF_IGNORE.toString());
                return true;
            }

            //Getting the target player.
            final Player target = Bukkit.getPlayer(args[0]);
            //Checking if the target player was found.
            if (target == null) {

                //Feedbacking the player.
                sender.sendMessage(Feedbacks.PLAYER_NOT_FOUND.toString());
                return true;
            }

            //Checking if the player is already ignored.
            if (user.getIgnoredUsers().contains(target.getUniqueId())) {

                //Un-ignoring the target player.
                user.removeIgnoredUser(target.getUniqueId());

                //Feedbacking the player.
                sender.sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Successfully unignored " + ChatColor.YELLOW + args[0] + ChatColor.GREEN + ".");
                return true;
            } else {

                //Ignoring the target player.
                user.addIgnoredUser(target.getUniqueId());

                //Feedbacking the player.
                sender.sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Successfully ignored " + ChatColor.YELLOW + args[0] + ChatColor.GREEN + ".");
                return true;
            }
        } else {

            //Feedbacking the console.
            sender.sendMessage(Feedbacks.CONSOLE_MESSAGE_ERROR.toString());
            return true;
        }
    }
}
