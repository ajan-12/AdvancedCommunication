package me.ajan12.advancedcommunication.Utilities;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import me.ajan12.advancedcommunication.Enums.PluginState;
import me.ajan12.advancedcommunication.Objects.Group;
import me.ajan12.advancedcommunication.Objects.User;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

public class DataStorage {

    public static void setup() {

        pluginTag = ChatColor.AQUA + "[" + ChatColor.RED + "AC" + ChatColor.AQUA + "]";

        pluginState = PluginState.IDLE;

        users = new HashSet<>();

        groups = new HashMap<>();

        protocolManager = ProtocolLibrary.getProtocolManager();

    }

    public static void purge() {

        protocolManager = null;

        groups.clear();
        groups = null;

        users.clear();
        users = null;

        pluginState = null;
        pluginTag = null;

    }

    //Plugin Tag
    public static String pluginTag;

    //Plugin State
    public static PluginState pluginState;

    public static void changePluginState(final PluginState state) { DataStorage.pluginState = state; }

    //Online Users
    public static HashSet<User> users;

    public static void addUser(final User user) { users.add(user); }
    public static void removeUser(final User user) { users.remove(user); }

    //Groups
    public static Map<UUID, Group> groups;

    public static void addGroup(final Group group) { groups.put(group.getUUID(), group); }
    public static void removeGroup(final Group group) { groups.remove(group.getUUID()); }

    //ProtocolManager FOR ProtcolLib
    public static ProtocolManager protocolManager;
}
