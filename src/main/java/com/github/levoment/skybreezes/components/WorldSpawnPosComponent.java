package com.github.levoment.skybreezes.components;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import java.util.Map;
import java.util.Set;

public class WorldSpawnPosComponent implements SpawnPosComponent {
    // Map containing the world spawn positions
    private Map<String, BlockPos> value;

    // Constructor for the class
    public WorldSpawnPosComponent(Map<String, BlockPos> spawnInformation) {
        this.value = spawnInformation;
    }

    @Override
    public Map<String, BlockPos> getValue() {
        // Return the map containing the world spawn positions
        return this.value;
    }

    @Override
    public void readFromNbt(CompoundTag compoundTag) {
        // Read the keys passed on the CompoundTag to store them on the Map containing the world spawn positions
        // This is called when the component Nbt is read from file
        // It is used to set the spawn positions in memory
        Set<String> setOfKeys = compoundTag.getKeys();
        for(String key : setOfKeys) {
            // Get the spawn position for the given key
            int[] spawnPosition = compoundTag.getIntArray(key);
            // Store the spawn position on the Map containing the world spawn positions
            this.value.put(key, new BlockPos(spawnPosition[0], spawnPosition[1], spawnPosition[2]));
        }
    }

    @Override
    public void writeToNbt(CompoundTag compoundTag) {
        // Iterate through the list of entries in the Map containing the world spawn positions
        for (Map.Entry<String,BlockPos> entry : this.value.entrySet()) {
            // Get the spawn point for the given entry
            int[] originalSpawnPoint = {entry.getValue().getX(), entry.getValue().getY(), entry.getValue().getZ()};
            // Put the spawn position on the CompoundTag for it to be saved in storage
            compoundTag.putIntArray(entry.getKey(), originalSpawnPoint);
        }
    }
}
