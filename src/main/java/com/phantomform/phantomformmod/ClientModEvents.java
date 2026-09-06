package com.phantomform.phantomformmod;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = PhantomFormMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(
                    PhantomFormMod.EYE_OF_SOUL.get(),
                    new ResourceLocation(PhantomFormMod.MOD_ID, "active"),
                    (stack, level, entity, seed) -> {
                        // 检查物品是否激活
                        boolean active = stack.getOrCreateTag().getBoolean("Active");
                        if (active && entity instanceof Player player) {
                            // 检查物品是否在主手或副手（比较物品类型和NBT，忽略数量）
                            ItemStack main = player.getMainHandItem();
                            ItemStack off = player.getOffhandItem();
                            if (!ItemStack.isSameItemSameTags(stack, main) && !ItemStack.isSameItemSameTags(stack, off)) {
                                active = false;  // 不在手中，强制显示未激活模型
                            }
                        }
                        return active ? 1.0F : 0.0F;
                    }
            );
        });
    }
}