package com.faust.stamina.client;

/** Pure one-row layout: fatigue belongs to its absolute capacity layer. */
public final class StaminaLayers {
    private StaminaLayers() {}
    public static int layer(int filled) { return Math.max(0, (filled - 1) / 20); }
    public static int units(int total) { return Math.min(20, Math.max(0, total)); }
    public static int absoluteUnit(int slot, int filled) {
        int layer = layer(filled);
        int top = Math.max(0, filled - layer * 20);
        return layer > 0 && slot >= top ? (layer - 1) * 20 + slot : layer * 20 + slot;
    }
    public static int state(int slot, int total, int available, int filled) {
        int start = layer(filled) * 20;
        if (start + slot >= available && start + slot < total) return 2;
        return absoluteUnit(slot, filled) < filled ? 0 : 1;
    }
}
