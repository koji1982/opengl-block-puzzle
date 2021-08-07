package com.honestastrology.glblocks;

import android.opengl.Matrix;
import android.util.Log;

public class FrustumCamera {
	
	private static QuaternionUIConnector _quaternionUIConnector;
	
	private static float[] eyePoint       = new float[3];
	private static float[] eyeCenter      = new float[]{4.0f,4.0f,4.0f};
	
	private float[] identityMatrix = new float[16];
	private float[] lookAtMatrix   = new float[16];
	
	private float[] _viewMatrix    = new float[16];
	
	private static float   testX  = 30.0f;
	private static float   testY  = 20.0f;
	private static float   testZ  = 30.0f;
	//private static float[] eyePoint   = {testX,testY,testZ};
	
	public FrustumCamera(){
		setEyeCenter(CurrentScene.getAreaFloat());
		QuaternionUIConnector.create(this);
		QuaternionUIConnector.rotate(QuaternionUIConnector.INITIALIZE);
		Matrix.setIdentityM(identityMatrix,0);
	}
	
	public void whenCameraMove(){
		//eyePoint   = new float[]{testX,testY,testZ};
		lookAtMatrix = new float[16];
		Matrix.setLookAtM(lookAtMatrix, 0, eyePoint[0], eyePoint[1],
				eyePoint[2], eyeCenter[0], eyeCenter[1], eyeCenter[2],
				0.0f, 1.0f, 0.0f);
		Matrix.multiplyMM(_viewMatrix, 0, identityMatrix, 0, lookAtMatrix, 0);
	}
	
	public float[] getViewMatrix(){
		return (float[])_viewMatrix.clone();
	}
	
	public float[] getEyePoint(){
		return eyePoint;
	}
	
	public void setEyePoint(float[] eyepoint){
		eyePoint = eyepoint;
	}
	
	public float[] getEyeCenter(){
		return eyeCenter;
	}
	
	public void setEyeCenter(float[] eyecenter){
		eyeCenter[0] = eyecenter[0]/2;
		eyeCenter[1] = eyecenter[1]/2;
		eyeCenter[2] = eyecenter[2]/2;
	}
	
}
