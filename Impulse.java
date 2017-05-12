import org.joml.Vector2f;

public class Impulse {

	protected AABB a, b;
	protected float e; //min of two restitution of the two objects
	
	public Impulse(AABB a, AABB b){
		this.a = a;
		this.b = b;
		this.e = getE();
	}
	
	
	
	public void resolveCollision(){
		
		//Calculate relative velocity
		Vector2f relativeVelocity = new Vector2f(b.velocity.add(new Vector2f(a.velocity).mul(-1)));
		
		//Calculate relative velocity in terms of the normal direction
		Vector2f velocityNormal = new Vector2f(relativeVelocity).normalize(); //???? is this correct?
		float velocityAlongNormal = new Vector2f(relativeVelocity).dot(new Vector2f(velocityNormal));
		
		if (velocityAlongNormal > 0){
			System.out.println("Velocities are seperating");
			return;
		}
		
		//Calculate impulse scalar
		float j = (-1 + e)*velocityAlongNormal;
		
		j /= 1/a.MASS + 1/b.MASS;
		
		//Apply impulse
		Vector2f impulse = new Vector2f(velocityNormal).mul(j);
		
		a.velocity.add(new Vector2f(impulse).mul(-a.INV_MASS));
		b.velocity.add(new Vector2f(impulse).mul(b.INV_MASS));
	}
	
	private float getE(){
		
		return Math.min(a.RESTITUTION, b.RESTITUTION);
	}
}
