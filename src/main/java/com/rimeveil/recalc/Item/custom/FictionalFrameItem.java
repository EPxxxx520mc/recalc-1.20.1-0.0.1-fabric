package com.rimeveil.recalc.Item.custom;

import com.rimeveil.recalc.data.PlayerFrameData;
import com.rimeveil.recalc.networking.ModNetworking;
import com.rimeveil.recalc.client.renderer.FictionalFrameRenderer;
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

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * ================================================
 * 🎬 虚构框架 - GeckoLib 动画物品
 * ================================================
 * 
 * 【功能】
 * - 继承 Item 并实现 GeoItem 接口
 * - 支持 3D 动画模型渲染
 * - 保留原有的右键附着框架功能
 * 
 * 【引用位置】
 * - Moditem.java: 注册为物品
 * - FictionalFrameRenderer.java: 渲染器
 * - RecalcClient.java: 注册渲染器
 * 
 * 【资源文件位置】
 * - 模型: assets/recalc/geo/fictional_frame.json
 * - 动画: assets/recalc/animations/fictional_frame.animation.json
 * - 纹理: assets/recalc/textures/item/fictional_frame.png
 * ================================================
 */
public class FictionalFrameItem extends Item implements GeoItem {
    
    /**
     * 【动画缓存】
     * GeckoLib 要求每个动画物品都有一个缓存实例
     * 用于存储和管理动画状态
     */
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    
    /**
     * 【渲染提供者】
     * 用于提供物品的渲染器实例
     * 这是 GeoItem 接口要求实现的方法
     */
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
    
    /**
     * 【定义动画】
     * 这里定义了物品的默认动画
     * "idle" 是在 BlockBench 中定义的动画名称
     * 
     * 如果要添加更多动画，可以这样定义：
     * private static final RawAnimation USE_ANIM = RawAnimation.begin().thenPlay("use");
     * private static final RawAnimation OPEN_ANIM = RawAnimation.begin().thenPlay("open");
     */
    private static final RawAnimation IDLE_ANIM = RawAnimation.begin()
        .thenLoop("idle");  // 循环播放 idle 动画
    
    /**
     * 【构造函数】
     * @param settings 物品设置
     */
    public FictionalFrameItem(Settings settings) {
        super(settings);
    }
    
    /**
     * ================================================
     * 🎮 右键使用功能（保留原有逻辑）
     * ================================================
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
    
    /**
     * ================================================
     * 🎬 GeckoLib 动画控制器注册
     * ================================================
     * 
     * 【功能】
     * 在这里注册物品的动画控制器
     * 控制器负责管理动画的播放、切换等
     * 
     * 【如何添加新动画】
     * 1. 在 BlockBench 中创建新动画并导出
     * 2. 在这里添加新的 AnimationController
     * 3. 使用 state.getController().setAnimation(新动画) 切换
     * ================================================
     */
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, state -> {
            // 循环播放 idle 动画
            // 如果要根据条件切换动画，可以在这里添加判断：
            // if (某个条件) {
            //     state.getController().setAnimation(其他动画);
            // }
            
            state.getController().setAnimation(IDLE_ANIM);
            return PlayState.CONTINUE;  // 继续播放
        }));
    }
    
    /**
     * 【获取动画缓存】
     * GeckoLib 要求的接口方法
     */
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
    
    /**
     * 【获取渲染提供者】
     * GeoItem 接口要求实现的方法
     * 用于提供物品的渲染器
     */
    @Override
    public Supplier<Object> getRenderProvider() {
        return this.renderProvider;
    }
    
    /**
     * 【创建渲染器】
     * 用于在客户端创建渲染器实例
     */
    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new FictionalFrameRenderer());
    }
}
