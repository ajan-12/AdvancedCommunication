package me.ajan12.advancedcommunication.Utilities;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import me.ajan12.advancedcommunication.AdvancedCommunication;
import me.ajan12.advancedcommunication.Enums.PluginState;
import me.ajan12.advancedcommunication.Objects.Group;
import me.ajan12.advancedcommunication.Objects.MentionedMessage;
import me.ajan12.advancedcommunication.Objects.User;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;

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

        RegisteredServiceProvider<Permission> rsp = AdvancedCommunication.getInstance().getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();

        messages = new HashSet<>();

        mentionedPlayers = new HashMap<>();
    }

    public static void purge() {

        mentionedPlayers.clear();
        mentionedPlayers = null;

        messages.clear();
        messages = null;

        perms = null;

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

    //Permission FOR Vault
    public static Permission perms;

    //Mentioned messages FOR mention highlighting
    public static HashSet<MentionedMessage> messages;

    public static void addMention(final MentionedMessage message) { messages.add(message); }
    public static void removeMention(final MentionedMessage message) { messages.remove(message); }

    //Mentioned players FOR mention cooldowns
    public static HashMap<UUID, Long> mentionedPlayers;

    public static void addMentionedPlayer(final UUID player) { mentionedPlayers.put(player, System.currentTimeMillis()); }
    public static void removeMentionedPlayer(final UUID player) { mentionedPlayers.remove(player); }
}
