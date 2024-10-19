package com.example.hideactionbar;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.lwjgl.input.Keyboard;

@Mod(modid = HideActionBar.MODID, name = HideActionBar.NAME, version = HideActionBar.VERSION)
public class HideActionBar {
    public static final String MODID = "hideactionbar";
    public static final String NAME = "Hide Action Bar";
    public static final String VERSION = "1.0";

    private static boolean hideActionBar = false;
    private static KeyBinding toggleKey;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        toggleKey = new KeyBinding("Toggle Action Bar", Keyboard.KEY_H, "Hide Action Bar");
        ClientRegistry.registerKeyBinding(toggleKey);
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Pre event) {
        if (event.type == RenderGameOverlayEvent.ElementType.TEXT && hideActionBar) {
            GuiIngame guiIngame = Minecraft.getMinecraft().ingameGUI;
            try {
                ReflectionHelper.setPrivateValue(GuiIngame.class, guiIngame, "", "recordPlaying", "field_73838_g");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (toggleKey.isPressed()) {
            hideActionBar = !hideActionBar;
            Minecraft.getMinecraft().thePlayer
                    .addChatMessage(new net.minecraft.util.ChatComponentText("Action bar hidden: " + hideActionBar));
        }
    }
}