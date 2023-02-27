package xyz.tildejustin.autoresetter;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

import java.io.*;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Autoresetter implements ModInitializer {

    public static File configFile;
    public static JsonConfig config;
    public static Biome[] biomes;
    public static Structure[] structures;
    public static boolean checkedReset;

    private static void createIfNonExistent(File file) {
        try {
            if (file.createNewFile()) {
                writeDefaultProperties(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeDefaultProperties(File file) {
        JsonConfig jsonConfig = new JsonConfig(new String[] {"beach"}, new Structure[]{new Structure("buried_treasure", 5)});
        try (Writer writer = new FileWriter(file)) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();
            gson.toJson(jsonConfig, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void onInitialize() {
        configFile = FabricLoader.getInstance().getConfigDir().resolve("autoresetter.json").toFile();
        createIfNonExistent(configFile);
        Gson gson = new Gson();
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(configFile));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        config = gson.fromJson(bufferedReader, JsonConfig.class);
        biomes = Arrays.stream(config.biomes).map((String biome) -> Registry.BIOME.get(new Identifier("minecraft", biome))).filter(Objects::nonNull).toArray(Biome[]::new);
        // there has to be a better way to do this
        structures = Arrays.stream(config.structures).filter((Structure structure) -> Registry.STRUCTURE_FEATURE.get(new Identifier("minecraft", structure.name)) != null).toArray(Structure[]::new);
        if (biomes.length == 0 && structures.length == 0) {
            throw new RuntimeException("Autoresetter: no valid autoreset conditions");
        }
    }
}