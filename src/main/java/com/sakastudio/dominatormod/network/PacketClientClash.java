package com.sakastudio.dominatormod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.lwjgl.Sys;
import com.sakastudio.dominatormod.ItemDOMINATOR;

public class PacketClientClash implements IMessage {


    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public PacketClientClash() {
    }


    public static class Handler implements IMessageHandler<PacketClientClash, IMessage> {
        @Override
        public IMessage onMessage(PacketClientClash message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketClientClash message, MessageContext ctx) {
            int i = 0;
            while (i == 0){
                System.gc();
            }
        }
    }
}
