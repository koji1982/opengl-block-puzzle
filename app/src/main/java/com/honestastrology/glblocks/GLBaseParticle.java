package com.honestastrology.glblocks;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.graphics.Bitmap;
import android.opengl.GLES20;

public class GLBaseParticle {
	
	private float[] centerPosition;
	private float lifetime;
	private float passedTime;
	private float[] startPosition;
	private float[] endPosition;
	
	
	private FloatBuffer lifetimeBuffer;
	private FloatBuffer startPBuffer;
	private FloatBuffer endPBuffer;
	private Bitmap texture;
	
	public GLBaseParticle(float[] center){
		centerPosition=center;
	}
	
	public void setCenterPosition(float[] center){
		centerPosition=center;
	}
	
	public void setLifetime(float time){
		lifetime=time;
		lifetimeBuffer=ByteBuffer.allocateDirect(4).
				order(ByteOrder.nativeOrder()).asFloatBuffer();
		lifetimeBuffer.put(time);
		lifetimeBuffer.position(0);
	}
	
	public void setStartPosition(float[] position){
		startPosition=position;
		startPBuffer=ByteBuffer.allocateDirect(3*4).
				order(ByteOrder.nativeOrder()).asFloatBuffer();
		startPBuffer.put(position);
		startPBuffer.position(0);
	}
	
	public void setEndPosition(float[] position){
		endPosition=position;
		endPBuffer=ByteBuffer.allocateDirect(3*4)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		endPBuffer.put(position);
		endPBuffer.position(0);
	}
	
	public void setTexture(Bitmap tex){
		texture=tex;
	}
	
	public float[] getCenterPosition(){
		return centerPosition;
	}
	
	public float getLifetime(){
		return lifetime;
	}
	
	public float[] getStartPosition(){
		return startPosition;
	}
	
	public float[] getEndPosition(){
		return endPosition;
	}
	
	public Bitmap getTexture(){
		return texture;
	}
	/*
	public void drawParticle(){
		currentTime = currentTime+0.1f;
		GLBaseError.errorLog("ctr_particle1");
		GLES20.glUseProgram(GLBaseParticleShader.mParticleProgram);
		GLBaseError.errorLog("ctr_particle2");
		GLES20.glUniform4fv(GLBaseParticleShader.uCenterPositionHandle,
				1, effectCenter, 0);
		
		GLES20.glUniform4fv(GLBaseParticleShader.uColorHandle,
				1, new float[]{1.0f,1.0f,1.0f,1.0f}, 0);
		GLES20.glUniform1f(GLBaseParticleShader.uCurrentTimeHandle, currentTime);
		GLES20.glUniform3fv(GLBaseParticleShader.uEffectEyeVectorHandle, 1, effectEyeVector, 0);
		GLES20.glUniform3fv(GLBaseParticleShader.uCollisionNormalHandle, 1,
				CollisionDetector.effectNormal,0);
		//GLES20.glUniform4fv(GLBaseParticleShader.uScreenEffectNormalHandle, 1, screenEffect, 0);
		GLES20.glUniformMatrix4fv(GLBaseParticleShader.uMVPMatrixHundle, 1, false,
				mMatrix, 0);
		GLBaseError.errorLog("ctr_particle6");
		GLES20.glVertexAttribPointer(GLBaseParticleShader.aLifetimeHandle, 1,GLES20.GL_FLOAT,
				false,0,particle.lifetimeBuffer);
		GLBaseError.errorLog("ctr_particle3");
		GLES20.glEnableVertexAttribArray(GLBaseParticleShader.aLifetimeHandle);
		GLBaseError.errorLog("ctr_particle4");
		GLES20.glUniform1f(GLBaseParticleShader.uHorizontalSecantHandle, 1.0f);
		GLES20.glUniform1f(GLBaseParticleShader.uVerticalSecantHandle, 1.0f);
		GLBaseError.errorLog("ctr_particle5");
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, pTexId[0]);
		
		//GLES20.glEnable(GLES20.GL_TEXTURE_2D);
		
		GLES20.glUniform1i(GLBaseParticleShader.uSampler2DHandle, 0);
		
		GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 1);
		
		if(currentTime>particle.lifetime){
			CollisionDetector.effectInt=0;
			currentTime=0.0f;
		}
		
		//GLES20.glDisableVertexAttribArray(GLBaseParticleShader.aLifetimeHandle);
		//GLES20.glDisableVertexAttribArray(GLBaseParticleShader.aStartPositionHandle);
		//GLES20.glDisableVertexAttribArray(GLBaseParticleShader.aEndPositionHandle);
	}
	*/
}
