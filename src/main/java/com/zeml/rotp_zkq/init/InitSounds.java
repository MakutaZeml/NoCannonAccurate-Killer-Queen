package com.zeml.rotp_zkq.init;

import java.util.function.Supplier;

import com.zeml.rotp_zkq.RotpKillerQueen;
import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.util.mc.OstSoundList;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, RotpKillerQueen.MOD_ID);

    public static final Supplier<SoundEvent> KQ_SUMMON = ModSounds.STAND_SUMMON_DEFAULT;
    
    public static final Supplier<SoundEvent> KQ_UNSUMMON = ModSounds.STAND_UNSUMMON_DEFAULT;
    
    public static final Supplier<SoundEvent> KQ_PUNCH_LIGHT = ModSounds.STAND_PUNCH_LIGHT;
    
    public static final Supplier<SoundEvent> KQ_PUNCH_HEAVY = ModSounds.STAND_PUNCH_HEAVY;
    
    public static final Supplier<SoundEvent> KQ_BARRAGE = ModSounds.STAND_PUNCH_LIGHT;


    
    public static final RegistryObject<SoundEvent> KQ_ORA = SOUNDS.register("killer_queen_shiba",
            () -> new SoundEvent(new ResourceLocation(RotpKillerQueen.MOD_ID, "killer_queen_shiba")));
    
    public static final RegistryObject<SoundEvent> KQ_ORA_LONG = SOUNDS.register("killer_queen_shiba_long",
            () -> new SoundEvent(new ResourceLocation(RotpKillerQueen.MOD_ID, "killer_queen_shiba_long")));
    
    public static final RegistryObject<SoundEvent> KQ_ORA_ORA_ORA = SOUNDS.register("killer_queen_shiba_ba_ba",
            () -> new SoundEvent(new ResourceLocation(RotpKillerQueen.MOD_ID, "killer_queen_shiba_ba_ba")));

    public static final RegistryObject<SoundEvent> KQ_BOMB= SOUNDS.register("killer_queen_boom",
            () -> new SoundEvent(new ResourceLocation(RotpKillerQueen.MOD_ID, "killer_queen_boom")));

    public static final RegistryObject<SoundEvent> USER_KQ = SOUNDS.register("user_kira",
            () -> new SoundEvent(new ResourceLocation(RotpKillerQueen.MOD_ID, "user_kira")));


    public static final RegistryObject<SoundEvent> KQ_SH_SUMMON = SOUNDS.register("kira_sheer_heart",
            ()-> new SoundEvent(new ResourceLocation(RotpKillerQueen.MOD_ID,"kira_sheer_heart")));

    public static final RegistryObject<SoundEvent> KQ_SH_KOICHIO = SOUNDS.register("sheer_heart",
            ()-> new SoundEvent(new ResourceLocation(RotpKillerQueen.MOD_ID,"sheer_heart")));

    public static final RegistryObject<SoundEvent> KQ_SH_STEP = SOUNDS.register("sheer_heart_step",
            ()-> new SoundEvent(new ResourceLocation(RotpKillerQueen.MOD_ID,"sheer_heart_step")));



    static final OstSoundList KQ_OST = new OstSoundList(new ResourceLocation(RotpKillerQueen.MOD_ID, "kq_ost"), SOUNDS);

}
