package me.ajan12.advancedcommunication.Objects;

import me.ajan12.advancedcommunication.Utilities.DataStorage;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Group {

    private String groupName;
    private String description;

    //true means everyone can send messages, false means only admins can.
    private boolean sendMessages;
    //true means everyone can edit group info and settings, false means only admins can.
    private boolean editInfo;
    //true means that the group is in slowdown mode.
    private boolean inSlowdown;

    //keys are the members, values show if they are group admin.
    private HashMap<Player, Boolean> members;

    private long createdTime;
    //last timestamp any action occurred in the group.
    private long lastUpdate;

    public Group(final String creator, final String groupName, final HashMap<Player, Boolean> members) {
        this.groupName = groupName;
        this.description = "A new group created by " + creator + ".";
        this.members = members;

        createdTime = System.currentTimeMillis();
        lastUpdate = System.currentTimeMillis();
    }

    public String getGroupName() { return groupName; }
    public void setGroupName(final String groupName) { this.groupName = groupName; }

    public String getDescription() { return description; }
    public void setDescription(final String description) { this.description = description; }

    public boolean isSendMessages() { return sendMessages; }
    public void setSendMessages(final boolean sendMessages) { this.sendMessages = sendMessages; }

    public boolean isEditInfo() { return editInfo; }
    public void setEditInfo(final boolean editInfo) { this.editInfo = editInfo; }

    public boolean isInSlowdown() { return inSlowdown; }
    public void setInSlowdown(final boolean inSlowdown) { this.inSlowdown = inSlowdown; }

    public HashMap<Player, Boolean> getMembers() { return members; }

    public long getCreatedTime() { return createdTime; }
    public long getLastUpdate() { return lastUpdate; }

    ///// SENDING MESSAGES ---------------------------------------------------------------------------------------------

    public void sendMessage(final Player sender, final String message) {
        //Checking if the sender is a part of this group.
        if (!members.containsKey(sender)) return;

        //Constructing the message to be sent.
        final String finalMessage =
                ChatColor.GOLD + "[" + ChatColor.AQUA + groupName + ChatColor.GOLD + "] " +
                ChatColor.AQUA + sender.getDisplayName() + ChatColor.DARK_AQUA + " » " +
                ChatColor.RESET + message;

        //Sending the message to all of the members of this group.
        members.keySet().forEach(member -> member.sendMessage(finalMessage));
        //Updating the lastUpdate
        lastUpdate = System.currentTimeMillis();
    }

    public void broadcast(final String message) {
        //Constructing the message to be sent.
        final String finalMessage =
                ChatColor.GOLD + "[" + ChatColor.AQUA + groupName + ChatColor.GOLD + "] " +
                ChatColor.RED + "Group Broadcast" + ChatColor.DARK_AQUA + " » " +
                ChatColor.RESET + message;

        //Sending the message to all of the members of this group.
        members.keySet().forEach(member -> member.sendMessage(finalMessage));
        //Updating the lastUpdate
        lastUpdate = System.currentTimeMillis();
    }

    // ADDING/REMOVING PLAYERS -----------------------------------------------------------------------------------------
    public void addMember(final Player player) {
        members.put(player, false);
        //Updating the lastUpdate
        lastUpdate = System.currentTimeMillis();
    }
    public void kickMember(final Player kicker, final Player player) {

        //Checking if the kicker is a part of this group.
        if (!members.containsKey(kicker)) return;
        //Checking if the kicked player is a part of this group.
        if (!members.containsKey(player)) return;

        //Removing the player from the database.
        members.remove(player);

        //Informing the player that they have been kicked.
        player.sendMessage(DataStorage.pluginTag + ChatColor.RED + " You have been kicked from group " + ChatColor.YELLOW + groupName + ChatColor.RED +  ".");

        //Broadcasting the group that a player has been kicked.
        broadcast(kicker.getName() + " has kicked the player " + player.getName() + " from this group.");

        //Updating the lastUpdate
        lastUpdate = System.currentTimeMillis();
    }

    // SAVING/REMOVING TO/FROM DISC --------------------------------------------------------------------------------------------------
    public void save() {
        //TODO: FILL HERE
    }

    public void purge() {
        //TODO: FILL HERE
    }
}
