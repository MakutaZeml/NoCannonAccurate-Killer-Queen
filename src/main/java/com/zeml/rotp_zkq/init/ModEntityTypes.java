package com.zeml.rotp_zkq.init;


import com.zeml.rotp_zkq.RotpKillerQueen;
import com.zeml.rotp_zkq.entity.damaging.projectile.BubbleBombEntity;
import com.zeml.rotp_zkq.entity.stand.SheerHeart;
import com.zeml.rotp_zkq.entity.damaging.projectile.SnowBombEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = RotpKillerQueen.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITIES =DeferredRegister.create(ForgeRegistries.ENTITIES, RotpKillerQueen.MOD_ID);

    public static final RegistryObject<EntityType<SnowBombEntity>>  SNOW_BOMB = ENTITIES.register("snow_bomb",
            ()->EntityType.Builder.<SnowBombEntity>of(SnowBombEntity:: new, EntityClassification.MISC).sized(0.15F,0.15F)
                    .setUpdateInterval(2).build(new ResourceLocation(RotpKillerQueen.MOD_ID,"snow_bubble").toString()));

    public static final RegistryObject<EntityType<BubbleBombEntity>>  BUBBLE_BOMB = ENTITIES.register("bubble_bomb",
            ()->EntityType.Builder.<BubbleBombEntity>of(BubbleBombEntity:: new, EntityClassification.MISC).sized(0.15F,0.15F)
                    .setUpdateInterval(2).build(new ResourceLocation(RotpKillerQueen.MOD_ID,"bubble_bomb").toString()));


    public static final RegistryObject<EntityType<SheerHeart>> SHEAR_HEART = ENTITIES.register("sheer_heart",
            ()->EntityType.Builder.<SheerHeart>of(SheerHeart::new, EntityClassification.MISC).sized(0.85F,0.7F)
                    .setUpdateInterval(2).build((new ResourceLocation(RotpKillerQueen.MOD_ID,"sheer_heart").toString())));


}
