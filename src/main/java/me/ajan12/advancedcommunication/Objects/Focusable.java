package me.ajan12.advancedcommunication.Objects;

import org.bukkit.command.CommandSender;

public abstract class Focusable {

    public abstract String getName();
    public abstract void sendMessage(final CommandSender sender, final String message);

}
