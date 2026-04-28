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

public class FictionalFrameItem extends Item {
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
}
