package com.faust.stamina.client;

/** Neutral 8px lightning masks, tinted independently for each half-unit. */
public final class StaminaSprites {
    public static final int SIZE = 8;
    public static final int GUI_SIZE = 8;
    public static final int STAMINA_COLOR = 0xFFFFD000;
    public static final int FATIGUE_COLOR = 0xFF000000;
    public static final int EMPTY_COLOR = 0xFF666666;
    public static final int EXHAUSTED_COLOR = 0xFFFF4040;

    private StaminaSprites() {}

    // Native GUI pixels: outline and shine stay neutral under every layer tint.
    private static final String[] MASK = {
        "00001100", "00011100", "00111000", "01111110",
        "01111100", "00011000", "00011000", "00010000"
    };
    private static boolean solid(int x, int y) {
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE && MASK[y].charAt(x) == '1';
    }
    /** Zero is transparent. Fatigue remains entirely black, including its highlight. */
    public static int pixel(int x, int y, int state, int color) {
        if (!solid(x, y)) return 0;
        if (state == 2) return FATIGUE_COLOR;
        boolean edge = !solid(x-1,y) || !solid(x+1,y) || !solid(x,y-1) || !solid(x,y+1);
        if (edge) return 0xFF181818;
        if (state == 1) return EMPTY_COLOR;
        if (!solid(x-1,y-1)) return 0xFFFFF3CF;
        return color;
    }

    /** 0 = filled, 1 = spent, 2 = unavailable due to fatigue. */
    public static int state(int unit, int available, int filled) {
        return unit >= available ? 2 : unit < filled ? 0 : 1;
    }

    public static int color(int state, boolean exhausted) {
        if (state == 2) return FATIGUE_COLOR;
        if (exhausted) return EXHAUSTED_COLOR;
        return state == 0 ? STAMINA_COLOR : EMPTY_COLOR;
    }

    public static String texture(int state) {
        return "textures/gui/stamina_lightning_" + (state == 1 ? "empty" : "full") + ".png";
    }

    public static int layerColor(int layer, boolean top, boolean inverse) {
        return layer > 0 && top != inverse ? 0xFFE9801C : STAMINA_COLOR;
    }

    public static int textureX(int state, int half) {
        return half * SIZE / 2;
    }
}
