import org.joml.Vector2f;

public class SAT {

	//Separating Axis Theorem, for collision between two polygons
	//Takes two AABBs as parameters and returns if they are colliding
	
	AABB box1;
	AABB box2;
	
	public SAT(AABB box1, AABB box2){
		this.box1 = box1;
		this.box2 = box2;
	}
	
	//checks if there is distance between the objects
	public boolean areHorizontallySeparated(){
		
		float length = Math.abs(box2.centre.x - box1.centre.x);
		float half_width_box1 = box1.getWidth()*0.5f;
		float half_width_box2 = box2.getWidth()*0.5f;
		float gap_between_boxes = length - half_width_box1 - half_width_box2;
		
		if(gap_between_boxes>0){
			System.out.println("There is a distance between the boxes");
			return true;
		}
		else if(gap_between_boxes == 0){
			System.out.println("Boxes are touching each other");
			return false;
		}
		else {
			System.out.println("Boxes are penetrating each other");
			return false;
		}
	
	}
	
	//checks if there is distance between the objects which are not oriented nicely
	//not yet tested
	public boolean areUnorientedHorizontallySeparated(){
		
		Vector2f dot10 = new Vector2f(box1.centre);
		Vector2f dot11 = new Vector2f(box1.max.x, box1.min.y);
		
		Vector2f dot20 = new Vector2f(box2.centre);
		Vector2f dot24 = new Vector2f(box2.min);
		
		//Actual calculations
		
		Vector2f axis = new Vector2f(-1,-1);
		
		Vector2f c = new Vector2f(dot20.x - dot10.x, dot20.y - dot10.x);
		
		Vector2f a = new Vector2f(dot11.x - dot10.x, dot11.y - dot11.x);
		
		Vector2f b = new Vector2f(dot24.x - dot20.x, dot24.y - dot20.y);
		
		float projC = c.dot(axis);
		
		float projA = a.dot(axis);
		
		float projB = b.dot(axis);
		
		float gap = projC - projA + projB; //projection of B is expected to be a negative number;
		
		if(gap > 0){
			System.out.println("There is a gap between the boxes");
			return true;			
		}
		else if(gap == 0){
			System.out.println("Boxes are touching");
			return false;
		}
		else{
			System.out.println("Boxes are penetrating each other");
			return false;
		}
	}
	
	private float magnitude(Vector2f vector){
		
		float magnitude = vector.x*vector.x + vector.y*vector.y;
		
		return magnitude;
	}
	
	
	
	
	
	
	
}
