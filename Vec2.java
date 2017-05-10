
public class Vec2 {

	protected float x;
	protected float y;
	protected float magnitude;
	protected float angle;
	
	public Vec2(float x, float y){
		this.x = x;
		this.y = y;
		angle = setAngle();
		magnitude = setMagnitude();
	}
	
	public Vec2(){}
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	
	public float setAngle(){
		
		float theta = (float) Math.atan(y/x);
		
		return theta;
	}
	
	public float setMagnitude(){
		
		float xSquared = x*x;
		float ySquared = y*y;
		float magnitudeSquared = xSquared + ySquared;
		float magnitude = (float) Math.sqrt(magnitudeSquared);
		
		return magnitude;
	}
	
	public String toString() {
		return "Vec2 [x=" + x + ", y=" + y + ", magnitude=" + magnitude + ", angle=" + angle + "]";
	}

	
	
	
}
