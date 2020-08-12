package sakastudio.dominatormod;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMapBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sakastudio.dominatormod.network.PacketHandler;
import sakastudio.dominatormod.network.PacketServerSendKey;

public class ItemAluminium extends Item {

    public ItemAluminium() {
        super();
        //レジストリに保存する名称を登録する。大文字禁止。
        this.setRegistryName(DOMINATORmod.MOD_ID, "aluminium");
        //クリエイティブタブを設定する。
        this.setCreativeTab(CreativeTabs.MATERIALS);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        EntityPlayer p = ClientProxy.Inctance().GetEntityPlayer();
        if(p != null){
            System.out.println("Item Right Click bbbb");
            PacketHandler.INSTANCE.sendToServer(new PacketServerSendKey(p.getEntityId()));
        }else {
            System.out.println("Item Right Click aaa");
        }
        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }
}
