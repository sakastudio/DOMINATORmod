package sakastudio.dominatormod.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    private static int packetId = 0;

    public static SimpleNetworkWrapper INSTANCE = null;

    public PacketHandler() {
    }

    public static int nextID() {
        return packetId++;
    }

    public static void registerMessages(String channelName) {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
        registerMessages();
    }

    public static void registerMessages() {
        // Register messages which are sent from the client to the server here:
        INSTANCE.registerMessage(PacketServerSendKey.Handler.class, PacketServerSendKey.class, nextID(), Side.SERVER);
        INSTANCE.registerMessage(PacketClientSendKey.Handler.class, PacketClientSendKey.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(PacketServerPlayerKill.Handler.class, PacketServerPlayerKill.class, nextID(), Side.SERVER);
        INSTANCE.registerMessage(PacketClientClash.Handler.class, PacketClientClash.class, nextID(), Side.CLIENT);
    }
}