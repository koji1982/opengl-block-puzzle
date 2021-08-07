package com.honestastrology.glblocks;

import java.util.ArrayList;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.view.MotionEvent;

public class GeometryManager {
	
	private GeometricCalculator _geometricCalculator;
	public BaseCalculator _baseCalculator;
	
	private int[] _viewPort = new int[4];
	
	public GeometryManager(ViewProcessor viewProcessor){
		_baseCalculator      = new BaseCalculator();
		_geometricCalculator = new GeometricCalculator(viewProcessor);
	}
	
	public void initGeoCalc(){
		_geometricCalculator.initGeoCalc();
	}
	
	public void updateViewMatrix(){
		_geometricCalculator.updateViewMatrix();
	}
	
	public void updateScreenAxis(){
		_geometricCalculator.onScreenAxis();
	}
	
	public void onDown(float x,float y,ArrayList<GLBaseObject> objList){
		if(GLBaseSurfaceView.getEventAction() == MotionEvent.ACTION_DOWN){
			_geometricCalculator.changeSegmentAtWorld(x,y,objList);
		}
	}
	
	public int onSwipe(float[] xy){
		return _geometricCalculator.onSwipe(xy);
	}
	
	public int getSelectedObj(){
		return _geometricCalculator.getSelectedIndex();
	}
	
}
