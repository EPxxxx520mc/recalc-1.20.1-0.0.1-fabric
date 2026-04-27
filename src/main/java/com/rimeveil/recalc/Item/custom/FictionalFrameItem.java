package com.rimeveil.recalc.Item.custom;

import com.rimeveil.recalc.data.PlayerFrameData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
                user.sendMessage(Text.literal("虚构框架已附着在你身上！"), true);
            } else {
                user.sendMessage(Text.literal("你已经附着了虚构框架！"), true);
            }
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
