package org.example.util;

import lombok.Getter;

/**
 * @file Coords.java
 * @author William Viet Anh Douglas Halliday
 * @date 2025
 */
@Getter
public class Coords {

    /*
    Basic class to hold x and y co-ordinates used to identify positions on the board,
    easily changeable to a dataclass or record in more modern versions of java.
     */

    private final int x;
    private final char y;

    public Coords(int x, char y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return this.y + " - " + this.x;
    }
}
