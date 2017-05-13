import org.joml.Vector3f;

public class Core {

	//static Test test;
	
	public static Vector3f getAcceleration(Vector3f velocity){
		return new Vector3f(velocity).mul(-1).normalize().mul(100);
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
		
		test = new Test();
		
		getEndPosition(new Vector3f(), new Vector3f(250,0,0), 0.001f);
		System.out.println("hi");
	}
}
