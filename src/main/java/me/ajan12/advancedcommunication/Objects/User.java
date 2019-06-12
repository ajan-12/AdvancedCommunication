package me.ajan12.advancedcommunication.Objects;

import com.sun.istack.internal.Nullable;

import org.bukkit.entity.Player;

public class User {

    private Player player;
    private Player focusedPlayer;

    public User(final Player player) {
        this.player = player;
        focusedPlayer = null;
    }

    public Player getPlayer() { return player; }
    public Player getFocusedPlayer() { return focusedPlayer; }

    public void setFocusedPlayer(@Nullable final Player focusedPlayer) { this.focusedPlayer = focusedPlayer; }
}
