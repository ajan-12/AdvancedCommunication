package me.ajan12.advancedcommunication.Listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import me.ajan12.advancedcommunication.AdvancedCommunication;
import me.ajan12.advancedcommunication.Objects.MentionedMessage;
import me.ajan12.advancedcommunication.Utilities.DataStorage;
import me.ajan12.advancedcommunication.Utilities.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;

public class ProtocolLibEvents {

    /**
     * Registers the ProtocolLib listeners.
     */
    public static void registerListeners() {
        //Adding the listeners.
        DataStorage.protocolManager.addPacketListener(new PacketAdapter(AdvancedCommunication.getInstance(), ListenerPriority.HIGH, PacketType.Play.Client.TAB_COMPLETE, PacketType.Play.Server.CHAT) {

            @Override
            public void onPacketReceiving(PacketEvent event) {
                try {

                    //Checking if the packet type is TAB_COMPLETE
                    if (event.getPacketType() == PacketType.Play.Client.TAB_COMPLETE) {

                        //The packet received by the server.
                        final PacketContainer packet = event.getPacket();

                        //Reading the text int the packet.
                        final String text = packet.getStrings().readSafely(0);
                        //Splitting the text up into its words.
                        final String[] words = text.split(" ");

                        //Checking if the last word starts with a "@".
                        if (words[words.length - 1].startsWith("@")) {
                            //Getting the player name fragment player has typed yet.
                            final String playerNameFragment = words[words.length - 1].replaceFirst("@", "");

                            //Getting the matches.
                            final HashSet<String> matches = new HashSet<>();
                            //Iterating over the players.
                            for (final Player player : Bukkit.getOnlinePlayers()) {

                                //Checking if the player's name starts with the player name fragment.
                                if (player.getName().toLowerCase().startsWith(playerNameFragment.toLowerCase())) {
                                    //Adding the matched player's name.
                                    matches.add("@" + player.getName());
                                }
                            }


                            //Checking if there were any matches.
                            if (matches.size() > 0) {

                                //Creating the packet we'll send.
                                final PacketContainer response = DataStorage.protocolManager.createPacket(PacketType.Play.Server.TAB_COMPLETE);

                                //Adding the matches to the response packet
                                final String[] matchesArr = matches.toArray(new String[0]);
                                response.getStringArrays().writeSafely(0, matchesArr);

                                //Sending a new packet to the client.
                                try {
                                    DataStorage.protocolManager.sendServerPacket(event.getPlayer(), response);
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }

                                //Cancelling this event.
                                event.setCancelled(true);
                            }
                        }
                    }
                } catch (IllegalStateException ignored) {}
            }

            @Override
            public void onPacketSending(PacketEvent event) {
                try {
                    //Checking if the packet type is CHAT
                    if (event.getPacketType() == PacketType.Play.Server.CHAT) {

                        //The packet sent by the server.
                        final PacketContainer packet = event.getPacket();

                        //Getting the User of the sender.
                        final MentionedMessage mentionedMessage = GeneralUtils.getMentionedMessage(event.getPlayer());

                        //Checking if the MentionedMessage was found.
                        if (mentionedMessage == null) return;

                        //Getting the ChatComponent of the packet.
                        final WrappedChatComponent chat = packet.getChatComponents().readSafely(0);

                        //Getting the JSON string of the chat component.
                        String json = chat.getJson();

                        //Highlighting the message.
                        json = json.replaceAll("\\{(\"color\":\"white\",)?(\"text\":\".*?\")\\}", "{\"color\":\"yellow\",$2}");

                        //Copy the content of the message into this temporary variable.
                        String tmpMessage = json;

                        //Iterating over all the mentions.
                        for (int i = 0; i <= mentionedMessage.getTotalMentions();) {

                            //extract the first found block of JSON representing a mention from the tmpMessage
                            String mentionJson = tmpMessage.replaceAll("(^.*?)(\\{\"color\":\"yellow\",\"text\":\"@.*?\"\\})(($|,\\{(\"color\":\"\\w+\",)?\"text\":\"\\W).*?$)", "$2");

                            System.out.println(ChatColor.stripColor("@" + event.getPlayer().getDisplayName()) + " SEPERATOR " + i + " SEPERATOR " + mentionJson.replaceAll("(^.*?\"text\":\"|\".*?\"text\":\"|\".*?$)","") + " SEPERATOR " + tmpMessage);

                            //Strip JSON formatting to get the plaintext displayname of the player, and compare it to the current player's displayname (with colorcodes stripped).
                            if (ChatColor.stripColor("@" + event.getPlayer().getDisplayName()).equals(mentionJson.replaceAll("(^.*?\"text\":\"|\".*?\"text\":\"|\".*?$)",""))) {

                                //If it matches, this mention JSON represents the current player being mentioned; We want to process it.
                                String mentionJsonNew = mentionJson.replaceAll("\\{","{\"bold\":true,").replaceAll("\"color\":\"(yellow|white)\"","\"color\":\"gold\"");
                                //Replace the old JSON block with the new, processed JSON block.
                                json = json.replace(mentionJson, mentionJsonNew);

                                //Telling the loop that we're done here.
                                break;
                            } else {

                                //If it does not match, this is not the JSON block we are looking for. Remove it from the tmpMessage and start over.
                                tmpMessage = tmpMessage.replaceFirst("(\\{\"color\":\"yellow\",\"text\":\"@.*?\"})($|,(\\{(\"color\":\"\\w+\",)?\"text\":\"\\W))","$3");
                            }

                            i++;
                        }

                        //Saving the json text to chat component.
                        chat.setJson(json);

                        //Writing the new ChatComponent to the packet.
                        packet.getChatComponents().writeSafely(0, chat);
                        //Saving the packet to the event.
                        event.setPacket(packet);

                        //Removing the player from mentionedMessage.
                        mentionedMessage.removePlayerMention(event.getPlayer());

                        //Checking if there are any players left and if not we're removing the mentionedMessage from DataStorage.
                        if (mentionedMessage.getPlayerMentions().size() <= 0) DataStorage.removeMention(mentionedMessage);
                    }
                } catch (IllegalStateException ignored) {}
            }
        });
    }
}
