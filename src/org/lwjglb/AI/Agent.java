package org.lwjglb.AI;

public class Agent {
    private final String ID;
    private final int startX;
    private final int startY;
    private final int startZ;
    private int targetX;
    private int targetY;
    private int targetZ;
    private int curX;
    private int curY;
    private int curZ;
    private boolean hasTarget = false;
    private int distToTarget;
    private int distFromStart;

    public Agent(String ID, int startX, int startY, int startZ){
        this.ID = ID;
        this.startX = startX;
        this.startY = startY;
        this.startZ = startZ;
        curX = startX;
        curY = startY;
        curZ = startZ;
        calcDistFromStart();
    }
    public void setTarget(int targetX, int targetY, int targetZ){
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
        hasTarget = true;
        calcDistToTarget();
    }

    public void calcDistToTarget(){
        int deltaX = Math.abs(targetX-curX);
        int deltaY = Math.abs(targetY-curY);
        int deltaZ = Math.abs(targetZ-curZ);
        distToTarget = deltaX + deltaY + deltaZ;
    }
    public void calcDistFromStart(){
        int deltaX = Math.abs(startX-curX);
        int deltaY = Math.abs(startY-curY);
        int deltaZ = Math.abs(startZ-curZ);
        distFromStart = deltaX + deltaY + deltaZ;
    }

    public String getID() {
        return ID;
    }

    public int getStartX() {
        return startX;
    }
    public int getStartY() {
        return startY;
    }
    public int getStartZ() {
        return startZ;
    }

    public int getTargetX() {
        return targetX;
    }
    public int getTargetY() {
        return targetY;
    }
    public int getTargetZ() {
        return targetZ;
    }

    public int getCurX() {
        return curX;
    }
    public void setCurX(int curX) {
        this.curX = curX;
        calcDistToTarget();
        calcDistFromStart();
    }
    public void addToCurX(int deltaX){
        curX = curX + deltaX;
        calcDistToTarget();
        calcDistFromStart();
    }

    public int getCurY() {
        return curY;
    }
    public void setCurY(int curY) {
        this.curY = curY;
        calcDistToTarget();
        calcDistFromStart();
    }
    public void addToCurY(int deltaY){
        curY = curY + deltaY;
        calcDistToTarget();
        calcDistFromStart();
    }

    public int getCurZ() {
        return curZ;
    }
    public void setCurZ(int curZ) {
        this.curZ = curZ;
        calcDistToTarget();
        calcDistFromStart();
    }
    public void addToCurZ(int deltaZ){
        curZ = curZ + deltaZ;
        calcDistToTarget();
        calcDistFromStart();
    }

    public boolean hasTarget() {
        return hasTarget;
    }

    public int getDistToTarget() {
        return distToTarget;
    }
    public int getDistFromStart() {
        return distFromStart;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "ID='" + ID + '\'' +
                ", startX=" + startX +
                ", startY=" + startY +
                ", startZ=" + startZ +
                ", targetX=" + targetX +
                ", targetY=" + targetY +
                ", targetZ=" + targetZ +
                ", curX=" + curX +
                ", curY=" + curY +
                ", curZ=" + curZ +
                '}';
    }
}
