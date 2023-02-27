package xyz.tildejustin.autoresetter;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.StructureFeature;

public class Structure {
    public String name;
    public Integer radius;

    public Structure() {}

    public Structure(String name, Integer radius) {
            this.name = name;
            this.radius = radius;
    }

    public StructureFeature getStructure() {
        return Registry.STRUCTURE_FEATURE.get(new Identifier("minecraft", this.name));
    }
}
