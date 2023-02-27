package xyz.tildejustin.autoresetter.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.tildejustin.autoresetter.Autoresetter;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "startIntegratedServer(Ljava/lang/String;Lnet/minecraft/util/registry/RegistryTracker$Modifiable;Ljava/util/function/Function;Lcom/mojang/datafixers/util/Function4;ZLnet/minecraft/client/MinecraftClient$WorldLoadAction;)V", at = @At("HEAD"))
    private void resetCheckedWorld(CallbackInfo ci) {
        Autoresetter.checkedReset = false;
    }
}
