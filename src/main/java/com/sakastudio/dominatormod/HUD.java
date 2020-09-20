package com.sakastudio.dominatormod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class HUD extends GuiScreen {

    public static final HUD Instance = new HUD();

    private HUD() {
    }

    private static final ResourceLocation HUD_TEXTURE = new ResourceLocation(DOMINATORmod.MOD_ID, "textures/gui/hud.png");

    public void Update() {
        if (ItemDOMINATOR.focusedPlayer != null) {
            Minecraft mc = Minecraft.getMinecraft();

            ScaledResolution resolution = new ScaledResolution(mc);
            mc.getTextureManager().bindTexture(HUD_TEXTURE);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, resolution.getScaledWidth(), resolution.getScaledHeight(), resolution.getScaledWidth(), resolution.getScaledHeight());
            int crime = ItemDOMINATOR.crimeCoefficient;
            String str = String.format("犯罪係数: %s", crime < 0 ? "0 [免罪体質]" : crime);
            mc.fontRenderer.drawString(str,
                    resolution.getScaledWidth() / 2 - mc.fontRenderer.getStringWidth(str) / 2,
                    resolution.getScaledHeight() / 2 + 10,
                    crime < 100 ? 0xffffff : crime < 300 ? 0xffff00 : 0xff0000);

            if (mc.player.getHeldItemMainhand().getItem() != DOMINATORmod.DOMINATOR)
                ItemDOMINATOR.focusedPlayer = null;
        }
    }
}
