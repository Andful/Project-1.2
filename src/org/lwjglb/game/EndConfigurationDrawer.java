package org.lwjglb.game;

import org.joml.Matrix4f;
import org.joml.Vector3i;
import org.lwjglb.engine.GameItem;
import org.lwjglb.engine.graph.Mesh;
import org.lwjglb.engine.graph.ShaderProgram;
import org.lwjglb.engine.graph.Texture;
import org.lwjglb.engine.graph.Transformation;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Andrea Nardi on 5/5/2017.
 */
public class EndConfigurationDrawer extends GameItem
{
    private List<Vector3i> endConfiguration;
    public EndConfigurationDrawer(List<Vector3i> endConfiguration)
    {
        super(getEndConfigurationMesh());
        this.endConfiguration=endConfiguration;
    }
    public static Mesh getEndConfigurationMesh()
    {
        float[] positions = new float[]{
                // V0
                -0.25f, 0.25f, 0.25f,
                // V1
                -0.25f, -0.25f, 0.25f,
                // V2
                0.25f, -0.25f, 0.25f,
                // V3
                0.25f, 0.25f, 0.25f,
                // V4
                -0.25f, 0.25f, -0.25f,
                // V5
                0.25f, 0.25f, -0.25f,
                // V6
                -0.25f, -0.25f, -0.25f,
                // V7
                0.25f, -0.25f, -0.25f,
                // For text coords in top face
                // V8: V4 repeated
                -0.25f, 0.25f, -0.25f,
                // V9: V5 repeated
                0.25f, 0.25f, -0.25f,
                // V10: V0 repeated
                -0.25f, 0.25f, 0.25f,
                // V11: V3 repeated
                0.25f, 0.25f, 0.25f,
                // For text coords in right face
                // V12: V3 repeated
                0.25f, 0.25f, 0.25f,
                // V13: V2 repeated
                0.25f, -0.25f, 0.25f,
                // For text coords in left face
                // V14: V0 repeated
                -0.25f, 0.25f, 0.25f,
                // V15: V1 repeated
                -0.25f, -0.25f, 0.25f,
                // For text coords in bottom face
                // V16: V6 repeated
                -0.25f, -0.25f, -0.25f,
                // V17: V7 repeated
                0.25f, -0.25f, -0.25f,
                // V18: V1 repeated
                -0.25f, -0.25f, 0.25f,
                // V19: V2 repeated
                0.25f, -0.25f, 0.25f,};
        float[] textCoords = new float[]{
                0.0f, 0.0f,
                0.0f, 1f,
                1f, 1f,
                1f, 0.0f,
                0.0f, 0.0f,
                1f, 0.0f,
                0.0f, 1f,
                1f, 1f,
                // For text coords in top face
                0.0f, 0.0f,
                1.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f,
                // For text coords in right face
                0.0f, 0.0f,
                0.0f, 1.0f,
                // For text coords in left face
                1.0f, 0.0f,
                1.0f, 1.0f,
                // For text coords in bottom face
                1.0f, 0.0f,
                1.0f, 0.0f,
                1.0f, 1.0f,
                1.0f, 1.0f,};
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
            texture = new Texture("resources/textures/end.png");
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
        for (Vector3i position:endConfiguration)
        {
            setPosition(position.x,position.y,position.z);
            Matrix4f modelViewMatrix = transformation.getModelViewMatrix(this, viewMatrix);
            shaderProgram.setUniform("modelViewMatrix", modelViewMatrix);
            // Render the mes for this game item
            this.getMesh().render();
        }
    }
}
