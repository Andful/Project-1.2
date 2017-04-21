package org.lwjglb.AI;

import java.util.ArrayList;

public class CommandList {
    int timeStep;
    ArrayList<GraphicsCommand> commandList;

    public CommandList() {
        timeStep = 1;
        this.commandList = commandList;
    }

    public void nextTimeStep(){
        timeStep++;
    }
    public int getTimeStep() {
        return timeStep;
    }
    public void setTimeStep(int timeStep) {
        this.timeStep = timeStep;
    }

    public void addCommand(int agentIndex, String operation){
        GraphicsCommand.Operation operation1 = new GraphicsCommand.Operation(operation);
        GraphicsCommand cmd = new GraphicsCommand(timeStep, agentIndex, operation1);
        commandList.add(cmd);
    }
    public void addCommand(Agent agent, String operation){
        GraphicsCommand.Operation operation1 = new GraphicsCommand.Operation(operation);
        GraphicsCommand cmd = new GraphicsCommand(timeStep, agent,operation1);
        commandList.add(cmd);
    }
    public void addCommand(String agentID, String operation){
        GraphicsCommand.Operation operation1 = new GraphicsCommand.Operation(operation);
        GraphicsCommand cmd = new GraphicsCommand(timeStep, agentID,operation1);
        commandList.add(cmd);
    }

    public ArrayList<GraphicsCommand> getCommandList() {
        return commandList;
    }
    public ArrayList<GraphicsCommand> getCommandsAtTimeStep(int timeStep){
        int firstOfTimeStep = -1;
        int lastOfTimeStep = -1;
        for(GraphicsCommand cmd : commandList){
            if(cmd.getTimeStep()==timeStep){
                if (firstOfTimeStep == -1) {
                    firstOfTimeStep = commandList.indexOf(cmd);
                }
                lastOfTimeStep = commandList.indexOf(cmd);
            }
            else if(cmd.getTimeStep()>timeStep){
                return (ArrayList<GraphicsCommand>) commandList.subList(firstOfTimeStep,lastOfTimeStep+1);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String string = " ";
        // TODO: 22-03-17 string does not include absolute coordinates of agents yet
        for(GraphicsCommand cmd : commandList){
            string += cmd.getTimeStep() + ", " + cmd.getAgentID() + ", "    + "\n";
        }
        return string;
    }
}
