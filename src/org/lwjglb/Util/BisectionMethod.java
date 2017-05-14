package org.lwjglb.Util;

public class BisectionMethod<E> {

   public static interface FunctionSet<E>
    {
        boolean fIsEqualToZero(E e);
        boolean fIsSmallerThanZero(E e);
        E getAverage(E a,E b);
        boolean stop(E a,E b);

    }
    public E findRoot(E a,E b, FunctionSet<E> fs){


        if((fs.fIsSmallerThanZero(a) && !fs.fIsSmallerThanZero(b)) || (!fs.fIsSmallerThanZero(a) && fs.fIsSmallerThanZero(b))){

            while(!fs.stop(a,b)){

                E p = fs.getAverage(a,b);
                if(fs.fIsEqualToZero(p)) {return p;}
                if((fs.fIsSmallerThanZero(a) && !fs.fIsSmallerThanZero(p)) || (!fs.fIsSmallerThanZero(a)) && fs.fIsSmallerThanZero(p)){
                    b = p;
                }
                else{
                    a = p;
                }
                System.out.println(a+" "+p+" "+b);
            }
            return fs.getAverage(a,b);
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args){
        BisectionMethod<Float> b=new BisectionMethod();
        class Function
        {
            float run(float x)
            {
                return x+1;
            }
        }
        Function f=new Function();
        class myFunctionSet implements FunctionSet<Float>{

            public boolean fIsEqualToZero(Float e)
            {
                return f.run(e)==0;
            }
            public boolean fIsSmallerThanZero(Float e) {

                return f.run(e)<0;
            }


            public Float getAverage(Float a, Float b) {

                return a + (b-a)/2;

            }


            public boolean stop(Float a, Float b) {

                float TOL = 0.0001f;

                return Math.abs(a-b)<TOL;

            }
        }
        System.out.println(new BisectionMethod<Float>().findRoot(-2f, 2f, new myFunctionSet()));
    }
}