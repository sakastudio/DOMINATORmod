package com.sakastudio.dominatormod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class HUD extends GuiScreen {

    public static final HUD Instance = new HUD();
    public HUD(){}
    private static final ResourceLocation HUD_TEXTURE = new ResourceLocation(DOMINATORmod.MOD_ID, "textures/gui/hud.png");
    public boolean draw = false;


    public  void Update(){
        if(draw){
            ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
            Minecraft.getMinecraft().getTextureManager().bindTexture(HUD_TEXTURE);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, resolution.getScaledWidth(),resolution.getScaledHeight(), resolution.getScaledWidth(), resolution.getScaledHeight());
        }
    }
}
