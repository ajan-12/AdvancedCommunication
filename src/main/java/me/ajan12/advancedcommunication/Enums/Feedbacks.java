package me.ajan12.advancedcommunication.Enums;

import me.ajan12.advancedcommunication.Utilities.DataStorage;
import org.bukkit.ChatColor;

/**
 * The messages sent when an error is faced.
 */
public enum Feedbacks {

    //Ignore command errors.
    NONE_IGNORED(DataStorage.pluginTag + ChatColor.DARK_RED + " You aren't ignoring anyone."),
    SELF_IGNORE(DataStorage.pluginTag + ChatColor.DARK_RED + " You cannot ignore yourself."),

    //Reply command errors.
    NOT_MESSAGED_BY(DataStorage.pluginTag + ChatColor.DARK_RED + " You haven't been messaged by anoyone recently."),

    //Group creation errors.
    BANNED_NAME(DataStorage.pluginTag + ChatColor.DARK_RED + " The specified group name is banned."),
    USED_NAME(DataStorage.pluginTag + ChatColor.DARK_RED + " The specified group name is already used."),
    NAME_TOO_LONG(DataStorage.pluginTag + ChatColor.DARK_RED + " The specified group name is too long. Maximum for group names is 32 characters."),
    NAME_TOO_SHORT(DataStorage.pluginTag + ChatColor.DARK_RED + " The specified group name is too short. Minimum for group names is 4 characters."),

    //Misc. group errors.
    NOT_MEMBER(DataStorage.pluginTag + ChatColor.DARK_RED + " You aren't a member of this group."),
    TARGET_NOT_MEMBER(DataStorage.pluginTag + ChatColor.DARK_RED + " The target player isn't a member of this group."),
    NOT_ADMIN(DataStorage.pluginTag + ChatColor.DARK_RED + " You aren't a group admin in this group."),

    //Invitation and Kicking errors.
    INVITE_SELF(DataStorage.pluginTag + ChatColor.DARK_RED + " You can't invite yourself."),
    NOT_INVITED(DataStorage.pluginTag + ChatColor.DARK_RED + " There isn't any ongoing invitations for you."),
    IN_GROUP_ALREADY(DataStorage.pluginTag + ChatColor.DARK_RED + " The specified player is already a member of this group."),
    KICK_SELF(DataStorage.pluginTag + ChatColor.DARK_RED + " You can't kick yourself."),

    //Not found errors.
    FOCUSABLE_NOT_FOUND(DataStorage.pluginTag + ChatColor.DARK_RED + " You aren't focusing on any group or player."),
    TARGET_FOCUSABLE_NOT_FOUND(DataStorage.pluginTag + ChatColor.DARK_RED + " Couldn't find the specified player/group."),
    GROUP_NOT_FOUND(DataStorage.pluginTag + ChatColor.DARK_RED + " Couldn't find the specified group."),
    PLAYER_NOT_FOUND(DataStorage.pluginTag + ChatColor.DARK_RED + " Couldn't find the specified player."),

    //User not found errors.
    SENDER_USER_NOT_FOUND(DataStorage.pluginTag + ChatColor.DARK_RED + " Please re-log to the server and try again."),
    TARGET_USER_NOT_FOUND_TARGET(DataStorage.pluginTag + ChatColor.DARK_RED + " Please re-log to the server."),
    TARGET_USER_NOT_FOUND_SENDER(DataStorage.pluginTag + ChatColor.DARK_RED + " The command has failed. Please try again."),

    //Misc. errors.
    CONSOLE_MESSAGE_ERROR(DataStorage.pluginTag + ChatColor.DARK_RED + " This command can only be applied in-game!"),
    PERMISSION_OTHERS_ERROR(DataStorage.pluginTag + ChatColor.DARK_RED + " You do not have the permission to do this to others!"),
    PERMISSION_ERROR(DataStorage.pluginTag + ChatColor.DARK_RED + " You do not have the permission to do this!");

    private String message;

    Feedbacks(final String message) {
        this.message = message;
    }

    @Override
    public String toString() { return message; }
}
