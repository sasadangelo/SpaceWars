package org.androidforfun.framework;

/**
 * Created by sasadangelo on 18/01/2017.
 */

public class MathUtils {
    static public final float PI = 3.1415927f;

    static private final float radFull = PI * 2;
    static private final float degFull = 360;

    static private final int SIN_BITS = 14; // 16KB. Adjust for accuracy.
    static private final int SIN_MASK = ~(-1 << SIN_BITS);
    static private final int SIN_COUNT = SIN_MASK + 1;

    static private final float degToIndex = SIN_COUNT / degFull;
    static public final float degreesToRadians = PI / 180;

    static private class Sin {
        static final float[] table = new float[SIN_COUNT];

        static {
            for (int i = 0; i < SIN_COUNT; i++)
                table[i] = (float)Math.sin((i + 0.5f) / SIN_COUNT * radFull);
            for (int i = 0; i < 360; i += 90)
                table[(int)(i * degToIndex) & SIN_MASK] = (float)Math.sin(i * degreesToRadians);
        }
    }
    /** Returns the sine in radians from a lookup table. */
    static public float sinDeg (float degrees) {
        return Sin.table[(int)(degrees * degToIndex) & SIN_MASK];
    }

    /** Returns the cosine in radians from a lookup table. */
    static public float cosDeg (float degrees) {
        return Sin.table[(int)((degrees + 90) * degToIndex) & SIN_MASK];
    }
}
