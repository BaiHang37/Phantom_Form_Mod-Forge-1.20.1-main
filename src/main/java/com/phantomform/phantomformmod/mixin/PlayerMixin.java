package com.phantomform.phantomformmod.mixin;

import com.phantomform.phantomformmod.EyeOfSoulItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Redirect(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isSpectator()Z")
    )
    private boolean redirectIsSpectator(Player player) {
        // 检查主手或副手是否有激活的灵魂之眼
        boolean hasActive = false;
        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();
        if (mainHand.getItem() instanceof EyeOfSoulItem && mainHand.getOrCreateTag().getBoolean("Active")) {
            hasActive = true;
        } else if (offHand.getItem() instanceof EyeOfSoulItem && offHand.getOrCreateTag().getBoolean("Active")) {
            hasActive = true;
        }
        // 如果手持激活物品，让 noPhysics = true，否则保持原状（旁观者模式）
        return hasActive || player.isSpectator();
    }
}