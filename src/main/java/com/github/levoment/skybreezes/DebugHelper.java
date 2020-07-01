package com.github.levoment.skybreezes;

import net.minecraft.client.MinecraftClient;

public class DebugHelper {
    public static boolean restoreMouse() {
        MinecraftClient.getInstance().mouse.unlockCursor();
        return true;
    }
}