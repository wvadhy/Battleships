package org.example.util;

import java.util.ArrayList;
import java.util.List;

public class Ship {

    /*
    Ship class for storing basic attributes.
     */

    private final int length;
    private boolean sunk = false;
    private int remaining;
    private List<Coords> positions;

    public Ship(int length) {
        this.length = length;
        this.remaining = length;
        this.positions = new ArrayList<>(length);
    }

    public void destroyPart() {
        remaining -= 1;
        System.out.println("Destroyed a part of " + this.length + " long battleship!");
        System.out.println(this.remaining + " parts remaining!");
        if (remaining == 0) {
            System.out.println("Sunk!");
            sunk = true;
        }
    }
    public int getRemaining() { return remaining; }
    public int getLength() { return length; }
    public boolean isSunk() { return sunk; }
    public List<Coords> getPositions() { return positions; }

    public void addPosition(int x, char y) {
        System.out.println("Added: " + "(" + x + ", " + y + ") to ship of length " + this.length);
        positions.add(new Coords(x, y));
    }
}
