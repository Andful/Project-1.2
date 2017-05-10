
public class AABB {

	//Axis Aligned Bounding Box, cannot rotate, squared off at 90 degrees
	
	protected Vec2 min; //top left corner
	protected Vec2 max; //bottom right corner
	protected Vec2 centre; //center of box
	
	public AABB(Vec2 min, Vec2 max){
		this.min = min;
		this.max = max;
		this.centre = setCentre();
	}

	public Vec2 getMin() {
		return min;
	}

	public void setMin(Vec2 min) {
		this.min = min;
	}

	public Vec2 getMax() {
		return max;
	}

	public void setMax(Vec2 max) {
		this.max = max;
	}

	public Vec2 setCentre(){
		
		Vec2 centre = new Vec2();
		
		centre.x = (min.x + max.x)/2 ;
		centre.y = (min.y + max.y)/2;
		
		return centre;
	}
	
	public float getWidth(){
		
		float width = Math.abs(max.x - min.x);
		
		return width;
	}
	
	public String toString() {
		return "AABB [min=" + min + ", max=" + max + "]";
	}
	
	
}
