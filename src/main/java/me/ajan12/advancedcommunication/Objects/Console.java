package me.ajan12.advancedcommunication.Objects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.UUID;

public class Console extends Focusable {

    @Override
    public String getName() { return ChatColor.DARK_RED + "CONSOLE"; }

    @Override
    public void sendMessage(Focusable sender, String message) {

        //Preparing the message.
        final String finalMessage =
                ChatColor.GOLD + "[" + ChatColor.AQUA + sender.getName() +
                ChatColor.GOLD + "] >> [" + getName() + ChatColor.GOLD + "] : " +
                ChatColor.RESET + message;

        //Sending the message to Console.
        Bukkit.getConsoleSender().sendMessage(finalMessage);

        //Sending the message to the sender.
        super.sendMessageSender(sender, this, message);

        //Sending the message to spies.
        super.sendMessageSpies(sender, this, message);
    }

    @Override
    public UUID getUUID() { return null; }
}
