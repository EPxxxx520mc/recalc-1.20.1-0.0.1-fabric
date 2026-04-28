package com.rimeveil.recalc.Item.custom;

import com.rimeveil.recalc.data.PlayerFrameData;
import com.rimeveil.recalc.networking.ModNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class FictionalFrameItem extends Item implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public FictionalFrameItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            if (!PlayerFrameData.hasFrameAttached(user)) {
                PlayerFrameData.attachFrame(user);
                user.sendMessage(Text.translatable("message.recalc.frame_attached"), true);
                if (user instanceof ServerPlayerEntity serverPlayer) {
                    ModNetworking.syncToPlayer(serverPlayer, true);
                }
            } else {
                user.sendMessage(Text.translatable("message.recalc.frame_already_attached"), true);
            }
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // 这里可以添加动画控制器
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
