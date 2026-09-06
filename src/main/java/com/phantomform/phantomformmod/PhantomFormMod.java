package com.phantomform.phantomformmod;

import com.phantomform.phantomformmod.network.ToggleSoulEyePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(PhantomFormMod.MOD_ID)
@Mod.EventBusSubscriber(modid = PhantomFormMod.MOD_ID)
public class PhantomFormMod {
    public static final String MOD_ID = "phantom_form";

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final RegistryObject<Item> EYE_OF_SOUL = ITEMS.register("eye_of_soul", EyeOfSoulItem::new);

    public PhantomFormMod() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        CHANNEL.registerMessage(0, ToggleSoulEyePacket.class, ToggleSoulEyePacket::toBytes,
                ToggleSoulEyePacket::new, ToggleSoulEyePacket::handle);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Player player = event.player;

        // 检查主手或副手是否有激活的灵魂之眼
        boolean hasActive = false;
        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();
        if (mainHand.getItem() instanceof EyeOfSoulItem && mainHand.getOrCreateTag().getBoolean("Active")) {
            hasActive = true;
        } else if (offHand.getItem() instanceof EyeOfSoulItem && offHand.getOrCreateTag().getBoolean("Active")) {
            hasActive = true;
        }

        if (hasActive) {
            // 禁用重力（防止下落）
            //player.setNoGravity(true);

            // 仅对生存模式玩家强制开启飞行
            if (!player.isCreative() && !player.isSpectator()) {
                if (!player.getAbilities().mayfly) {
                    player.getAbilities().mayfly = true;
                    player.getAbilities().flying = true;
                    player.onUpdateAbilities();
                }
            }
            player.fallDistance = 0.0f;
        } else {
            // 恢复重力
            //player.setNoGravity(false);

            // 仅对生存模式玩家关闭飞行
            if (!player.isCreative() && !player.isSpectator()) {
                if (player.getAbilities().mayfly) {
                    player.getAbilities().mayfly = false;
                    player.getAbilities().flying = false;
                    player.onUpdateAbilities();
                }
            }
            //player.fallDistance = 0.0f;
        }
    }
    // 在 PhantomFormMod 类中添加
    public static final String NETWORK_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MOD_ID, "main"),
            () -> NETWORK_VERSION,
            version -> version.equals(NETWORK_VERSION),
            version -> version.equals(NETWORK_VERSION)
    );
}