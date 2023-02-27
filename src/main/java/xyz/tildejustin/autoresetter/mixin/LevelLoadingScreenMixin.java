package xyz.tildejustin.autoresetter.mixin;

import me.voidxwalker.autoreset.Atum;
import me.voidxwalker.worldpreview.WorldPreview;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.LevelLoadingScreen;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.tildejustin.autoresetter.Autoresetter;
import xyz.tildejustin.autoresetter.Structure;

import java.util.Objects;

@Mixin(value = LevelLoadingScreen.class, priority = 2000)
public abstract class LevelLoadingScreenMixin {
    @Inject(method = "render", at = @At("HEAD"))
    private void render(CallbackInfo ci) {
        if (WorldPreview.world != null && WorldPreview.player != null && WorldPreview.clientWord != null && !Autoresetter.checkedReset) {
            boolean reset = true;
            BlockPos spawnPos = WorldPreview.player.getBlockPos();
            if (Autoresetter.biomes.length != 0) {
                Biome spawnBiome = WorldPreview.player.getEntityWorld().getBiome(spawnPos);
                for (Biome biome : Autoresetter.biomes) {
                    if (spawnBiome.getClass().equals(biome.getClass())) {
                        reset = false;
                        break;
                    }
                }
            }
            if (Autoresetter.structures.length > 0 && reset) {
                for (Structure structure : Autoresetter.structures) {
                    if (WorldPreview.world.getServer().getOverworld().locateStructure(structure.getStructure(), spawnPos, structure.radius, false) != null) {
                        reset = false;
                        break;
                    }
                }
            }
            System.out.println("Autoreset: " + reset);
            if (reset) {
                Atum.hotkeyPressed = true;
            }
            Autoresetter.checkedReset = true;
        }
    }
}
