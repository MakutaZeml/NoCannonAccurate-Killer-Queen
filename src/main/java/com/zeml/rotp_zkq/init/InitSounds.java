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

    
    public static final Supplier<SoundEvent> KQ_UNSUMMON = SOUNDS.register("killer_queen_unsummon",
            ()->new SoundEvent(new ResourceLocation(RotpKillerQueen.MOD_ID,"killer_queen_unsummon"))
    );

    public static final Supplier<SoundEvent> KQ_SUMMON = SOUNDS.register("killer_queen_summon",
            ()->new SoundEvent(new ResourceLocation(RotpKillerQueen.MOD_ID,"killer_queen_summon"))
    );


    public static final Supplier<SoundEvent> KQ_PUNCH_LIGHT = SOUNDS.register("killer_queen_punch",
            ()->new SoundEvent(new ResourceLocation(RotpKillerQueen.MOD_ID,"killer_queen_punch")));

    public static final Supplier<SoundEvent> KQ_PUNCH_HEAVY = SOUNDS.register("killer_queen_punch_heavy",
            ()->new SoundEvent(new ResourceLocation(RotpKillerQueen.MOD_ID,"killer_queen_punch_heavy")
    ));
    
    public static final RegistryObject<SoundEvent> KQ_ORA = SOUNDS.register("killer_queen_shiba",
            () -> new SoundEvent(new ResourceLocation(RotpKillerQueen.MOD_ID, "killer_queen_shiba")));
    
    public static final RegistryObject<SoundEvent> KQ_ORA_LONG = SOUNDS.register("killer_queen_shiba_long",
            () -> new SoundEvent(new ResourceLocation(RotpKillerQueen.MOD_ID, "killer_queen_shiba_long")));
    
    public static final RegistryObject<SoundEvent> KQ_ORA_ORA_ORA = SOUNDS.register("killer_queen_shiba_ba_ba",
            () -> new SoundEvent(new ResourceLocation(RotpKillerQueen.MOD_ID, "killer_queen_shiba_ba_ba")));

    public static final RegistryObject<SoundEvent> KQ_BOMB= SOUNDS.register("killer_queen_boom",
            () -> new SoundEvent(new ResourceLocation(RotpKillerQueen.MOD_ID, "killer_queen_boom")));

    public static final RegistryObject<SoundEvent> KQ_UNBOMB= SOUNDS.register("killer_queen_unboom",
            () -> new SoundEvent(new ResourceLocation(RotpKillerQueen.MOD_ID, "killer_queen_unboom")));

    public static final RegistryObject<SoundEvent> USER_KQ = SOUNDS.register("user_kira",
            () -> new SoundEvent(new ResourceLocation(RotpKillerQueen.MOD_ID, "user_kira")));


    public static final RegistryObject<SoundEvent> KQ_SH_SUMMON = SOUNDS.register("kira_sheer_heart",
            ()-> new SoundEvent(new ResourceLocation(RotpKillerQueen.MOD_ID,"kira_sheer_heart")));

    public static final RegistryObject<SoundEvent> KQ_SH_KOICHIO = SOUNDS.register("sheer_heart",
            ()-> new SoundEvent(new ResourceLocation(RotpKillerQueen.MOD_ID,"sheer_heart")));

    public static final RegistryObject<SoundEvent> KQ_SH_STEP = SOUNDS.register("sheer_heart_step",
            ()-> new SoundEvent(new ResourceLocation(RotpKillerQueen.MOD_ID,"sheer_heart_step")));


    public static final RegistryObject<SoundEvent> KQ_BITES_ZA_DUST = SOUNDS.register("bites_za_dust",
            ()-> new SoundEvent(new ResourceLocation(RotpKillerQueen.MOD_ID,"bites_za_dust")));

    public static final RegistryObject<SoundEvent> USER_DUST = SOUNDS.register("user_bites_za_dust",
            ()-> new SoundEvent(new ResourceLocation(RotpKillerQueen.MOD_ID,"user_bites_za_dust")));

    public
    static final OstSoundList KQ_OST = new OstSoundList(new ResourceLocation(RotpKillerQueen.MOD_ID, "kq_ost"), SOUNDS);

}
