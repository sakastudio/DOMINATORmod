package sakastudio.samplepacketmod;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class KeyBindings {

    public static KeyBinding tutorialKey;

    @SideOnly(Side.CLIENT)
    public static void init() {
        tutorialKey = new KeyBinding("key.tutorial", Keyboard.KEY_H, "key.categories.modtut");
        try {
            ClientRegistry.registerKeyBinding(tutorialKey);
        } catch (Exception e) {
            // 例外発生時の処理
        }
    }
}
