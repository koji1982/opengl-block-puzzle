package com.honestastrology.glblocks;

import android.util.Log;

import java.util.ArrayList;

public class UITouchDetector {
	
	private static final float COLLECTION_VALUE_Y = 0.1f;
	
	private float displayWidth;
	private float displayHeight;
	
	private float[] currentAreaFloat;
	private float areaRadius;
	private float commonCameraRadius;//視点移動用　共通
	private float commonAxisRadius;  //軸固定用　共通
	private float commonRectangleRadiusX; //長方形用X　共通
	private float commonRectangleRadiusY; //長方形用Y　共通
	
	private final float DEVIDED_UPPER_Y = 0.8f;
	private final float DEVIDED_RIGHT_X = 0.5f;
	private final float DEVIDED_LEFT_X  = 0.4f;
	private final float DEVIDED_CENTER_Y = 0.0f;
	
	private ArrayList<float[]> uiLocationList = new ArrayList<float[]>();
	
	private final int UP_BUTTON       = 0;
	private final int DOWN_BUTTON     = 1;
	private final int RIGHT_BUTTON    = 2;
	private final int LEFT_BUTTON     = 3;
	private final int NEAR_BUTTON     = 4;
	private final int FAR_BUTTON      = 5;
	private final int AXIS_X_RADIO    = 6;
	private final int AXIS_Y_RADIO    = 7;
	private final int AXIS_Z_RADIO    = 8;
	private final int RETURN_BUTTON   = 9;
	private final int RESTART_BUTTON  = 10;
	private final int BGM_BUTTON      = 11;
	private final int MULTIPLE_BUTTON = 12;
	private final int NICHE_BUTTON    = 13;
	private final int NUMBER_BUTTON   = 14;
	
	private int detectInt           = -1;
	private float _aspect;
	
	public UITouchDetector(FixedUIEnum[] uiEnums){
		for(int i=0,j=uiEnums.length;i<j;i++){
			uiLocationList.add(uiEnums[i].getAllocation());
		}
		commonCameraRadius     = FixedUIEnum.getCommonCameraRadius();
		commonAxisRadius       = FixedUIEnum.getCommonAxisRadius();
		commonRectangleRadiusX = FixedUIEnum.getCommonRectangleRadiusX();
		commonRectangleRadiusY = FixedUIEnum.getCommonRectangleRadiusY();
		
	}
	
	public void setDisplaySize(float width,float height){
		displayWidth  = width;
		displayHeight = height;
		_aspect          = (float)width / (float)height;
	}
	
	public int touchDetect(float x,float y){
		float transX = 2 * x / displayWidth -1;
		float transY = -2 * y / displayHeight +1;
		detectUI(transX,transY);
		return detectInt;
	}
	
	private void detectUI(float x,float y){
		if(y > DEVIDED_UPPER_Y){
			if(detectUIUpper(x,y))return;
		}
		if(x > DEVIDED_RIGHT_X){
			if(detectUIRight(x,y))return;
		}
		if(x < DEVIDED_LEFT_X && y < DEVIDED_CENTER_Y){
			if(detectUILeft(x,y))return;
		}
		detectInt = -1;
	}
	
	private boolean detectUIUpper(float x,float y){
		if(onCircle(RETURN_BUTTON,commonAxisRadius,x,y)){
			detectInt = RETURN_BUTTON;
			return true;
		}
		if(onCircle(RESTART_BUTTON,commonAxisRadius,x,y)){
			detectInt = RESTART_BUTTON;
			return true;
		}
		if(onCircle(BGM_BUTTON,commonAxisRadius,x,y)){
			detectInt = BGM_BUTTON;
			return true;
		}
		return false;
	}
	
	private boolean detectUIRight(float x,float y){
		if(onCircle(NEAR_BUTTON,commonCameraRadius,x,y)){
			detectInt = NEAR_BUTTON;
			return true;
		}
		if(onCircle(FAR_BUTTON,commonCameraRadius,x,y)){
			detectInt = FAR_BUTTON;
			return true;
		}
		
		//三つのrectangleボタン(multi３種)は下端を基準点として表示されているようなので
		//y値の判定もそれに合わせて行う
		if(Math.abs(getUIX(MULTIPLE_BUTTON)-x)<commonRectangleRadiusX
				   && Math.abs( (getUIY(MULTIPLE_BUTTON)+COLLECTION_VALUE_Y) -y) < commonRectangleRadiusY){
			detectInt = MULTIPLE_BUTTON;
			return true;
		}
		if(Math.abs(getUIX(NUMBER_BUTTON)-x)<commonRectangleRadiusX
				   && Math.abs((getUIY(NUMBER_BUTTON)+COLLECTION_VALUE_Y)-y) < commonRectangleRadiusY){
			detectInt = NUMBER_BUTTON;//MULTIPLE と同じ動作
			return true;
		}
		if(Math.abs(getUIX(NICHE_BUTTON)-x)<commonRectangleRadiusX
				   && Math.abs((getUIY(NICHE_BUTTON)+COLLECTION_VALUE_Y) -y) < commonRectangleRadiusY){
			detectInt = NICHE_BUTTON;
			return true;
		}
		return false;
	}
	
	private boolean detectUILeft(float x,float y){
		if(onCircle(UP_BUTTON,commonCameraRadius,x,y)){
			detectInt = UP_BUTTON;
			return true;
		}
		if(onCircle(DOWN_BUTTON,commonCameraRadius,x,y)){
			detectInt = DOWN_BUTTON;
			return true;
		}
		if(onCircle(RIGHT_BUTTON,commonCameraRadius,x,y)){
			detectInt = RIGHT_BUTTON;
			return true;
		}
		if(onCircle(LEFT_BUTTON,commonCameraRadius,x,y)){
			detectInt = LEFT_BUTTON;
			return true;
		}
		if(onCircle(AXIS_X_RADIO,commonAxisRadius,x,y)){
			detectInt = AXIS_X_RADIO;
			return true;
		}
		if(onCircle(AXIS_Y_RADIO,commonAxisRadius,x,y)){
			detectInt = AXIS_Y_RADIO;
			return true;
		}
		if(onCircle(AXIS_Z_RADIO,commonAxisRadius,x,y)){
			detectInt = AXIS_Z_RADIO;
			return true;
		}
		return false;
	}
	
	private float getUIX(int index){
		return uiLocationList.get(index)[0];
	}
	
	private float getUIY(int index){
		return uiLocationList.get(index)[1];
	}
	
	private boolean onCircle(int id, float rad, float x, float y){
		Log.e("getUIX(id)", String.valueOf(getUIX(id)));
		Log.e("getUIY(id)", String.valueOf(getUIY(id)));
		Log.e("x", String.valueOf(x));
		Log.e("y", String.valueOf(y));
		Log.e("rad * rad", String.valueOf(rad*rad));
		float correctedY = y / _aspect;
		float correctedLocationY = getUIY(id) / _aspect;
		Log.e("correctedY ", String.valueOf(correctedY));
		boolean isDetect = BaseCalculator.calcSquaredDist(getUIX(id), correctedLocationY, x, correctedY) < rad * rad;
		Log.e("isDetect ", String.valueOf(isDetect));
//		return BaseCalculator.calcSquaredDist(getUIX(id), getUIY(id), x, y) < rad * rad;
		return isDetect;
	}
	
}
