package com.faust.stamina.client;

/** Native 9px lightning sprites, tinted independently for each half-unit. */
public final class StaminaSprites {
    public static final int SIZE = 9;
    public static final int GUI_SIZE = 9;
    public static final int STAMINA_COLOR = 0xFFFFD000;
    public static final int FATIGUE_COLOR = 0xFF37373B;
    public static final int EMPTY_COLOR = 0xFF151518;
    public static final int EXHAUSTED_COLOR = 0xFFFF4040;

    private StaminaSprites() {}

    // Native 9x9 geometry: O outline, H translucent shine, S translucent shadow.
    private static final String[] PIXELS = {
        "..OOOOO..", ".OHBBSO..", ".OHBSO...", "OHBBOOO..",
        "OHBBBBSO.", "OOOBBSO..", "..OHSO...", ".OHSO....", ".OOO....."
    };
    private static char part(int x, int y) {
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE ? PIXELS[y].charAt(x) : '.';
    }
    /** Base pigment. Shine and shadow are independent translucent overlays. */
    public static int pixel(int x, int y, int state, int color) {
        char part = part(x, y);
        if (part == '.') return 0;
        if (part == 'O') return state == 1 && color == EMPTY_COLOR ? 0xFF09090B : 0xFF211D29;
        return state == 2 ? FATIGUE_COLOR : color;
    }
    public static int hollowPixel(int x,int y,int color){return part(x,y)=='O'?color:0;}
    public static int overlay(int x, int y, int state) {
        return switch(part(x, y)) {
            case 'H' -> state == 1 ? 0x10FFFFFF : 0x60FFFFFF;
            case 'S' -> 0x50000000;
            default -> 0;
        };
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
