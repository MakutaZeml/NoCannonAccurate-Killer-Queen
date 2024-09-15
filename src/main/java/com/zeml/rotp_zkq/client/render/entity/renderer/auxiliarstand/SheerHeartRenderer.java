package com.zeml.rotp_zkq.client.render.entity.renderer.auxiliarstand;

import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.client.standskin.StandSkinsManager;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;
import com.zeml.rotp_zkq.RotpKillerQueen;

import com.zeml.rotp_zkq.client.render.entity.model.auxiliarstand.SheerHeartModel;
import com.zeml.rotp_zkq.entity.stand.SheerHeart;
import com.zeml.rotp_zkq.init.InitStands;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import java.util.concurrent.atomic.AtomicReference;

public class SheerHeartRenderer extends MobRenderer<SheerHeart, SheerHeartModel> {

   protected static final ResourceLocation TEXTURE =  new ResourceLocation(RotpKillerQueen.MOD_ID,"textures/entity/stand/sheer_heart.png");
   protected static final ResourceLocation VOID = new ResourceLocation(RotpKillerQueen.MOD_ID,"/textures/entity/stand/void.png");
   protected static ResourceLocation SHEER = TEXTURE;

   public SheerHeartRenderer(EntityRendererManager rendererManager){
       super(rendererManager,new SheerHeartModel(),0F);
   }

    @Override
    public ResourceLocation getTextureLocation(SheerHeart p_110775_1_) {
       if(p_110775_1_.getOwner() != null){
           IStandPower.getStandPowerOptional(p_110775_1_.getOwner()).ifPresent(power -> {
               StandType<?> KQ = InitStands.KQ_STAND.getStandType();
               if(power.getType() == KQ){
                   SHEER =StandSkinsManager.getInstance() != null? (StandSkinsManager.getInstance().getRemappedResPath(manager -> manager
                           .getStandSkin(power.getStandInstance().get()), TEXTURE)): TEXTURE ;
               }else {
                   SHEER = TEXTURE;
               }
           });
       }
        return ClientUtil.canSeeStands()?SHEER:VOID;
    }


}
