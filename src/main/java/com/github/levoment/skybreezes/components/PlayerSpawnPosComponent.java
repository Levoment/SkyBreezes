package com.github.levoment.skybreezes.components;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

import java.util.Map;

interface SpawnPosComponent extends ComponentV3 {
    Map<String, BlockPos> getValue();
}

interface PlayerLastDimensionComponent extends ComponentV3 {
    String playerUUID();
    int[] lastTeleportPosition();
    boolean lastVisitedSkyBreezes();

}
public class PlayerSpawnPosComponent implements PlayerLastDimensionComponent {
    private String playerUUID;

    private int[] lastTeleportPosition;
    private boolean lastVisitedSkyBreezes;

    public PlayerSpawnPosComponent(String playerUUID, int[] lastTeleportPosition, boolean lastVisitedSkyBreezes) {
        this.playerUUID = playerUUID;
        this.lastTeleportPosition = lastTeleportPosition;
        this.lastVisitedSkyBreezes = lastVisitedSkyBreezes;
    }

    @Override
    public String playerUUID() {
        return this.playerUUID;
    }

    @Override
    public int[] lastTeleportPosition() {
        if (this.lastTeleportPosition != null) {
            return this.lastTeleportPosition;
        } else {
            int[] valueToReturn = new int[]{-1000000, -1000000, -1000000};
            return new int[]{-1000000, -1000000, -1000000};
        }
    }

    @Override
    public boolean lastVisitedSkyBreezes() {
        return this.lastVisitedSkyBreezes;
    }

    @Override public void readFromNbt(NbtCompound tag) {
        String playerUUID = tag.getString("skbrz_playerUUID");
        int[] spawnPositions = tag.getIntArray("SkyBreezesPortalPosition");
        Boolean lastVisitedSkyBreezes1 = tag.getBoolean("LastVisitedSkyBreezes");
        this.playerUUID = playerUUID;
        this.lastTeleportPosition = spawnPositions;
        this.lastVisitedSkyBreezes = lastVisitedSkyBreezes1;
    }
    @Override public void writeToNbt(NbtCompound tag) {
        tag.putIntArray("SkyBreezesPortalPosition", this.lastTeleportPosition);
        tag.putString("skbrz_playerUUID", this.playerUUID);
        tag.putBoolean("LastVisitedSkyBreezes", this.lastVisitedSkyBreezes);
    }

    public String getPlayerUUID() {
        return playerUUID;
    }

    public int[] getLastTeleportPosition() {
        return lastTeleportPosition;
    }

    public boolean isLastVisitedSkyBreezes() {
        return lastVisitedSkyBreezes;
    }

    public void setPlayerUUID(String playerUUID) {
        this.playerUUID = playerUUID;
    }

    public void setLastTeleportPosition(int[] lastTeleportPosition) {
        this.lastTeleportPosition = lastTeleportPosition;
    }

    public void setLastVisitedSkyBreezes(boolean lastVisitedSkyBreezes) {
        this.lastVisitedSkyBreezes = lastVisitedSkyBreezes;
    }
}
