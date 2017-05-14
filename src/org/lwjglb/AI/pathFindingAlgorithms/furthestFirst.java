package org.lwjglb.AI.pathFindingAlgorithms;

import org.joml.Vector3i;
import org.lwjglb.Util.Array3D;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Andrea Nardi on 5/8/2017.
 */
public class furthestFirst<AgentId> implements PathFindingAlgorithm<AgentId>
{
    private final static int EMPTY=Integer.MAX_VALUE-2;
    private final static int OBSTACLE=Integer.MAX_VALUE-1;
    private final static int AGENT=Integer.MAX_VALUE;

    public class Agent
    {
        AgentId id;
        Vector3i pos;
        public Agent(AgentId id,Vector3i pos)
        {
            this.id=id;
            this.pos=pos;
        }

        @Override
        public int hashCode()
        {
            return pos.hashCode();
        }
        public boolean equals(Object o)
        {
            return this.pos.equals(o);
        }
    }

    @Override
    public void computeMovments(Vector3i sizeEnviroment, List<Vector3i> startConfiguration, List<AgentId> agentsId, List<Vector3i> endConfiguration, List<Vector3i> obstacle, List<List<Movment<AgentId>>> result)
    {
        Array3D<Integer> blocks= generateBlockArray(sizeEnviroment,startConfiguration,obstacle);
        List<Agent> agents=new LinkedList<>();
        List<Agent> movableAgents=new LinkedList<>();
        {
            Iterator<AgentId> ids=agentsId.iterator();
            Iterator<Vector3i> positions=startConfiguration.iterator();
            while(ids.hasNext())
            {
                AgentId id=ids.next();
                Vector3i position=positions.next();
                Agent agent=new Agent(id,position);
                agents.add(agent);
                movableAgents.add(agent);
            }
        }
        for(Vector3i end:endConfiguration)
        {
            Agent lastMoved=null;
            while(blocks.get(end)!=AGENT)
            {
                Array3D<Integer> distance = generateDistanceArray(sizeEnviroment, blocks, end);
                lastMoved=doSingleMove(movableAgents,blocks,distance,result);
            }
            movableAgents.remove(lastMoved);
        }
    }
    public boolean separates(Vector3i agentPosition,Array3D<Integer> blocks)
    {
        class aFunction
        {
            public boolean doDFS(Vector3i pos,Array3D<Boolean> checked,Array3D<Integer> blocks)
            {
                if(checked.isInBound(pos) && !checked.get(pos) && blocks.get(pos)==AGENT)
                {
                    checked.set(pos,true);
                    for(Vector3i toUse : PathFindingAlgorithm.getAdiacentPosition(agentPosition))
                    {
                        while(blocks.isInBound(toUse) && blocks.get(toUse)==OBSTACLE)
                        {
                            toUse.y++;
                        }
                        doDFS(toUse,checked,blocks);
                    }
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        aFunction f=new aFunction();
        boolean oneDFS=false;
        Array3D<Boolean> checked=new Array3D<Boolean>(blocks.size(),false);
        checked.set(agentPosition,true);
        for(Vector3i toUse : PathFindingAlgorithm.getAdiacentPosition(agentPosition))
        {
            if(!oneDFS)
            {
                if(f.doDFS(toUse,checked,blocks))
                {
                    oneDFS=true;
                }
            }
            else
            {
                if(f.doDFS(toUse,checked,blocks))
                {
                    return true;
                }
            }
        }
        return false;
    }
    private int turn=0;
    public Agent doSingleMove(List<Agent> movableAgents,Array3D<Integer> blocks,Array3D<Integer> distance,List<List<Movment<AgentId>>> result)
    {
        Agent globalToMove=null;
        Vector3i globalTo=null;
        int globalLength = -1;
        turn++;
        for(Agent agent:movableAgents)
        {
            blocks.set(agent.pos,EMPTY);
            Vector3i localTo=null;
            int localLength=Integer.MAX_VALUE;
            if (hasAdiacentAgent(agent.pos,blocks) &&
                    (!blocks.isInBound(PathFindingAlgorithm.above(agent.pos)) || blocks.get(PathFindingAlgorithm.above(agent.pos))!=AGENT) &&
                    !separates(agent.pos,blocks))
            {
                for(Vector3i toUse:PathFindingAlgorithm.getAdiacentPosition(agent.pos))
                {
                    if (blocks.isInBound(toUse) && blocks.get(toUse) == AGENT &&
                            blocks.get(PathFindingAlgorithm.above(agent.pos))==EMPTY)
                    {
                        toUse.y++;
                    }
                    while (blocks.isInBound(PathFindingAlgorithm.below(toUse)) &&
                            blocks.get(PathFindingAlgorithm.below(toUse)) == EMPTY)
                    {
                        toUse.y--;
                    }
                    if (blocks.isInBound(toUse) &&
                            hasAdiacentAgent(toUse,blocks) && //maeby to change
                            blocks.get(toUse) == EMPTY &&
                            distance.get(toUse) != Integer.MAX_VALUE &&
                            localLength > distance.get(toUse))
                    {
                        localLength = distance.get(toUse);
                        localTo = toUse;
                    }
                }
            }
            if(localLength!=Integer.MAX_VALUE && localLength>globalLength)
            {
                globalToMove=agent;
                globalLength=localLength;
                globalTo=localTo;
            }
            blocks.set(agent.pos,AGENT);
        }
        List<Movment<AgentId>> toAdd=new LinkedList<>();
        toAdd.add(new Movment<AgentId>(globalToMove.id,new Vector3i(globalToMove.pos),new Vector3i(globalTo)));
        result.add(toAdd);
        blocks.set(globalToMove.pos,EMPTY);
        blocks.set(globalTo,AGENT);
        globalToMove.pos=globalTo;
        return globalToMove;
    }
    public boolean hasAdiacentAgent(Vector3i agent,Array3D<Integer> obstacles)
    {
        for(Vector3i toUse:PathFindingAlgorithm.getAdiacentPosition(agent))
        {
            if(obstacles.isInBound(toUse) && obstacles.get(toUse)==AGENT)
            {
                return true;
            }
        }
        {
            Vector3i toUse=PathFindingAlgorithm.above(agent);
            if(obstacles.isInBound(toUse) && obstacles.get(toUse)==AGENT)
            {
                return true;
            }
        }
        {
            Vector3i toUse=PathFindingAlgorithm.below(agent);
            if(obstacles.isInBound(toUse) && obstacles.get(toUse)==AGENT)
            {
                return true;
            }
        }
        return false;
    }
    public static Array3D<Integer> generateDistanceArray(Vector3i sizeEnviroment,Array3D<Integer> blocks,Vector3i end)
    {
        Array3D<Integer> result= new Array3D<Integer>(sizeEnviroment,Integer.MAX_VALUE);
        Queue<Vector3i> first=new LinkedList<>();
        Queue<Vector3i> second=new LinkedList<>();
        int length=1;
        first.add(end);
        result.set(end,0);
        do
        {
            do
            {
                bfsOneStep(second,first.remove(),result,blocks,length);
            }
            while(!first.isEmpty());
            first=second;
            second=new LinkedList<>();
            length++;
        }
        while(!first.isEmpty());
        return result;
    }
    public static void bfsOneStep(Queue<Vector3i>toAdd,Vector3i v,Array3D<Integer> result,Array3D<Integer> blocks,int length)
    {
        if(v.y>0 && blocks.get(new Vector3i(v.x,v.y-1,v.z))==EMPTY)
        {

            Vector3i toUse=PathFindingAlgorithm.below(v);
            if(result.isInBound(toUse) && result.get(toUse)==Integer.MAX_VALUE)
            {
                result.set(toUse,length);
                toAdd.add(toUse);
            }
        }
        {
            List<Vector3i> positions=PathFindingAlgorithm.getAdiacentPosition(v);
            for(Vector3i toUse:positions)
            {
                while(blocks.isInBound(toUse) && blocks.get(toUse)!=EMPTY)
                {
                    toUse.y++;
                }
                if(blocks.isInBound(PathFindingAlgorithm.below(toUse)) && blocks.get(PathFindingAlgorithm.below(toUse))==EMPTY)
                {
                    toUse.y--;
                }
                if(result.isInBound(toUse) && result.get(toUse)==Integer.MAX_VALUE)
                {
                    result.set(toUse,length);
                    toAdd.add(toUse);
                }
            }
        }
    }

    public static Array3D<Integer> generateBlockArray(Vector3i sizeEnviroment,List<Vector3i> startConfiguration,List<Vector3i> obstacle)
    {
        Array3D<Integer> result=new Array3D<>(sizeEnviroment,EMPTY);
        for(Vector3i v:startConfiguration)
        {
            result.set(v,AGENT);
        }
        for(Vector3i v:obstacle)
        {
            result.set(v,OBSTACLE);
        }
        return result;
    }
}
