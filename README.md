## How it works
The mod resets for any biome OR structure within a set radius, as configured by the user. Invalid configurations will be filtered out on runtime, but if there are no valid conditions the mod will throw a RuntimeException.

## How to use
The autoresetter is configured with the `autoresetter.json` file in the config directory of your instance. It has two parts, biomes and structures.

{% warning %}

**Warning:** config is case-sensitive and everything should be lowercase

{% endwarning %}

Biomes is a list of all biomes the mod will reset for. The full list of these is:

> ocean, default, plains, desert, mountains, forest, taiga, swamp, river, nether_wastes, the_end, frozen_ocean, frozen_river, snowy_tundra, snowy_mountains, mushroom_fields, mushroom_field_shore, beach, desert_hills, wooded_hills, taiga_hills, mountain_edge, jungle, jungle_hills, jungle_edge, deep_ocean, stone_shore, snowy_beach, birch_forest, birch_forest_hills, dark_forest, snowy_taiga, snowy_taiga_hills, giant_tree_taiga, giant_tree_taiga_hills, wooded_mountains, savanna, savanna_plateau, badlands, wooded_badlands_plateau, badlands_plateau, small_end_islands, end_midlands, end_highlands, end_barrens, warm_ocean, lukewarm_ocean, cold_ocean, deep_warm_ocean, deep_lukewarm_ocean, deep_cold_ocean, deep_frozen_ocean, the_void, sunflower_plains, desert_lakes, gravelly_mountains, flower_forest, taiga_mountains, swamp_hills, ice_spikes, modified_jungle, modified_jungle_edge, tall_birch_forest, tall_birch_hills, dark_forest_hills, snowy_taiga_mountains, giant_spruce_taiga, giant_spruce_taiga_hills, modified_gravelly_mountains, shattered_savanna, shattered_savanna_plateau, eroded_badlands, modified_wooded_badlands_plateau, modified_badlands_plateau, bamboo_jungle, bamboo_jungle_hills, soul_sand_valley, crimson_forest, warped_forest, and basalt_deltas.


Structures are pairs of names and radii. The supported structures are:

> pillager_outpost, mineshaft, mansion, jungle_pyramid, desert_pyramid, igloo, ruined_portal, shipwreck, swamp_hut, stronghold, monument, ocean_ruin, fortress, endcity, buried_treasure, village, nether_fossil, and bastion_remnant.

Examples of valid configs are:

```
{
  "biomes": [],
  "structures": [
    {
      "name": "village",
      "radius": 50
    }
  ]
}
```
```
{
  "biomes": [
    "beach"
  ],
  "structures": [
    {
      "name": "buried_treasure",
      "radius": 5
    },
    {
      "name": "shipwreck",
      "radius": 20
    }
  ]
}
```

Message tildejustin#4317 if you have any questions or issues, or open a bug report on github. Happy cheating ^-^!