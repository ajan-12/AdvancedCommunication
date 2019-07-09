package me.ajan12.advancedcommunication.Utilities;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import me.ajan12.advancedcommunication.Objects.Group;
import me.ajan12.advancedcommunication.Objects.User;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.HashSet;

public class DataStorage {

    public static void setup() {
        pluginTag = ChatColor.AQUA + "[" + ChatColor.RED + "AC" + ChatColor.AQUA + "]";
        users = new HashSet<>();
        protocolManager = ProtocolLibrary.getProtocolManager();
    }

    public static void purge() {
        users.clear();
        users = null;
        pluginTag = null;
    }
    //Plugin Tag
    public static String pluginTag;

    //Online Users
    static HashSet<User> users;

    public static void addUser(final User user) { users.add(user); }
    public static void removeUser(final User user) { users.remove(user); }

    //Groups
    static ArrayList<Group> groups;

    public static void addGroup(final Group group) { groups.add(group); }
    public static void removeGroup(final Group group) { groups.remove(group); }

    //ProtocolManager FOR ProtcolLib
    static ProtocolManager protocolManager;
}
