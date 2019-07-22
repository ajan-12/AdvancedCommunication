package me.ajan12.advancedcommunication.Objects;

import java.util.HashMap;

public class MentionedMessage {

    private HashMap<org.bukkit.entity.Player, Integer> playerMentions;
    private int totalMentions;

    public MentionedMessage(final HashMap<org.bukkit.entity.Player, Integer> playerMentions, final int totalMentions) {
        this.playerMentions = playerMentions;
        this.totalMentions = totalMentions;
    }

    public HashMap<org.bukkit.entity.Player, Integer> getPlayerMentions() { return playerMentions; }
    public int getTotalMentions() { return totalMentions; }

    public void removePlayerMention(final org.bukkit.entity.Player player) { playerMentions.remove(player); }
}
