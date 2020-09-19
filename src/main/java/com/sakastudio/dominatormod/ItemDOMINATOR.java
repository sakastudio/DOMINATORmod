package com.sakastudio.dominatormod;

import com.sakastudio.dominatormod.network.PacketHandler;
import com.sakastudio.dominatormod.network.PacketServerPlayerKill;
import com.sakastudio.dominatormod.network.PacketServerSendKey;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDOMINATOR extends Item {

    public static int crimeCoefficient;
    static EntityPlayer focusedPlayer;

    public ItemDOMINATOR() {
        super();
        //レジストリに保存する名称を登録する。大文字禁止。
        this.setRegistryName(DOMINATORmod.MOD_ID, "dominator");
        //クリエイティブタブを設定する。
        this.setCreativeTab(CreativeTabs.COMBAT);
        //翻訳名を登録する。大文字非推奨。
        this.setTranslationKey("dominator");
        this.setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        EntityPlayer newFocused = ClientProxy.Inctance().GetEntityPlayer();
        if (focusedPlayer != null && !focusedPlayer.equals(newFocused))
            focusedPlayer = null;

        if (focusedPlayer == null) {
            if (newFocused != null) {
                PacketHandler.INSTANCE.sendToServer(new PacketServerSendKey(newFocused.getEntityId()));
                focusedPlayer = newFocused;
            }
        } else {
            if (crimeCoefficient >= 100) {
                Minecraft.getMinecraft().getSoundHandler().stopSounds();
                if (crimeCoefficient < 300) {
                    //パラライザー
                    PacketHandler.INSTANCE.sendToServer(new PacketServerPlayerKill(focusedPlayer.getEntityId(), false));
                } else {
                    //エリミネーター
                    PacketHandler.INSTANCE.sendToServer(new PacketServerPlayerKill(focusedPlayer.getEntityId(), true));
                    Minecraft.getMinecraft().player.sendChatMessage("/ban " + focusedPlayer.getName());
                }
                worldIn.playSound(playerIn, playerIn.posX, playerIn.posY, playerIn.posZ, DOMINATORmod.Shot, SoundCategory.PLAYERS, 1f, 1f);
            }
            focusedPlayer = null;
        }
        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }

    public static void playSoundDominator() {
        Minecraft.getMinecraft().getSoundHandler().stopSounds();
        // Minecraft.getMinecraft().player.sendMessage(new TextComponentString("犯罪係数: " + crimeCoefficient));
        EntityPlayer player = Minecraft.getMinecraft().player;
        player.world.playSound(
                player,
                player.posX, player.posY, player.posZ,
                crimeCoefficient < 100 ? DOMINATORmod.Under100 : crimeCoefficient < 300 ? DOMINATORmod.Over100 : DOMINATORmod.Over300,
                SoundCategory.PLAYERS,
                1f, 1f
        );
    }
}
