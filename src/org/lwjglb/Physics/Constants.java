package org.lwjglb.Physics;

import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector3f;

import static java.lang.Math.*;

import java.util.List;

/**
 * Created by Andrea Nardi on 5/16/2017.
 */
public class Constants
{
    public static final Vector3d GRAVITY = new Vector3d(0,-1.622f,0);
    public static final double MASS = 1;
    public static final double KINETIC_FRICTION = 0.5;
    public static final double STATIC_FRICTION = 0.75;
    public static final double TOL = 0.001;

    public static Vector3f getFrictionForce(Vector3f velocity, Vector3f position, List<Vector3f> blocks)
    {
        boolean once = false;
        here:
        while(true)
        {
            for (Vector3f vec : blocks)
            {
                if (vec != position)
                {
                    if (Math.abs(position.y - vec.y) < TOL)
                    {
                        if (position.x - vec.x < 1 && position.z - vec.z < 1)
                        {
                            if (!once)
                            {
                                position.y++;
                                once = true;
                                continue here;
                            } else
                            {
                                return null;
                            }
                        }
                    }
                }
            }
            break;
        }
        double surfaceTouching=0;
        while(true)
        {
            if(Math.abs(position.y)<TOL)
            {
                surfaceTouching=1;
                position.y=0;
            }
            for (Vector3f vec : blocks)
            {
                if(vec!=position)
                {
                    if(Math.abs(position.y-vec.y)<TOL)
                    {
                        if(position.x-vec.x<1 && position.z-vec.z<1)
                        {

                        }
                    }
                    if (Math.abs(position.y - 1 - vec.y) < TOL || Math.abs(position.y + 1 - vec.y) < TOL)
                    {
                        if (Math.abs(position.x - vec.x) < 1 && Math.abs(position.z - vec.z) < 1)
                        {
                            surfaceTouching += (1 - abs(position.x - vec.x)) * (1 - abs(position.z - vec.z));
                        }
                    }
                }
            }
            if(surfaceTouching==0)
            {
                position.y--;
            }
            else
            {
                break;
            }
        }
        return new Vector3f(velocity).normalize().mul(-1).mul((float)( -GRAVITY.y*MASS*KINETIC_FRICTION)).mul((float)(surfaceTouching));
    }
    public static  Vector3f getAcceleration(Vector3f velocity, Vector3f position, List<Vector3f> blocks)
    {
        return getFrictionForce(velocity,position,blocks).mul((float)(1.0/MASS));
    }
}
