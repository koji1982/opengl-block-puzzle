package com.honestastrology.glblocks;

import android.view.MotionEvent;

public class GLRadioUI extends GLBaseFixedParticle {
	
	private RadioUIGroup _radioUIGroup;
	private final int radioGroupIndex;
	
	
	public GLRadioUI(int id, float[] position, float texsize) {
		super(id, position, texsize);
		_radioUIGroup   = RadioUIGroup.getInstance();
		radioGroupIndex = _radioUIGroup.addRadio(this);
	}
	
	@Override
	public void onTouch(){
		if( MotionEvent.ACTION_DOWN != GLBaseSurfaceView.getEventAction()){
			return;
		}
		if(radioGroupIndex == GeometricCalculator.getAxisRadioInt()){
			_radioUIGroup.clearRadio();
			return;
		}
		_radioUIGroup.changeAxisSelected(radioGroupIndex);
	}
	
	@Override
	public void returnUsualTex(){
	}
	
	public void setAxis(){
		super.setReactId();
		_radioUIGroup.selectAxis(this.getId());
	}
	
	@Override
	public void setUsualId(){
		super.setUsualId();
	}
	
}
