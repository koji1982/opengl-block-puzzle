package com.honestastrology.glblocks;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class GLPlaneEffect{
	
	private final FloatBuffer vertices;
	private final FloatBuffer uv;
	private float currentTime;
	private float lifeTime;
	
	public GLPlaneEffect(){
		vertices=ByteBuffer.allocateDirect(6*3*4)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		vertices.put(new float[]{1.0f,1.0f,0.0f,-1.0f,1.0f,0.0f,1.0f,-1.0f,0.0f,
				-1.0f,1.0f,0.0f,-1.0f,-1.0f,0.0f,1.0f,-1.0f,0.0f});
		vertices.position(0);
		uv=ByteBuffer.allocateDirect(6*2*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		uv.put(new float[]{1.0f,0.0f,1.0f,1.0f,0.0f,0.0f,
				0.0f,1.0f,0.0f,0.0f,1.0f,0.0f});
		uv.position(0);
		
		currentTime=0.0f;
		lifeTime=6.0f;
		
	}
	
	public float currentTimeProgress(){
		return currentTime+=0.1f;
	}
	
	public void currentTimeReset(){
		currentTime=0.0f;
	}
	
	
}
