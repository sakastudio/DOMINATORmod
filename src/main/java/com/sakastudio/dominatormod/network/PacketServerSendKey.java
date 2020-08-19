package com.sakastudio.dominatormod.network;

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
import com.sakastudio.dominatormod.Utility;

import java.util.List;

public class PacketServerSendKey implements IMessage {
    public int EntityID;

    public PacketServerSendKey() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        EntityID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
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

            EntityPlayerMP playerEntity = ctx.getServerHandler().player;

            List<EntityPlayerMP> list = playerEntity.getServer().getPlayerList().getPlayers();
            int num = 0;

            for (EntityPlayerMP item: list) {
                if(message.EntityID == item.getEntityId()){
                    num = Utility.GetCrimeCoefficient(item);
                    break;
                }
            }
            PacketHandler.INSTANCE.sendTo(new PacketClientSendKey(num),playerEntity);
        }
    }
}
