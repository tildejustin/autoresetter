package xyz.tildejustin.autoresetter;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

import java.util.Arrays;

public class JsonConfig {
    public String[] biomes;
    public Structure[] structures;

    public JsonConfig() {
    }

    public JsonConfig(String[] biomes, Structure[] structures) {
        this.biomes = biomes;
        this.structures = structures;
    }
}
