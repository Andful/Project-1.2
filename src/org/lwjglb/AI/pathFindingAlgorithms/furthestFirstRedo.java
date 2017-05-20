package org.lwjglb.AI.pathFindingAlgorithms;

import org.joml.Vector3i;
import org.lwjglb.Util.Array3D;

import java.util.*;

/**
 * Created by Andrea Nardi on 5/8/2017.
 */
public class furthestFirstRedo<AgentId> implements PathFindingAlgorithm<AgentId>
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
                lastMoved=doSingleMove(movableAgents,blocks,distance,end,result);
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
                if(checked.isInBound(pos) &&
                        !checked.get(pos) &&
                        blocks.get(pos)==AGENT)
                {
                    checked.set(pos,true);
                    for(Vector3i toUse : PathFindingAlgorithm.getAdiacentPosition(pos))
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
    public Agent doSingleMove(List<Agent> movableAgents,Array3D<Integer> blocks,Array3D<Integer> distance,Vector3i endPosition,List<List<Movment<AgentId>>> result)
    {
        PriorityQueue<Agent> priorityQueueToMove=getToMovePriorityQueue(movableAgents,endPosition,distance);
        Agent globalToMove=null;
        Vector3i globalTo=null;
        int globalLength=-1;
        while(!priorityQueueToMove.isEmpty())
        {
            Agent localToMove=priorityQueueToMove.remove();
            blocks.set(localToMove.pos,EMPTY);
            Vector3i localTo=null;
            int localLength=Integer.MAX_VALUE;
            if(!separates(localToMove.pos,blocks))
            {
                List<Vector3i> adiacentPosition = PathFindingAlgorithm.getAdiacentPosition(localToMove.pos);
                getLowestPosition(blocks, adiacentPosition);
                for (Vector3i v : adiacentPosition)
                {
                    if (blocks.isInBound(v) &&
                            blocks.get(v)==EMPTY &&
                            localLength>distance.get(v) &&
                            hasAdiacentAgent(v,blocks))
                    {
                        localTo=v;
                        localLength=distance.get(v);
                    }
                }
            }
            if(localLength!=Integer.MAX_VALUE && globalLength<localLength && localLength<findDistance(localToMove.pos,endPosition,blocks))
            {
                globalToMove=localToMove;
                globalTo=localTo;
                globalLength=localLength;
            }
            blocks.set(localToMove.pos,AGENT);
        }
        blocks.set(globalToMove.pos,EMPTY);
        blocks.set(globalTo,AGENT);
        LinkedList<Movment<AgentId>> a=new LinkedList<>();
        a.add(new Movment<AgentId>(globalToMove.id,globalToMove.pos,globalTo));
        result.add(a);
        globalToMove.pos=globalTo;
        return globalToMove;
    }
    public int findDistance(Vector3i start,Vector3i end,Array3D<Integer> blocks)
    {
        class Fun
        {
            public void bfsOneStep(Queue<Vector3i>toAdd,Vector3i v,Array3D<Integer> result,Array3D<Integer> blocks,int length)
            {
                if(v.y>0 && blocks.get(new Vector3i(v.x,v.y-1,v.z))!=OBSTACLE)
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
                        while(blocks.isInBound(toUse) && blocks.get(toUse)==OBSTACLE)
                        {
                            toUse.y++;
                        }
                        if(blocks.isInBound(PathFindingAlgorithm.below(toUse)) && blocks.get(PathFindingAlgorithm.below(toUse))!=OBSTACLE)
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
        }
        Array3D<Integer> result= new Array3D<Integer>(blocks.size(),Integer.MAX_VALUE);
        Queue<Vector3i> first=new LinkedList<>();
        Queue<Vector3i> second=new LinkedList<>();
        int length=1;
        first.add(end);
        result.set(end,0);
        do
        {
            do
            {
                new Fun().bfsOneStep(second,first.remove(),result,blocks,length);
            }
            while(!first.isEmpty());
            first=second;
            second=new LinkedList<>();
            length++;
        }
        while(!first.isEmpty());
        return result.get(start);
    }
    public void getLowestPosition(Array3D<Integer> blocks,List<Vector3i> a)
    {
        for(Vector3i v:a)
        {
            if(blocks.isInBound(v))
            {
                if (blocks.get(v) == AGENT)
                {
                    v.y++;
                }
                while (blocks.isInBound(PathFindingAlgorithm.below(v)) && blocks.get(PathFindingAlgorithm.below(v)) == EMPTY)
                {
                    v.y--;
                }
            }
        }
    }
    public PriorityQueue<Agent> getToMovePriorityQueue(List<Agent> agents,Vector3i endPosition,Array3D<Integer> distance)
    {
        PriorityQueue<Agent> result=new PriorityQueue<>(agents.size(),
        (Agent a,Agent b)->{
            double lengthA=new Vector3i(a.pos).sub(endPosition).length();
            double lengthB=new Vector3i(b.pos).sub(endPosition).length();
            if(lengthA>lengthB)
            {
                return -1;
            }
            if(lengthA<lengthB)
            {
                return 1;
            }
            return 0;
        });
        for(Agent agent:agents)
        {
            result.add(agent);
        }
        return result;
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
