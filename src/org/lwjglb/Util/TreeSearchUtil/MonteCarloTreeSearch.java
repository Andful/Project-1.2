package org.lwjglb.Util.TreeSearchUtil;

import org.joml.Vector3d;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.lwjglb.Util.GridFrame;

import static java.lang.Math.*;

/**
 * Created by Andrea Nardi on 5/22/2017.
 */
public class MonteCarloTreeSearch<Node> extends TreeSearch<Node>
{
    private FunctionSet<Node> fs;
    private final double maxWeight;
    MonteCarloTreeSearch(double maxWeight)
    {
        this.maxWeight=maxWeight;
    }
    class TreeNode
    {
        WeightedNode<Node> wn;
        List<TreeNode> children;
        double weight;
        long tryes;
        TreeNode(WeightedNode<Node> wn)
        {
            this.wn=wn;
        }
        boolean isExpandableLeafe() {return children==null;}
        boolean isLeafe() {return children.isEmpty();}
        public void expand()
        {
            List<WeightedNode<Node>> wns=fs.expand(this.wn);
            children=new LinkedList<TreeNode>();
            for(WeightedNode<Node> wn:wns)
            {
                children.add(new TreeNode(wn));
            }
        }
        public void goDeeper()
        {
            if(children==null)
            {
                expand();
                this.goDeeper(10);
            }
            if(children.isEmpty())
            {
                tryes+=1;
                weight=wn.weight;
                return;
            }
            double t=0;
            for(TreeNode child:children)
            {
                t+=child.tryes;
            }
            TreeNode toGoDeeperWith=getNextNode(children,t);
            toGoDeeperWith.goDeeper(10);
            weight=0;
            tryes=0;
            for(TreeNode child:children)
            {
                weight+=child.weight;
                tryes+=child.tryes;
            }
        }
        public void goDeeper(int n)
        {
            if(n==-1)
            {
                tryes += 1;
                weight = wn.weight;
                return;
            }
            if (children == null)
            {
                expand();
            }
            if (children.isEmpty())
            {
                tryes += 1;
                weight = wn.weight;
                return;
            }
            double t = 0;
            for (TreeNode child : children)
            {
                t += child.tryes;
            }
            TreeNode toGoDeeperWith = children.get((int) (random() * children.size()));
            toGoDeeperWith.goDeeper(n - 1);
            weight = 0;
            tryes = 0;
            for (TreeNode child : children)
            {
                weight += child.weight;
                tryes += child.tryes;
            }
        }
    }
    public TreeNode getNextNode(List<TreeNode> ltn,double t)
    {
        Iterator<TreeNode> iterator=ltn.iterator();
        TreeNode result=null;
        List<TreeNode> unexplored=new LinkedList<>();
        double maxWight=Integer.MIN_VALUE;
        while(iterator.hasNext())
        {
            TreeNode localResult=iterator.next();
            double localMaxWight=getToReturnWeight(localResult,t);
            if(Double.isNaN(localMaxWight))
            {
                unexplored.add(localResult);
            }
            else
            {
                if (localMaxWight > maxWight)
                {
                    result = localResult;
                    maxWight = localMaxWight;
                }
            }
        }
        for(TreeNode test:ltn)
        {
            System.out.println(getToReturnWeight(test,t));
        }
        System.out.println();
        if(result==null || (random()<0.1 && unexplored.size()>0))
        {
            return unexplored.get((int)(random()*unexplored.size()));
        }
        else
        {
            return result;
        }
    }
    public double getToReturnWeight(TreeNode tn,double t)
    {
        return tn.weight/tn.tryes+sqrt(2)*sqrt(log(t)/tn.tryes);
    }
    public List<Node> getNodes(FunctionSet<Node> fs, WeightedNode<Node> node)
    {
        this.fs=fs;
        TreeNode treeNode=new TreeNode(node);
        treeNode.expand();
        for(int i=0;i<1000;i++)
        {
            treeNode.goDeeper();
        }
        /*for(TreeNode tn:treeNode.children)
        {
            System.out.println(tn.weight+" "+tn.tryes);
        }*/
        List<Node> result=new LinkedList<>();
        TreeNode temp=treeNode;
        while(temp!=null&&!temp.isExpandableLeafe())
        {
            result.add(temp.wn.node);
            Iterator<TreeNode> iter=temp.children.iterator();
            temp=null;
            for(TreeNode tn=iter.next();iter.hasNext();tn=iter.next())
            {
                if(temp==null || (temp.weight/temp.tryes)<(tn.weight/tn.tryes))
                {
                    temp=tn;
                }
            }
        }
        if(temp!=null)
        {
            result.add(temp.wn.node);
        }
        return result;
    }
    public static void main(String[] args)
    {
        class MyNode
        {
            int distance;
            int x;
            int y;
            public int hashCode()
            {
                return 31*x+y;
            }
            public boolean equals(Object o)
            {
                MyNode a=(MyNode)o;
                return (x==a.x)&&(y==a.y);
            }
            public String toString()
            {
                return ("x="+x+" y="+y);
            }
        }
        boolean[][] maze=new boolean[32][32];
        for(int i=0;i<maze.length;i++)
        {
            for(int j=0;j<maze[0].length;j++)
            {
                maze[i][j]=Math.random()>0.80;
            }
        }
        MyNode start=new MyNode();
        start.distance=0;
        start.x=0;
        start.y=0;
        MyNode end=new MyNode();
        end.distance=0;
        end.x=maze.length-1;
        end.y=maze[0].length-1;
        double maxValue=sqrt(end.x*end.x+end.y*end.y);
        TreeSearch.WeightedNode<MyNode> startWN=new TreeSearch.WeightedNode<MyNode>(new MyNode(),maxValue);

        Color[][]  colors=new Color[maze.length][maze[0].length];
        for(int i=0;i<maze.length;i++)
        {
            for(int j=0;j<maze[0].length;j++)
            {
                if(maze[i][j])
                {
                    colors[i][j]=Color.BLACK;
                }
                else
                {
                    colors[i][j]=Color.WHITE;
                }
            }
        }
        JFrame frame=new GridFrame(colors);
        class MyExpandFunction implements TreeSearch.FunctionSet<MyNode>
        {
            @Override
            public List<WeightedNode<MyNode>> expand(WeightedNode<MyNode> from)
            {
                MyNode start=from.node;
                List<WeightedNode<MyNode>> result=new LinkedList<>();
                for(int i=-1;i<=1;i+=2)
                {
                    if(start.x+i>=0 &&
                            start.x+i<maze.length &&
                            !maze[start.x+i][start.y])
                    {
                        MyNode toAdd=new MyNode();
                        toAdd.distance=start.distance+1;
                        toAdd.x=start.x+i;
                        toAdd.y=start.y;
                        double weight=1-Math.sqrt((toAdd.x-end.x)*(toAdd.x-end.x)+(toAdd.y-end.y)*(toAdd.y-end.y))/maxValue;
                        //System.out.println(weight);
                        result.add(new WeightedNode<>(toAdd,weight));
                    }
                    if(start.y+i>=0 &&
                            start.y+i<maze[0].length &&
                            !maze[start.x][start.y+i])
                    {
                        MyNode toAdd=new MyNode();
                        toAdd.distance=start.distance+1;
                        toAdd.x=start.x;
                        toAdd.y=start.y+i;
                        double weight=1-Math.sqrt((toAdd.x-end.x)*(toAdd.x-end.x)+(toAdd.y-end.y)*(toAdd.y-end.y))/maxValue;
                        //System.out.println(weight);
                        result.add(new WeightedNode<>(toAdd,weight));
                    }
                }
                for(WeightedNode<MyNode> a:result)
                {
                    colors[a.node.x][a.node.y]=Color.BLUE;
                    frame.repaint();
                    /*try
                    {
                        Thread.sleep(3);
                    }
                    catch(Exception e)
                    {
                    }*/
                }
                return result;
            }
        }
        List<MyNode> path=new MonteCarloTreeSearch<MyNode>(sqrt(maze.length*maze.length+maze[0].length*maze[0].length)).getNodes(new MyExpandFunction(),startWN);
        if(path!=null)
        {
            for (MyNode node : path)
            {
                colors[node.x][node.y] = Color.GREEN;
            }
        }
        frame.repaint();
    }
}
