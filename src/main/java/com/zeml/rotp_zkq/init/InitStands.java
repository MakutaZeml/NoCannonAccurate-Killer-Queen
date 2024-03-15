package com.zeml.rotp_zkq.init;

import com.github.standobyte.jojo.entity.stand.StandPose;
import com.zeml.rotp_zkq.RotpKillerQueen;
import com.zeml.rotp_zkq.action.stand.*;
import com.zeml.rotp_zkq.client.render.entity.model.stand.KillerQueenModel;
import com.zeml.rotp_zkq.entity.stand.stands.KQStandEntity;
import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.action.stand.StandEntityAction.Phase;
import com.github.standobyte.jojo.action.stand.StandEntityBlock;
import com.github.standobyte.jojo.action.stand.StandEntityHeavyAttack;
import com.github.standobyte.jojo.action.stand.StandEntityLightAttack;
import com.github.standobyte.jojo.action.stand.StandEntityMeleeBarrage;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject;
import com.github.standobyte.jojo.init.power.stand.ModStandsInit;
import com.github.standobyte.jojo.power.impl.stand.StandInstance.StandPart;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;
import com.github.standobyte.jojo.power.impl.stand.type.EntityStandType;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class InitStands {
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<Action<?>> ACTIONS = DeferredRegister.create(
            (Class<Action<?>>) ((Class<?>) Action.class), RotpKillerQueen.MOD_ID);
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<StandType<?>> STANDS = DeferredRegister.create(
            (Class<StandType<?>>) ((Class<?>) StandType.class), RotpKillerQueen.MOD_ID);
    
 // ======================================== Killer Queen ========================================

    public static final RegistryObject<StandEntityAction> KQ_PUNCH = ACTIONS.register("kq_punch", 
            () -> new StandEntityLightAttack(new StandEntityLightAttack.Builder()
                    .punchSound(InitSounds.KQ_PUNCH_LIGHT)
                    .standSound(Phase.WINDUP, InitSounds.KQ_ORA)));
    
    public static final RegistryObject<StandEntityAction> KQ_BARRAGE = ACTIONS.register("kq_barrage", 
            () -> new StandEntityMeleeBarrage(new StandEntityMeleeBarrage.Builder()
                    .barrageHitSound(InitSounds.KQ_PUNCH_LIGHT)
                    .standSound(InitSounds.KQ_ORA_ORA_ORA)));
    
    public static final RegistryObject<StandEntityHeavyAttack> KQ_COMBO_PUNCH = ACTIONS.register("kq_combo_punch", 
            () -> new KQFinisher(new StandEntityHeavyAttack.Builder()
                    .punchSound(InitSounds.KQ_PUNCH_HEAVY)
                    .standSound(Phase.WINDUP, InitSounds.KQ_ORA_LONG)
                    .partsRequired(StandPart.ARMS)));
    
    public static final RegistryObject<StandEntityHeavyAttack> KQ_HEAVY_PUNCH = ACTIONS.register("kq_heavy_punch", 
            () -> new StandEntityHeavyAttack(new StandEntityHeavyAttack.Builder()
                    .punchSound(InitSounds.KQ_PUNCH_HEAVY)
                    .standSound(Phase.WINDUP, InitSounds.KQ_ORA_LONG)
                    .partsRequired(StandPart.ARMS)
                    .setFinisherVariation(KQ_COMBO_PUNCH)
                    .shiftVariationOf(KQ_PUNCH).shiftVariationOf(KQ_BARRAGE)));
    
    public static final RegistryObject<StandEntityAction> KQ_BLOCK = ACTIONS.register("kq_block", 
            () -> new StandEntityBlock());

    public static final RegistryObject<StandEntityAction> KQ_ITEM_BOMB = ACTIONS.register("kq_itembomb",
            ()->new ItemFBomb(new StandEntityAction.Builder()
                    .needsFreeMainHand().resolveLevelToUnlock(1)
                    .partsRequired(StandPart.ARMS).cooldown(5)));

    public static final RegistryObject<StandEntityAction> KQ_ENTITY_BOMB = ACTIONS.register("kq_entitybomb",
            ()->new EntityBomb(new StandEntityAction.Builder().resolveLevelToUnlock(2).standWindupDuration(10)
                    .standPose(StandPose.SUMMON).standPerformDuration(10).standPose(StandPose.RANGED_ATTACK)
            ));

    public static final RegistryObject<StandEntityAction> KQ_ITEM_BOMB_EX = ACTIONS.register("kq_item_explo",
            ()->new ItemFBombExplode(new StandEntityAction.Builder().standPerformDuration(5).standPerformDuration(20)
                    .shiftVariationOf(KQ_ITEM_BOMB).staminaCost(20).standSound(InitSounds.KQ_BOMB).standPose(StandPose.RANGED_ATTACK)
            ));


    public static final RegistryObject<StandEntityAction> KQ_ENTITY_EX = ACTIONS.register("kq_entity_explo",
            ()->new EntityExplode(new StandEntityAction.Builder().standWindupDuration(5)
                    .staminaCost(250F).standPose(StandPose.RANGED_ATTACK).resolveLevelToUnlock(1)
                    .standSound(InitSounds.KQ_BOMB)));

    public static final RegistryObject<StandEntityAction> KQ_ENTITY_QUIT = ACTIONS.register("kq_entity_quit",
            ()->new EntityQuitBomb(new StandEntityAction.Builder().standWindupDuration(5)
                    .staminaCost(250F).standPose(StandPose.RANGED_ATTACK).shiftVariationOf(KQ_ENTITY_EX).shiftVariationOf(KQ_ENTITY_BOMB)
                    .standSound(InitSounds.KQ_UNSUMMON)));

    public static final RegistryObject<StandEntityAction> KQ_SECOND_BOMB = ACTIONS.register("sheer_heart",
            ()->new SheerHeartAttack(new StandEntityAction.Builder().staminaCost(200)
                    .resolveLevelToUnlock(3).cooldown(200)
            ));

    public static final RegistryObject<StandEntityAction> KQ_SB_BACK = ACTIONS.register("shear_heart_back",
            ()->new SHABack(new StandEntityAction.Builder().shiftVariationOf(KQ_SECOND_BOMB)));

    public static final RegistryObject<StandEntityAction> KQ_SNOW_BOMB = ACTIONS.register("kq_snow_bomb",
            ()->new SnowBomb(new StandEntityAction.Builder().staminaCost(100).resolveLevelToUnlock(4).cooldown(25).partsRequired(StandPart.ARMS)
                    ));

    public static final RegistryObject<StandEntityAction> KQ_BUBBLE_BOMB = ACTIONS.register("kq_bubble_bomb",
            ()->new HBubbleBomb(new StandEntityAction.Builder().staminaCost(100).cooldown(25).partsRequired(StandPart.ARMS)
            ));

    public static final RegistryObject<StandEntityAction> KQ_SNOW_EXPLODE = ACTIONS.register("snow_explode",
            ()-> new ExplodeSnow(new StandEntityAction.Builder().staminaCost(100).shiftVariationOf(KQ_SNOW_BOMB)
                    .standSound(InitSounds.KQ_BOMB).standPose(StandPose.RANGED_ATTACK).shiftVariationOf(KQ_BUBBLE_BOMB)
                    .standPerformDuration(20))) ;

    public static final EntityStandRegistryObject<EntityStandType<StandStats>, StandEntityType<KQStandEntity>> KQ_STAND =
            new EntityStandRegistryObject<>("killer_queen",
                    STANDS, 
                    () -> new EntityStandType<StandStats>(
                            0xda7baf, ModStandsInit.PART_4_NAME,

                            new StandAction[] {
                                    KQ_PUNCH.get(), 
                                    KQ_BARRAGE.get(),
                                    KQ_SNOW_BOMB.get(),
                            },
                            new StandAction[] {
                                    KQ_BLOCK.get(),
                                    KQ_ENTITY_BOMB.get(),
                                    KQ_ITEM_BOMB.get(),
                                    KQ_SECOND_BOMB.get(),

                            },


                            StandStats.class, new StandStats.Builder()
                            .tier(6)
                            .power(14.0)
                            .speed(12.0)
                            .range(3.0, 8.0)
                            .durability(12.0)
                            .precision(12.0)
                            .build("Killer Queen"),

                            new StandType.StandTypeOptionals()
                            .addSummonShout(InitSounds.USER_KQ)
                            .addOst(InitSounds.KQ_OST)), 

                    InitEntities.ENTITIES, 
                    () -> new StandEntityType<KQStandEntity>(KQStandEntity::new, 0.65F, 1.95F)
                    .summonSound(InitSounds.KQ_SUMMON)
                    .unsummonSound(InitSounds.KQ_UNSUMMON))
            .withDefaultStandAttributes();
}
