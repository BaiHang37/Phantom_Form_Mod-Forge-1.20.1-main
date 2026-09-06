package com.phantomform.phantomformmod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class EyeOfSoulItem extends Item {
    private static final String ACTIVE_TAG = "Active";

    public EyeOfSoulItem() {
        super(new Item.Properties().stacksTo(1));
    }

    // 右键不再做任何操作，返回 PASS（无反应）
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        // 什么都不做，直接返回 PASS
        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        boolean active = stack.getOrCreateTag().getBoolean(ACTIVE_TAG);

        // 显示当前状态（激活/未激活）
        tooltip.add(Component.translatable("item.phantom_form.eye_of_soul.desc",
                active ? Component.translatable("item.phantom_form.eye_of_soul.active")
                        : Component.translatable("item.phantom_form.eye_of_soul.inactive")));
    }
}