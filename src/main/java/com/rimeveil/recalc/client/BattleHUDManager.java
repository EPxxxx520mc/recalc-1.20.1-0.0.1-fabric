
package com.rimeveil.recalc.client;

import com.rimeveil.recalc.Recalc;

public class BattleHUDManager {
    private static boolean hudVisible = false;
    private static boolean cursorVisible = false;
    
    private static float energyCurrent = 100.0f;
    private static float energyMax = 100.0f;
    
    public static void toggleHUD() {
        hudVisible = !hudVisible;
        if (!hudVisible) {
            cursorVisible = false;
        }
    }
    
    public static boolean isHUDVisible() {
        return hudVisible;
    }
    
    public static boolean isCursorVisible() {
        return cursorVisible;
    }
    
    public static void setCursorVisible(boolean visible) {
        cursorVisible = visible;
    }
    
    public static void toggleCursor() {
        if (hudVisible) {
            cursorVisible = !cursorVisible;
        }
    }
    
    public static float getEnergyCurrent() {
        return energyCurrent;
    }
    
    public static void setEnergyCurrent(float value) {
        energyCurrent = Math.max(0.0f, Math.min(value, energyMax));
    }
    
    public static void addEnergy(float amount) {
        energyCurrent = Math.min(energyCurrent + amount, energyMax);
    }
    
    public static void removeEnergy(float amount) {
        energyCurrent = Math.max(energyCurrent - amount, 0.0f);
    }
    
    public static float getEnergyMax() {
        return energyMax;
    }
    
    public static void setEnergyMax(float value) {
        energyMax = Math.max(1.0f, value);
        if (energyCurrent > energyMax) {
            energyCurrent = energyMax;
        }
    }
    
    public static void reset() {
        hudVisible = false;
        cursorVisible = false;
    }
}
