package com.honestastrology.glblocks;

public class QuaternionUIConnector {
	
	private static QuaternionUIConnector _quaternionUIConnector = new QuaternionUIConnector();
	private static FrustumCamera _frustumCamera;
	
	public  final static int INITIALIZE     = -1;
	private final static int ROTATE_UP      =  0;
	private final static int ROTATE_DOWN    =  1;
	private final static int ROTATE_RIGHT   =  2;
	private final static int ROTATE_LEFT    =  3;
	private final static int MOVE_FORWARD   =  4;
	private final static int MOVE_BACKWARD  =  5;
	
	private static float           cameraDistance = -0.2f;
	private static double        horizontalRadian =  Math.PI/5;
	private static double          verticalRadian = -Math.PI/5;
	private static Double rotateRadianValue = 0.02;
	private final static float         moveConst = 0.005f;
	
	private final static double defaultHorizontalRadian = Math.PI/5;
	private final static double   defaultVerticalRadian = -Math.PI/5;
	
	private final static float      cameraNearLimit =  0.4f;
	private final static float       cameraFarLimit = -2.7f;
	private final static double cameraVerticalLimit = Math.PI/3;
	
	private QuaternionUIConnector(){
	}
	
	public static float getHorizontalDegree(){
		return (float)(( horizontalRadian / Math.PI) + 180.0);
	}
	
	public static QuaternionUIConnector create(FrustumCamera camera){
		_frustumCamera   = camera;
		cameraDistance   = CurrentScene.getEyeDistance();
		horizontalRadian = defaultHorizontalRadian;
		verticalRadian   = defaultVerticalRadian;
		rotate(INITIALIZE);
		return _quaternionUIConnector;
	}
	
	public static QuaternionUIConnector connectUI(int id){
		return _quaternionUIConnector;
	}
	
	public static float[] multiplyQuaternion(float[] q1,float[] q2){//float[4] resultQ{t,x,y,z}
		float[] v1      = {q1[1],q1[2],q1[3]};
		float[] v2      = {q2[1],q2[2],q2[3]};
		float   t       = q1[0] * q2[0] - BaseCalculator.calcDot(v1, v2);
		float[] cross   = BaseCalculator.calcCross(v1, v2);
		float[] resultQ = {
				t,
				q1[0]*q2[1]+q2[0]*q1[1]+cross[0],
				q1[0]*q2[2]+q2[0]*q1[2]+cross[1],
				q1[0]*q2[3]+q2[0]*q1[3]+cross[2]};
		return resultQ;
	}
	
	public static float[] rotateEyePoint(float[] point,float[] axisR, double theta,float[] axisNormal){
		float[] p = {0.0f, point[0]-axisR[0], point[1]-axisR[1], point[2]-axisR[2]};
		float[] q = {
				(float)Math.cos(theta/2),
				axisNormal[0]*(float)Math.sin(theta/2),
				axisNormal[1]*(float)Math.sin(theta/2),
				axisNormal[2]*(float)Math.sin(theta/2)};
		float[] r = {
				(float)Math.cos(theta/2),
				-axisNormal[0]*(float)Math.sin(theta/2),
				-axisNormal[1]*(float)Math.sin(theta/2),
				-axisNormal[2]*(float)Math.sin(theta/2)};
		
		float[] interimQ = multiplyQuaternion(q,p);
		float[] resultQ  = multiplyQuaternion(interimQ,r);
		float[] result   = {resultQ[1]+axisR[0],resultQ[2]+axisR[1],resultQ[3]+axisR[2]};
		return result;
	}
	
	public static float[] rotateEyeVertical(float[] axisR, double theta){
		float[] n          = {1.0f,0.0f,0.0f};
		float[] fixedPoint = {axisR[0],axisR[1],axisR[2]+30.0f};
		float[] position = rotateEyePoint(fixedPoint, axisR, theta, n);
		n = new float[]{0.0f,1.0f,0.0f};
		return rotateEyePoint(position, axisR, horizontalRadian,n);
	}
	
	public static float[] rotateEyeHorizontal(float[] point,float[] axisR, double theta){
		float[] n = {0.0f,1.0f,0.0f};
		float[] fixedPoint = {axisR[0],axisR[1],axisR[2]+10.0f};
		float[] position   = rotateEyePoint(fixedPoint, axisR, theta, n);
		return rotateEyePoint(position, axisR, verticalRadian,n);
	}
	
	public static float[] calcQuaternion(){
		return rotateEyeVertical(_frustumCamera.getEyeCenter(),verticalRadian);
	}
	
	public static void setRotateRadianValue(double val){
		rotateRadianValue = val;
	}
	
	public static void rotate(int id){
		float[] point = new float[]{};
		float[]     v = new float[]{};
		switch(id){
		case INITIALIZE:
			point = calcQuaternion();
			break;
		case ROTATE_UP:
			if(verticalRadian <= -cameraVerticalLimit){
				verticalRadian = -cameraVerticalLimit;
				return;
			}else {
				verticalRadian -= rotateRadianValue;
			}
			point = calcQuaternion();
			break;
		case ROTATE_DOWN:
			if(verticalRadian >= cameraVerticalLimit){
				verticalRadian = cameraVerticalLimit;
				return;
			}else {
				verticalRadian +=rotateRadianValue;
			}
			point = calcQuaternion();
			break;
		case ROTATE_RIGHT:
			horizontalRadian += rotateRadianValue;
			point = calcQuaternion();
			break;
		case ROTATE_LEFT:
			horizontalRadian -= rotateRadianValue;
			point = calcQuaternion();
			break;
		case MOVE_FORWARD:
			point = calcQuaternion();
			cameraDistance += 0.01f;
			if(cameraDistance >= cameraNearLimit)cameraDistance = cameraNearLimit;
			break;
		case MOVE_BACKWARD:
			point = calcQuaternion();
			cameraDistance -= 0.01f;
			if(cameraDistance <= cameraFarLimit)cameraDistance = cameraFarLimit;
			break;
		}
		v = BaseCalculator.calcDistance(_frustumCamera.getEyeCenter(), point);
		point = new float[]{point[0]+v[0]*cameraDistance,point[1]+v[1]*cameraDistance,point[2]+v[2]*cameraDistance};
		
		_frustumCamera.setEyePoint(point);
	}
	
}
