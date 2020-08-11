package sakastudio.samplepacketmod;


import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid="samplepacketmod", name="SamplePacketMod", version="1.0.0")
public class SamplePacketMod {
    public static SamplePacketMod instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        PacketHandler.registerMessages("samplepacketmod");
    }

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
        // register server commands
        event.registerServerCommand(new Command());
    }
}