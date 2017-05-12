import org.joml.Vector2f;

public class AABB {

	//Axis Aligned Bounding Box, cannot rotate, squared off at 90 degrees
	
	protected Vector2f min; //top left corner
	protected Vector2f max; //bottom right corner
	protected Vector2f centre; //center of box
	protected Vector2f velocity;
	public static final float RESTITUTION = 0.7f; //to be tested
	public static final float MASS = 1.0f;
	public static final float INV_MASS = 1.0f;
	
	public AABB(Vector2f min, Vector2f max){
		this.min = min;
		this.max = max;
		this.centre = setCentre();
		velocity = new Vector2f();
	}

	public Vector2f getMin() {
		return min;
	}

	public void setMin(Vector2f min) {
		this.min = min;
	}

	public Vector2f getMax() {
		return max;
	}

	public void setMax(Vector2f max) {
		this.max = max;
	}

	private Vector2f setCentre(){
		
		Vector2f centre = new Vector2f();
		
		centre.x = (min.x + max.x)/2 ;
		centre.y = (min.y + max.y)/2;
		
		return centre;
	}
	
	public float getWidth(){
		
		float width = (float) Math.abs(max.x - min.x);
		
		return width;
	}
	
	public Vector2f getCentre() {
		return centre;
	}

	public Vector2f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2f velocity) {
		this.velocity = velocity;
	}

	public String toString() {
		return "AABB [min=" + min + ", max=" + max + "]";
	}
	
	
}
