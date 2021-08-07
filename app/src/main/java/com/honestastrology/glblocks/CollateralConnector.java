package com.honestastrology.glblocks;

import android.os.Handler;
import android.view.SurfaceHolder;

public class CollateralConnector {
	
	private static CollateralConnector _collateralConnector = new CollateralConnector();
	
	private GLBlocksActivity _glBlocksActivity;
	private GLBaseSurfaceView _glBaseSurfaceView;
	private GLBaseRenderer _glBaseRenderer;
	
	private Handler handler = new Handler();
	private static boolean isThreadExist = false;
	private int detachStartTime = 0;
	private int      detachTime = 0;
	
	private CollateralConnector(){
	}
	
	public static CollateralConnector getInstance(){
		return _collateralConnector;
	}
	
	public GLBaseRenderer getGLBaseRenderer() {
		return _glBaseRenderer;
	}

	public void setGLBaseRenderer(GLBaseRenderer _glBaseRenderer) {
		this._glBaseRenderer = _glBaseRenderer;
	}

	public GLBlocksActivity getGLBlocksActivity() {
		return _glBlocksActivity;
	}

	public void setGLBlocksActivity(GLBlocksActivity _glBlocksActivity) {
		this._glBlocksActivity = _glBlocksActivity;
	}

	public GLBaseSurfaceView getGLBaseSurfaceView() {
		return _glBaseSurfaceView;
	}

	public void setGLBaseSurfaceView(GLBaseSurfaceView _glBaseSurfaceView) {
		this._glBaseSurfaceView = _glBaseSurfaceView;
	}
	
	public void callActivityNotify(){
		_glBlocksActivity.rendererNotify();
	}
	
//	public void detachRenderer(){
//		_glBaseSurfaceView.queueEvent(new Runnable(){
//			@Override
//			public void run(){
//				_glBaseSurfaceView.detachRenderer();
//			}
//		});
//	}
	
}
