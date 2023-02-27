package xyz.tildejustin.autoresetter.mixin;

import me.voidxwalker.autoreset.Atum;
import me.voidxwalker.worldpreview.WorldPreview;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.LevelLoadingScreen;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
            if (Autoresetter.biomes.length != 0) {
                Biome spawnBiome = WorldPreview.player.getEntityWorld().getBiome(WorldPreview.spawnPos);
                for (Biome biome : Autoresetter.biomes) {
                    if (spawnBiome.getClass().equals(biome.getClass())) {
                        reset = false;
                        break;
                    }
                }
            }
            if (Autoresetter.structures.length > 0 && reset) {
                for (Structure structure : Autoresetter.structures) {
                    BlockPos structureLocation = WorldPreview.world.getServer().getOverworld().locateStructure(structure.getStructure(), WorldPreview.spawnPos, structure.radius, false);
                    System.out.println("structure: " + structureLocation);
                    if (structureLocation != null) {
                        System.out.println(structure + ": " + structureLocation.getX() + " " + structureLocation.getZ());
                        System.out.println("current pos:" + WorldPreview.spawnPos.getX() + " " + WorldPreview.spawnPos.getZ());
                        if (Math.abs((WorldPreview.spawnPos.getX() << 4) - (structureLocation.getX() << 4)) <= structure.radius && Math.abs((WorldPreview.spawnPos.getZ() << 4) - (structureLocation.getZ() << 4)) <= structure.radius) {
                            reset = false;
                            break;
                        }
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
