package com.honestastrology.glblocks;

import android.opengl.GLES20;
import android.view.MotionEvent;

public class GLToggleUIObject extends GLBaseFixedParticle {
	
	public GLToggleUIObject(int id, float[] position, float texsize) {
		super(id, position, texsize);
		if(SoundManager.isStageBgmSilent())setReactId();
	}
	
	@Override
	public void onTouch(){
		if(MotionEvent.ACTION_DOWN == GLBaseSurfaceView.getEventAction()){
			if(SoundManager.isStageBgmSilent()){
				SoundManager.setStageBgmSilent(false);
				SoundManager.resumeSong();
				setUsualId();
			}else{
				SoundManager.setStageBgmSilent(true);
				SoundManager.pauseSong();
				setReactId();
			}
		}
	}
	
	@Override
	public void drawParticle(){
		GLES20.glUseProgram(GLBaseFixedParticleShader.mFixedParticleProgram);
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
