package com.rimeveil.recalc.client.model;

import com.rimeveil.recalc.Recalc;
import com.rimeveil.recalc.Item.custom.FictionalFrameItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class FictionalFrameModel extends GeoModel<FictionalFrameItem> {
    @Override
    public Identifier getModelResource(FictionalFrameItem object) {
        return new Identifier(Recalc.MOD_ID, "geo/fictional_frame.geo.json");
    }

    @Override
    public Identifier getTextureResource(FictionalFrameItem object) {
        // 使用你现有的材质，这里假设是 texture.png
        return new Identifier(Recalc.MOD_ID, "textures/item/texture.png");
    }

    @Override
    public Identifier getAnimationResource(FictionalFrameItem object) {
        return new Identifier(Recalc.MOD_ID, "animations/fictional_frame.animation.json");
    }
}
