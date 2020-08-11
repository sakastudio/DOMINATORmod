package sakastudio.samplepacketmod;

import io.netty.buffer.ByteBuf;
import io.netty.handler.ssl.ReferenceCountedOpenSslContext;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.List;

public class Command extends CommandBase {
    @Override
    public String getName() {
        return "test";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "test";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args[0].equals("s")){
            PacketHandler.INSTANCE.sendToServer(new PacketSendKey());
        }else if(args[0].equals("c")){
            List<EntityPlayerMP> list = server.getPlayerList().getPlayers();
            System.out.println(list.get(0).getName());
            PacketHandler.INSTANCE.sendTo(new PacketSendKey(),list.get(0));
        }
    }
}
