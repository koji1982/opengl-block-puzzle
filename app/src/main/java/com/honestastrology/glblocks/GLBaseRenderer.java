package com.honestastrology.glblocks;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

public class GLBaseRenderer implements Renderer {
	
	private GLBlocksActivity _glBlocksActivity;
	private GLBaseSurfaceView _glBaseSurfaceView;
	private Context _context;
	private ControlProcessor _controlProcessor;
	private ModelProcessor _modelProcessor;
	private ViewProcessor _viewProcessor;
	private SystemAnalyzer _systemAnalyzer;
	
	private int renderingCount = 0;
	private float fps          = 0;
	private long  spf          = 0;
	private long processTime   = 0;
	private long adjustSum     = 0;
	private long timeOverCount = 0;
	private long onTimeCount   = 0;
	private long totalCount    = 0;
	private long lapTimeOne    = 0;
	private int thresholdCount = 0;
	private float aspect;
	
	public GLBaseRenderer(Context context){
		_context=context;
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		initialize(_context);
		callActivityNotify();
	}
	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		viewSetting(width,height);
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
//		SystemAnalyzer.measuredBiginFlag();
		_controlProcessor.controlProcess();
		_modelProcessor.modelProcess();
		_viewProcessor.viewProcess();
		SoundManager.frameReset();
		analyzeProcess();
	}
	
	public void onTouch(float x, float y,int pointerId){
		_systemAnalyzer.setTouchedId(_controlProcessor.onTouch(x, y, pointerId));
	}
	
	public void onSwipe(float[] xy){
		_modelProcessor.onSwipe(xy);
	}
	
	public void onUpMotion(int pointerId){
		_controlProcessor.onUpMotion(pointerId);
	}
	
	public void stopAllRepeat(){
		_controlProcessor.stopAllRepeat();
	}
	
	public boolean queryRepeatId(int pointerId){
		return _controlProcessor.queryRepeatId(pointerId);
	}
	
	private void initialize(Context context){
		_viewProcessor    = new ViewProcessor();
		_modelProcessor   = new ModelProcessor(context,_viewProcessor);
		_controlProcessor = new ControlProcessor(_modelProcessor,_viewProcessor);
		_viewProcessor.setModelProcessor(_modelProcessor);
		setActivity();
		setGLBaseSurfaceView();
		_glBaseSurfaceView.setTouchEventEnable(true);
		GLBaseObject.setVelocity(0.18f);
	}
	
	private void viewSetting(int width,int height){
		GLES20.glViewport(0,0,width,height);
		_viewProcessor.setViewport(width,height);
		_viewProcessor.setProjection(width,height);
		_controlProcessor.setDisplaySize(width,height);
		_modelProcessor.initGeoCalc();
	}
	
	public void callActivityNotify(){
		_glBlocksActivity.rendererNotify();
	}
	
	public void setActivity(){
		_glBlocksActivity = CollateralConnector.getInstance().getGLBlocksActivity();
	}
	
	public void setGLBaseSurfaceView(){
		_glBaseSurfaceView = CollateralConnector.getInstance().getGLBaseSurfaceView();
	}
	
	public void analyzeProcess(){
//		processTime = SystemAnalyzer.measuredEndFlag();
//		spf = SystemAnalyzer.calcSecondParFrame();
//		if(23-spf>=0){
//			adjustSum  += SystemAnalyzer.adjustWaiting(23-spf);
//			onTimeCount++;
//		}
//		else {
//			timeOverCount++;
//		}
//		totalCount++;
//		if(totalCount == 1000){
//			SystemAnalyzer.setThauframeTime(System.currentTimeMillis() - lapTimeOne);
//		}else if(totalCount == 1){
//			lapTimeOne  = System.currentTimeMillis();
//		}
//		SystemAnalyzer.setSpfLap();
		if(SystemAnalyzer.compareLastTime()>3000){
			fps = SystemAnalyzer.getFPSAverage(renderingCount);
//			SystemAnalyzer.setAdjustSum(adjustSum);
//			SystemAnalyzer.setFrameCount(onTimeCount,timeOverCount,totalCount);
			renderingCount = 0;
//			adjustSum      = 0;
			Log.e("FPS",String.valueOf(fps));
			Log.e("velocity",String.valueOf(GLBaseObject.getVelocity()));
			if(fps < 48){
				thresholdCount++;
				if(thresholdCount > 3){
					GLBaseObject.setVelocity(0.25f);
					QuaternionUIConnector.setRotateRadianValue(0.03);
				}
			}
		}
//		callActivityNotify();
		renderingCount++;
		
	}

	
}
