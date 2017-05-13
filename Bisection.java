
public class Bisection<E> {
	
	static interface FunctionSet<E>
	{
		boolean isSmallerThanZero(E e);
		E getAverage(E a,E b);
		boolean stop(E a,E b);
	}
	public E findRoot(E a,E b, FunctionSet<E> fs){
		
		
		if((fs.isSmallerThanZero(a) && !fs.isSmallerThanZero(b)) || (!fs.isSmallerThanZero(a) && fs.isSmallerThanZero(b))){
		
			while(!fs.stop(a,b)){
				
				E p = fs.getAverage(a,b);
				
				if((fs.isSmallerThanZero(a) && !fs.isSmallerThanZero(p)) || (!fs.isSmallerThanZero(a)) && fs.isSmallerThanZero(p)){
					b = p;
				}
				else{
					a = p;
				}	
			}
			return fs.getAverage(a,b);	
		}
		else
		{
			throw new IllegalArgumentException();
		}
	}
	
	public static void main(String[] args){
		Bisection<Float> b=new Bisection();
		class Function
		{
			float run(float x)
			{
				return x+1;
			}
		}
		Function f=new Function();
		class myFunctionSet implements FunctionSet<Float>{

			public boolean isSmallerThanZero(Float e) {
				
				return f.run(e)<0;
			}

			
			public Float getAverage(Float a, Float b) {

				return a + (b-a)/2;
				
			}

			
			public boolean stop(Float a, Float b) {
				
				float TOL = 0.01f;
				
				return Math.abs(a-b)>TOL;
				
			}
		}
		System.out.println(new Bisection<Float>().findRoot(-2f, 2f, new myFunctionSet()));
	}
}
