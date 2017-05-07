package org.lwjglb.AI;

import org.joml.Vector3i;

import java.util.List;

/**
 * Created by Andrea Nardi on 4/25/2017.
 */
public interface PathFindingAlgorithm<AgentId>
{
    public void computeMovments(Vector3i sizeEnviroment, List<Vector3i> startConfiguration, List<AgentId> agents, List<Vector3i> endConfiguration, List<Vector3i> obstacle,List<List<Movment<AgentId>>> result);
    public static class Movment<AgentId>
    {
        public Movment(AgentId id,Vector3i from,Vector3i to)
        {
            this.id=id;
            this.from=from;
            this.to=to;
        }
        public String toString()
        {
            return (id.toString()+" "+from.toString()+" "+to.toString());
        }
        public AgentId id;
        public Vector3i from;
        public Vector3i to;
    }
}
