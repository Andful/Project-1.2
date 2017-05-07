package org.lwjglb.game;

import org.joml.Vector3i;
import org.lwjglb.engine.GameItem;
import org.lwjglb.engine.graph.Mesh;
import org.lwjglb.engine.graph.Texture;

/**
 * Created by lucas on 3/21/2017.
 */
public class Floor extends GameItem
{




    public Floor(Vector3i enviromentSize)
    {
        super(getFloorMesh(enviromentSize));

    }




    public static Mesh getFloorMesh(Vector3i enviromentSize)
    {
        float[] positions = new float[]{
                // V0
                enviromentSize.x-0.5f, -0.5f, enviromentSize.z-0.5f,
                // V1
                -0.5f, -0.5f, enviromentSize.z-0.5f,
                // V2
                enviromentSize.x-0.5f, -0.5f, -0.5f,
                // V3
                -0.5f, -0.5f, -0.5f,
        };
        float[] textCoords = new float[]{
                0.0f, 0.0f,
                0.0f, enviromentSize.z,
                enviromentSize.x, 0.0f,
                enviromentSize.x,enviromentSize.z,
               };
        int[] indices = new int[]{
                1,2,3,
                1,2,0,};
        Texture texture=null;
        try
        {
            texture = new Texture("resources/textures/floor.png");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return new Mesh(positions,textCoords,indices,texture);
    }
}
