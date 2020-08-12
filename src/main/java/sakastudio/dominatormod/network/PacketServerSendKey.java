package sakastudio.dominatormod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import java.util.List;

public class PacketServerSendKey implements IMessage {
    public int EntityID;

    public PacketServerSendKey() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        // Encoding the position as a long is more efficient
        //blockPos = BlockPos.fromLong(buf.readLong());

        EntityID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        // Encoding the position as a long is more efficient

        //PacketBuffer b = (PacketBuffer) buf;
        //b.writeString(entityPlayer.toString());
        //buf.writeByte(b.arrayOffset());

        buf.writeInt(EntityID);
    }

    public PacketServerSendKey(int id) {
        EntityID = id;
    }

    public static class Handler implements IMessageHandler<PacketServerSendKey, IMessage> {
        @Override
        public IMessage onMessage(PacketServerSendKey message, MessageContext ctx) {
            /*Always use a construct like this to actually handle your message. This ensures that
            your 'handle' code is run on the main Minecraft thread. 'onMessage' itself
            is called on the networking thread so it is not safe to do a lot of things
            here.*/
            //FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            System.out.println("server onMessage");

            //MinecraftServer server = Minecraft.getMinecraft().getIntegratedServer().getServer();
            //List<EntityPlayerMP> list = server.getPlayerList().getPlayers();
            //System.out.println(list.get(0).getName());
            //PacketHandler.INSTANCE.sendTo(new PacketClientSendKey(),list.get(0));
            //return new PacketClientSendKey();

            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));

            return null;
        }

        private void handle(PacketServerSendKey message, MessageContext ctx) {
            // This code is run on the server side. So you can do server-side calculations here
            System.out.println("server handle");

            //返信用プレイヤーエンティティ
            EntityPlayerMP playerEntity = ctx.getServerHandler().player;

            //プレイヤーリストの取得
            List<EntityPlayerMP> list = playerEntity.getServer().getPlayerList().getPlayers();
            NonNullList<ItemStack> inv;
            //エンティティIDからターゲットエンティティを取得
            for (EntityPlayerMP item: list) {
                if(message.EntityID == item.getEntityId()){
                    System.out.println(item.getName());
                    inv = item.inventory.mainInventory;
                    for(ItemStack itemStack:inv){
                        System.out.println(itemStack.getItem().getRegistryName());
                    }
                    break;
                }
            }

            //Minecraft.getMinecraft();
            //MinecraftServer server = Minecraft.getMinecraft().getIntegratedServer().getServer();
            //List<EntityPlayerMP> list = server.getPlayerList().getPlayers();

            PacketHandler.INSTANCE.sendTo(new PacketClientSendKey(),playerEntity);
        }
    }
}
