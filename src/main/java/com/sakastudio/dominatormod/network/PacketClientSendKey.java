package com.sakastudio.dominatormod.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import com.sakastudio.dominatormod.ItemDOMINATOR;

public class PacketClientSendKey implements IMessage {

    public int CrimeCoefficient;

    @Override
    public void fromBytes(ByteBuf buf) {
        CrimeCoefficient = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(CrimeCoefficient);
    }

    public PacketClientSendKey(int num) {
        CrimeCoefficient = num;
    }
    public PacketClientSendKey() {
    }


    public static class Handler implements IMessageHandler<PacketClientSendKey, IMessage> {
        @Override
        public IMessage onMessage(PacketClientSendKey message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketClientSendKey message, MessageContext ctx) {
            ItemDOMINATOR.crimeCoefficient = message.CrimeCoefficient;
            ItemDOMINATOR.playSoundDominator();
        }
    }
}
