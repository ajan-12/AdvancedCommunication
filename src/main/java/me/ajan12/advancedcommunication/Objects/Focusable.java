package me.ajan12.advancedcommunication.Objects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public abstract class Focusable {

    public abstract String getName();
    public abstract void sendMessage(final Focusable sender, final String message);
    public abstract UUID getUUID();

    /**
     * This method is used by Focusable's to send a message to their senders.
     *
     * @param sender : The receiver of the message.
     * @param message: The message to be sent.
     */
    void sendMessageSender(final Focusable sender, final Focusable receiver, final String message) {

        //Checking if the sender is Console.
        if (sender instanceof Console) {

            //Preparing the message.
            final String finalMessage =
                    ChatColor.GOLD + "[" + ChatColor.AQUA + sender.getName() +
                    ChatColor.GOLD + "] >> [" + receiver.getName() + ChatColor.GOLD + "] : " +
                    ChatColor.RESET + message;

            //Sending the message to Console.
            Bukkit.getConsoleSender().sendMessage(finalMessage);

        //Checking if the sender is an User.
        } else if (sender instanceof User) {

            //Preparing the message.
            final String finalMessage =
                    ChatColor.GOLD + "[" + ChatColor.RED + "You" +
                    ChatColor.GOLD + "] >> [" + ChatColor.AQUA + receiver.getName() + ChatColor.GOLD + "] : " +
                    ChatColor.RESET + message;

            //Getting the player the sender represents.
            final Player player = ((User) sender).getPlayer();
            //Checking if getting the player is successful.
            if (player == null) return;

            //Sending the message to the player.
            player.sendMessage(finalMessage);
        }
    }

    /**
     * This method is used by Focusable's to send a message to the spies.
     *
     * @param sender  : The sender of the message.
     * @param receiver: The receiver of the message.
     * @param message : The message to be sent.
     */
    void sendMessageSpies(final Focusable sender, final Focusable receiver, final String message) {

        //Iterating over all the players online.
        for (final Player player : Bukkit.getOnlinePlayers()) {
            //Checking if the player is spying.
            if (player.hasPermission("advancedcommunication.spy")) {

                //Checking if the player iterating on is the receiver, if so we're passing them as they already got the message.
                if (player.getUniqueId().equals(receiver.getUUID())) continue;

                //Checking if the player iterating on is the sender, if so we're passing them as they already got the message.
                if (player.getUniqueId().equals(sender.getUUID())) continue;

                //Preparing the message.
                final String finalMessage =
                        ChatColor.GOLD + "[" + ChatColor.RED + "SPY" + ChatColor.GOLD + "] " +
                        ChatColor.GOLD + "[" + ChatColor.AQUA + sender.getName() +
                        ChatColor.GOLD + "] >> [" + ChatColor.AQUA + receiver.getName() + ChatColor.GOLD + "] : " +
                        ChatColor.RESET + message;

                //Sending the message to the player as they are spying.
                player.sendMessage(finalMessage);
            }
        }

    }
}
