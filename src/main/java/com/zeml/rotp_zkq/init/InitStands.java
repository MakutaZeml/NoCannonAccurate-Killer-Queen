package com.zeml.rotp_zkq.init;

import com.github.standobyte.jojo.entity.stand.StandPose;
import com.zeml.rotp_zkq.RotpKillerQueen;
import com.zeml.rotp_zkq.action.stand.*;
import com.zeml.rotp_zkq.action.stand.BitesZaDust.*;
import com.zeml.rotp_zkq.action.stand.punch.*;
import com.zeml.rotp_zkq.entity.stand.stands.BZDEntity;
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
            () -> new KQNormalPunch(new StandEntityLightAttack.Builder()
                    .punchSound(InitSounds.KQ_PUNCH_LIGHT)
                    .standSound(Phase.WINDUP, InitSounds.KQ_ORA)));
    
    public static final RegistryObject<StandEntityAction> KQ_BARRAGE = ACTIONS.register("kq_barrage", 
            () -> new KQBarrage(new StandEntityMeleeBarrage.Builder()
                    .barrageHitSound(InitSounds.KQ_PUNCH_LIGHT)
                    .standSound(InitSounds.KQ_ORA_ORA_ORA)));
    
    public static final RegistryObject<StandEntityHeavyAttack> KQ_COMBO_PUNCH = ACTIONS.register("kq_combo_punch", 
            () -> new KQFinisher(new StandEntityHeavyAttack.Builder()
                    .punchSound(InitSounds.KQ_PUNCH_HEAVY)
                    .standSound(Phase.WINDUP, InitSounds.KQ_ORA_LONG)
                    .partsRequired(StandPart.ARMS)));
    
    public static final RegistryObject<StandEntityHeavyAttack> KQ_HEAVY_PUNCH = ACTIONS.register("kq_heavy_punch", 
            () -> new KQHeavyPunch(new StandEntityHeavyAttack.Builder()
                    .punchSound(InitSounds.KQ_PUNCH_HEAVY)
                    .standSound(Phase.WINDUP, InitSounds.KQ_ORA_LONG)
                    .partsRequired(StandPart.ARMS)
                    .setFinisherVariation(KQ_COMBO_PUNCH)
                    .shiftVariationOf(KQ_PUNCH).shiftVariationOf(KQ_BARRAGE)));
    
    public static final RegistryObject<StandEntityAction> KQ_BLOCK = ACTIONS.register("kq_block", 
            () -> new KQBlock());

    public static final RegistryObject<StandEntityAction> KQ_ITEM_BOMB = ACTIONS.register("kq_itembomb",
            ()->new ItemFBomb(new StandEntityLightAttack.Builder().punchSound(InitSounds.VOID).swingSound(InitSounds.VOID)
                    .standPose(EntityExplode.DETONATE).standOffsetFromUser(0,0,0)
                    .needsFreeMainHand().resolveLevelToUnlock(1)
                    .partsRequired(StandPart.ARMS).cooldown(5)));

    public static final RegistryObject<StandEntityAction> KQ_ENTITY_BOMB = ACTIONS.register("kq_entitybomb",
            ()->new PunchBomb(new StandEntityLightAttack.Builder()
                    .standRecoveryTicks(40).standOffsetFront()
                    .resolveLevelToUnlock(2).staminaCost(125).cooldown(60).partsRequired(StandPart.ARMS)
                    .shout(InitSounds.USER_KQ).standPose(PunchBomb.BOMB_PUNCH)
            ));

    public static final RegistryObject<StandEntityAction> KQ_ITEM_BOMB_EX = ACTIONS.register("kq_item_explo",
            ()->new ItemFBombExplode(new StandEntityLightAttack.Builder().standWindupDuration(20).standRecoveryTicks(10)
                    .shiftVariationOf(KQ_ITEM_BOMB).staminaCost(20).standSound(InitSounds.KQ_BOMB).standPose(StandPose.RANGED_ATTACK)
            ));


    public static final RegistryObject<StandEntityAction> KQ_ENTITY_EX = ACTIONS.register("kq_entity_explo",
            ()->new EntityExplode(new StandEntityLightAttack.Builder().holdToFire(20,false)
                    .staminaCost(250F).standPose(EntityExplode.DETONATE).resolveLevelToUnlock(1)
                    .standSound(Phase.RECOVERY,InitSounds.KQ_BOMB))
                    );

    public static final RegistryObject<StandEntityAction> KQ_ENTITY_QUIT = ACTIONS.register("kq_entity_quit",
            ()->new EntityQuitBomb(new StandEntityAction.Builder().standWindupDuration(5)
                    .staminaCost(50F).standPose(StandPose.RANGED_ATTACK).shiftVariationOf(KQ_ENTITY_EX).shiftVariationOf(KQ_ENTITY_BOMB)
                    .standSound(InitSounds.KQ_UNBOMB)));

    public static final RegistryObject<StandEntityAction> KQ_BLOCK_BOMB = ACTIONS.register("kq_block_bomb",
            ()->new BlockBombAction(new StandEntityAction.Builder().standWindupDuration(5)
                    .cooldown(60).standSound(InitSounds.USER_KQ)
                    .staminaCost(75F).standPose(PunchBomb.BOMB_PUNCH)
            ));


    public static final RegistryObject<StandEntityAction> KQ_BLOCK_EXPLODE = ACTIONS.register("kq_block_explode",
            ()->new ExplodeBlockAction(new StandEntityLightAttack.Builder().standWindupDuration(10)
                    .cooldown(60)
                    .staminaCost(125F).standPose(StandPose.RANGED_ATTACK).standSound(InitSounds.KQ_BOMB)
                   ));

    public static final RegistryObject<StandEntityAction> KQ_BLOCK_QUIT = ACTIONS.register("kq_block_quit",
            ()->new BlockQuitBomb(new StandEntityLightAttack.Builder().standWindupDuration(5).shiftVariationOf(KQ_BLOCK_BOMB)
                    .shiftVariationOf(KQ_BLOCK_EXPLODE)
                    .staminaCost(5F).standPose(StandPose.RANGED_ATTACK)
                    .standSound(InitSounds.KQ_UNBOMB)));

    public static final RegistryObject<StandEntityAction> KQ_SECOND_BOMB = ACTIONS.register("sheer_heart",
            ()->new SheerHeartAttack(new StandEntityAction.Builder().staminaCost(200)
                    .resolveLevelToUnlock(3).cooldown(200).shout(InitSounds.KQ_SH_SUMMON)
            ));

    public static final RegistryObject<StandEntityAction> KQ_SB_BACK = ACTIONS.register("shear_heart_back",
            ()->new SHABack(new StandEntityAction.Builder().shiftVariationOf(KQ_SECOND_BOMB)
                    .standSound(InitSounds.KQ_UNSUMMON)
            ));

    public static final RegistryObject<StandAction> KQ_SNOW_BOMB = ACTIONS.register("kq_snow_bomb",
            ()->new SnowBomb(new StandAction.Builder().staminaCost(100).resolveLevelToUnlock(4).cooldown(100).partsRequired(StandPart.ARMS)
                    ));

    public static final RegistryObject<StandAction> KQ_BUBBLE_BOMB = ACTIONS.register("kq_bubble_bomb",
            ()->new HBubbleBomb(new StandAction.Builder().staminaCost(100).cooldown(100).partsRequired(StandPart.ARMS)
            ));

    public static final RegistryObject<StandEntityAction> KQ_SNOW_EXPLODE = ACTIONS.register("snow_explode",
            ()-> new ExplodeSnow(new StandEntityLightAttack.Builder().staminaCost(100).shiftVariationOf(KQ_SNOW_BOMB)
                    .standSound(InitSounds.KQ_BOMB).standPose(StandPose.RANGED_ATTACK).shiftVariationOf(KQ_BUBBLE_BOMB)
                    .standPerformDuration(20))) ;


    //-------------------------------------Bites Za Dust Stuff-------------------------------------------------

    public static final RegistryObject<StandAction> KQ_TIME_MARKER =ACTIONS.register("time_mark",
            ()-> new TimeMarker(new StandAction.Builder().noResolveUnlock().staminaCost(50))
            );

    public static final RegistryObject<StandEntityAction> KQ_RESET = ACTIONS.register("kq_reset",()->
            new ResetDay(new StandEntityAction.Builder().staminaCost(500)
                    .holdToFire(60,false).standPose(EntityExplode.DETONATE).standRecoveryTicks(1)
                    .cooldown(2000)));

    public static final RegistryObject<StandAction> KQ_REMOVE_MARK =ACTIONS.register("remov_mark",
            ()-> new RemoveTimeMarker(new StandAction.Builder().shiftVariationOf(KQ_RESET))
    );

    public static final RegistryObject<StandAction> PUT_VICTIM = ACTIONS.register("put_dust",
            ()-> new SelectHayato(new StandAction.Builder()));

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
                                    KQ_BLOCK_BOMB.get(),
                                    KQ_SECOND_BOMB.get(),
                            },


                            StandStats.class, new StandStats.Builder()
                            .tier(6)
                            .power(15.0)
                            .speed(14.0)
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


    // ======================================== Bites Za Dust ========================================

    public static final RegistryObject<StandAction> QUIT_VICTIM = ACTIONS.register("quit_dust",
            ()-> new RemoveHayato(new StandAction.Builder()));


}
