package me.ajan12.advancedcommunication.Utilities;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;

import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class PacketUtils {

    /**
     * Sending a Tab Complete packet from server to player.
     *
     * @param p      : The player to send the packet to.
     * @param id     : The transaction id of the packet.
     * @param start  : The starting index for the part that will be replaced.
     * @param length : The length of the part that will be replaced.
     * @param count  : The amount of matches.
     * @param matches: The matches that will be used while replacing.
     */
    public static void sendTabCompletePacketServer(final Player p, final int id, final int start, final int length, final int count, final String... matches) {

        //Creating the packet.
        PacketContainer packet = DataStorage.protocolManager.createPacket(PacketType.Play.Client.TAB_COMPLETE);

        //Initializing the integer fields.
        packet.getIntegers().
                //Writing the id.
                writeSafely(0, id).
                //Writing the start index.
                writeSafely(1, start).
                //Writing the length to be replaced.
                writeSafely(2, length).
                //Writing the amount of matches.
                writeSafely(3, count);

        //Writing the matches
        packet.getStringArrays().writeSafely(0, matches);

        try {

            //Sending the packet to the player.
            DataStorage.protocolManager.sendServerPacket(p, packet);

        } catch (InvocationTargetException e) {

            //Sending the packet failed, logging.
            throw new RuntimeException("Cannot send packet " + packet, e);

        }
    }

    /**
     * Sends a hotbar message to the player.
     *
     * @param p      : The player to send the packet to.
     * @param message: The message to show above the hotbar.
     */
    public static void sendHotbarMessage(final Player p, final String message) {

        //Creating a new packet to send the message on hotbar.
        PacketContainer hotbarMessage = DataStorage.protocolManager.createPacket(PacketType.Play.Client.CHAT);

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
}
