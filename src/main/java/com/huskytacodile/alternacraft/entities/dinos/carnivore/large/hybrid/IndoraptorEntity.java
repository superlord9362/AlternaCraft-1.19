package com.huskytacodile.alternacraft.entities.dinos.carnivore.large.hybrid;

import org.jetbrains.annotations.Nullable;

import com.huskytacodile.alternacraft.entities.ai.CrepuscularSleepGoal;
import com.huskytacodile.alternacraft.entities.ai.DinoSittingGoal;
import com.huskytacodile.alternacraft.entities.ai.SleepingRandomLookAroundGoal;
import com.huskytacodile.alternacraft.entities.dinos.HybridEntity;
import com.huskytacodile.alternacraft.entities.variant.GenderVariant;
import com.huskytacodile.alternacraft.entities.variant.IVariant;
import com.huskytacodile.alternacraft.util.ModSoundEvents;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class IndoraptorEntity extends HybridEntity {
    public IndoraptorEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        this.setTame(false);
    }

    public static AttributeSupplier.Builder attributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 135.00D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.FOLLOW_RANGE, 25.0D)
                .add(Attributes.ATTACK_DAMAGE, 24.0D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.2, false));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(4, new SleepingRandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new DinoSittingGoal(this));
        this.goalSelector.addGoal(4, new CrepuscularSleepGoal(this));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(Items.NETHERITE_SWORD), false));
        this.goalSelector.addGoal(3, new RandomSwimmingGoal(this,0,1));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.targetSelector.addGoal(5, new NonTameRandomTargetGoal<>(this, Animal.class, false,
                getPreySelection(this)));
    }
    
    public void aiStep() {
    	super.aiStep();
    	if (this.isAsleep() || this.isNaturallySitting()) {
    		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0);
    	} else {
    		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2D);
    	}
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_146746_, DifficultyInstance p_146747_, MobSpawnType p_146748_, @Nullable SpawnGroupData p_146749_, @Nullable CompoundTag p_146750_) {
        GenderVariant variant = Util.getRandom(GenderVariant.values(), this.random);
        setVariant(variant);
        return super.finalizeSpawn(p_146746_, p_146747_, p_146748_, p_146749_, p_146750_);
    }

    @Override
    public IVariant getVariant() {
        return GenderVariant.byId(this.getTypeVariant() & 255);
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return this.isAsleep() ? null : ModSoundEvents.INDORAPTOR_ROAR_1.get();
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return ModSoundEvents.INDORAPTOR_ROAR_3.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return ModSoundEvents.INDORAPTOR_ROAR_2.get();
    }

    @Override
    public String getAnimationName() {
        return "indoraptor";
    }

    @Override
    protected Item getTamingItem() {
        return Items.NETHERITE_SWORD;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }
}