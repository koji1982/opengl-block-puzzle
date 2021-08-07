package com.honestastrology.glblocks;

public class BaseCalculator {
	
	public static boolean calcRoundOdd(float a){
		boolean oddBool=false;
		if(Math.IEEEremainder((double)a, 2.0)==1){
			oddBool=true;
		}
		return oddBool;
	}
	
	public static float calcPythagoreanLong(float a, float b){
		float squaredHypotenuse=a*a+b*b;
		return squaredHypotenuse;
	}
	
	public static float calcSquaredDist(float ax,float ay,float bx,float by){
		float squaredHypotenuse = (bx-ax) * (bx-ax) + (by-ay) * (by-ay);
		return squaredHypotenuse;
	}
	
	public static double calcVectorTheta(float[] a, float[] b){
		return Math.acos(calcDot2D(a, b)/(calcAbs2D(a)*calcAbs2D(b)));
	}
	
	public static double calcCosTheta(float a,float b,float c){ // 辺b,c,の間の角をthetaとする
		double theta=Math.acos((b*b+c*c-a*a)/2*b*c);
		return theta;
	}
	
	public static double calcScreenTheta(float x,float y,float hypo){
		float minusY=-y;
		double theta=calcCosTheta(minusY, hypo, x);
		
		return theta;
	}
	
	public static float[] calcDistance(float[] a, float[] b){
		return new float[]{a[0]-b[0], a[1]-b[1], a[2]-b[2]};//return    b to a Vector
	}
	
	public static float calcDot2D(float[] a, float[] b){
		return a[0]*b[0]+a[1]*b[1];
	}
	
	public static double calcDot(double[] a, double[] b){
		return ((a[0]*b[0])+(a[1]*b[1])+(a[2]*b[2]));
	}
	
	public static float calcDot(float[] a, float[] b){
		return ((a[0]*b[0])+(a[1]*b[1])+(a[2]*b[2]));
	}
	
	public static double[] calcCross(double[] u,double[] v){
		return new double[]{u[1]*v[2]-u[2]*v[1],u[2]*v[0]-u[0]*v[2],u[0]*v[1]-u[1]*v[0]};
	}
	
	public static float[] calcCross(float[] u,float[] v){
		return new float[]{u[1]*v[2]-u[2]*v[1],u[2]*v[0]-u[0]*v[2],u[0]*v[1]-u[1]*v[0]};
	}
	
	public static float calcAbs2D(float[]a){
		return (float)Math.sqrt(a[0]*a[0]+a[1]*a[1]);
	}
	
	public static float calcAbs(float[] a){
		return (float)Math.sqrt(a[0]*a[0]+a[1]*a[1]+a[2]*a[2]);
	}
	
	public static float[] calcEnhance(float[] f, float enhance){
		float a=1.0f;
		float b=1.0f;
		float c=1.0f;
		if(f[0]*enhance<a)a=f[0]*enhance;
		if(f[1]*enhance<b)b=f[1]*enhance;
		if(f[2]*enhance<c)c=f[2]*enhance;
		return new float[]{a,b,c,1.0f};
	}
	
	public static float[] calcNormal2D(float[] vector){ //未確認
		double calc=(double)((vector[0]*vector[0])+(vector[1]*vector[1]));
		float normVector=(float)Math.sqrt(calc);
		float[] normal=new float[]{
				vector[0]/normVector,vector[1]/normVector
		};
		return normal;
	}
	
	public static float[] calcNormal(float[] vector){
		double calc=(double)((vector[0]*vector[0])+(vector[1]*vector[1])+(vector[2]*vector[2]));
		float normVector=(float)Math.sqrt(calc);
		float[] normal=new float[]{
				vector[0]/normVector,vector[1]/normVector,vector[2]/normVector
		};
		return normal;
	}
	
}
