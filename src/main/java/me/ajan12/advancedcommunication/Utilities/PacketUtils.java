package me.ajan12.advancedcommunication.Utilities;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class PacketUtils {

    /**
     * Sends a hotbar message to the player.
     *
     * @param p      : The player to send the packet to.
     * @param message: The message to show above the hotbar.
     */
    public static void sendHotbarMessage(final Player p, final String message) {

        //Creating a new packet to send the message on hotbar.
        final PacketContainer hotbarMessage = DataStorage.protocolManager.createPacket(PacketType.Play.Server.CHAT);

        //Setting the message to be shown.
        hotbarMessage.getChatComponents().writeSafely(0, WrappedChatComponent.fromText(message));
        //Setting the chat type to GAME_INFO which means above hotbar.
        hotbarMessage.getChatTypes().writeSafely(0, EnumWrappers.ChatType.GAME_INFO);

        try {
            //Sending the packet to the player.
            DataStorage.protocolManager.sendServerPacket(p, hotbarMessage);

        } catch (InvocationTargetException e) {

            //Sending the packet failed, logging.
            throw new RuntimeException("Cannot send packet " + hotbarMessage, e);
        }
    }

    public static String jsonToString(String json) {

        //Replacing the json-style font options with ChatColor based options.
        json = json
                .replaceAll("\"bold\":true", ChatColor.BOLD.toString())
                .replaceAll("\"underlined\":true",ChatColor.UNDERLINE.toString())
                .replaceAll("\"strikethrough\":true",ChatColor.STRIKETHROUGH.toString())
                .replaceAll("\"obfuscated\":true",ChatColor.MAGIC.toString());

        //Replacing the json-style color options with ChatColor based options.
        for (final ChatColor chatColor: ChatColor.values()) {
            json = json.replaceAll("\"color\":\"" + chatColor.name().toLowerCase() + "\",",chatColor.toString());
        }

        //Removing the json bull-shittery.
        json = json
                .replaceAll("^.*?(§)","$1")
                .replaceAll("\"text\":\"(.*?)\"\\}(,\\{|\\],|$)", "$1");

        //Returning the String json.
        return json;
    }
}
