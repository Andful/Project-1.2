package org.lwjglb.Util.TreeSearchUtil;

import java.util.List;

/**
 * Created by Lucas on 20/05/2017.
 */
public abstract class  TreeSearch<Node> {

    public static interface FunctionSet<Node> {
        List<WeightedNode<Node>> expand(WeightedNode<Node> node);
    }

    public static class WeightedNode<Node> {
        public double weight;
        public Node node;
        public WeightedNode(Node node, double weight){
            this.node = node;
            this.weight = weight;

        }
    }
    public abstract List<Node> getNodes(FunctionSet<Node> fs,WeightedNode<Node> node);
}
