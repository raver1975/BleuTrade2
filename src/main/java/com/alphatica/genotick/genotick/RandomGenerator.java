package com.alphatica.genotick.genotick;

import java.util.Random;

public class RandomGenerator {
    public static Random assignRandom() {
        Random random = new Random();
        String seedString = System.getenv("GENOTICK_RANDOM_SEED");
        if( seedString != null) {
            long seed = Long.parseLong(seedString);
            random.setSeed(seed);
        }
        return random;
    }

}
