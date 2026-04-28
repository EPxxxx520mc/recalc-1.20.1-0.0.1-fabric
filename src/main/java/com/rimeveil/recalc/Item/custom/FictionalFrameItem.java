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
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

/**
 * ================================================
 * 🎬 虚构框架物品 - GeckoLib 动画物品
 * ================================================
 *
 * 【功能】
 * - 继承 GeoItem，支持 3D 动画模型
 * - 右键使用可附着框架到玩家身上
 *
 * 【引用位置】
 * - Moditem.java:69 注册物品
 * - FictionalFrameRenderer.java 渲染器
 * - RecalcClient.java 注册渲染器
 * ================================================
 */
public class FictionalFrameItem extends Item implements GeoItem {

    // ==========================================
    // 🎮 GeckoLib 动画系统
    // ==========================================

    /**
     * 【动画缓存】
     * GeckoLib 要求每个动画物品都有一个缓存实例
     * 用于存储和管理动画状态
     */
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    /**
     * 【定义动画】
     * 这里定义要在 BlockBench 中制作的动画名称
     * 例如："idle" 表示空闲动画
     *
     * 注意：需要在 BlockBench 中导出同名动画！
     */
    private static final RawAnimation IDLE_ANIM = RawAnimation.begin()
            .thenLoop("idle");

    // ==========================================
    // 🏗️ 构造函数
    // ==========================================

    public FictionalFrameItem(Settings settings) {
        super(settings);
    }

    // ==========================================
    // 🎬 GeckoLib 动画接口实现
    // ==========================================

    /**
     * 【注册动画控制器】
     * 在这里定义物品的动画行为
     *
     * 【调用位置】
     * - GeckoLib 内部自动调用
     *
     * 【功能】
     * - 添加动画控制器，控制播放什么动画
     * - 可以添加多个控制器，实现复杂动画
     */
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, state -> {
            // 循环播放 idle 动画
            state.getController().setAnimation(IDLE_ANIM);
            return PlayState.CONTINUE;
        }));
    }

    /**
     * 【获取动画缓存】
     * GeckoLib 内部使用
     */
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    // ==========================================
    // 🎮 物品使用逻辑（原有功能保留）
    // ==========================================

    /**
     * 【右键使用物品】
     * 当玩家手持虚构框架右键时，将框架附着到玩家身上
     *
     * 【调用位置】
     * - Minecraft 内部调用（玩家右键物品时）
     *
     * 【功能】
     * 1. 检查玩家是否已有框架
     * 2. 如果没有，附着框架并播放动画
     * 3. 如果已有，提示已附着
     */
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
}
