package sakastudio.dominatormod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import sakastudio.dominatormod.Utility;

import java.util.List;

public class PacketServerPlayerKill implements IMessage {
    public int EntityID;
    public boolean isClash;

    public PacketServerPlayerKill() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        // Encoding the position as a long is more efficient
        //blockPos = BlockPos.fromLong(buf.readLong());

        EntityID = buf.readInt();
        isClash = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        // Encoding the position as a long is more efficient

        //PacketBuffer b = (PacketBuffer) buf;
        //b.writeString(entityPlayer.toString());
        //buf.writeByte(b.arrayOffset());

        buf.writeInt(EntityID);
        buf.writeBoolean(isClash);
    }

    public PacketServerPlayerKill(int id,boolean clash) {
        isClash = clash;
        EntityID = id;
    }

    public static class Handler implements IMessageHandler<PacketServerPlayerKill, IMessage> {
        @Override
        public IMessage onMessage(PacketServerPlayerKill message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));

            return null;
        }

        private void handle(PacketServerPlayerKill message, MessageContext ctx) {

            //プレイヤーリストの取得
            List<EntityPlayerMP> list = ctx.getServerHandler().player.getServer().getPlayerList().getPlayers();
            int num = 0;

            //エンティティIDからターゲットエンティティを取得
            for (EntityPlayerMP item: list) {
                //ターゲットのプレイヤーである
                if(message.EntityID == item.getEntityId()){
                    //そのプレイヤーのインベントリをみて犯罪係数を確定
                    item.onKillCommand();
                    if(message.isClash){
                        PacketHandler.INSTANCE.sendTo(new PacketClientClash(),item);
                    }
                    break;
                }
            }
        }
    }
}
