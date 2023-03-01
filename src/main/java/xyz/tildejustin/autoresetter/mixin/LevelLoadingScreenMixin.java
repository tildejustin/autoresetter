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


@Mixin(value = LevelLoadingScreen.class, priority = 2000)
public abstract class LevelLoadingScreenMixin {
    @Inject(method = "render", at = @At("HEAD"))
    private void render(CallbackInfo ci) {
        if (!Autoresetter.checkedReset && WorldPreview.calculatedSpawn && WorldPreview.player != null && MinecraftClient.getInstance().getServer().getOverworld() != null) {
            System.out.println("current pos:" + WorldPreview.player.getX() + " " + WorldPreview.player.getZ());
            boolean reset = true;
            if (Atum.seed.length() > 0 || !Atum.isRunning) {
                System.out.println("not autoresetting, atum either set seed or not running");
                Autoresetter.checkedReset = true;
                return;
            }
            if (Autoresetter.biomes.length != 0) {
                Biome spawnBiome = MinecraftClient.getInstance().getServer().getOverworld().getBiome(new BlockPos((int) WorldPreview.player.getX(), (int) WorldPreview.player.getX(), (int) WorldPreview.player.getZ()));
                System.out.println("spawn biome: " + spawnBiome.getClass());
                for (Biome biome : Autoresetter.biomes) {
                    System.out.println("candidate biome: " + biome.getClass());
                    if (spawnBiome.getClass().equals(biome.getClass())) {
                        reset = false;
                        break;
                    }
                }
            }
            if (Autoresetter.structures.length > 0 && reset) {
                for (Structure structure : Autoresetter.structures) {
                    BlockPos structureLocation = MinecraftClient.getInstance().getServer().getOverworld().locateStructure(structure.getStructure(), WorldPreview.spawnPos, structure.radius, false);
                    System.out.println("structure: " + structureLocation);
                    if (structureLocation != null) {
                        System.out.println(structure + ": " + structureLocation.getX() + " " + structureLocation.getZ());
                        // learned *something* in school
                        if (Math.sqrt(
                                Math.pow(Math.abs((WorldPreview.spawnPos.getX() >> 4) - (structureLocation.getX() >> 4)), 2) +
                                Math.pow(Math.abs((WorldPreview.spawnPos.getZ() >> 4) - (structureLocation.getZ() >> 4)), 2)
                            ) < structure.radius
                        ) {
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
