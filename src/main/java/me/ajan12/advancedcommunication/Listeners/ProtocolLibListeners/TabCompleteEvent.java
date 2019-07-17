package me.ajan12.advancedcommunication.Listeners.ProtocolLibListeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

import me.ajan12.advancedcommunication.AdvancedCommunication;
import me.ajan12.advancedcommunication.Utilities.DataStorage;
import me.ajan12.advancedcommunication.Utilities.PacketUtils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashSet;

public class TabCompleteEvent {

    /**
     * Registers this listener to ProtocolLib.
     */
    public static void register() {

        //Adding a PacketListener.
        DataStorage.protocolManager.addPacketListener(

                //Creating a new PacketAdapter with HIGH ListenerPriority to let other plugins do their jobs first.
                new PacketAdapter(AdvancedCommunication.getInstance(), ListenerPriority.HIGH, PacketType.Play.Server.TAB_COMPLETE) {

                    @Override
                    public void onPacketReceiving(PacketEvent event) {
                        //Checking if the packet type is TAB_COMPLETE
                        if (event.getPacketType() == PacketType.Play.Server.TAB_COMPLETE) {

                            //The packet we received.
                            PacketContainer packet = event.getPacket();

                            //The transaction id.
                            int id = packet.getIntegers().readSafely(0);

                            //The message that was typed yet.
                            String message = packet.getStrings().readSafely(0);

                            //Checking if the last char is @
                            if (message.charAt(message.length() - 1) == '@') {

                                //Getting the online players.
                                Collection<? extends Player> players = Bukkit.getOnlinePlayers();

                                //Removing the sender because we don't want it to be offered as a match.
                                players.remove(event.getPlayer());

                                //Array version of players.
                                final Player[] playersArr = (Player[]) players.toArray();
                                //Creating a String array for matches.
                                final String[] matches = new String[playersArr.length];

                                //Iterating over the playersArr
                                for (int i = 0; i < playersArr.length; i++) {

                                    //Getting the player that we are iterating over.
                                    final Player p = playersArr[i];
                                    //Adding its name to matches.
                                    matches[i] = p.getName();

                                }

                                //Sending a tab complete packet from server to the player to respond.
                                PacketUtils.sendTabCompletePacketServer(event.getPlayer(), id, message.length() - 1, 0, matches.length, matches);

                            } else {

                                //Splitting the message into its words.
                                final String[] words = message.split(" ");

                                //Checking if the last word contains @
                                if (words[words.length - 1].startsWith("@")) {

                                    //The amount of name that the player has typed.
                                    final String nameBeginning = words[words.length - 1].replace("@", "");

                                    //Getting the online players.
                                    Collection<? extends Player> players = Bukkit.getOnlinePlayers();

                                    //Removing the sender because we don't want it to be offered as a match.
                                    players.remove(event.getPlayer());

                                    //Array version of players.
                                    final Player[] playersArr = (Player[]) players.toArray();
                                    //Creating a String hashset for matches.
                                    final HashSet<String> matches = new HashSet<>();

                                    //Iterating over the playersArr
                                    for (final Player p : playersArr) {

                                        //Getting the player that we are iterating over.
                                        //Getting the name of the player.
                                        final String name = p.getName();

                                        //Checking if the name is bigger than the nameBeginning.
                                        if (name.length() > nameBeginning.length()) {

                                            //Creating a new chars array.
                                            char[] chars = new char[nameBeginning.length()];

                                            //Filling the chars array with the same amount of chars with nameBeginning to compare 1 by 1.
                                            name.getChars(0, nameBeginning.length() - 1, chars, 0);

                                            //Chars that matches with nameBeginning.
                                            int charsMatched = 0;

                                            //Iterating over chars.
                                            for (int j = 0; j < chars.length; j++) {

                                                //Getting the char we're iterating over.
                                                char c = chars[j];

                                                //Checking if the char is the same char at the same index on nameBeginning.
                                                if (c == nameBeginning.charAt(j)) {

                                                    //Incrementing charsMatched by 1.
                                                    charsMatched++;

                                                    //Checking if the char is upper case, if so we make it lower case and try again.
                                                } else if (Character.isUpperCase(c)) {

                                                    //Making the char lower case.
                                                    c = Character.toLowerCase(c);

                                                    //Checking if the char is the same char at the same index on nameBeginning.
                                                    if (c == nameBeginning.charAt(j)) charsMatched++;

                                                    //Checking if the char is upper case, if so we make it lower case and try again.
                                                } else if (Character.isLowerCase(c)) {

                                                    //Making the char upper case.
                                                    c = Character.toUpperCase(c);

                                                    //Checking if the char is the same char at the same index on nameBeginning.
                                                    if (c == nameBeginning.charAt(j)) charsMatched++;
                                                }
                                            }

                                            //Checking if all the chars match between the player name and nameBeginning.
                                            if (charsMatched == chars.length) matches.add(p.getName());

                                        }
                                    }

                                    //Converting the hashset into an array.
                                    final String[] matchesArr = (String[]) matches.toArray();

                                    //Sending a tab complete packet from server to the player to respond.
                                    PacketUtils.sendTabCompletePacketServer(event.getPlayer(), id, message.indexOf(words[words.length - 1]) + 1, words[words.length - 1].length() - 1, matchesArr.length, matchesArr);

                                }
                            }
                        }
                    }
        });
    }
}
