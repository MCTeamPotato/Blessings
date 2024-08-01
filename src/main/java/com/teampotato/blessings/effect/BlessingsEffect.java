package com.teampotato.blessings.effect;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;
import java.util.UUID;

public class BlessingsEffect extends MobEffect {
    public BlessingsEffect() {
        super(MobEffectCategory.BENEFICIAL, 1314520);
    }

    public static final Object2IntMap<UUID> DURATION_DATA = Object2IntMaps.synchronize(new Object2IntOpenHashMap<>());

    public static final BlessingsEffect INSTANCE = new BlessingsEffect();

    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {}

    public void applyInstantenousEffect(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity livingEntity, int amplifier, double health) {}

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    public boolean isBeneficial() {
        return true;
    }
}
