package me.ajan12.advancedcommunication.Objects;

import com.sun.istack.internal.Nullable;
import me.ajan12.advancedcommunication.Utilities.PacketUtils;
import org.bukkit.ChatColor;

import java.util.UUID;

public class Player extends Focusable {

    private org.bukkit.entity.Player player;
    private String displayName;

    public Player(@Nullable final org.bukkit.entity.Player player) {
        this.player = player;
        displayName = (player == null) ? null : player.getDisplayName();
    }

    public org.bukkit.entity.Player getPlayer() { return player; }

    @Override
    public String getName() { return displayName; }

    @Override
    public UUID getUUID() { return player.getUniqueId(); }

    @Override
    public void sendMessage(Focusable sender, String message) {

        //Sending the message to both target player and sender player.
        player.sendMessage(
                ChatColor.GOLD + "[" + ChatColor.AQUA + sender.getName() +
                ChatColor.GOLD + "] >> [" + ChatColor.RED + "You" + ChatColor.GOLD + "] » " +
                ChatColor.RESET + message);

        //Sending a hotbar message to the focused player.
        PacketUtils.sendHotbarMessage(player,
                ChatColor.GOLD + "[" + ChatColor.AQUA + sender.getName() +
                ChatColor.GOLD + "] >> [" + ChatColor.RED + "You" + ChatColor.GOLD + "] » " +
                ChatColor.RESET + message);

    }
}
