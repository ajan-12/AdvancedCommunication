package me.ajan12.advancedcommunication.Objects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.UUID;

public class Console extends Focusable {

    @Override
    public String getName() { return ChatColor.DARK_RED + "CONSOLE"; }

    @Override
    public void sendMessage(Focusable sender, String message) {
        //Sending the message to both target player and sender player.
        Bukkit.getConsoleSender().sendMessage(
                ChatColor.GOLD + "[" + ChatColor.AQUA + sender.getName() +
                ChatColor.GOLD + "] >> [" + getName() + ChatColor.GOLD + "] » " +
                ChatColor.RESET + message);
    }

    @Override
    public UUID getUUID() { return null; }
}
