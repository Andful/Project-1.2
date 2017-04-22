package org.lwjglb.game;

import org.lwjglb.engine.GameItem;
import org.lwjglb.engine.graph.Mesh;
import org.lwjglb.engine.graph.Texture;

/**
 * Created by lucas on 3/21/2017.
 */
public class Floor extends GameItem
{




    public Floor()
    {
        super(getFloorMesh());

    }




    public static Mesh getFloorMesh()
    {
        float[] positions = new float[]{
                // V0
                100f, -0.25f, 100f,
                // V1
                -100f, -0.25f, 100f,
                // V2
                100f, -0.25f, -100f,
                // V3
               - 100f, -0.25f, -100f,
        };
        float[] textCoords = new float[]{
                0.0f, 0.0f,
                0.0f, 100f,
                100f, 0.0f,
                100f, 100f,
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
