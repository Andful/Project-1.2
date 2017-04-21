package org.lwjglb.game;

import org.lwjglb.engine.GameItem;
import org.lwjglb.engine.graph.Mesh;
import org.lwjglb.engine.graph.Texture;
import org.joml.*;

import java.lang.Math;
import java.util.List;

/**
 * Created by Andrea Nardi on 3/21/2017.
 */


public class Agent extends GameItem implements Block
{
    private static final Vector3f G=new Vector3f(0.0f,-9.81f,0.0f);
    private Vector3f acceleration;
    private Vector3f velocity;
    private List<Block> blocks;
    public Agent(List<Block> blocks)
    {
        super(getAgentMesh());
        setScale(SCALE);
        velocity=new Vector3f();
        this.blocks=blocks;
    }

    public void update(float interval,List<Block> blocks)
    {
        //System.out.println(toString()+" "+getPosition().toString());
        move(interval);
    }
    public void move(float interval)
    {
        addPosition((new Vector3f(velocity)).mul(interval));
        checkCollision();
    }
    public void setVelocity(Vector3f velocity)
    {
        this.velocity=velocity;
    }
    private void checkCollision()
    {
        for(Block block:blocks)
        {
            checkCollision(block);
        }
    }
    private void checkCollision(Block block)
    {
        if(block!=this && Math.abs(getPosition().x-block.getPosition().x)<DISTANCE_FROM_CENTER*2
                && Math.abs(getPosition().y-block.getPosition().y)<DISTANCE_FROM_CENTER*2
                && Math.abs(getPosition().z-block.getPosition().z)<DISTANCE_FROM_CENTER*2)
        {
            Vector3f toAdd=new Vector3f(velocity).normalize().mul(-2*DISTANCE_FROM_CENTER);
            setPosition(toAdd.x==0?getPosition().x:toAdd.x+block.getPosition().x,
                    toAdd.y==0?getPosition().y:toAdd.y+block.getPosition().y,
                    toAdd.z==0?getPosition().z:toAdd.z+block.getPosition().z
            );
        }
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
}