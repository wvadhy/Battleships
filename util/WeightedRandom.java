package org.example.util;

import java.util.Random;

public class WeightedRandom {

    /*
    Creates incremental weights for the placement of ships,
    the higher the index the higher the weight and chance for placement
    of a ship rather than a blank space.
    */

    private final int total;
    private final int[] counts;
    private final Random rand;
    private int current;

    public WeightedRandom(int[] weights)
    {
        rand = new Random();
        counts = weights.clone();

        for(int i=1; i<counts.length; i++)
            counts[i] += counts[i-1];

        total = counts[counts.length-1];
    }

    public int nextIndex()
    {
        int idx = 0;
        int pick = rand.nextInt(total);
        while(pick >= counts[idx]) idx++;
        current = idx;
        return idx;
    }

    public int currentIndex() { return current; }
}