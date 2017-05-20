package lwjglb.Util.TreeUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Lucas on 20/05/2017.
 */
public class GreedyTreeSearch<Node> extends TreeSearch<Node> {
    private Node root;
    private FunctionSet<Node> fs;
    public GreedyTreeSearch(Node root,FunctionSet<Node> fs){
        this.root = root;
        this.fs = fs;
    }
    public List<Node> getNode(){
        LinkedList<Node> result=new LinkedList();
        result.add(root);
        Node temp=root;
        while(!fs.stop(temp))
        {
            List<WeightedNode<Node>> toCheck=fs.expand(temp);
            if(toCheck.size()==0) {
            return result;
            }
            else
            {
                WeightedNode<Node> max=null;
                for(WeightedNode<Node> wn:toCheck)
                {
                    if(max==null||max.weight>wn.weight) {
                        max=wn;
                    }
                }
                result.add(max.node);
                temp=max.node;
            }
        }
        return result;
    }
}
