package com.honestastrology.glblocks;

public class ControlProcessor {
	
	private ModelProcessor _modelProcessor;
	private ViewProcessor _viewProcessor;
	private UITouchDetector _touchDetector;
	private CameraUIRepeater _cameraUIRepeater;
	
	public ControlProcessor(ModelProcessor modelProcessor,ViewProcessor viewProcessor){
		_modelProcessor   = modelProcessor;
		_viewProcessor    = viewProcessor;
		_touchDetector    = new UITouchDetector(_modelProcessor.createUI());
		_cameraUIRepeater = CameraUIRepeater.getInstance();
		_cameraUIRepeater.setModelProcessor(_modelProcessor);
	}
	
	public void setDisplaySize(float width,float height){
		_touchDetector.setDisplaySize(width,height);
	}
	
	public void controlProcess(){
		_cameraUIRepeater.repeatUIControl();
	}
	
	public int onTouch(float x,float y,int pointerId){
		int detectedNumber = _touchDetector.touchDetect(x, y);
		if(detectedNumber != -1){
			_cameraUIRepeater.setUIRepeater(detectedNumber,true,pointerId);
		}
		if(detectedNumber < 0 || detectedNumber > 5){
			return _modelProcessor.onTouch(detectedNumber,x,y);
		}
		return detectedNumber;
	}
	
	public void onUpMotion(int pointerId){
		_cameraUIRepeater.onUpMotion(pointerId);
	}
	
	public void stopAllRepeat(){
		_cameraUIRepeater.stopAllRepeat();
	}
	
	public boolean queryRepeatId(int pointerId){
		return _cameraUIRepeater.queryRepeatId(pointerId);
	}
	
	
}
