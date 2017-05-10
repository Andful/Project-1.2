
public class SAT {

	//Separating Axis Theorem, for collision between two polygons
	//Takes two polygons as parameters and returns if they are colliding
	
	AABB box1;
	AABB box2;
	
	public SAT(AABB box1, AABB box2){
		this.box1 = box1;
		this.box2 = box2;
	}
	
	
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
	
	/*
	public boolean areUnorientedHorizontallySeparated(){
		
		Vec2 point1 = box1.centre;
		Vec2 point2 = new Vec2(box1.max.x, box1.min.y);
		
		Vec2 point3 = box2.centre;
		Vec2 point4 = new Vec2(box2.min.x, box2.min.y);
		
		Vec2 pAxis = new Vec2(-1,1);
		
		
	}
	*/
	
	
	
	
	
	
}
