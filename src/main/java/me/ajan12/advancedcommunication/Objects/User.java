package me.ajan12.advancedcommunication.Objects;

import com.sun.istack.internal.Nullable;
import org.bukkit.entity.Player;

import java.util.*;

public class User {

    private Player player;
    private Focusable focused;

    //Group name, Group id.
    private Map<String, UUID> groups;

    public User(final Player player) {
        this.player = player;
        focused = null;
        groups = new HashMap<>();
    }

    public Player getPlayer() { return player; }

    public Focusable getFocused() { return focused; }
    public boolean isFocused() { return focused != null; }
    public void setFocused(@Nullable final Focusable focused) { this.focused = focused; }

    public Map<String, UUID> getGroups() { return groups; }
    public UUID getGroup(final String name) { return groups.get(name); }

    public void addGroup(final String groupName, final UUID groupID) { groups.put(groupName, groupID); }
    public void removeGroup(final String group) { groups.remove(group); }
}
