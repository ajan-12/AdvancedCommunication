package me.ajan12.advancedcommunication.Objects;

import com.sun.istack.internal.Nullable;
import me.ajan12.advancedcommunication.AdvancedCommunication;
import me.ajan12.advancedcommunication.Utilities.DataStorage;
import me.ajan12.advancedcommunication.Utilities.PacketUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

public class User extends Focusable implements Mutable{

    private UUID uuid;

    private Focusable focused;
    private Focusable messagedBy;

    private boolean isInvited;
    @Nullable private String inviter;
    @Nullable private UUID invitedGroup;

    private ArrayList<UUID> ignoredUsers;
    private HashMap<String, UUID> groups;

    private byte muteState;
    private long muteEnd;

    private String muteReason;

    public User(final UUID uuid) {
        this.uuid = uuid;

        focused = null;
        messagedBy = null;

        isInvited = false;
        inviter = null;
        invitedGroup = null;

        ignoredUsers = new ArrayList<>();
        groups = new HashMap<>();
    }

    public User(final UUID uuid, final ArrayList<UUID> ignoredUsers, final HashMap<String, UUID> groups, final byte muteState, final long muteEnd, final String muteReason) {
        this.uuid = uuid;

        focused = null;

        isInvited = false;
        inviter = null;
        invitedGroup = null;

        this.ignoredUsers = ignoredUsers;
        this.groups = groups;

        this.muteState = muteState;
        this.muteEnd = muteEnd;
        this.muteReason = muteReason;
    }

    public Player getPlayer() { return Bukkit.getPlayer(uuid); }

    public Focusable getFocused() { return focused; }
    public boolean isFocused() { return focused != null; }
    public void setFocused(@Nullable final Focusable focused) { this.focused = focused; }

    public Focusable getMessagedBy() { return messagedBy; }
    public boolean isMessagedBy() { return messagedBy != null; }
    public void setMessagedBy(@Nullable final Focusable messagedBy) { this.messagedBy = messagedBy; }

    public boolean isInvited() { return isInvited; }
    public void setInvited(boolean invited) { isInvited = invited; }

    public String getInviter() { return inviter; }
    public void setInviter(@Nullable final String inviter) { this.inviter = inviter; }

    public UUID getInvitedGroup() { return invitedGroup; }
    public void setInvitedGroup(@Nullable final UUID invitedGroup) { this.invitedGroup = invitedGroup; }

    public ArrayList<UUID> getIgnoredUsers() { return ignoredUsers; }
    public void addIgnoredUser(final UUID uuid) { ignoredUsers.add(uuid); }
    public void removeIgnoredUser(final UUID uuid) { ignoredUsers.remove(uuid); }

    public Map<String, UUID> getGroups() { return groups; }
    public UUID getGroup(final String name) { return groups.get(name); }

    public void addGroup(final String name, final UUID uuid) { groups.put(name, uuid); }
    public void removeGroup(final String group) { groups.remove(group); }

    /**
     * Invites this user to the given group.
     * This method sends messages to the group, invitee and handles the expiration.
     *
     * @param group  : The group User is invited to.
     * @param inviter: The name of the inviter of this invitation.
     */
    public void inviteToGroup(final Group group, final String inviter) {

        //Getting the pluginTag to ease my work.
        final String pluginTag = DataStorage.pluginTag;

        //Getting the player this User represents.
        final Player player = getPlayer();
        //Checking if the player exists.
        if (player == null) return;

        //Storing the invitation.
        this.isInvited = true;
        this.inviter = inviter;
        this.invitedGroup = group.getUUID();

        //Broadcasting to the group.
        group.broadcast(ChatColor.YELLOW + inviter + ChatColor.AQUA + " has invited " +
                ChatColor.YELLOW + player.getName() + ChatColor.AQUA + " to the group.");

        //Helping the player about accepting and denying the invitation.
        player.sendMessage(pluginTag + " " + ChatColor.YELLOW + inviter + ChatColor.AQUA + " has invited you to the group " + ChatColor.YELLOW + group);
        player.sendMessage(pluginTag + ChatColor.AQUA + " If you wish to accept the invitation: ");
        player.sendMessage(ChatColor.AQUA + " - " + ChatColor.YELLOW + "/group accept" + ChatColor.GREEN + " : " + ChatColor.AQUA + "To accept.");
        player.sendMessage(ChatColor.AQUA + " - " + ChatColor.YELLOW + "/group deny" + ChatColor.GREEN + " : " + ChatColor.AQUA + "To deny.");
        player.sendMessage(pluginTag + ChatColor.DARK_RED + " This invitation will expire in 5 minutes.");

        //Scheduling the expiration.
        Bukkit.getScheduler().runTaskLaterAsynchronously(AdvancedCommunication.getInstance(), () -> {

            //Checking if the User is still invited.
            if (isInvited) {

                //Informing the User about the expiration.
                player.sendMessage(pluginTag + ChatColor.DARK_RED + " The invitation from " + ChatColor.YELLOW + inviter + ChatColor.AQUA + " has been expired!");
                //Informing the group about the expiration.
                group.broadcast(ChatColor.AQUA + "The invitation to " + ChatColor.YELLOW + player.getName() + ChatColor.AQUA + " has been expired!");

                //Removing the invitation.
                this.isInvited = false;
                this.inviter = null;
                this.invitedGroup = null;
            }

        //6000 ticks is 5 minutes.
        }, 6000);
    }

    // Focusable stuff
    @Override
    public String getName() { return getPlayer().getDisplayName(); }

    @Override
    public UUID getUUID() { return uuid; }

    @Override
    public void sendMessage(Focusable sender, String message) {

        //Getting the player this User represents.
        final Player player = getPlayer();
        //Checking if the player exists.
        if (player == null) return;

        //Preparing the message.
        final String finalMessage =
                ChatColor.GOLD + "[" + ChatColor.AQUA + sender.getName() +
                ChatColor.GOLD + "] >> [" + ChatColor.RED + "You" + ChatColor.GOLD + "] : " +
                ChatColor.RESET + message;

        //Sending the message to the sender.
        super.sendMessageSender(sender, this, message);

        //Checking if the ignoredUsers contains the sender.
        if (ignoredUsers.contains(sender.getUUID())) return;

        //Sending the message to spies.
        super.sendMessageSpies(sender, this, message);

        //Sending the message to both target player and sender player.
        player.sendMessage(finalMessage);

        //Sending a hotbar message to the focused player.
        PacketUtils.sendHotbarMessage(player, finalMessage);

        //Setting the messagedBy.
        messagedBy = sender;
    }

    // Mutable stuff
    @Override
    public byte getMuteState() { return muteState; }

    @Override
    public boolean isSoftMuted() { return muteState == 0x01; }

    @Override
    public boolean isHardMuted() { return muteState == 0x02; }

    @Override
    public void softMute(boolean softMuted) {

        if (softMuted) {
            muteState = 0x01;
        } else {
            muteState = 0x00;
        }
    }

    @Override
    public void hardMute(boolean hardMuted) {

        if (hardMuted) {
            muteState = 0x02;
        } else {
            muteState = 0x00;
        }
    }

    @Override
    public long getMuteEnd() { return muteEnd; }
    @Override
    public void setMuteEnd(long muteEnd) { this.muteEnd = muteEnd; }

    @Override
    public String getMuteReason() { return muteReason; }
    @Override
    public void setMuteReason(String muteReason) { this.muteReason = muteReason; }
}
