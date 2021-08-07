package com.honestastrology.glblocks;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

public class ModelProcessor {
	
	private ViewProcessor _viewProcessor;
	
	private SceneManager    _sceneManager;
	private ObjManager      _objManager;
	private GeometryManager _geometryManager;
	private UIManager       _uiManager;
	
	private boolean nowMoving    = false;
	private boolean finishBool   = false;
	private boolean completeBool = false;
	private int      finishCount  = 0;
	
	public ModelProcessor(Context context,ViewProcessor viewProcessor){
		_viewProcessor = viewProcessor;
		initialize(context);
	}
	
	private void initialize(Context context){
		_sceneManager    = new SceneManager();
		_objManager      = ObjManager.getInstance(context);
		_geometryManager = new GeometryManager(_viewProcessor);
	}
	
	public void initGeoCalc(){
		initializeCamera();
		_geometryManager.initGeoCalc();
		updateCamera();
	}
	
	public void modelProcess(){
		movingProcessBranch();
		_objManager.objAffine(_viewProcessor.getVMatrix(),_viewProcessor.getPMatrix());
		_objManager.commonObjAffine(_viewProcessor.getVMatrix(),_viewProcessor.getPMatrix());
	}
	
	private void movingProcessBranch(){
		if(!completeBool){
			if(nowMoving){
				if(!_objManager.isReadyCollision()){
					_objManager.detectAllCube();
				}
			}
			if(_objManager.movingProgress()){
				nowMoving = false;
			}
			if(finishBool){
				finishPrepare();
			}else{
				finishBool = ObjManager.isFinishStage();
			}
		}else{
			finishCount++;
		}
		if(finishCount == 60 ){
			finishProcess();
		}
		
	}
	
	public void sceneUpdate(int sceneNum){
		_sceneManager.update(sceneNum);
		_objManager.sceneUpdate(sceneNum);
	}
	
	public GLBaseObject getObject(int index){
		return _objManager.getObject(index);
	}
	
	public void drawObject(){
		_objManager.drawObject();
	}
	
	public void drawCommonObj(){
		_objManager.drawCommonObj();
	}
	
	public TexManager getTexManager(){
		return _objManager.getTexManager();
	}
	
	public FixedUIEnum[] createUI(){
		FixedUIEnum[] uiEnums = UIManager.createUI();
		_uiManager            = UIManager.getInstance();
		return uiEnums;
	}
	
	public int onTouch(int number,float x,float y){
		if(number>-1){
			_uiManager.onTouchUI(number);
			updateCamera();
		}else{
			_geometryManager.onDown(x,y,_objManager.getObjList());
		}
		return number;
	}
	
	public void onSwipe(float[] xy){
		if(nowMoving)return;
		int direction = _geometryManager.onSwipe(xy);
		if(direction>-1){
			nowMoving    = true;
			_objManager.setCountOK(true);
			_objManager.setMoveDistance(0.0f);
			_objManager.setStrokeParFrame(0);
			if(GLIrregularFixedUI.isMultipleButtonOn()){
				_objManager.moveMultiple(direction);
			}else{
				int selected = _geometryManager.getSelectedObj();
				if(selected > -1){
					_objManager.moveSingle(selected,direction);
				}
			}
		}
	}
	
	public void initializeCamera(){
		_viewProcessor.updateVPMatrix();
		_geometryManager.updateViewMatrix();
	}
	
	public void updateCamera(){
		_viewProcessor.updateVPMatrix();
		_geometryManager.updateViewMatrix();
		_geometryManager.updateScreenAxis();
	}
	
	private void finishPrepare(){
		SoundManager.finishEffect();
		_objManager.allHighlight();
		RadioUIGroup.setAxisBool(false);
		completeBool = true;
	}
	
	private void finishProcess(){
		CollateralConnector.getInstance().getGLBlocksActivity().stageFinish();
	}
	
}
