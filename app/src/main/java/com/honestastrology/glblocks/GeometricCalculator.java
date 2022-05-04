package com.honestastrology.glblocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.opengl.GLU;
import android.opengl.Matrix;
import android.util.Log;

public class GeometricCalculator {
	
	private ViewProcessor _viewProcessor;
	private int[]        _viewport;
	private float[]      _viewMatrix;
	private float[]      _projectionMatrix;
	private HashMap<Integer, Float> selectedObjMap;
	private float minPointToObj;
	private float minDistanceRay;
	private int nearIndex;
	private int rayDistanceIndex;
	private static int selectedIndex = -1;
	private int lastSelectedIndex     = -1;
	private float[] axisForScreenZ = new float[4];
	private float[] _axisMatrix    = new float[16];
	
	private final float[] screenXVector={1.0f,0.0f};
	private final float[] screenYVector={0.0f,1.0f};
	private float[] dirXVector = new float[2];
	private float[] dirYVector = new float[2];
	private float[] dirZVector = new float[2];
	private double swipeAndXRad;
	private double swipeAndYRad;
	private double swipeAndZRad;
	private double swipeAndYAxisRad;
	private static int axisRadioInt = -1;
	private static boolean multipleBool = false;
	
	public static final int MOVE_X_PLUS  = 0;
	public static final int MOVE_X_MINUS = 1;
	public static final int MOVE_Y_PLUS  = 2;
	public static final int MOVE_Y_MINUS = 3;
	public static final int MOVE_Z_PLUS  = 4;
	public static final int MOVE_Z_MINUS = 5;
	
	private static int _sceneNumber;
	
	public GeometricCalculator(ViewProcessor viewprocessor){
		_viewProcessor = viewprocessor;
	}
	
	public void initGeoCalc(){
		_viewport         = _viewProcessor.getViewPort();
		_projectionMatrix = _viewProcessor.getPMatrix();
		selectedIndex     = -1;
		lastSelectedIndex = -1;
		_sceneNumber	  = GLBlocksActivity.getSceneNumber();
		Log.e("geometric calculator","geo initialize");
	}
	
	public void updateViewMatrix(){
		_viewMatrix       = _viewProcessor.getVMatrix();
	}
	
	public static int getSelectedIndex(){
		return selectedIndex;
	}
	
	public void changeSegmentAtWorld(float screenX,float screenY,ArrayList<GLBaseObject> objList){
		float pointY=_viewport[3]-screenY;
		float[] rayNear=new float[4];
		float[] rayFar=new float[4];
		GLU.gluUnProject(screenX, pointY, 0.0f, _viewMatrix, 0, _projectionMatrix, 0, _viewport, 0, rayNear, 0);
		GLU.gluUnProject(screenX, pointY, 1.0f, _viewMatrix, 0, _projectionMatrix, 0, _viewport, 0, rayFar, 0);
		detectIntersection(rayNear,rayFar,objList);
	}
	
	public void detectIntersection(float[] near,float[] far,ArrayList<GLBaseObject> objList){
		float[] rayNear=new float[]{
				near[0]/near[3],near[1]/near[3],near[2]/near[3]
		};
		float[] rayFar=new float[]{
				far[0]/far[3],far[1]/far[3],far[2]/far[3]
		};
		float[] rayVec=new float[]{
				rayFar[0]-rayNear[0],rayFar[1]-rayNear[1],rayFar[2]-rayNear[2]
		};
		
		float[] objPlace;
		float[] nearToObj;
		float   dotACAC;
		float   dotABAC;
		float   dotABAB=BaseCalculator.calcDot(rayVec, rayVec);
		float lineObjDistance;
		boolean selectBool=false;
		float minDistance;
		float absDistanceObj;
		int keyNum;
		
		boolean testFirstBool=true;
		
		selectedObjMap = new HashMap<Integer,Float>();
		
		selectRay:for(int i=0,j=objList.size();i<j;i++){
			if(objList.get(i).isWall())continue selectRay;
			if( objList.get(i).isBesideWall() )continue selectRay;
			objPlace  = objList.get(i).getPlace();
			nearToObj = BaseCalculator.calcDistance(objPlace, rayNear);
			dotACAC   = BaseCalculator.calcDot(nearToObj, nearToObj);
			dotABAC   = BaseCalculator.calcDot(rayVec, nearToObj);
			lineObjDistance = dotACAC - ( dotABAC * dotABAC / dotABAB );
			absDistanceObj  = BaseCalculator.calcAbs(nearToObj);
			if(2.0f > lineObjDistance){
				selectedObjMap.put(i, absDistanceObj);
				selectBool = true;
			}
			
			if(testFirstBool){
				minPointToObj    = lineObjDistance;
				minDistanceRay   = absDistanceObj;
				nearIndex        = i;
				rayDistanceIndex = i;
				testFirstBool    = false;
			}
			
			if(minPointToObj > lineObjDistance){
				minPointToObj = lineObjDistance;
				nearIndex     = i;
			}
			
			if(minDistanceRay > absDistanceObj){
				minDistanceRay   = absDistanceObj;
				rayDistanceIndex = i;
			}
			
		}
		
		if(selectBool){
			Iterator ite=selectedObjMap.keySet().iterator();
			if(ite.hasNext()){
				keyNum            = Integer.parseInt(ite.next().toString());
				minDistance       = selectedObjMap.get(keyNum);
				lastSelectedIndex = selectedIndex;
				selectedIndex     = keyNum;
				while(ite.hasNext()){
					keyNum = Integer.parseInt(ite.next().toString());
					if(minDistance > selectedObjMap.get(keyNum)){
						minDistance   = selectedObjMap.get(keyNum);
						selectedIndex = keyNum;
					}
				}
			}
			
			if(selectedIndex > -1){
				if(lastSelectedIndex > -1){
					if(!isMultipleSelect()){
						objList.get(lastSelectedIndex).defaultMtl();
						objList.get(lastSelectedIndex).setSelectedBool(false);
						((GLInferiorTexObject)objList.get(lastSelectedIndex)).checkAndDesideDownHighlight();
						
					}
				}
				
				if(isMultipleSelect()){
					if(objList.get(selectedIndex).isSelected()){
						objList.get(selectedIndex).defaultMtl();
						objList.get(selectedIndex).setSelectedBool(false);
						((GLInferiorTexObject)objList.get(selectedIndex)).checkAndDesideDownHighlight();
					}else{
						objList.get(selectedIndex).setSelectedBool(true);
					}
				}else{
					objList.get(selectedIndex).setSelectedBool(true);
				}
				//if(axisRadioInt>-1)setAxisBar(axisRadioInt);
			}
		}
	}
	
	public void onScreenAxis(){
		float[] eyeCenter = _viewProcessor.getEyeCenter();
		float[] minusZ=new float[]{
				eyeCenter[0],eyeCenter[1],eyeCenter[2]+1.0f,1.0f
		};
		float[] plusX=new float[]{
				eyeCenter[0]+1.0f,eyeCenter[1],eyeCenter[2],1.0f
		};
		float[] plusY=new float[]{
				eyeCenter[0],eyeCenter[1]+1.0f,eyeCenter[2],1,0f
		};
		dirZVector=calcAxis(minusZ);
		dirXVector=calcAxis(plusX);
		dirYVector=calcAxis(plusY);
	}
	
	public float[] calcAxis(float[] preAxis){
		Matrix.multiplyMM(_axisMatrix , 0, _projectionMatrix, 0, _viewMatrix, 0);
		Matrix.multiplyMV(axisForScreenZ, 0, _axisMatrix, 0, preAxis, 0);
		//float w = axisForScreenZ[3]/axisForScreenZ[2];
		float x = (axisForScreenZ[0]/axisForScreenZ[3]*_viewport[2]+_viewport[2])/2;
		float y = (-(axisForScreenZ[1]/axisForScreenZ[3]*_viewport[3])+_viewport[3])/2;
		float[] axisVector = new float[]{x-_viewport[2]/2,y-_viewport[3]/2};
		return axisVector;
		/* test axis direction
		float[] v=new float[]{
			eyeCenterX+1.0f,eyeCenterY,eyeCenterZ
		};
		mBaseManager.objArray.get(6).setPlace(v);
		onTouchScreen(x, y);
		*/
	}
	
	
	
	
	public int onSwipe(float[] xy){
		if(selectedIndex>-1 || getAxisRadioInt()>-1){
			if(getAxisRadioInt()>-1){
				switch(getAxisRadioInt()){
				case 0:
					swipeAndXRad=BaseCalculator.calcVectorTheta(xy, dirXVector);
					if(swipeAndXRad<Math.PI/2){
						return 0;
					}else{
						return 1;
					}
				case 1:
					swipeAndYRad=BaseCalculator.calcVectorTheta(xy, dirYVector);
					if(swipeAndYRad<Math.PI/2){
						return 2;
					}else{
						return 3;
					}
					
				case 2:
					swipeAndZRad=BaseCalculator.calcVectorTheta(xy, dirZVector);
					if(swipeAndZRad<Math.PI/2){
						return 4;
					}else{
						return 5;
					}
				}
			}else{
				// x方向を角度０とする
				swipeAndYAxisRad=BaseCalculator.calcVectorTheta(xy, screenYVector);
				if(swipeAndYAxisRad<Math.PI/12){
					return 3;
					
				}else if(swipeAndYAxisRad>Math.PI*11/12){
					return 2;
					
				}
				//axisXZApparentRad=CollisionDetector.calcVectorTheta(dirXVector, dirZVector);
				//if(axisXZApparentRad<Math.PI/12||axisXZApparentRad>(Math.PI*11/12)){}
				swipeAndXRad=BaseCalculator.calcVectorTheta(xy, dirXVector);
				if(swipeAndXRad<Math.PI/12){
					return 0;
				}else if(swipeAndXRad>Math.PI*11/12){
					return 1;
				}else{
					swipeAndZRad=BaseCalculator.calcVectorTheta(xy, dirZVector);
					if(swipeAndZRad<Math.PI/12){
						return 4;
					}else if(swipeAndZRad>Math.PI*11/12){
						return 5;
					}
				}
					
				
			}
			
		}
		return -1;
	}

	public static int getAxisRadioInt() {
		return axisRadioInt;
	}

	public static void setAxisRadioInt(int axisRadioInt) {
		GeometricCalculator.axisRadioInt = axisRadioInt;
	}

	public static boolean isMultipleSelect() {
		return multipleBool;
	}

	public static void setMultipleBool(boolean multipleBool) {
		GeometricCalculator.multipleBool = multipleBool;
	}
	
	public static void resetSelectedIndex(){
		selectedIndex = -1;
	}
	
	/*
	public void objMoveDirection(int direction){
		if(InferiorCollisionDetector.nowMoving)return;
		velocity=0.1f;
		objArray.get(selectedIndex).setMovingBool(true);
		objArray.get(selectedIndex).setMoveDirection(direction);
		InferiorCollisionDetector.countBool=true;
		//objMove(selectedIndex);
		//CollisionDetector.startTravel();
	}
	*/
	
	
}
