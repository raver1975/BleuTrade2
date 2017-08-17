package com.alphatica.genotick.instructions;

import com.alphatica.genotick.mutator.Mutator;

class Tools {
    static double mutateDouble(double argument, Mutator mutator) {
        double next = argument * mutator.getNextDouble();
        if(argument > 0) {
             return argument + next;
        } else {
            return argument - next;
        }
    }
}
