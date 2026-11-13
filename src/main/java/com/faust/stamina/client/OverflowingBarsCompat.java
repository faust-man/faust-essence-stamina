package com.faust.stamina.client;

import net.minecraft.client.gui.*;
import java.lang.reflect.Method;

/** Client-only optional bridge; no loader or mandatory Overflowing Bars dependency. */
public final class OverflowingBarsCompat {
    private static final Method COUNT;
    private static final Object CONFIG;
    static {
        Method count = null; Object config = null;
        try {
            Class<?> mod = Class.forName("fuzs.overflowingbars.OverflowingBars");
            Class<?> holder = Class.forName("fuzs.puzzleslib.api.config.v3.ConfigHolder");
            config = holder.getMethod("get", Class.class).invoke(mod.getField("CONFIG").get(null), Class.forName("fuzs.overflowingbars.config.ClientConfig"));
            count = Class.forName("fuzs.overflowingbars.client.gui.RowCountRenderer").getMethod("drawBarRowCount", GuiGraphics.class, int.class, int.class, int.class, boolean.class, Font.class);
        } catch (ClassNotFoundException absent) {
            // Normal when the optional mod is not installed.
        } catch (ReflectiveOperationException incompatible) {
            org.slf4j.LoggerFactory.getLogger("fauststamina").warn("Overflowing Bars bridge unavailable", incompatible);
        }
        COUNT = count; CONFIG = config;
    }
    public static boolean enabled() { return COUNT != null; }
    public static int color(int layer, boolean top) {
        if (layer == 0) return StaminaSprites.STAMINA_COLOR;
        boolean inverse = false;
        try {
            Object health = CONFIG.getClass().getField("health").get(CONFIG);
            inverse = health.getClass().getField("inverseColoring").getBoolean(health);
        } catch (ReflectiveOperationException failure) { throw new IllegalStateException(failure); }
        return StaminaSprites.layerColor(layer, top, inverse);
    }
    public static void count(GuiGraphics g, int x, int y, int filled, Font font) {
        if (filled <= 20) return;
        try { COUNT.invoke(null, g, x, y, filled, false, font); }
        catch (ReflectiveOperationException failure) { throw new IllegalStateException(failure); }
    }
}
