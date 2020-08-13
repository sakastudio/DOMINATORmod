package com.sakastudio.dominatormod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMapBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.sakastudio.dominatormod.network.*;
import scala.collection.parallel.ParIterableLike;

import java.awt.*;

public class ItemDOMINATOR extends Item {

    static boolean isCechking = true;
    public static int CrimeCoefficient;
    static String cachePlayer;
    static World cacheWorld;
    static EntityPlayer cacheEntityPlayer;


    public ItemDOMINATOR() {
        super();
        //レジストリに保存する名称を登録する。大文字禁止。
        this.setRegistryName(DOMINATORmod.MOD_ID, "dominator");
        //クリエイティブタブを設定する。
        this.setCreativeTab(CreativeTabs.MATERIALS);
        //翻訳名を登録する。大文字非推奨。
        this.setTranslationKey("dominator");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        cacheWorld = worldIn;
        cacheEntityPlayer = playerIn;
        EntityPlayer p = ClientProxy.Inctance().GetEntityPlayer();

        if(p != null){
            if(isCechking){
                PacketHandler.INSTANCE.sendToServer(new PacketServerSendKey(p.getEntityId()));
                cachePlayer = p.getName();
                isCechking = false;
            }else if(!isCechking && cachePlayer.equals(p.getName())){
                if(CrimeCoefficient < 100){
                    isCechking = true;
                    //何もしない
                }else if(CrimeCoefficient < 300){
                    //パラライザー
                    PacketHandler.INSTANCE.sendToServer(new PacketServerPlayerKill(p.getEntityId(),false));
                    worldIn.playSound(playerIn,playerIn.posX,playerIn.posY,playerIn.posZ, DOMINATORmod.Shot, SoundCategory.PLAYERS,1f,1f);
                }else{
                    //エリミネーター
                    PacketHandler.INSTANCE.sendToServer(new PacketServerPlayerKill(p.getEntityId(),true));
                    worldIn.playSound(playerIn,playerIn.posX,playerIn.posY,playerIn.posZ, DOMINATORmod.Shot, SoundCategory.PLAYERS,1f,1f);
                }
                isCechking = true;
            }
        }else {
            isCechking = true;
        }
        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }

    public static void PlaySoundDominator(){
        Minecraft.getMinecraft().player.sendMessage(new TextComponentString("CrimeCoefficient " + CrimeCoefficient));
        if(CrimeCoefficient < 100){
            isCechking = true;
            //何もしない
            cacheWorld.playSound(cacheEntityPlayer,cacheEntityPlayer.posX,cacheEntityPlayer.posY,cacheEntityPlayer.posZ, DOMINATORmod.Under100, SoundCategory.PLAYERS,1f,1f);
        }else if(CrimeCoefficient < 300){
            //パラライザー
            cacheWorld.playSound(cacheEntityPlayer,cacheEntityPlayer.posX,cacheEntityPlayer.posY,cacheEntityPlayer.posZ, DOMINATORmod.Over100, SoundCategory.PLAYERS,1f,1f);
        }else{
            //エリミネーター
            cacheWorld.playSound(cacheEntityPlayer,cacheEntityPlayer.posX,cacheEntityPlayer.posY,cacheEntityPlayer.posZ, DOMINATORmod.Over300, SoundCategory.PLAYERS,1f,1f);
        }
    }
}
