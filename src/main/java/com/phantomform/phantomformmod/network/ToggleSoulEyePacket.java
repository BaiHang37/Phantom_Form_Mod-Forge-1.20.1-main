package com.phantomform.phantomformmod.network;

import com.phantomform.phantomformmod.EyeOfSoulItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ToggleSoulEyePacket {
    public ToggleSoulEyePacket() {
    }

    public ToggleSoulEyePacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public static void handle(ToggleSoulEyePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                // 查找主手或副手中的 EyeOfSoulItem
                ItemStack mainHand = player.getMainHandItem();
                ItemStack offHand = player.getOffhandItem();
                ItemStack targetStack = null;
                if (mainHand.getItem() instanceof EyeOfSoulItem) {
                    targetStack = mainHand;
                } else if (offHand.getItem() instanceof EyeOfSoulItem) {
                    targetStack = offHand;
                }
                if (targetStack != null) {
                    // 仅切换 Active 标签，不干预能力
                    boolean newActive = !targetStack.getOrCreateTag().getBoolean("Active");
                    targetStack.getOrCreateTag().putBoolean("Active", newActive);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}