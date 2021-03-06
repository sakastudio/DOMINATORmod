package com.sakastudio.dominatormod;

import com.sakastudio.dominatormod.command.SetGoogleSheetsCommand;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;
import com.sakastudio.dominatormod.network.PacketHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(
        modid = DOMINATORmod.MOD_ID,
        name = DOMINATORmod.MOD_NAME,
        version = DOMINATORmod.VERSION
)
public class DOMINATORmod{

    public static final String MOD_ID = "dominatormod";
    public static final String MOD_NAME = "DOMINATORmod";
    public static final String VERSION = "1.0.0";

    public static final Item DOMINATOR = new ItemDOMINATOR();
    public static final SoundEvent Over100 =new SoundEvent(new ResourceLocation(DOMINATORmod.MOD_ID, "over100"));
    public static final SoundEvent Over300 =new SoundEvent(new ResourceLocation(DOMINATORmod.MOD_ID, "over300"));
    public static final SoundEvent Under100 =new SoundEvent(new ResourceLocation(DOMINATORmod.MOD_ID, "under100"));
    public static final SoundEvent Shot =new SoundEvent(new ResourceLocation(DOMINATORmod.MOD_ID, "shot"));

    @Mod.EventHandler
    //この関数でMODファイル自体をイベントの発火先にする。
    public void construct(FMLConstructionEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        PacketHandler.registerMessages(MOD_ID);
    }

    //アイテムを登録するイベント。旧preinitのタイミングで発火する。
    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(DOMINATOR);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void registerModels(ModelRegistryEvent event) {
        OBJLoader.INSTANCE.addDomain(MOD_ID);
        ModelLoader.setCustomModelResourceLocation(DOMINATOR, 0, new ModelResourceLocation(new ResourceLocation(MOD_ID, "dominator.obj"), "inventory"));
    }

    //画面内にエンティティがいる時のイベント
    @SubscribeEvent
    public void displayEntityStatus(RenderGameOverlayEvent.Pre event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.CHAT) {
            return;
        }
        ClientProxy.Inctance().setEntityInCrosshairs();
    }

    @SubscribeEvent
    public void drawHealthBar(RenderGameOverlayEvent.Pre event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.CHAT) {
            return;
        }
        HUD.Instance.Update();
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event){
        event.registerServerCommand(new SetGoogleSheetsCommand());
    }

    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static DOMINATORmod INSTANCE;

    /**
     * This is the first initialization event. Register tile entities here.
     * The registry events below will have fired prior to entry to this method.
     */
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {

    }

    /**
     * This is the second initialization event. Register custom recipes
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    /**
     * This is the final initialization event. Register actions from other mods here
     */
    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {

    }

    /**
     * Forge will automatically look up and bind blocks to the fields in this class
     * based on their registry name.
     */
    @GameRegistry.ObjectHolder(MOD_ID)
    public static class Blocks {
      /*
          public static final MySpecialBlock mySpecialBlock = null; // placeholder for special block below
      */
    }

    /**
     * Forge will automatically look up and bind items to the fields in this class
     * based on their registry name.
     */
    @GameRegistry.ObjectHolder(MOD_ID)
    public static class Items {
      /*
          public static final ItemBlock mySpecialBlock = null; // itemblock for the block above
          public static final MySpecialItem mySpecialItem = null; // placeholder for special item below
      */
    }

    /**
     * This is a special class that listens to registry events, to allow creation of mod blocks and items at the proper time.
     */
    @Mod.EventBusSubscriber
    public static class ObjectRegistryHandler {
        /**
         * Listen for the register event for creating custom items
         */
        @SubscribeEvent
        public static void addItems(RegistryEvent.Register<Item> event) {
           /*
             event.getRegistry().register(new ItemBlock(Blocks.myBlock).setRegistryName(MOD_ID, "myBlock"));
             event.getRegistry().register(new MySpecialItem().setRegistryName(MOD_ID, "mySpecialItem"));
            */
        }

        /**
         * Listen for the register event for creating custom blocks
         */
        @SubscribeEvent
        public static void addBlocks(RegistryEvent.Register<Block> event) {
           /*
             event.getRegistry().register(new MySpecialBlock().setRegistryName(MOD_ID, "mySpecialBlock"));
            */
        }
    }
    /* EXAMPLE ITEM AND BLOCK - you probably want these in separate files
    public static class MySpecialItem extends Item {

    }

    public static class MySpecialBlock extends Block {

    }
    */
}
