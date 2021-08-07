package com.honestastrology.glblocks;

import java.util.ArrayList;

public class CameraUIRepeater {
	
	private static CameraUIRepeater _cameraUIRepeater = new CameraUIRepeater();
	private static OnTouchedPointerId[] onTouchedUIList;
	private static final int  TOUCHABLE_UI_NUMBER = 15;
	private static final int REPEATABLE_UI_NUMBER = 6;
	private ModelProcessor _modelProcessor;
	
	private CameraUIRepeater(){
		onTouchedUIList = new OnTouchedPointerId[TOUCHABLE_UI_NUMBER];
		for(int i=0;i<TOUCHABLE_UI_NUMBER;i++){
			onTouchedUIList[i] = new OnTouchedPointerId();
		}
	}
	
	public static CameraUIRepeater getInstance(){
		return _cameraUIRepeater;
	}
	
	public void setModelProcessor(ModelProcessor modelProcessor){
		_modelProcessor = modelProcessor;
	}
	
	public void repeatUIControl(){
		for(int i=0;i<REPEATABLE_UI_NUMBER;i++){
			if(onTouchedUIList[i].isTouched()){
				_modelProcessor.onTouch(i, 0.0f, 0.0f);
			}
		}
	}
	
	public void onUpMotion(int pointerId){
		for(int i=0;i<TOUCHABLE_UI_NUMBER;i++){
			if(onTouchedUIList[i].getPointerId() == pointerId){
				onTouchedUIList[i].setTouched(false);
				onTouchedUIList[i].setPointerId(-1);
			}
		}
	}
	
	public boolean queryRepeatId(int pointerId){
		for(int i=0;i<TOUCHABLE_UI_NUMBER;i++){
			if(onTouchedUIList[i].getPointerId() == pointerId){
				return true;
			}
		}
		return false;
	}
	
	public void stopAllRepeat(){
		for(int i=0;i<TOUCHABLE_UI_NUMBER;i++){
			if(onTouchedUIList[i].isTouched()){
				onTouchedUIList[i].setTouched(false);
				onTouchedUIList[i].setPointerId(-1);
			}
		}
	}
	
	public boolean isTouched(int uiNumber){
		return onTouchedUIList[uiNumber].isTouched();
	}
	
	public int getTouchedPointerId(int uiNumber){
		return onTouchedUIList[uiNumber].getPointerId();
	}
	
	public void setUIRepeater(int uiNumber, boolean istouched,int pointerId){
		onTouchedUIList[uiNumber].setTouched(istouched);
		onTouchedUIList[uiNumber].setPointerId(pointerId);
	}
	
	public void setUITouched(int uiNumber,boolean isTouched){
		onTouchedUIList[uiNumber].setTouched(isTouched);
	}
	
	public void setPointerId(int uiNumber,int pointerId){
		onTouchedUIList[uiNumber].setPointerId(pointerId);
	}
	
	
	public class OnTouchedPointerId{
		
		private boolean isTouched = false;
		private int      pointerId = -1;
		
		private OnTouchedPointerId(){
		}
		
		public boolean isTouched() {
			return isTouched;
		}
		public void setTouched(boolean isTouched) {
			this.isTouched = isTouched;
		}
		public int getPointerId() {
			return pointerId;
		}
		public void setPointerId(int pointerId) {
			this.pointerId = pointerId;
		}
		
	}
	
}
