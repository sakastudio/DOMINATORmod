package sakastudio.dominatormod;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMapBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sakastudio.dominatormod.network.*;

import java.awt.*;

public class ItemDOMINATOR extends Item {

    boolean isCechking = true;
    public static int CrimeCoefficient;
    String cachePlayer;


    public ItemDOMINATOR() {
        super();
        //レジストリに保存する名称を登録する。大文字禁止。
        this.setRegistryName(DOMINATORmod.MOD_ID, "dominator");
        //クリエイティブタブを設定する。
        this.setCreativeTab(CreativeTabs.MATERIALS);
        //翻訳名を登録する。大文字非推奨。
        this.setTranslationKey("DOMINATOR");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        EntityPlayer p = ClientProxy.Inctance().GetEntityPlayer();

        if(p != null){
            if(isCechking){
                PacketHandler.INSTANCE.sendToServer(new PacketServerSendKey(p.getEntityId()));
                cachePlayer = p.getName();
                isCechking = false;
            }else if(!isCechking && cachePlayer.equals(p.getName())){
                if(CrimeCoefficient < 100){
                    //何もしない

                }else if(CrimeCoefficient < 300){
                    //パラライザー
                    PacketHandler.INSTANCE.sendToServer(new PacketServerPlayerKill(p.getEntityId(),false));
                }else{
                    //エリミネーター
                    PacketHandler.INSTANCE.sendToServer(new PacketServerPlayerKill(p.getEntityId(),true));
                }
                isCechking = true;
            }
        }else {
            isCechking = true;
        }
        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }
}
