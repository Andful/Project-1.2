package org.lwjglb.engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjglb.engine.graph.Mesh;
import org.lwjglb.engine.graph.ShaderProgram;
import org.lwjglb.engine.graph.Transformation;

public class GameItem {

    private final Mesh mesh;
    
    private final Vector3f position;
    
    private float scale;

    private final Vector3f rotation;

    public GameItem(Mesh mesh) {
        this.mesh = mesh;
        position = new Vector3f(0, 0, 0);
        scale = 1;
        rotation = new Vector3f(0, 0, 0);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }
    public void addPosition(Vector3f pos)
    {
        position.add(pos);
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }
    
    public Mesh getMesh() {
        return mesh;
    }

    public void draw(ShaderProgram shaderProgram, Transformation transformation, Matrix4f viewMatrix)
    {
        // Set model view matrix for this item
        Matrix4f modelViewMatrix = transformation.getModelViewMatrix(this, viewMatrix);
        shaderProgram.setUniform("modelViewMatrix", modelViewMatrix);
        // Render the mes for this game item
        this.getMesh().render();
    }
}