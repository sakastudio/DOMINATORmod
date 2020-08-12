package sakastudio.dominatormod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityEnderChest;
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

        EntityID = buf.readInt();
        isClash = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
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
                    //そのプレイヤーをキル
                    item.onKillCommand();
                    InventoryEnderChest ender = item.getInventoryEnderChest();
                    //エンダーチェストの中身をドロップ
                    for (int i = 0;i<ender.getSizeInventory();i++){
                        EntityItem itemEntity = new EntityItem(item.world,item.posX,item.posY + 1,item.posZ,ender.getStackInSlot(i));
                        item.world.spawnEntity(itemEntity);
                        ender.setInventorySlotContents(i,new ItemStack(new Block(Material.AIR)));
                    }


                    if(message.isClash){
                        //相手プレイヤーをクラッシュ
                        PacketHandler.INSTANCE.sendTo(new PacketClientClash(),item);
                    }
                    break;
                }
            }
        }
    }
}
