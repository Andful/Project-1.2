package org.lwjglb.AI;

import org.joml.Vector3i;

import java.util.*;

/**
 * Created by Andrea Nardi on 4/29/2017.
 */
public class VectorizedEnviromentGreedyPathFinding<AgentId> implements PathFindingAlgorithm<AgentId>
{
    public static final int OBSTACLE=-2;
    public static final int EMPTY=-1;
    public void computeMovments(Vector3i sizeEnviroment, List<Vector3i> sc, List<AgentId> agentIds, List<Vector3i> endConfiguration, List<Vector3i> obstacles, List<List<Movment<AgentId>>> result)
    {
        List<Vector3i> allAgentsPosition=new LinkedList<>();
        List<Vector3i> startConficuration=new LinkedList<Vector3i>()
        {
            {
                for(Vector3i v:sc)
                {
                    Vector3i temp=new Vector3i(v);
                    add(temp);
                    allAgentsPosition.add(temp);
                }
            }
        };
        List<Agent> agents=new LinkedList<>();
        {
            Iterator<Vector3i> iter=startConficuration.iterator();
            for(AgentId id:agentIds)
            {
                agents.add(new Agent(id,iter.next()));
            }
        }
        boolean[][][] agentsPosition = new boolean[sizeEnviroment.x][sizeEnviroment.y][sizeEnviroment.z];
        for (Vector3i v : allAgentsPosition)
        {
            agentsPosition[v.x][v.y][v.z] = true;
        }
        for(Vector3i endPoint:endConfiguration)
        {
            int[][][] enviroment=getLengthEnviroment(sizeEnviroment,obstacles,endPoint);
            Agent head = null;
            {
                int distance=Integer.MAX_VALUE;
                for(Agent a:agents)
                {
                    if(enviroment[a.pos.x][a.pos.y][a.pos.z]<distance && enviroment[a.pos.x][a.pos.y][a.pos.z]>=0)
                    {
                        head=a;
                        distance=enviroment[a.pos.x][a.pos.y][a.pos.z];
                    }
                }

            }
            if(numberMovableAgents(agents,agentsPosition)>=2)
            {
                while(!head.pos.equals(endPoint))
                {
                    Vector3i arrive = getArrivePosition(enviroment, agentsPosition, head.pos);
                    Queue<Agent> tails = getTails(enviroment, agentsPosition, agents, head);

                    Agent tail = null;
                    try
                    {
                        for (tail = tails.remove(); !pathFindSingleAgentBFS(enviroment, agentsPosition, tail, arrive, result); tail = tails.remove());
                    } catch (NoSuchElementException e){
                        pathFindSingleAgentBFS(enviroment,agentsPosition,head,endPoint,result);
                        head=tail;
                    }
                    head = tail;
                }
                agents.remove(head);
            }
            else
            {
                pathFindSingleAgentBFS(enviroment,agentsPosition,findMovableAgent(agents,agentsPosition),endPoint,result);
            }
        }
    }
    private Agent findMovableAgent(List<Agent> agents,boolean[][][]agentsPosition)
    {
        for(Agent agent:agents)
        {
            if(hasAdiacentAgent(agentsPosition,agent.pos))
            {
                return agent;
            }
        }
        return null;
    }
    private int numberMovableAgents(List<Agent> agents,boolean[][][]agentsPosition)
    {
        int result=0;
        for(Agent agent:agents)
        {
            if(hasAdiacentAgent(agentsPosition,agent.pos))
            {
                result++;
            }
        }
        return result;
    }
    private boolean pathFindSingleAgentBFS(int[][][] enviroment,boolean [][][] agents,Agent agent,Vector3i end,List<List<Movment<AgentId>>> toAdd)
    {
        Vector3i start=new Vector3i(agent.pos);
        agents[agent.pos.x][agent.pos.y][agent.pos.z]=false;
        Vector3i[][][] cameFrom=new Vector3i[enviroment.length][enviroment[0].length][enviroment[0][0].length];
        cameFrom[agent.pos.x][agent.pos.y][agent.pos.z]=new Vector3i(-1,-1,-1);
        Queue<Vector3i> first=new LinkedList<>();
        Queue<Vector3i> second=new LinkedList<>();
        first.add(agent.pos);
        do
        {
            do
            {
                Vector3i current=first.remove();
                if(hasAdiacentAgent(agents,current))
                {
                    for (int p = -1; p <= 1; p += 2)
                    {
                        {
                            Vector3i toCheck=new Vector3i(current.x + p, current.y, current.z);
                            if(isInBounds(enviroment,toCheck) && enviroment[toCheck.x][toCheck.y][toCheck.z]!=OBSTACLE)
                            {
                                Vector3i toUse = findLowestPoint(new Vector3i(current.x + p, current.y, current.z), enviroment, agents);
                                if (toUse != null && cameFrom[toUse.x][toUse.y][toUse.z] == null)
                                {
                                    cameFrom[toUse.x][toUse.y][toUse.z] = current;
                                    second.add(toUse);
                                }
                            }
                        }
                        {
                            Vector3i toCheck=new Vector3i(current.x , current.y, current.z+p);
                            if(isInBounds(enviroment,toCheck) && enviroment[toCheck.x][toCheck.y][toCheck.z]!=OBSTACLE)
                            {
                                Vector3i toUse = findLowestPoint(new Vector3i(current.x, current.y, current.z + p), enviroment, agents);
                                if (toUse != null && cameFrom[toUse.x][toUse.y][toUse.z] == null)
                                {
                                    cameFrom[toUse.x][toUse.y][toUse.z] = current;
                                    second.add(toUse);
                                }
                            }
                        }
                    }
                }
            }
            while(!first.isEmpty());
            first=second;
            second=new LinkedList<>();
        }
        while(!(first.isEmpty()||first.contains(end)));
        if(first.isEmpty()){return false;}
        LinkedList<Vector3i> path=new LinkedList<>();
        Vector3i current=end;
        while(current.x!=-1 || current.y!=-1 || current.z!=-1)
        {
            path.add(current);
            current=cameFrom[current.x][current.y][current.z];
        }

        agents[end.x][end.y][end.z]=true;
        List<Vector3i> result=new ArrayList<>(path.size()-1);
        for(int i=0;i<path.size()-1;i++)
        {
            result.add(null);
        }
        int i=result.size()-1;
        for(Iterator<Vector3i> it=path.iterator();i>=0;i--)
        {
            result.set(i,it.next());
        }
        Vector3i oldV=start;
        for(Vector3i v:result)
        {
            LinkedList<Movment<AgentId>> list= new LinkedList<Movment<AgentId>>();
            list.add(new Movment<AgentId>(agent.id,oldV, v));
            toAdd.add(list);
            oldV=v;
        }
        agent.pos.x=end.x;
        agent.pos.y=end.y;
        agent.pos.z=end.z;
        return true;
    }
    private boolean hasAdiacentAgent(boolean[][][] agents,Vector3i agentPosition)
    {
        for(int i=-1;i<=1;i+=2)
        {
            {
                Vector3i toUse=new Vector3i(agentPosition.x+i,agentPosition.y,agentPosition.z);
                if(isInBounds(agents,toUse) && agents[toUse.x][toUse.y][toUse.z])
                {
                    return true;
                }
            }
            {
                Vector3i toUse=new Vector3i(agentPosition.x,agentPosition.y,agentPosition.z+i);
                if(isInBounds(agents,toUse) && agents[toUse.x][toUse.y][toUse.z])
                {
                    return true;
                }
            }
        }
        {
            Vector3i toUse=new Vector3i(agentPosition.x,agentPosition.y-1,agentPosition.z);
            if(isInBounds(agents,toUse) && agents[toUse.x][toUse.y][toUse.z])
            {
                return true;
            }
        }
        return false;
    }
    private static <E> boolean isInBounds(E[][][] arr,Vector3i v)
    {
        return (v.x>=0 && v.x<arr.length && v.y>=0 && v.y<arr[0].length && v.z>=0 && v.z<arr[0][0].length);
    }
    private static boolean isInBounds(int[][][] arr,Vector3i v)
    {
        return (v.x>=0 && v.x<arr.length && v.y>=0 && v.y<arr[0].length && v.z>=0 && v.z<arr[0][0].length);
    }
    private static boolean isInBounds(boolean[][][] arr,Vector3i v)
    {
        return (v.x>=0 && v.x<arr.length && v.y>=0 && v.y<arr[0].length && v.z>=0 && v.z<arr[0][0].length);
    }
    private Queue<Agent> getTails(int[][][] enviroment,boolean[][][] agensPosition,List<Agent> agents,Agent head)
    {
        LinkedList<Agent> result=new LinkedList<>();
        HashMap<Vector3i,Boolean> notSeparatesMap=new HashMap<>();
        for(Agent agent:agents)
        {
            if (agent != head &&
                    !(agent.pos.y+1<agensPosition[0].length && agensPosition[agent.pos.x][agent.pos.y+1][agent.pos.z]) &&
                    hasAdiacentAgent(agensPosition,agent.pos))
            {
                ListIterator<Agent> insert = result.listIterator();
                while(true)
                {
                    boolean notSeparates = !agentSeparates(enviroment,agensPosition, agent.pos);
                    notSeparatesMap.put(agent.pos, notSeparates);
                    if (insert.hasNext())
                    {
                        Agent toCompare = insert.next();
                        if (notSeparates == notSeparatesMap.get(toCompare.pos))
                        {
                            if (enviroment[agent.pos.x][agent.pos.y][agent.pos.z] >
                                    enviroment[toCompare.pos.x][toCompare.pos.y][toCompare.pos.z])
                            {
                                insert.previous();
                                insert.add(agent);
                                insert.next();
                                break;
                            }
                        }
                        else
                        {
                            if (notSeparates)
                            {
                                insert.previous();
                                insert.add(agent);
                                insert.next();
                                break;
                            }
                        }
                    }
                    else
                    {
                        insert.add(agent);
                        break;
                    }
                }
            }
        }
        return result;
    }
    private static boolean agentSeparates(int[][][] enviroment,boolean[][][] agents,Vector3i pos)
    {
        boolean[][][] checked=new boolean[enviroment.length][enviroment[0].length][enviroment[0][0].length];
        checked[pos.x][pos.y][pos.z]=true;
        boolean firstDfs=false;
        for(int d=-1;d<=1;d+=2)
        {
            {
                Vector3i toUse = new Vector3i(pos.x + d, pos.y, pos.z);
                if (isInBounds(enviroment, toUse))
                {
                    if (enviroment[toUse.x][toUse.y][toUse.z] == OBSTACLE)
                    {
                        while (isInBounds(enviroment, toUse) && enviroment[toUse.x][toUse.y][toUse.z] == OBSTACLE)
                        {
                            toUse.y++;
                        }
                        if (isInBounds(enviroment, toUse) && agents[toUse.x][toUse.y][toUse.z])
                        {
                            if(doDfs(agents, enviroment, checked, toUse))
                            {
                                if(firstDfs)
                                {
                                    return true;
                                }
                                else
                                {
                                    firstDfs=true;
                                }
                            }
                        }
                    } else
                    {
                        while (isInBounds(enviroment, toUse) && !agents[toUse.x][toUse.y][toUse.z])
                        {
                            toUse.y--;
                        }
                        if (isInBounds(enviroment, toUse) && agents[toUse.x][toUse.y][toUse.z])
                        {
                            if(doDfs(agents, enviroment, checked, toUse))
                            {
                                if(firstDfs)
                                {
                                    return true;
                                }
                                else
                                {
                                    firstDfs=true;
                                }
                            }
                        }
                    }
                }
            }
            {
                Vector3i toUse = new Vector3i(pos.x, pos.y, pos.z+d);
                if (isInBounds(enviroment, toUse))
                {
                    if (enviroment[toUse.x][toUse.y][toUse.z] == OBSTACLE)
                    {
                        while (isInBounds(enviroment, toUse) && enviroment[toUse.x][toUse.y][toUse.z] == OBSTACLE)
                        {
                            toUse.y++;
                        }
                        if (isInBounds(enviroment, toUse) && agents[toUse.x][toUse.y][toUse.z])
                        {
                            if(doDfs(agents, enviroment, checked, toUse))
                            {
                                if(firstDfs)
                                {
                                    return true;
                                }
                                else
                                {
                                    firstDfs=true;
                                }
                            }
                        }
                    } else
                    {
                        while (isInBounds(enviroment, toUse) && !agents[toUse.x][toUse.y][toUse.z])
                        {
                            toUse.y--;
                        }
                        if (isInBounds(enviroment, toUse) && agents[toUse.x][toUse.y][toUse.z])
                        {
                            if(doDfs(agents, enviroment, checked, toUse))
                            {
                                if(firstDfs)
                                {
                                    return true;
                                }
                                else
                                {
                                    firstDfs=true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    private static boolean doDfs(boolean[][][] agents,int[][][] enviroment,boolean[][][] checked,Vector3i toCheck)
    {
        if(isInBounds(agents,toCheck))
        {
            if(agents[toCheck.x][toCheck.y][toCheck.z] && !checked[toCheck.x][toCheck.y][toCheck.z])
            {
                checked[toCheck.x][toCheck.y][toCheck.z] = true;
                for(int d=-1;d<=1;d+=2)
                {
                    {
                        Vector3i toUse = new Vector3i(toCheck.x + d, toCheck.y, toCheck.z);
                        if (isInBounds(enviroment, toUse))
                        {
                            if (enviroment[toUse.x][toUse.y][toUse.z] == OBSTACLE)
                            {
                                while (isInBounds(enviroment, toUse) && enviroment[toUse.x][toUse.y][toUse.z] == OBSTACLE)
                                {
                                    toUse.y++;
                                }
                                if (isInBounds(enviroment, toUse) && agents[toUse.x][toUse.y][toUse.z])
                                {
                                    doDfs(agents, enviroment, checked, toUse);
                                }
                            } else
                            {
                                while (isInBounds(enviroment, toUse) && !agents[toUse.x][toUse.y][toUse.z])
                                {
                                    toUse.y--;
                                }
                                if (isInBounds(enviroment, toUse) && agents[toUse.x][toUse.y][toUse.z])
                                {
                                    doDfs(agents, enviroment, checked, toUse);
                                }
                            }
                        }
                    }
                    {
                        Vector3i toUse = new Vector3i(toCheck.x, toCheck.y, toCheck.z+d);
                        if (isInBounds(enviroment, toUse))
                        {
                            if (enviroment[toUse.x][toUse.y][toUse.z] == OBSTACLE)
                            {
                                while (isInBounds(enviroment, toUse) && enviroment[toUse.x][toUse.y][toUse.z] == OBSTACLE)
                                {
                                    toUse.y++;
                                }
                                if (isInBounds(enviroment, toUse) && agents[toUse.x][toUse.y][toUse.z])
                                {
                                    doDfs(agents, enviroment, checked, toUse);
                                }
                            } else
                            {
                                while (isInBounds(enviroment, toUse) && !agents[toUse.x][toUse.y][toUse.z])
                                {
                                    toUse.y--;
                                }
                                if (isInBounds(enviroment, toUse) && agents[toUse.x][toUse.y][toUse.z])
                                {
                                    doDfs(agents, enviroment, checked, toUse);
                                }
                            }
                        }
                    }
                }
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
    private Vector3i getArrivePosition(int[][][] enviroment,boolean[][][] agents,Vector3i head)
    {
        if(head.equals(new Vector3i(3,0,6)))
        {
            System.out.println("hi");
        }
        Vector3i result=null;
        int attachedDistance=enviroment[head.x][head.y][head.z];
        int unattachedDistace=enviroment[head.x][head.y][head.z];
        boolean isAttached=false;
        for(int p=-1;p<=1;p+=2)
        {
            {
                Vector3i toUse=findLowestPoint(new Vector3i(head.x+p,head.y,head.z),enviroment,agents);
                if(toUse!=null && isInBounds(enviroment,toUse) && enviroment[toUse.x][toUse.y][toUse.z]!=EMPTY)
                {
                    boolean tempIsAttached = hasAdiacentAgent(agents, toUse);
                    if (isAttached)
                    {
                        if (attachedDistance > enviroment[toUse.x][toUse.y][toUse.z])
                        {
                            result = toUse;
                            attachedDistance = enviroment[toUse.x][toUse.y][toUse.z];
                        }
                    }
                    else
                    {
                        if(tempIsAttached && attachedDistance>enviroment[toUse.x][toUse.y][toUse.z])
                        {
                            isAttached=true;
                            attachedDistance=enviroment[toUse.x][toUse.y][toUse.z];
                            result=toUse;
                        }
                        else if(!tempIsAttached && unattachedDistace>enviroment[toUse.x][toUse.y][toUse.z])
                        {
                            unattachedDistace=enviroment[toUse.x][toUse.y][toUse.z];
                            result=toUse;
                        }
                    }
                }
            }
            {
                Vector3i toUse=findLowestPoint(new Vector3i(head.x,head.y,head.z+p),enviroment,agents);
                if(toUse!=null && isInBounds(enviroment,toUse) && enviroment[toUse.x][toUse.y][toUse.z]!=EMPTY)
                {
                    boolean tempIsAttached = hasAdiacentAgent(agents, toUse);
                    if (isAttached)
                    {
                        if (attachedDistance > enviroment[toUse.x][toUse.y][toUse.z])
                        {
                            result = toUse;
                            attachedDistance = enviroment[toUse.x][toUse.y][toUse.z];
                        }
                    }
                    else
                    {
                        if(tempIsAttached && attachedDistance>enviroment[toUse.x][toUse.y][toUse.z])
                        {
                            isAttached=true;
                            attachedDistance=enviroment[toUse.x][toUse.y][toUse.z];
                            result=toUse;
                        }
                        else if(!tempIsAttached && unattachedDistace>enviroment[toUse.x][toUse.y][toUse.z])
                        {
                            unattachedDistace=enviroment[toUse.x][toUse.y][toUse.z];
                            result=toUse;
                        }
                    }
                }
            }
        }
        {
            Vector3i toUse=new Vector3i(head.x,head.y+1,head.z);
            if(toUse!=null && isInBounds(enviroment,toUse) && enviroment[toUse.x][toUse.y][toUse.z]!=EMPTY)
            {
                boolean tempIsAttached = hasAdiacentAgent(agents, toUse);
                if (isAttached)
                {
                    if (attachedDistance > enviroment[toUse.x][toUse.y][toUse.z])
                    {
                        result = toUse;
                        attachedDistance = enviroment[toUse.x][toUse.y][toUse.z];
                    }
                }
                else
                {
                    if(tempIsAttached && attachedDistance>enviroment[toUse.x][toUse.y][toUse.z])
                    {
                        isAttached=true;
                        attachedDistance=enviroment[toUse.x][toUse.y][toUse.z];
                        result=toUse;
                    }
                    else if(!tempIsAttached && unattachedDistace>enviroment[toUse.x][toUse.y][toUse.z])
                    {
                        unattachedDistace=enviroment[toUse.x][toUse.y][toUse.z];
                        result=toUse;
                    }
                }
            }
        }
        System.out.println("headDistance:"+enviroment[head.x][head.y][head.z]);
        System.out.println("attachedDistance:"+attachedDistance);
        System.out.println("unattachedDistace:"+unattachedDistace);
        return result;
    }
    private static Vector3i findLowestPoint(Vector3i from,int[][][] enviroment,boolean[][][] agents)
    {
        Vector3i toUse=new Vector3i(from.x,from.y+1,from.z);
        if(!isInBounds(enviroment,toUse) || enviroment[toUse.x][toUse.y][toUse.z]==OBSTACLE || agents[toUse.x][toUse.y][toUse.z])
        {
            toUse=new Vector3i(from);
            if(!isInBounds(enviroment,toUse) || enviroment[toUse.x][toUse.y][toUse.z]==OBSTACLE || agents[toUse.x][toUse.y][toUse.z])
            {
                return null;
            }
        }
        while(isInBounds(enviroment,toUse) && enviroment[toUse.x][toUse.y][toUse.z]!=OBSTACLE && !agents[toUse.x][toUse.y][toUse.z])
        {
            toUse.y--;
        }
        toUse.y++;
        return toUse;
    }
    private static Vector3i getAverage(List<Vector3i> list)
    {
        Vector3i sum=new Vector3i();
        for(Vector3i v:list)
        {
            sum.add(v);
        }
        return new Vector3i((int)Math.round((sum.x*1.0)/list.size()),
                (int)Math.round((sum.y*1.0)/list.size()),
                (int)Math.round((sum.z*1.0)/list.size()));
    }
    public static int[][][] getLengthEnviroment(Vector3i sizeEnviroment,List<Vector3i> obstacles,Vector3i startPoint)
    {
        final int[][][] result=new int[sizeEnviroment.x][sizeEnviroment.y][sizeEnviroment.z];
        for(int x=0;x<result.length;x++)
        {
            for(int y=0;y<result[0].length;y++)
            {
                for(int z=0;z<result[0][0].length;z++)
                {
                    result[x][y][z]=EMPTY;
                }
            }
        }
        for(Vector3i v:obstacles)
        {
            result[v.x][v.y][v.z]=OBSTACLE;
        }
        int length=1;
        Queue<Vector3i> first=new LinkedList<>();
        Queue<Vector3i> second=new LinkedList<>();

        first.add(startPoint);
        result[startPoint.x][startPoint.y][startPoint.z]=0;
        do
        {
            do
            {
                oneBfsStep(result,first.remove(),length,second);
            }
            while(!first.isEmpty());
            first=second;
            second=new LinkedList<Vector3i>()
            {
                @Override
                public boolean add(Vector3i vector3i)
                {
                    if(vector3i.equals(new Vector3i(3,2,7)))
                    {
                        System.out.println("hi");
                    }
                    return super.add(vector3i);
                }
            };
            length++;
        }
        while(!first.isEmpty());
        return result;
    }
    private static void oneBfsStep(int[][][] enviroment,Vector3i from,int length,Queue<Vector3i> toAdd)
    {
        if(from.y>0 && enviroment[from.x][from.y-1][from.z]!=OBSTACLE)
        {
            if(enviroment[from.x][from.y-1][from.z]==EMPTY)
            {
                enviroment[from.x][from.y-1][from.z]=length;
                toAdd.add(new Vector3i(from.x, from.y - 1, from.z));
            }
        }
        else
        {
            for (int d = -1; d <= 1; d += 2)
            {
                {
                    Vector3i toUse = findHighestPoint(new Vector3i(from.x+d, from.y, from.z), enviroment);
                    if(toUse!=null)
                    {
                        if (enviroment[toUse.x][toUse.y][toUse.z] == EMPTY)
                        {
                            enviroment[toUse.x][toUse.y][toUse.z] = length;
                            toAdd.add(toUse);
                        }
                    }
                }
                {
                    Vector3i toUse = findHighestPoint(new Vector3i(from.x, from.y, from.z+d), enviroment);
                    if(toUse!=null)
                    {
                        if (enviroment[toUse.x][toUse.y][toUse.z] == EMPTY)
                        {
                            enviroment[toUse.x][toUse.y][toUse.z] = length;
                            toAdd.add(toUse);
                        }
                    }
                }
            }
        }
    }
    private static Vector3i findHighestPoint(Vector3i v,int[][][] enviroment)
    {
        Vector3i result=new Vector3i(v.x,v.y,v.z);
        if(!isInBounds(enviroment,result))
        {
            return null;
        }
        if(enviroment[result.x][result.y][result.z]!=OBSTACLE && v.y>0 && enviroment[result.x][result.y-1][result.z]!=OBSTACLE)
        {
            result.y--;
            return result;
        }
        while (isInBounds(enviroment, result) && enviroment[result.x][result.y][result.z]==OBSTACLE)
        {
            result.y++;
        }
        if(!isInBounds(enviroment,result))
        {
            return null;
        }
        else
        {
            return result;
        }
    }
    private class Agent
    {
        final AgentId id;
        final Vector3i pos;
        Agent(AgentId id,Vector3i pos)
        {
            this.id=id;
            this.pos=pos;
        }
    }
    public static void main(String[] args)
    {
    }
}
