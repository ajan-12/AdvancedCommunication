package me.ajan12.advancedcommunication.Objects;

import me.ajan12.advancedcommunication.Enums.Feedbacks;
import me.ajan12.advancedcommunication.Utilities.DataStorage;
import me.ajan12.advancedcommunication.Utilities.UserUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Group extends Focusable {

    //Unique id of the group.
    private UUID id;

    private String groupName;
    private String description;

    //true means everyone can send messages, false means only admins can.
    private boolean sendMessages;
    //true means everyone can edit group info and settings, false means only admins can.
    private boolean editInfo;
    //true means that the group is in slowdown mode.
    private boolean inSlowdown;

    //Keys are the members, values show if they are group admin.
    private HashMap<UUID, Boolean> members;

    //The timestamp that the group was created in.
    private long createdTime;
    //Last timestamp any action occurred in the group.
    private long lastUpdate;

    /**
     * Used to create a completely new group.
     */
    public Group(final String creator, final String name, final HashMap<UUID, Boolean> members) {
        this.id = UUID.randomUUID();

        this.groupName = name;
        this.description = "A new group created by " + creator + ".";
        this.members = members;

        createdTime = System.currentTimeMillis();
        lastUpdate = System.currentTimeMillis();
    }

    /**
     * Used to import a group from the database.
     */
    public Group(final UUID uuid, final String name, final String description, final boolean sendMessages, final boolean editInfo, final boolean inSlowdown, final HashMap<UUID, Boolean> members, final long createdTime, final long lastUpdate) {
        this.id = uuid;

        this.groupName = name;
        this.description = description;

        this.sendMessages = sendMessages;
        this.editInfo = editInfo;
        this.inSlowdown = inSlowdown;

        this.members = members;

        this.createdTime = createdTime;
        this.lastUpdate = lastUpdate;

    }

    @Override
    public UUID getUUID() { return id; }

    @Override
    public String getName() { return groupName; }
    public void setName(final String groupName) { this.groupName = groupName; }

    public String getDescription() { return description; }
    public void setDescription(final String description) { this.description = description; }

    public boolean isSendMessages() { return sendMessages; }
    public void setSendMessages(final boolean sendMessages) { this.sendMessages = sendMessages; }

    public boolean isEditInfo() { return editInfo; }
    public void setEditInfo(final boolean editInfo) { this.editInfo = editInfo; }

    public boolean isInSlowdown() { return inSlowdown; }
    public void setInSlowdown(final boolean inSlowdown) { this.inSlowdown = inSlowdown; }

    public HashMap<UUID, Boolean> getMembers() { return members; }
    public void addMember(final UUID player, final boolean isAdmin) { members.put(player, isAdmin); }

    public long getCreatedTime() { return createdTime; }
    public long getLastUpdate() { return lastUpdate; }

    ///// SENDING MESSAGES ---------------------------------------------------------------------------------------------

    @Override
    public void sendMessage(final Focusable sender, final String message) {

        //Preparing the message.
        final String finalMessage =
                ChatColor.GOLD + "[" + ChatColor.AQUA + groupName + ChatColor.GOLD + "] " +
                ChatColor.AQUA + sender.getName() + ChatColor.DARK_AQUA + " : " +
                ChatColor.RESET + message;

        //Sending the message to all of the members of this group.
        members.keySet().forEach(member -> {
            final Player target = Bukkit.getPlayer(member);
            if (target != null) target.sendMessage(finalMessage);
        });

        //Updating the lastUpdate
        lastUpdate = System.currentTimeMillis();

        //Sending the message to spies.
        super.sendMessageSpies(sender, this, message);

    }

    public void broadcast(final String message) {
        //Constructing the message to be sent.
        final String finalMessage =
                ChatColor.GOLD + "[" + ChatColor.AQUA + groupName + ChatColor.GOLD + "] " +
                ChatColor.RED + "Group Broadcast" + ChatColor.DARK_AQUA + " » " +
                ChatColor.RESET + message;

        //Sending the message to all of the members of this group.
        members.keySet().forEach(member -> {
            final Player target = Bukkit.getPlayer(member);
            if (target != null) target.sendMessage(finalMessage);
        });
        //Updating the lastUpdate
        lastUpdate = System.currentTimeMillis();
    }

    @Override
    public String toString() { return getName(); }

    // ADDING/REMOVING PLAYERS -----------------------------------------------------------------------------------------
    public void addMember(final Player player) {

        //Adding the player to the members.
        members.put(player.getUniqueId(), false);

        //Informing the group members.
        broadcast(ChatColor.YELLOW + player.getName() + ChatColor.AQUA + " has joined the group!");

        //Updating the lastUpdate
        lastUpdate = System.currentTimeMillis();
    }
    public void kickMember(final Focusable kicker, final Player player) {

        //Getting the User of the player.
        final User user = UserUtils.getUser(player);

        //Checking if the user was found.
        if (user == null) {

            //Feedbacking the player.
            player.sendMessage(Feedbacks.SENDER_USER_NOT_FOUND.toString());
            return;
        }

        //Removing the player from the database.
        members.remove(player.getUniqueId());

        //Informing the player that they have been kicked.
        player.sendMessage(DataStorage.pluginTag + ChatColor.RED + " You have been kicked from group " + ChatColor.YELLOW + groupName + ChatColor.RED +  ".");

        //Broadcasting the group that a player has been kicked.
        broadcast(ChatColor.YELLOW + kicker.getName() + ChatColor.AQUA + " has kicked the player " + ChatColor.YELLOW + player.getName() + ChatColor.AQUA + " from the group.");

        //Removing the group from the player.
        user.removeGroup(groupName);

        //Updating the lastUpdate
        lastUpdate = System.currentTimeMillis();
    }
    public void memberLeave(final Player player) {

        //Getting the User of the player.
        final User user = UserUtils.getUser(player);

        //Checking if the user was found.
        if (user == null) {

            //Feedbacking the player.
            player.sendMessage(Feedbacks.SENDER_USER_NOT_FOUND.toString());
            return;
        }

        //Removing the group from the player.
        user.removeGroup(groupName);

        //Removing the player from the database.
        members.remove(player.getUniqueId());

        //Broadcasting the group that a player has left.
        broadcast(ChatColor.YELLOW + player.getName() + ChatColor.AQUA + " has left the group.");

        //Feedbacking the player.
        player.sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " You have successfully left the group " + ChatColor.YELLOW + groupName + ChatColor.GREEN + ".");

        //Updating the lastUpdate
        lastUpdate = System.currentTimeMillis();
    }
}
