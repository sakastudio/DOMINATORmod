package sakastudio.dominatormod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import sakastudio.dominatormod.Utility;

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
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));

            return null;
        }

        private void handle(PacketServerSendKey message, MessageContext ctx) {

            //返信用プレイヤーエンティティ
            EntityPlayerMP playerEntity = ctx.getServerHandler().player;

            //プレイヤーリストの取得
            List<EntityPlayerMP> list = playerEntity.getServer().getPlayerList().getPlayers();
            int num = 0;

            //エンティティIDからターゲットエンティティを取得
            for (EntityPlayerMP item: list) {
                //ターゲットのプレイヤーである
                if(message.EntityID == item.getEntityId()){
                    //そのプレイヤーのインベントリをみて犯罪係数を確定
                    num = Utility.GetCrimeCoefficient(item);
                    break;
                }
            }

            PacketHandler.INSTANCE.sendTo(new PacketClientSendKey(num),playerEntity);
        }
    }
}
