package org.lwjglb.AI;

import java.util.ArrayList;

public class Crowd{
    private static ArrayList<Agent> crowd;
    private boolean[][][] crowdMap;

    public Crowd() {
        crowd = new ArrayList<>();
    }

    public void addAgent(String ID, int startX, int startY, int startZ){
        Agent agent = new Agent(ID, startX, startY, startZ);
        crowd.add(agent);
    }
    public void makeCrowdMap(){
        if(Environment.getObstacleMap()==null){
            Environment.createObstacleMap();
        }
        crowdMap = Environment.getObstacleMap().clone();
        for(Agent A : crowd){
            crowdMap[A.getCurX()][A.getCurY()][A.getCurZ()]=true;
        }
    }

    public ArrayList<Agent> getCrowdList() {
        return crowd;
    }
    public Agent getAgent(int index){
        return crowd.get(index);
    }
    public Agent getAgentByID(String ID){
        try {
            for (Agent A : crowd) {
                if (A.getID().equals(ID)) {
                    return A;
                }
            }
            throw new Exception("Agent not found");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public Agent getAgentByStartLocation(int x, int y, int z){
        try {
            for (Agent A : crowd) {
                if(A.getStartX() == x && A.getStartY() == y && A.getStartZ() == z){
                    return A;
                }
            }
            throw new Exception("Agent not found");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public Agent getAgentByTargetLocation(int x, int y, int z){
        try {
            for (Agent A : crowd) {
                if(A.getTargetX() == x && A.getTargetY() == y && A.getTargetZ() == z){
                    return A;
                }
            }
            throw new Exception("Agent not found");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public Agent getAgentByCurLocation(int x, int y, int z){
        try {
            for (Agent A : crowd) {
                if(A.getCurX() == x && A.getCurY() == y && A.getCurZ() == z){
                    return A;
                }
            }
            throw new Exception("Agent not found");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public String toString() {
        return "Crowd{" +
                "crowd=" + crowd +
                '}';
    }
}
