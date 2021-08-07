package com.honestastrology.glblocks;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.graphics.Bitmap;
import android.opengl.GLES20;

public class GLBaseFixedParticle {
	
	private final float[] centerPosition;// = new float[]{-0.5f,-0.5f,0.0f,1.0f};
	private final float texSize;
	private final int _id;
	private int glBoundenUId;
	private int glBoundenRId;
	private int selectedId;
	private Bitmap usualTexture;
	private Bitmap reactTexture;
	
	public GLBaseFixedParticle(int id,float[] position, float texsize){
		_id                        = id;
		centerPosition             = new float[]{position[0],position[1],0.0f,1.0f};
		texSize                    = texsize * GLBlocksActivity.getWindowMag();
		BoundenTexImage usualTex   = TexManager.getReference().loadTexImage(UITextureData.getUsualUITex(id));
		glBoundenUId               = usualTex.getBoundenId();
		usualTexture               = usualTex.getTexImage();
		BoundenTexImage reactTex   = TexManager.getReference().loadTexImage(UITextureData.getReactUITex(id));
		glBoundenRId               = reactTex.getBoundenId();
		reactTexture               = reactTex.getTexImage();
		selectedId                 = glBoundenUId;
		
	}
	
	public int getId() {
		return _id;
	}

	public float[] getCenterPosition(){
		return centerPosition;
	}
	
	public Bitmap getUsualTexture() {
		return usualTexture;
	}

	public void setUsualTexture(Bitmap usualtexture) {
		this.usualTexture = usualtexture;
	}

	public Bitmap getReactTexture() {
		return reactTexture;
	}

	public void setReactTexture(Bitmap reactTexture) {
		this.reactTexture = reactTexture;
	}
	
	protected void setReactId(){
		selectedId = glBoundenRId;
	}
	
	protected void onTouch(){
		selectedId = glBoundenRId;
		if(getId() == GLBlocksActivity.RETURN_BUTTON){//return button 仮実装
			UIManager.callDialog(getId());
			return;
		}
		if(getId() == GLBlocksActivity.RESTART_BUTTON){//restart button
			UIManager.callDialog(getId());
			return;
		}
		QuaternionUIConnector.rotate(getId());
	}
	
	public int getBoundenUsualId(){
		return glBoundenUId;
	}
	
	public int getBoundenReactId(){
		return glBoundenRId;
	}
	
	protected void setUsualId(){
		selectedId = glBoundenUId;
	}
	
	protected void returnUsualTex(){
		selectedId = glBoundenUId;
	}
	
	protected void setSelectedId(int id){
		selectedId = id;
	}
	
	protected int getSelectedId(){
		return selectedId;
	}
	
	public void drawParticle(){
		GLES20.glUseProgram(GLBaseFixedParticleShader.mFixedParticleProgram);
		GLES20.glUniform4fv(GLBaseFixedParticleShader.uCenterPositionHandle,
				1, centerPosition, 0);
		GLES20.glUniform1f(GLBaseFixedParticleShader.uTexSizeHandle, getTexSize());
		GLES20.glUniform4fv(GLBaseFixedParticleShader.uColorHandle,
				1, new float[]{1.0f,1.0f,1.0f,1.0f}, 0);
		
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, getSelectedId());
		//GLES20.glEnable(GLES20.GL_TEXTURE_2D);
		GLES20.glUniform1i(GLBaseFixedParticleShader.uSampler2DHandle, 0);
		GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 1);
		returnUsualTex();
	}

	public float getTexSize() {
		return texSize;
	}
	
	
}
