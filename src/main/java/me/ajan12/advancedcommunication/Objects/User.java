package me.ajan12.advancedcommunication.Objects;

import com.sun.istack.internal.Nullable;

import me.ajan12.advancedcommunication.AdvancedCommunication;
import me.ajan12.advancedcommunication.Utilities.DataStorage;

import me.ajan12.advancedcommunication.Utilities.PacketUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User extends Focusable{

    private UUID uuid;
    private String name;

    private Focusable focused;

    private boolean isInvited;
    @Nullable private String inviter;
    @Nullable private UUID invitedGroup;

    //Group name, Group id.
    private Map<String, UUID> groups;

    public User(@Nullable final String name, final UUID uuid) {
        this.name = name;
        this.uuid = uuid;

        focused = null;

        isInvited = false;
        inviter = null;
        invitedGroup = null;

        groups = new HashMap<>();
    }

    public User(@Nullable final String name, final UUID uuid, final HashMap<String, UUID> groups) {
        this.name = name;
        this.uuid = uuid;

        focused = null;

        isInvited = false;
        inviter = null;
        invitedGroup = null;

        this.groups = groups;
    }

    public Player getPlayer() { return Bukkit.getPlayer(uuid); }

    public Focusable getFocused() { return focused; }
    public boolean isFocused() { return focused != null; }
    public void setFocused(@Nullable final Focusable focused) { this.focused = focused; }

    public boolean isInvited() { return isInvited; }
    public void setInvited(boolean invited) { isInvited = invited; }

    public String getInviter() { return inviter; }
    public void setInviter(@Nullable final String inviter) { this.inviter = inviter; }

    public UUID getInvitedGroup() { return invitedGroup; }
    public void setInvitedGroup(@Nullable final UUID invitedGroup) { this.invitedGroup = invitedGroup; }

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
    public String getName() { return name; }
    public void setName(final String name) { this.name = name; }

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

        //Sending the message to both target player and sender player.
        player.sendMessage(finalMessage);

        //Sending a hotbar message to the focused player.
        PacketUtils.sendHotbarMessage(player, finalMessage);

        //Sending the message to the sender.
        super.sendMessageSender(sender, this, message);

        //Sending the message to spies.
        super.sendMessageSpies(sender, this, message);
    }
}
