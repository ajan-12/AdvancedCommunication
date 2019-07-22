package me.ajan12.advancedcommunication.Commands.GroupCommand;

import me.ajan12.advancedcommunication.Enums.Feedbacks;
import me.ajan12.advancedcommunication.Utilities.DataStorage;
import me.ajan12.advancedcommunication.Utilities.GroupUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class ListCommand {

    static boolean execute(final CommandSender player, final String[] args) {

        //Getting the pluginTag to ease my work.
        final String pluginTag = DataStorage.pluginTag;

        //Checking if the args length is 1.
        if (args.length == 1) {

            //Checking if the sender is a Player.
            if (player instanceof Player) {

                //Getting the list of groups the player is in.
                final String list = GroupUtils.listGroups((Player) player);

                //Sending the message containing the groups the player is in.
                player.sendMessage(pluginTag + ChatColor.GREEN + " The groups you are in: " + ChatColor.YELLOW + list);

                //Returning true because we don't want to show the command usage.
                return true;
            } else {

                //Informing the console.
                player.sendMessage(Feedbacks.CONSOLE_MESSAGE_ERROR.toString());
                return true;
            }

        //Checking if the args length is 2.
        } else if (args.length == 2) {

            //Getting the player specified in the 2nd argument.
            final Player target = Bukkit.getPlayer(args[1]);

            //Checking if the target exists.
            if (target == null || !target.isOnline()) {

                //Informing the sender that the target cannot be found.
                player.sendMessage(Feedbacks.PLAYER_NOT_FOUND.toString());
                return true;
            } else {

                //Getting the list of groups the target is in.
                final String list = GroupUtils.listGroups(target);

                //Sending the message containing the groups the target is in.
                player.sendMessage(pluginTag + ChatColor.GREEN + " The groups you are in: " + ChatColor.YELLOW + list);
                return true;
            }
        }

        //Informing the sender that this command can only be applied in-game.
        player.sendMessage(Feedbacks.CONSOLE_MESSAGE_ERROR.toString());
        return true;
    }
}
