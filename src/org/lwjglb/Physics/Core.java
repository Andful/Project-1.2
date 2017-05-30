package org.lwjglb.Physics;

import org.joml.Vector2d;
import org.joml.Vector3f;
import org.joml.Vector3f;
import org.lwjglb.engine.GameItem;

import java.util.LinkedList;
import java.util.List;
import static java.lang.Math.*;
import java.awt.Window;

import static java.lang.Math.pow;

public class Core {
    public static class Velocity
    {
        GameItem gi;
        Vector3f startPosition;
        Vector3f endPosition;
        Vector3f velocity;
        Vector3f velocityDirection;
        Vector3f acceleration;
        List<Vector3f> blocks;
        float dt;
        public Velocity(GameItem gi,Vector3f endPosition,List<Vector3f> blocks,float dt)
        {
            this.gi=gi;
            this.dt=dt;
            startPosition=gi.getPosition();
            velocity=getVelocity(new Vector3f(gi.getPosition()),endPosition,blocks,dt);
            this.blocks=blocks;
            acceleration=new Vector3f();
            this.endPosition=endPosition;
            velocityDirection=new Vector3f(velocity).normalize();
        }
        public boolean update()
        {
            gi.getPosition().add(new Vector3f(velocity).mul(dt));
            velocity.add(new Vector3f(Constants.getAcceleration(velocity,gi.getPosition(),blocks)).mul(dt));
            acceleration=Constants.getAcceleration(new Vector3f(velocityDirection),gi.getPosition(),blocks);
            System.out.println(velocityDirection);
            //return pow(gi.getPosition().x-endPosition.x,2)+pow(gi.getPosition().z-endPosition.z,2)<pow(endPosition.x-startPosition.x,2)+pow(endPosition.z-startPosition.z,2);
            return new Vector3f(velocity).normalize().sub(velocityDirection).length()>1;
        }
    }
    public static Vector3f getAcceleration(Vector3f velocity){
        return new Vector3f(velocity).mul(-1).normalize().mul(100);//TODO
    }
    public static Vector3f getVelocity(Vector3f initialPosition, Vector3f endPosition, List<Vector3f> blocks, float dt)
    {
        Vector3f position = getEndPosition(initialPosition,endPosition,blocks,0.001f,0.001f);
        Vector3f velocityDirection=new Vector3f(endPosition).sub(initialPosition);
        velocityDirection.y=0;
        velocityDirection.normalize();
        Vector3f velocity=new Vector3f();
        Vector3f acceleration=Constants.getAcceleration(velocityDirection,position,blocks);

        while(pow(position.x-endPosition.x,2)+pow(position.z-endPosition.z,2)<pow(endPosition.x-initialPosition.x,2)+pow(endPosition.z-initialPosition.z,2))
        {
            position.add(new Vector3f(velocity.x,0,velocity.z).mul(dt));
            velocity.add(new Vector3f(acceleration).mul(dt));
            acceleration=Constants.getAcceleration(velocityDirection,position,blocks);
        }
        return velocity.mul(-1);
    }
    public static double projectionLenth(Vector3f a,Vector3f b)
    {
        return new Vector2d(a.x,a.z).sub(new Vector2d(b.x,b.z)).length();
    }
    public static Vector3f getEndPosition(Vector3f initialPosition, Vector3f endPosition, List<Vector3f> blocks,float ds,float TOL){
        Vector3f velocityDirection=new Vector3f(initialPosition.x-endPosition.x,0,initialPosition.z-endPosition.z).normalize();
        Vector3f position=new Vector3f(initialPosition);
        while(new Vector3f(initialPosition).sub(position).length()<new Vector3f(initialPosition).sub(endPosition).length())
        {
            boolean puttedUp = false;
                position.add(new Vector3f(velocityDirection).mul(ds));
            here:
            while(true)
            {
                for(Vector3f block:blocks)
                {
                    if(abs(position.x-block.x)<1 && abs(position.z-block.z)<1 && abs(position.y-block.y)<TOL)
                    {
                        if(puttedUp)
                        {
                            return null;
                        }
                        else
                        {
                            puttedUp=true;
                            position.y++;
                            continue here;
                        }
                    }
                }
                break;
            }
            hi:
            while(true)
            {
                if(Math.abs(position.y)<TOL)
                {
                    position.y=0;
                    break hi;
                }
                for (Vector3f vec : blocks)
                {
                    if(vec!=position)
                    {
                        if(Math.abs(position.y-vec.y)<TOL)
                        {
                            if(position.x-vec.x<1 && position.z-vec.z<1)
                            {
                                break hi;
                            }
                        }
                        if (Math.abs(position.y - 1 - vec.y) < TOL || Math.abs(position.y + 1 - vec.y) < TOL)
                        {
                            if (Math.abs(position.x - vec.x) < 1 && Math.abs(position.z - vec.z) < 1)
                            {
                                break hi;
                            }
                        }
                    }
                }
                position.y--;
            }
        }
        Vector3f result=new Vector3f(endPosition.x,position.y,endPosition.z);
        return result;
    }

    public static void main(String[] args){
        //getEndPosition(new Vector3f(), new Vector3f(250,0,0), 0.001f);
        System.out.println(getVelocity(new Vector3f(0,1,0),new Vector3f(1,1,0),new LinkedList<Vector3f>()
                {
                        {
                            //add(new Vector3f());
                            //add(new Vector3f(1,0,0));
                        }
                },0.0001f).x);
    }
}