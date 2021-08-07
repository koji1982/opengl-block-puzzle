package com.honestastrology.glblocks;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.util.Log;
import android.view.MotionEvent;

public class GLIrregularFixedUI extends GLBaseFixedParticle {
	
	private static boolean multipleButtonOn = false;
	private static boolean    nicheButtonOn = false;
	private static int     multipleMoveCount = 5;
	private int[]             boundenUsualIds = new int[6];
	private int[]             boundenReactIds = new int[6];
	private Bitmap[]              usualImages = new Bitmap[6];
	private Bitmap[]              reactImages = new Bitmap[6];
	private final int   WHEN_MULTIPLE_BUTTON = 12;
	private final int      WHEN_NICHE_BUTTON = 13;
	private final int     WHEN_NUMBER_BUTTON = 14;
	
	public GLIrregularFixedUI(int id, float[] position, float texsize) {
		super(id, position, texsize);
		multipleButtonOn = false;
		multipleMoveCount = CurrentScene.getMultipleCount();
		if(getId() == WHEN_MULTIPLE_BUTTON || getId() == WHEN_NICHE_BUTTON)return;
		usualImages[0]     = super.getUsualTexture();
		reactImages[0]     = super.getReactTexture();
		boundenUsualIds[0] = super.getBoundenUsualId();
		boundenReactIds[0] = super.getBoundenReactId();
		for(int i=1;i<6;i++){
			BoundenTexImage btUImage = TexManager.getReference().loadTexImage
			(UITextureData.getUsualUITex(WHEN_NUMBER_BUTTON + i));
			usualImages[i]     = btUImage.getTexImage();
			boundenUsualIds[i] = btUImage.getBoundenId();
			BoundenTexImage btRImage = TexManager.getReference().loadTexImage
					(UITextureData.getReactUITex(WHEN_NUMBER_BUTTON + i));
			reactImages[i]     = btRImage.getTexImage();
			boundenReactIds[i] = btRImage.getBoundenId();
		}
	}
	
	public static int getMultipleMoveCount() {
		return multipleMoveCount;
	}

	public static void setMultipleMoveCount(int multipleMoveCount) {
		GLIrregularFixedUI.multipleMoveCount = multipleMoveCount;
	}
	
	public static void decreaseMultipleCount(){
		multipleMoveCount--;
		setMultipleButtonOn(false);
	}

	public static boolean isMultipleButtonOn() {
		return multipleButtonOn;
	}
	
	private static void startSearchNiche(){
		nicheButtonOn = true;
		ObjManager.searchNiche();
	}
	
	private static void changeAxisDependDimension(){
		if(CurrentScene.getWallDimension() == 1){
			GeometricCalculator.setAxisRadioInt(2);
		}else if(CurrentScene.getWallDimension() == 2){
			if(GeometricCalculator.getAxisRadioInt() == 0){
				GeometricCalculator.setAxisRadioInt(2);
			}
		}
	}
	
	public static void changingOtherAxisIfInSelectedNiche(int index){
		if(nicheButtonOn){
			switch(CurrentScene.getWallDimension()){
			case 1:
				if(index != 2){
					setMultipleButtonOn(false);
				}
				break;
			case 2:
				if(index == 0){
					setMultipleButtonOn(false);
				}else{
					startSearchNiche();
				}
				break;
			case 3:
				startSearchNiche();
				break;
			}
		}
	}
	
	@Override
	protected void setUsualId(){
		if(getId() == WHEN_NUMBER_BUTTON){
			setSelectedId(boundenUsualIds[multipleMoveCount]);
		}else{
			super.setUsualId();
		}
	}
	
	@Override
	protected void setReactId(){
		if(getId() == WHEN_NUMBER_BUTTON){
			setSelectedId(boundenReactIds[multipleMoveCount]); 
		}else{
			super.setReactId();
		}
	}
	
	public static void setMultipleButtonOn(boolean multipleButtonOn) {
		if(!multipleButtonOn){
			ObjManager.defaultAllObjMtl();
			ObjManager.defaultAllBar();
			nicheButtonOn = false;
		}
		GLIrregularFixedUI.multipleButtonOn = multipleButtonOn;
	}

	private void checkMultipleButton(){
		if(isMultipleButtonOn()){
			if(getId() == WHEN_NICHE_BUTTON){
				if(nicheButtonOn){
					setReactId();
				}else{
					setUsualId();
				}
			}else{
				setReactId();
				GeometricCalculator.setMultipleBool(true);
			}
		}else{
			setUsualId();
			GeometricCalculator.setMultipleBool(false);
		}
	}
	
	@Override
	public void onTouch(){
		if(getId() == WHEN_MULTIPLE_BUTTON || getId() == WHEN_NUMBER_BUTTON 
				|| getId() == WHEN_NICHE_BUTTON){
			if( MotionEvent.ACTION_DOWN != GLBaseSurfaceView.getEventAction()){
				return;
			}
			if(multipleMoveCount < 1){
				return;
			}
			if(isMultipleButtonOn()){
				setMultipleButtonOn(false);
			}else{
				setMultipleButtonOn(true);
				if(getId() == WHEN_NICHE_BUTTON){
					changeAxisDependDimension();
					startSearchNiche();
				}
			}
		}
	}
	
	@Override
	public void drawParticle(){
		checkMultipleButton();
		GLES20.glUseProgram(GLBaseFixedParticleShader.mAdjustedProgram);
		GLES20.glUniform4fv(GLBaseFixedParticleShader.uCenterPositionHandle,
				1, getCenterPosition(), 0);
		GLES20.glUniform1f(GLBaseFixedParticleShader.uTexSizeHandle, getTexSize());
		GLES20.glUniform4fv(GLBaseFixedParticleShader.uColorHandle,
				1, new float[]{1.0f,1.0f,1.0f,1.0f}, 0);
		
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, getSelectedId());
		GLES20.glUniform1i(GLBaseFixedParticleShader.uSampler2DHandle, 0);
		GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 1);
	}
	
}
