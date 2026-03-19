package com.rimeveil.recalc.sound;

import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;
import com.rimeveil.recalc.Recalc;
public class ModSoundEvents {
    public static final SoundEvent TEXT = register("text_sound");
    public static final SoundEvent BLOCK_BREAK = register("block_break");
    public static final SoundEvent BLOCK_PLACE = register("block_place");
    public static final SoundEvent BLOCK_STEP = register("block_step");
    public static final SoundEvent BLOCK_HIT = register("block_hit");
    public static final SoundEvent BLOCK_FALL = register("block_fall");
    public static final SoundEvent BLOCK_LOOT = register("block_loot");

    public static final BlockSoundGroup BLOCKS = new BlockSoundGroup(1.0F,1.0F,
        BLOCK_BREAK,
        BLOCK_STEP,
        BLOCK_PLACE,
        BLOCK_HIT,
        BLOCK_FALL
    );
    private static SoundEvent register(String name) {
        Identifier id = new Identifier(Recalc.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
    };  
}
