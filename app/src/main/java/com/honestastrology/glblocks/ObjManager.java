package com.honestastrology.glblocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.content.Context;
import android.opengl.Matrix;
import android.util.Log;

public class ObjManager {
	
	private static ObjManager _objManager = new ObjManager();
	private static ObjLoader _objLoader;
	private static ArrayList<GLBaseObject> _objList;
	private static HashMap<Integer,Integer>  _movingMap;
	private static ArrayList<Integer>       _removeList;
	private int[] drawingOrder;
	private static TexManager _texManager;
	private static ArrayList<GLBaseObject> _wallObjs;
	private static ArrayList<GLBaseObject> _barObjs;
	private static Iterator _iterator;
	
	private boolean readyCollision = false;
	private float     moveDistance;
	private boolean        countOK;
	private boolean    travelValid;
	private int      strokeParFrame = 0;
	private static int squareCount = 0;
	
	private static boolean isFinishStage = false;
	private static int   cubeCountInNiche = 0;
	
	private static ArrayList<float[]> squareBesideWall;
	
	private static final float[] DEFAULT_BAR_PLACE = {0.0f,0.0f,250.0f};
	
	public static int loopSize     = 0;
	private static int clearCount  = 0;
	
	private ObjManager(){
	}
	
	public static void copyFlyweightBar(GLInferiorObject miniBar){
		_objLoader.registerFlyWeightBar( miniBar );
	}
	
	public static GLBaseObject getAreaCube(){
		return _wallObjs.get( 0 );
	}
	
	public static ObjManager getInstance(Context context){
		long startTime = System.currentTimeMillis();
		_texManager      = new TexManager(context);
		_objLoader       = new ObjLoader(context);
		_objList         = new ArrayList<GLBaseObject>();
		_movingMap       = new HashMap<Integer,Integer>();
		_removeList      = new ArrayList<Integer>();
		_barObjs         = _objLoader.loadBarObject();
		_wallObjs        = _objLoader.loadWallObject();
		_objList         = _objLoader.loadObjArray();
		prepareForNiche();
		checkFinishAllCubes();
		setFinishStage(false);
		long stopTime = System.currentTimeMillis();
		long rap      = stopTime - startTime;
		Log.e("loading_time", "rap "+String.valueOf(rap));
		return _objManager;
	}
	
	public void detectAllCube(){
		_movingMap = CubeCollision.detectAllCube(_objList);
		setReadyCollision(true);
	}
	
	public boolean movingProgress(){
		if(_movingMap.isEmpty()){
			setReadyCollision(false);
			return true;
		}
		_iterator  = ((HashMap<Integer, Integer>) _movingMap.clone()).keySet().iterator();
		int movingIndex = -1;
		int targetIndex = -2;
		int soundId     = -1;
		setTravelValid(true);
		while(_iterator.hasNext()){
			movingIndex = (Integer)_iterator.next();
			targetIndex = _movingMap.get(movingIndex);
			if(!CubeCollision.detectCubeCollision(movingIndex,targetIndex,
					_objList, CurrentScene.getAreaFloat())){
				soundId = SoundManager.COLLISION_EFFECT_WALL;
				_objList.get(movingIndex).setMovingBool(false);
				_objList.get(movingIndex).setWaiting(true);
				_removeList.add(movingIndex);
				if(targetIndex > -1){
					soundId = SoundManager.randomSE();
					_objList.get(targetIndex).setMoveDirection(_objList.get(movingIndex).getMoveDirection());
					int nextTarget = CubeCollision.nearestObjectIndex(_objList,
							targetIndex,_objList.get(targetIndex).getMoveDirection());
					if(!_objList.get(targetIndex).isWaiting()){
						_movingMap.put(targetIndex, nextTarget);
					}
				}
				SoundManager.playSoundEffect(soundId);
			}else{
				_objList.get(movingIndex).objMove();
				if(isTravelValid()){
					checkCount();
					setTravelValid(false);
				}
			}
		}
		if(!_removeList.isEmpty()){
			for(int i=0,j=_removeList.size();i<j;i++){
				_movingMap.remove(_removeList.get(i));
			}
		}
		_removeList.clear();
		if(_movingMap.isEmpty()){
			setReadyCollision(false);
			_movingMap.clear();
			isFinishStage = checkFinishAllCubes();
		}
		return _movingMap.isEmpty();
	}
	
	public void objAffine(float[] vMatrix,float[] pMatrix){
		loopSize =_objList.size();
		affineProcess(vMatrix,pMatrix,_objList);
	}
	
	public void commonObjAffine(float[] vMatrix,float[] pMatrix){
		barAffine(vMatrix,pMatrix);
		wallAffine(vMatrix,pMatrix);
	}
	
	public void wallAffine(float[] vMatrix,float[]pMatrix){
		affineProcess(vMatrix,pMatrix,_wallObjs);
	}
	
	public void barAffine(float[] vMatrix, float[] pMatrix){
		multipleBarAffine();
		affineProcessForBar(vMatrix,pMatrix,_barObjs);
	}
	
	public void multipleBarAffine(){
		if(_objList.size() == 0)return;
		for(int i=0,j=_barObjs.size();i<j;i++){
			if(_objList.get(i).isSelected() && RadioUIGroup.getAxisBool()){
				_barObjs.get(i).setPlace(_objList.get(i).getPlace());
				_barObjs.get(i).setRotate(RadioUIGroup.getAxisRotate());
			}else{
				_barObjs.get(i).setPlace(DEFAULT_BAR_PLACE);
			}
		}
	}
	
	public void affineProcess(float[] vMatrix, float[] pMatrix,
			ArrayList<GLBaseObject> objList){
		for(int i=0,j=objList.size();i<j;i++){
			float[] MVMatrix  = setAffine(objList,vMatrix.clone(),i);
			float[] MVPMatrix = new float[16];
			Matrix.multiplyMM(MVPMatrix,0,pMatrix,0,MVMatrix,0);
			objList.get(i).setMVPMatrix(MVPMatrix);
		}
	}
	
	private void affineProcessForBar(float[] vMatrix, float[] pMatrix,
			ArrayList<GLBaseObject> objList){
		barAffine:for(int i=0,j=objList.size();i<j;i++){
			if(objList.get(i).getPlaceZ() == 250.0f)continue barAffine;
			float[] MVMatrix  = new float[16];
			float[] MVPMatrix = new float[16];
			MVMatrix = setAffine(objList,vMatrix.clone(),i);
			Matrix.multiplyMM(MVPMatrix,0,pMatrix,0,MVMatrix,0);
			objList.get(i).setMVPMatrix(MVPMatrix);
		}
	}
	
	public void sceneUpdate(int sceneNum){
		_barObjs    = _objLoader.loadBarObject();
		_wallObjs   = _objLoader.loadWallObject();
		_objList    = _objLoader.update(sceneNum);
	}
	
	public float[] setAffine(ArrayList<GLBaseObject> objList,float[] viewMatrix, int index){
		Matrix.translateM(viewMatrix, 0,
				objList.get(index).getPlaceX(),
				objList.get(index).getPlaceY(),
				objList.get(index).getPlaceZ());
		Matrix.rotateM(viewMatrix, 0, 
				objList.get(index).getRotate()[0],
				objList.get(index).getRotate()[1],
				objList.get(index).getRotate()[2],
				objList.get(index).getRotate()[3]);
		Matrix.scaleM(viewMatrix, 0,
				objList.get(index).getScale()[0],
				objList.get(index).getScale()[1],
				objList.get(index).getScale()[2]);
		return viewMatrix;
	}
	
	public GLBaseObject  getObject(int index){
		return _objList.get(index);
	}
	
	public ArrayList<GLBaseObject> getObjList(){
		return _objList;
	}
	
	public TexManager getTexManager(){
		return _texManager;
	}
	
	public void drawObject(){
		drawSeparatedByGroup(0);
		drawSeparatedByGroup(1);
		GLInferiorTexObject.frameProgressHighlight();
	}
	
	private void drawSeparatedByGroup(int objGroupIndex){
		GLInferiorTexObject.setFirstDrawn(true);
		GLInferiorTexObject.setLastMtlPattern(GLInferiorTexObject.MTL_INITIALIZE);
		for(int i=0,j=_objList.size();i<j;i++){
			_objList.get(i).drawObject(objGroupIndex);
		}
		GLInferiorTexObject.separatedDisableVertexAttribArray();
	}
	
	public void drawCommonObj(){
		for(int i=0,j=_barObjs.size();i<j;i++){
			_barObjs.get(i).drawObject();
		}
		_wallObjs.get(1).drawObject();
		_wallObjs.get(0).drawObject();
	}
	
	public void moveSingle(int index, int direction) {
		_objList.get(index).setMoveDirection(direction);
		SoundManager.playSoundEffect(SoundManager.randomSE());
	}
	
	public void moveMultiple(int direction){
		for(int i=0,j=_objList.size();i<j;i++){
			if(_objList.get(i).isSelected()){
				_objList.get(i).setMoveDirection(direction);
			}
		}
		GLIrregularFixedUI.decreaseMultipleCount();
		SoundManager.playSoundEffect(SoundManager.randomSE());
	}
	
	public static void defaultAllObjMtl(){
		for(int i=0,j=_objList.size();i<j;i++){
			_objList.get(i).defaultMtl();
			_objList.get(i).setSelectedBool(false);
		}
		checkFinishAllCubes();
		GeometricCalculator.resetSelectedIndex();
	}
	
	public static void defaultAllBar(){
		for(int i=0,j=_barObjs.size();i<j;i++){
			_barObjs.get(i).setPlace(DEFAULT_BAR_PLACE);
		}
	}
	
	public boolean isReadyCollision() {
		return readyCollision;
	}

	public void setReadyCollision(boolean readyCollision) {
		this.readyCollision = readyCollision;
	}
	
	public float getMoveDistance() {
		return moveDistance;
	}

	public void setMoveDistance(float moveDistance) {
		this.moveDistance = moveDistance;
	}

	public static ArrayList<GLBaseObject> getSaveArray(){
		return _objList;
	}
	
	private void checkCount(){
		setStrokeParFrame(getStrokeParFrame()+1);
		if(!isCountOK())return;
		setMoveDistance(getMoveDistance() + 0.2f);
		if(getMoveDistance() > 1.7f){
			SystemAnalyzer.incrementMoveCount();
			CollateralConnector.getInstance().callActivityNotify();
			setCountOK(false);
		}
	}
	
	public boolean isCountOK() {
		return countOK;
	}

	public void setCountOK(boolean countOK) {
		this.countOK = countOK;
	}

	public int getStrokeParFrame() {
		return strokeParFrame;
	}

	public void setStrokeParFrame(int strokeParFrame) {
		this.strokeParFrame = strokeParFrame;
	}

	public boolean isTravelValid() {
		return travelValid;
	}

	public void setTravelValid(boolean travelValid) {
		this.travelValid = travelValid;
	}

	public static boolean isFinishStage() {
		return isFinishStage;
	}

	public static void setFinishStage(boolean isfinishstage) {
		isFinishStage = isfinishstage;
	}

	public static boolean checkFinishAllCubes(){
		cubeCountInNiche = 0;
		checkCubeBesideWall(CurrentScene.getWallDimension());
		return cubeCountInNiche == clearCount;
	}
	
	private static void checkCubeBesideWall(int dimension){
		switch(dimension){
		case 1:
			for(int k=0,l=_objList.size();k<l;k++){
				if(_objList.get(k).getPlaceZ() == 1.0f){
					((GLInferiorTexObject)_objList.get(k)).setBesideWall(true);
					((GLInferiorTexObject)_objList.get(k)).downHighlightMtl();
					cubeCountInNiche++;
				}else{
					((GLInferiorTexObject)_objList.get(k)).setBesideWall(false);
					_objList.get(k).defaultMtl();
				}
			}
			break;
		case 2:
			for(int k=0,l=_objList.size();k<l;k++){
				if(_objList.get(k).getPlaceZ() == 1.0f){
					((GLInferiorTexObject)_objList.get(k)).setBesideWall(true);
					((GLInferiorTexObject)_objList.get(k)).downHighlightMtl();
					cubeCountInNiche++;
				}else if(_objList.get(k).getPlaceX() == 1.0f){
					((GLInferiorTexObject)_objList.get(k)).setBesideWall(true);
					((GLInferiorTexObject)_objList.get(k)).downHighlightMtl();
					cubeCountInNiche++;
				}else{
					((GLInferiorTexObject)_objList.get(k)).setBesideWall(false);
					_objList.get(k).defaultMtl();
				}
			}
			break;
		case 3:
			for(int k=0,l=_objList.size();k<l;k++){
				if(_objList.get(k).getPlaceZ() == 1.0f){
					((GLInferiorTexObject)_objList.get(k)).setBesideWall(true);
					((GLInferiorTexObject)_objList.get(k)).downHighlightMtl();
					cubeCountInNiche++;
				}else if(_objList.get(k).getPlaceY() == 1.0f){
					((GLInferiorTexObject)_objList.get(k)).setBesideWall(true);
					((GLInferiorTexObject)_objList.get(k)).downHighlightMtl();
					cubeCountInNiche++;
				}else if(_objList.get(k).getPlaceX() == 1.0f){
					((GLInferiorTexObject)_objList.get(k)).setBesideWall(true);
					((GLInferiorTexObject)_objList.get(k)).downHighlightMtl();
					cubeCountInNiche++;
				}else{
					((GLInferiorTexObject)_objList.get(k)).setBesideWall(false);
					_objList.get(k).defaultMtl();
				}
			}
			break;
		}
	}
	
	public static void prepareForNiche(){
		int areaLength   = (int)CurrentScene.getAreaFloat()[0]/2;
		squareCount      = (int) Math.pow((double)areaLength,2.0);
		clearCount       = calcClearCount(areaLength,squareCount);
		squareBesideWall = new ArrayList<float[]>(squareCount);
		for(int i=0;i<areaLength;i++){
			for(int j=0;j<areaLength;j++){
				float[] coord = new float[2];
				coord[0] = (float) j * 2 + 1;
				coord[1] = (float) i * 2 + 1;
				squareBesideWall.add(coord);
			}
		}
	}
	
	public static void searchNiche(){
		int         checkingAxis = decideCheckingAxis();
		int planeCoordinateAxisA = CubeCollision.convertAxisInt(checkingAxis+1);
		int planeCoordinateAxisB = CubeCollision.convertAxisInt(checkingAxis-1);
		
		defaultAllObjMtl();
		
		ArrayList<float[]> copiedArray      = (ArrayList<float[]>) squareBesideWall.clone();
		ArrayList<Integer> floatCubeIndices  = new ArrayList<Integer>();
		ArrayList<Integer> deleteIndices     = new ArrayList<Integer>();
		ArrayList<Integer> activeCubeIndices = new ArrayList<Integer>();
		
		for(int i=0,j=_objList.size();i<j;i++){
			if(_objList.get(i).getPlace()[checkingAxis] == 1.0f){
				for(int k=0;k<squareCount;k++){
					if(_objList.get(i).getPlace()[planeCoordinateAxisA] == copiedArray.get(k)[0]){
						if(_objList.get(i).getPlace()[planeCoordinateAxisB] == copiedArray.get(k)[1]){
							deleteIndices.add(k);
						}
					}
				}
			}else{
				floatCubeIndices.add(i);
			}
		}
		
		for(int i=copiedArray.size();i>-1;i--){
			for(int j=0,k=deleteIndices.size();j<k;j++){
				if(i == deleteIndices.get(j)){
					copiedArray.remove(i);
				}
			}
		}
		
		for(int i=0,j=copiedArray.size();i<j;i++){
			for(int k=0,l=floatCubeIndices.size();k<l;k++){
				int floatCubeIndex = floatCubeIndices.get(k);
				if(copiedArray.get(i)[0] == _objList.get(floatCubeIndex).getPlace()[planeCoordinateAxisA]){
					if(copiedArray.get(i)[1] == _objList.get(floatCubeIndex).getPlace()[planeCoordinateAxisB]){
						activeCubeIndices.add(floatCubeIndex);
						_objList.get(floatCubeIndex).setSelectedBool(true);
					}
				}
			}
		}
		
		for(int i=0,j=activeCubeIndices.size();i<j;i++){
			for(int k=i+1;k<j;k++){
				if(_objList.get(activeCubeIndices.get(i)).getPlace()[planeCoordinateAxisA]
						== _objList.get(activeCubeIndices.get(k)).getPlace()[planeCoordinateAxisA]){
					if(_objList.get(activeCubeIndices.get(i)).getPlace()[planeCoordinateAxisB] 
							== _objList.get(activeCubeIndices.get(k)).getPlace()[planeCoordinateAxisB]){
						_objList.get(activeCubeIndices.get(i)).setSelectedBool(false);
					}
				}
			}
		}
		RadioUIGroup.decideAxis(checkingAxis);
		RadioUIGroup.setCheckedAxisForNiche(checkingAxis);
	}
	
	private static int decideCheckingAxis(){
		int checkingAxis = GeometricCalculator.getAxisRadioInt();
		if(checkingAxis == -1){
			checkingAxis = 2;
		}
		return checkingAxis;
	}
	
	private static int calcClearCount(int areaLength, int squareCount){
		int result = 0;
		switch(CurrentScene.getWallDimension()){
		case 1:
			result = squareCount;
			break;
		case 2:
			result = squareCount * 2 - areaLength;
			break;
		case 3:
			result = squareCount * 3 - areaLength * 3 + 1;
		}
		return result;
	}
	
	public void allHighlight(){
		for(int i=0,j=_objList.size();i<j;i++){
			_objList.get(i).setSelectedBool(true);
		}
	}
	
}
