package me.ajan12.advancedcommunication.Commands.HardMuteCommand;

import me.ajan12.advancedcommunication.Enums.Feedbacks;
import me.ajan12.advancedcommunication.Objects.User;
import me.ajan12.advancedcommunication.Utilities.DataStorage;
import me.ajan12.advancedcommunication.Utilities.UserUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

public class HardMuteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        //Easing my work in the next lines.
        final String pluginTag = DataStorage.pluginTag;

        //Checking if the sender is a Player.
        if (sender instanceof Player) {
            //Checking if the sender has the permission for this command.
            if (!sender.hasPermission("advancedcommunication.command.hardmute")) {

                //Feedbacking the player.
                sender.sendMessage(Feedbacks.PERMISSION_ERROR.toString());
                return true;
            }
        }

        //Checking if the argument amount is either more than 3 or less than 2.
        if (args.length == 0) HelpCommand.execute(sender);
        //Checking if the 1st argument is "help".
        if (args[0].equalsIgnoreCase("help")) HelpCommand.execute(sender);

        //Getting the targeting player.
        final Player target = Bukkit.getPlayer(args[0]);
        //Checking if the targeting player exists.
        if (target == null) {

            //Feedbacking the sender.
            sender.sendMessage(Feedbacks.PLAYER_NOT_FOUND.toString());
            return true;
        }

        //Getting the User of the target player.
        final User targetUser = UserUtils.getUser(target);

        //Checking if the target was already hardmuted.
        if (targetUser.isHardMuted()) {

            //Un-hardmuting the targetUser.
            targetUser.hardMute(false);
            //Setting the mute length to 0L.
            targetUser.setMuteEnd(0L);

            //Feedbacking the sender.
            sender.sendMessage(pluginTag + ChatColor.GREEN + " Successfully un-hard-muted the player " + ChatColor.YELLOW + args[0] + ChatColor.GREEN + ".");
            return true;
        }

        int nextArg = 1;
        //The time when the mute will end.
        long muteEnd = Long.MAX_VALUE;
        //Checking if the mute length is defined.
        if (args.length >= nextArg + 1) {
            //Checking if the next arg is a number.
            if (args[nextArg].matches("[0-9]+")) {
                muteEnd = (Integer.parseInt(args[1]) * 1000) + muteEnd;
                nextArg++;
            }
        }

        //If the mute should be broadcasted.
        boolean silenced = false;
        //Checking if silenced is defined.
        if (args.length >= nextArg + 1) {
            //Checking if the next arg is "-s".
            if (args[nextArg].equalsIgnoreCase("-s")) {
                silenced = true;
                nextArg++;
            }
        }

        //The reason to broadcast.
        String reason = ChatColor.WHITE + "Misconduct.";
        //Checking if reason is defined.
        if (args.length >= nextArg + 1) {

            //The StringBuilder to replace the reason.
            final StringBuilder reasonBuilder = new StringBuilder();
            //Iterating over the rest of the arguments to construct the reason.
            while (nextArg < args.length) {

                //Adding this argument to the reasonBuilder.
                reasonBuilder.append(args[nextArg]);
                nextArg++;
            }

            //Saving the reason.
            reason = reasonBuilder.toString();
        }

        //Hardmuting the targetUser.
        targetUser.hardMute(true);
        //Setting the mute length.
        targetUser.setMuteEnd(muteEnd);
        //Setting the mute reason.
        targetUser.setMuteReason(reason);

        //Checking if this mute is silenced.
        if (!silenced) {
            //Broadcasting the mute.
            Bukkit.broadcastMessage(pluginTag + ChatColor.AQUA + " The player " + ChatColor.YELLOW + args[0] + ChatColor.AQUA + " has been muted for \"" + reason + ChatColor.AQUA + "\".");
        }

        //Feedbacking the sender.
        sender.sendMessage(pluginTag + ChatColor.GREEN + " Successfully hard-muted the player " + ChatColor.YELLOW + args[0] + ChatColor.GREEN + ".");
        return true;
    }
}
