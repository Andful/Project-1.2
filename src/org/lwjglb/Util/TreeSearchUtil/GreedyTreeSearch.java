package org.lwjglb.Util.TreeSearchUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Lucas on 20/05/2017.
 */
public class GreedyTreeSearch<Node> extends TreeSearch<Node> {
    public List<Node> getNodes(FunctionSet<Node> fs,WeightedNode<Node> root)
    {
        LinkedList<Node> result=new LinkedList();
        result.add(root.node);
        WeightedNode<Node> temp=root;
        while(true)
        {
            List<WeightedNode<Node>> toCheck=fs.expand(temp);
            if(toCheck.isEmpty())
            {
                break;
            }
            else
            {
                WeightedNode<Node> min=temp;
                for(WeightedNode<Node> wn:toCheck)
                {
                    if(min.weight>wn.weight) {
                        min=wn;
                    }
                }
                if(temp==min)
                {
                    break;
                }
                result.add(min.node);
                temp=min;
            }
        }
        return result;
    }
}
