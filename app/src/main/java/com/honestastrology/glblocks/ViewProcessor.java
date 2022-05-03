package com.honestastrology.glblocks;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

public class ViewProcessor {
	
	private ModelProcessor _modelProcessor;
	private UIManager 	   _uiManager;
	private FrustumCamera  _frustumCamera;
	
	private int[] _viewport;
	private float aspect;
	private float near    = 3.0f;
	private float far     = 200.0f;
	
	private float[] _projectionMatrix = new float[16];
	private float[] _VPMatrix         = new float[16];
	
	int updateTestInt = 0;
	
	public ViewProcessor(){
		initialize();
	}
	
	public void setModelProcessor(ModelProcessor modelProcessor){
		_modelProcessor = modelProcessor;
		_uiManager      = UIManager.getInstance();
		_frustumCamera  = new FrustumCamera();
	}
	
	public void initialize(){
		GLBaseShader.createShaderProgram();
		GLBaseTexShader.createShaderProgram();
		GLBaseFixedParticleShader.createProgram();
		GLBaseFixedParticleShader.createAdjustedProgram();
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		GLES20.glEnable(GLES20.GL_CULL_FACE);
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
	}
	
	public void viewProcess(){
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT|GLES20.GL_DEPTH_BUFFER_BIT);
		_modelProcessor.drawObject();
		_modelProcessor.drawCommonObj();
		_uiManager.drawUI();
	}
	
	public void updateVPMatrix(){
		_frustumCamera.whenCameraMove();
	}
	
	public float[] getVMatrix(){
		return (float[])_frustumCamera.getViewMatrix();
	}
	
	public float[] getPMatrix(){
		return (float[])_projectionMatrix.clone();
	}
	
	public float[] computeVPMatrix(){
		Matrix.setIdentityM( _VPMatrix, 0);
		Matrix.multiplyMM(
						_VPMatrix, 0,
						getPMatrix(), 0,
						getVMatrix(), 0);
		return _VPMatrix;
	}
	
	public void setViewport(int width,int height){
		_viewport=new int[]{0,0,width,height};
	}
	
	public int[] getViewPort(){
		return _viewport;
	}
	
	public float[] getEyeCenter(){
		return _frustumCamera.getEyeCenter();
	}
	
	public void setProjection(int width,int height){
		aspect          = (float)width / (float)height;
		float[] leftM  = new float[16];
		float[] rightM = new float[16];
		Matrix.setIdentityM(leftM, 0);
		Matrix.frustumM(rightM,0,-aspect,aspect,-1.0f,1.0f,near,far);
		Matrix.multiplyMM(_projectionMatrix,0,leftM,0,rightM,0);
	}
	
	public float getAspect(){
		return aspect;
	}
	
}
