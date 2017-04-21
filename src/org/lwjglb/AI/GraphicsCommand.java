package org.lwjglb.AI;

public class GraphicsCommand {
    private int timeStep;
    private int agentIndex;
    private String agentID;
    private Operation operation;

    public GraphicsCommand(int timeStep, int agentIndex, Operation operation) {
        this.timeStep = timeStep;
        this.agentIndex = agentIndex;
        agentID = Environment.getCrowd().getAgent(agentIndex).getID();
        this.operation = operation;
    }
    public GraphicsCommand(int timeStep, Agent agent, Operation operation) {
        this.timeStep = timeStep;
        agentIndex = Environment.getCrowd().getCrowdList().indexOf(agent);
        agentID = agent.getID();
        this.operation = operation;
    }

    public GraphicsCommand(int timeStep, String agentID, Operation operation) {
        this.timeStep = timeStep;
        agentIndex = Environment.getCrowd().getCrowdList().indexOf(Environment.getCrowd().getAgentByID(agentID));
        this.agentID = agentID;
        this.operation = operation;
    }

    public int getTimeStep() {
        return timeStep;
    }

    public int getAgentIndex() {
        return agentIndex;
    }

    public String getAgentID() {
        return agentID;
    }

    public Operation getOperation() {
        return operation;
    }

    public static class Operation{
        String operation;
        public Operation(String operation) {
            try{
                if(operation.equals("x-")
                        ||operation.equals("x+")
                        ||operation.equals("y-")
                        ||operation.equals("y+")
                        ||operation.equals("z-")
                        ||operation.equals("z+")) {
                    this.operation = operation;
                }
                else{
                    throw new Exception("false operation");
                }
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        public String getOperation() {
            return operation;
        }
    }
}
