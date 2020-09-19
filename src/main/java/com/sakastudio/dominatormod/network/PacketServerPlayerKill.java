package com.sakastudio.dominatormod.network;

import com.sakastudio.dominatormod.CustomObjects;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import com.sakastudio.dominatormod.Utility;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Random;

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

            //設定するメッセージ
            //String chatMessage = "";

            //エンティティIDからターゲットエンティティを取得
            for (EntityPlayerMP item: list) {
                //ターゲットのプレイヤーである
                if(message.EntityID == item.getEntityId()){

                    InventoryEnderChest ender = item.getInventoryEnderChest();
                    //エンダーチェストの中身をドロップ
                    for (int i = 0;i<ender.getSizeInventory();i++){
                        EntityItem itemEntity = new EntityItem(item.world,item.posX,item.posY + 1,item.posZ,ender.getStackInSlot(i));
                        item.world.spawnEntity(itemEntity);
                        ender.setInventorySlotContents(i,new ItemStack(new Block(Material.AIR)));
                    }

                    //そのキルのソース
                    DamageSource d;

                    if(message.isClash){
                        //相手プレイヤーをクラッシュ
                        PacketHandler.INSTANCE.sendTo(new PacketClientClash(),item);

                        //ソースを設定
                        String chatMessage = CustomObjects.Instance.GetCustomBanlog(item.getName());
                        d = new DamageSource("BanSibylNone") {
                            @Override public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn) {
                                if (!StringUtils.isBlank(chatMessage))
                                    return new TextComponentString(chatMessage);
                                return super.getDeathMessage(entityLivingBaseIn);
                            }
                        }.setDamageAllowedInCreativeMode();
                    }else {
                        //ソースを設定
                        String chatMessage = CustomObjects.Instance.GetCustomKilllog(item.getName());
                        d = new DamageSource("SibylNone") {
                            @Override public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn) {
                                if (!StringUtils.isBlank(chatMessage))
                                    return new TextComponentString(chatMessage);
                                return super.getDeathMessage(entityLivingBaseIn);
                            }
                        }.setDamageAllowedInCreativeMode();
                    }
                    //キル
                    item.attackEntityFrom(d,100000);
                }
            }
//            for (EntityPlayerMP item: list) {
//                item.sendMessage(new TextComponentString(chatMessage));
//            }
        }
    }
}
