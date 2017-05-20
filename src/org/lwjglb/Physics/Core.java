package org.lwjglb.Physics;

import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.lwjglb.Util.BisectionMethod;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import static java.lang.Math.pow;

public class Core {

    //static Test test;

    public static Vector3d getAcceleration(Vector3d velocity){
        return new Vector3d(velocity).mul(-1).normalize().mul(100);//TODO
    }
    public static Vector3d getVelocity(Vector3d initialPosition, Vector3d endPosition, List<Vector3d> blocks, float dt)
    {
        return getVelocity(initialPosition,new Vector2d(endPosition.x,endPosition.z),blocks,dt);
    }
    public static Vector3d getVelocity(Vector3d initialPosition, Vector2d endPosition, List<Vector3d> blocks, float dt)
    {
        Vector3d velocityDirection=new Vector3d(endPosition.x,initialPosition.y,endPosition.y).sub(initialPosition);
        Vector3d oldPosition=new Vector3d(initialPosition);
        Vector3d position=new Vector3d(initialPosition);
        Vector3d oldAcceleration=Constants.getAcceleration(velocityDirection,position,blocks).mul(-1);
        Vector3d acceleration=oldAcceleration;
        Vector3d oldVelocity=new Vector3d();
        Vector3d velocity=new Vector3d();
        while(pow(position.x-initialPosition.x,2)+pow(position.z-initialPosition.z,2)<pow(endPosition.x-initialPosition.x,2)+pow(endPosition.y-initialPosition.z,2))
        {
            position.add(new Vector3d(oldVelocity).add(velocity).mul(dt/2));
            oldVelocity=velocity;
            velocity=new Vector3d(oldAcceleration).add(acceleration).mul(dt/2).add(velocity);
            oldAcceleration=acceleration;
            acceleration=Constants.getAcceleration(velocityDirection,position,blocks).mul(-1);
        }

        return oldVelocity;
    }
    public static Vector3d getEndPosition(Vector3d initialPosition, Vector3d initialVelocity, List<Vector3d> blocks, float dt){

        Vector3d velocity = new Vector3d(initialVelocity);
        Vector3d position = new Vector3d(initialPosition);

        while(new Vector3d(velocity).add(initialVelocity).length() > initialVelocity.length()){
            position = new Vector3d(position).add(new Vector3d(velocity).mul(dt));
            velocity = new Vector3d(velocity).add(new Vector3d(Constants.getAcceleration(initialVelocity,position,blocks).mul(dt)));
        }

        return position;

    }

    public static void main(String[] args){
        //getEndPosition(new Vector3d(), new Vector3d(250,0,0), 0.001f);
        System.out.println(getVelocity(new Vector3d(0,1,0),new Vector3d(1,1,0),new LinkedList<Vector3d>()
                {
                        {
                            //add(new Vector3d());
                            //add(new Vector3d(1,0,0));
                        }
                },0.0001f).x);
    }
}