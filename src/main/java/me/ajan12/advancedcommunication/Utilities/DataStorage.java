package me.ajan12.advancedcommunication.Utilities;

import me.ajan12.advancedcommunication.Objects.User;

import org.bukkit.ChatColor;

import java.util.HashSet;

public class DataStorage {

    public static void setup() {
        pluginTag = ChatColor.AQUA + "[" + ChatColor.RED + "AC" + ChatColor.AQUA + "]";
        users = new HashSet<>();
    }

    public static void purge() {
        users.clear();
        users = null;
        pluginTag = null;
    }
    //Plugin Tag
    public static String pluginTag;

    //Online Users
    public static HashSet<User> users;

    public static void addUser(final User user) { users.add(user); }
    public static void removeUser(final User user) { users.remove(user); }
}
