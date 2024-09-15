package com.zeml.rotp_zkq.capability.entity;

import com.zeml.rotp_zkq.network.AddonPackets;
import com.zeml.rotp_zkq.network.server.SetBoolTimeMarkerPacket;
import com.zeml.rotp_zkq.network.server.SetCapabilityBomb;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.UUID;

public class LivingData implements INBTSerializable<CompoundNBT>  {
    private final LivingEntity entity;
    private UUID bomb = null;
    private boolean hasTimeMark = false;
    private long timeMark = -1;
    private UUID hayatoUUID;
    private UUID yoshikageUUID;


    public LivingData(LivingEntity entity) {
        this.entity = entity;
        this.bomb = entity.getUUID();
        this.yoshikageUUID = entity.getUUID();
        this.hayatoUUID = entity.getUUID();
    }

    public void setBomb(UUID bombUUID){
        this.bomb = bombUUID;
        if(entity instanceof ServerPlayerEntity){
            AddonPackets.sendToClient(new SetCapabilityBomb(entity.getId(),bombUUID), (ServerPlayerEntity) entity);
        }
    }

    public void setHasTimeMark(boolean timeMark){
        this.hasTimeMark = timeMark;
        if(entity instanceof  ServerPlayerEntity){
            AddonPackets.sendToClient(new SetBoolTimeMarkerPacket(entity.getId(), timeMark), (ServerPlayerEntity) entity);
        }
    }

    public void setTimeMark(long timeMark){
        this.timeMark = timeMark;
    }


    public void setHayatoUUID(UUID hayatoUUID){
        this.hayatoUUID = hayatoUUID;
    }

    public void setYoshikageUUID(UUID yoshikageUUID){
        this.yoshikageUUID = yoshikageUUID;
    }

    public UUID getBomb(){
        return bomb;
    }

    public boolean getHasTimeMarker(){
        return this.hasTimeMark;
    }

    public long getTimeMark(){
        return this.timeMark;
    }
    public void syncWithAnyPlayer(ServerPlayerEntity player) {

        //AddonPackets.sendToClient(new TrPickaxesThrownPacket(entity.getId(), pickaxesThrown), player);
    }
    public void syncWithEntityOnly(ServerPlayerEntity player) {
        AddonPackets.sendToClient(new SetCapabilityBomb(entity.getId(),bomb), (ServerPlayerEntity) entity);
        AddonPackets.sendToClient(new SetBoolTimeMarkerPacket(entity.getId(), this.hasTimeMark), (ServerPlayerEntity) entity);
//        AddonPackets.sendToClient(new SomeDataPacket(someDataField), player);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putUUID("Bomb",bomb);
        nbt.putBoolean("Has Time Mark", hasTimeMark);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        bomb = nbt.getUUID("Bomb");
        hasTimeMark = nbt.getBoolean("Has Time Mark");
    }


}
