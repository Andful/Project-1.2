package org.lwjglb.game;

import org.joml.Matrix4f;
import org.joml.Vector3i;
import org.lwjglb.AI.PathFindingAlgorithm;
import org.lwjglb.engine.GameItem;
import org.lwjglb.engine.graph.Mesh;
import org.lwjglb.engine.graph.ShaderProgram;
import org.lwjglb.engine.graph.Texture;
import org.lwjglb.engine.graph.Transformation;

import java.util.List;

/**
 * Created by Andrea Nardi on 5/levels.2/2017.
 */
public class AgentDrawer extends GameItem
{
    private List<Vector3i> agentsPosition;
    private List<List<PathFindingAlgorithm.Movment<Integer>>> movmentList;
    private int index=-1;
    public AgentDrawer(List<Vector3i> agentPositions,List<List<PathFindingAlgorithm.Movment<Integer>>> movmentList)
    {
        super(getAgentMesh());
        this.agentsPosition=agentPositions;
        this.movmentList=movmentList;
    }
    public static Mesh getAgentMesh()
    {
        float[] positions = new float[]{
                // V0
                -0.5f, 0.5f, 0.5f,
                // V1
                -0.5f, -0.5f, 0.5f,
                // V2
                0.5f, -0.5f, 0.5f,
                // V3
                0.5f, 0.5f, 0.5f,
                // V4
                -0.5f, 0.5f, -0.5f,
                // V5
                0.5f, 0.5f, -0.5f,
                // V6
                -0.5f, -0.5f, -0.5f,
                // V7
                0.5f, -0.5f, -0.5f,
                // For text coords in top face
                // V8: V4 repeated
                -0.5f, 0.5f, -0.5f,
                // V9: V5 repeated
                0.5f, 0.5f, -0.5f,
                // V10: V0 repeated
                -0.5f, 0.5f, 0.5f,
                // V11: V3 repeated
                0.5f, 0.5f, 0.5f,
                // For text coords in right face
                // V12: V3 repeated
                0.5f, 0.5f, 0.5f,
                // V13: V2 repeated
                0.5f, -0.5f, 0.5f,
                // For text coords in left face
                // V14: V0 repeated
                -0.5f, 0.5f, 0.5f,
                // V15: V1 repeated
                -0.5f, -0.5f, 0.5f,
                // For text coords in bottom face
                // V16: V6 repeated
                -0.5f, -0.5f, -0.5f,
                // V17: V7 repeated
                0.5f, -0.5f, -0.5f,
                // V18: V1 repeated
                -0.5f, -0.5f, 0.5f,
                // V19: V2 repeated
                0.5f, -0.5f, 0.5f,};
        float[] textCoords = new float[]{
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0.0f,
                0.0f, 0.0f,
                0.5f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                // For text coords in top face
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.0f, 1.0f,
                0.5f, 1.0f,
                // For text coords in right face
                0.0f, 0.0f,
                0.0f, 0.5f,
                // For text coords in left face
                0.5f, 0.0f,
                0.5f, 0.5f,
                // For text coords in bottom face
                0.5f, 0.0f,
                1.0f, 0.0f,
                0.5f, 0.5f,
                1.0f, 0.5f,};
        int[] indices = new int[]{
                // Front face
                0, 1, 3, 3, 1, 2,
                // Top Face
                8, 10, 11, 9, 8, 11,
                // Right face
                12, 13, 7, 5, 12, 7,
                // Left face
                14, 15, 6, 4, 14, 6,
                // Bottom face
                16, 18, 19, 17, 16, 19,
                // Back face
                4, 6, 7, 5, 4, 7,};
        Texture texture=null;
        try
        {
            texture = new Texture("resources/textures/grassblock.png");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return new Mesh(positions,textCoords,indices,texture);
    }
    public void draw(ShaderProgram shaderProgram, Transformation transformation, Matrix4f viewMatrix)
    {
        // Set model view matrix for this item
        for (Vector3i position:agentsPosition)
        {
            setPosition(position.x,position.y,position.z);
            Matrix4f modelViewMatrix = transformation.getModelViewMatrix(this, viewMatrix);
        shaderProgram.setUniform("modelViewMatrix", modelViewMatrix);
        // Render the mes for this game item
        this.getMesh().render();
        }
    }
    public void nextMove()
    {
        index++;
        List<PathFindingAlgorithm.Movment<Integer>> moves=null;
        if(index>=movmentList.size())
        {
            index=movmentList.size()-1;
        }
        else
        {
            moves=movmentList.get(index);
            System.out.println("size:"+movmentList.size());
            for(PathFindingAlgorithm.Movment<Integer> m:moves)
            {
                agentsPosition.set(m.id,m.to);
            }
        }
    }
    public void previouseMove()
    {
        if(index>=0)
        {
            List<PathFindingAlgorithm.Movment<Integer>> moves=movmentList.get(index);
            for(PathFindingAlgorithm.Movment<Integer> m:moves)
            {
                agentsPosition.set(m.id,m.from);
            }
            index--;
        }
    }
}
