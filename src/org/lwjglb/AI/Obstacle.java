package org.lwjglb.AI;

public class Obstacle {
    private final String ID;
    private final int X;
    private final int Y;
    private final int Z;

    public Obstacle(String id, int x, int y, int z) {
        ID = id;
        X = x;
        Y = y;
        Z = z;
    }

    public String getID() {
        return ID;
    }
    public int getX() {
        return X;
    }
    public int getY() {
        return Y;
    }
    public int getZ() {
        return Z;
    }

    @Override
    public String toString() {
        return "Obstacle{" +
                "ID='" + ID + '\'' +
                ", X=" + X +
                ", Y=" + Y +
                ", Z=" + Z +
                '}';
    }
}
