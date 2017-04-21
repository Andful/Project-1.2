package org.lwjglb.AI;

import java.util.ArrayList;

public class Environment {
    private static ArrayList<Obstacle> obstacles;
    private static Crowd crowd;
    private static boolean[][][] obstacleMap;
    private static xyzStore highestXYZ = new xyzStore(0,0,0);
    public static final int worldOffset = 0;


    public Environment() {
        obstacles = new ArrayList<>();
        crowd = new Crowd();
    }

    public void addObstacle(String ID, int x, int y, int z){
        Obstacle obstacle = new Obstacle(ID,x,y,z);
        obstacles.add(obstacle);
    }

    public static void createObstacleMap(){
        obstacleMap = new boolean[highestXYZ.getX()+2*worldOffset+1][highestXYZ.getY()+worldOffset+1][highestXYZ.getZ()+2*worldOffset+1];
        for(Obstacle O : obstacles){
            obstacleMap[O.getX()][O.getY()][O.getZ()]=true;
        }
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }
    public static Crowd getCrowd() {
        return crowd;
    }
    public Obstacle getObstacle(int index){
        return obstacles.get(index);
    }
    public Obstacle getObstacleByID(String ID){
        try {
            for (Obstacle O : obstacles) {
                if (O.getID() == ID) {
                    return O;
                }
            }
            throw new Exception("Obstacle not found");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public Obstacle getObstacleByLocation(int x, int y, int z){
        try {
            for (Obstacle O : obstacles) {
                if(O.getX() == x && O.getY() == y && O.getZ() == z){
                    return O;
                }
            }
            throw new Exception("Obstacle not found");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public static boolean[][][] getObstacleMap() {
        return obstacleMap;
    }

    public void updateHighestXYZ(int x, int y, int z){
        if(x>highestXYZ.getX()){highestXYZ.setX(x);}
        if(y>highestXYZ.getY()){highestXYZ.setY(y);}
        if(z>highestXYZ.getZ()){highestXYZ.setZ(z);}
    }

    public xyzStore getHighestXYZ() {
        return highestXYZ;
    }


    @Override
    public String toString() {
        return "Environment{" +
                "obstacles=" + obstacles +
                '}';
    }

    public static class xyzStore{
        int x;
        int y;
        int z;
        public xyzStore(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
        public void setX(int x) {
            this.x = x;
        }
        public void setY(int y) {
            this.y = y;
        }
        public void setZ(int z) {
            this.z = z;
        }
        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
        public int getZ() {
            return z;
        }

        @Override
        public String toString() {
            return "xyzStore{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    '}';
        }
    }
}

