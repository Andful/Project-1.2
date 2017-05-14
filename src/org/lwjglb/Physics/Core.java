package org.lwjglb.Physics;

import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.lwjglb.Util.BisectionMethod;

public class Core {

    //static Test test;

    public static Vector3f getAcceleration(Vector3f velocity){
        return new Vector3f(velocity).mul(-1).normalize().mul(100);//TODO
    }
    public static Vector3f getVelocity(Vector3f initialPosition,Vector3f endPosition, float TOL, float dt)
    {
        BisectionMethod<Vector3f> solver = new BisectionMethod<>();
        class FunctionSet implements BisectionMethod.FunctionSet<Vector3f>
        {
            public boolean fIsEqualToZero(Vector3f e){
                return false;
            }
            public boolean fIsSmallerThanZero(Vector3f e){
                Vector3f result = getEndPosition(initialPosition, e, dt);
                return (new Vector3f(endPosition).sub(initialPosition).length() < new Vector3f(result).sub(initialPosition).length());
            }

            public Vector3f getAverage(Vector3f a,Vector3f b){
                Vector3f average = new Vector3f(a);

                average.add(b);

                average.mul(0.5f);

                return average;

            }
            public boolean stop(Vector3f a,Vector3f b){

                Vector3f distance = new Vector3f(a).sub(b);

                return distance.length() < TOL;
            }
        }
        return new BisectionMethod<Vector3f>().findRoot(new Vector3f(),new Vector3f(100000,0,0),new FunctionSet());//TODO
    }
    public static Vector3f getEndPosition(Vector3f initialPosition, Vector3f initialVelocity, float dt){

        Vector3f velocity = new Vector3f(initialVelocity);
        Vector3f position = new Vector3f(initialPosition);

        while(new Vector3f(velocity).add(initialVelocity).length() > initialVelocity.length()){

            position = new Vector3f(position).add(new Vector3f(velocity).mul(dt));
			
			/*
			test.position = position;
			
			
			try
			{
				Thread.sleep(1);
			}
			catch(Exception e)
			{
			
			}
			*/

            velocity = new Vector3f(velocity).add(new Vector3f(getAcceleration(initialVelocity).mul(dt)));

        }

        return position;

    }

    public static void main(String[] args){
        //getEndPosition(new Vector3f(), new Vector3f(250,0,0), 0.001f);
        System.out.println(getVelocity(new Vector3f(),new Vector3f(10,0,0),0.001f,0.001f));
    }
}