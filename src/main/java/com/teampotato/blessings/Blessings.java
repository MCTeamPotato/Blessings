package com.teampotato.blessings;

import com.teampotato.blessings.effect.BlessingsEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("unused")
@Mod(Blessings.MOD_ID)
public class Blessings {
    public static final String MOD_ID = "blessings";

    private static final DeferredRegister<MobEffect> REGISTER = DeferredRegister.create(ForgeRegistries.POTIONS, MOD_ID);
    private static final RegistryObject<MobEffect> BLESSINGS = REGISTER.register(MOD_ID, () -> BlessingsEffect.INSTANCE);

    public Blessings() {
        REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());

        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, (LivingHurtEvent event) -> {
            LivingEntity entity = event.getEntityLiving();
            if (entity.level.isClientSide()) return;
            float currentHealth = entity.getHealth();
            Optional<MobEffectInstance> mobEffectInstanceOptional = Optional.ofNullable(entity.getEffect(BlessingsEffect.INSTANCE));
            mobEffectInstanceOptional.ifPresent(mobEffectInstance -> event.setAmount(event.getAmount() * (float) Math.pow(0.8D, (double) mobEffectInstance.getAmplifier() + 1)));
            float amount = event.getAmount();
            if (currentHealth <= amount) {
                mobEffectInstanceOptional.ifPresent(mobEffectInstance -> {
                    UUID id = entity.getUUID();
                    float initialDuration = BlessingsEffect.DURATION_DATA.getInt(id);
                    float currentDuration = mobEffectInstance.getDuration();
                    event.setAmount(0F);
                    entity.setHealth(entity.getMaxHealth() * (currentDuration / initialDuration));
                    entity.removeEffect(BlessingsEffect.INSTANCE);
                    BlessingsEffect.DURATION_DATA.removeInt(id);
                });
            }
        });

        MinecraftForge.EVENT_BUS.addListener((PotionEvent.PotionAddedEvent event) -> {
            LivingEntity entity = event.getEntityLiving();
            MobEffectInstance instance = event.getPotionEffect();
            if (entity.level.isClientSide()) return;
            if (instance.getEffect().equals(BlessingsEffect.INSTANCE)) {
                BlessingsEffect.DURATION_DATA.put(entity.getUUID(), instance.getDuration());
            }
        });
    }
}
