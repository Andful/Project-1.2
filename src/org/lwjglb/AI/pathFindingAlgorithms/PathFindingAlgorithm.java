package org.lwjglb.AI.pathFindingAlgorithms;

import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrea Nardi on 4/25/2017.
 */
public interface PathFindingAlgorithm<AgentId>
{
    public void computeMovments(Vector3i sizeEnviroment, List<Vector3i> startConfiguration, List<AgentId> agentsId, List<Vector3i> endConfiguration, List<Vector3i> obstacle,List<List<Movment<AgentId>>> result);
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
    public static List<Vector3i> getAdiacentPosition(Vector3i pos)
    {
        List<Vector3i> result=new ArrayList<Vector3i>(4);
        for(int p=-1;p<=1;p+=2)
        {
            result.add(new Vector3i(pos.x+p,pos.y,pos.z));
            result.add(new Vector3i(pos.x,pos.y,pos.z+p));
        }
        return result;
    }
    public static Vector3i above(Vector3i v)
    {
        return new Vector3i(v.x,v.y+1,v.z);
    }
    public static Vector3i below(Vector3i v)
    {
        return new Vector3i(v.x,v.y-1,v.z);
    }
    public static void main(String[] args)
    {
        Vector3i pos =new Vector3i();
        System.out.println(getAdiacentPosition(pos));
    }
}
