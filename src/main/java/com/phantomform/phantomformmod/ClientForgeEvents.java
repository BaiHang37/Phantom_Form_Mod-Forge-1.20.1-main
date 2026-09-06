package com.phantomform.phantomformmod;

import com.phantomform.phantomformmod.network.ToggleSoulEyePacket;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PhantomFormMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientForgeEvents {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (KeyBindings.TOGGLE_KEY.get().consumeClick()) {
            PhantomFormMod.CHANNEL.sendToServer(new ToggleSoulEyePacket());
        }
    }
}