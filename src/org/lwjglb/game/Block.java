package org.lwjglb.game;

import org.joml.Vector3f;

/**
 * Created by Andrea Nardi on 3/21/2017.
 */
public interface Block
{
    public static final float SCALE=1.0f;
    public static final float DISTANCE_FROM_CENTER=SCALE/2.0f;
    public Vector3f getPosition();
}
