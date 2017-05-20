package org.lwjglb.Physics;

import org.joml.Vector3d;

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

    public static Vector3d getFrictionForce(Vector3d velocity, Vector3d position, List<Vector3d> blocks)
    {
        double surfaceTouching=0;
        while(true)
        {
            if(Math.abs(position.y)<TOL)
            {
                surfaceTouching=1;
            }
            for (Vector3d vec : blocks)
            {
                if (Math.abs(position.y - 1 - vec.y) < TOL || Math.abs(position.y + 1 - vec.y) < TOL)
                {
                    if (Math.abs(position.x - vec.x) < 1 && Math.abs(position.z - vec.z) < 1)
                    {
                        surfaceTouching += (1 - abs(position.x - vec.x)) * (1 - abs(position.z - vec.z));
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
        return new Vector3d(velocity).normalize().mul(-1).mul( -GRAVITY.y*MASS*KINETIC_FRICTION).mul(surfaceTouching);
    }
    public static  Vector3d getAcceleration(Vector3d velocity, Vector3d position, List<Vector3d> blocks)
    {
        return getFrictionForce(velocity,position,blocks).div(MASS);
    }
}
