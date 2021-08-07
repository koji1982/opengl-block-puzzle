package com.honestastrology.glblocks;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;
import android.util.Log;

public class InferiorVBO {
	
	public int VBOId;
	public int VBOCount;
	public FloatBuffer floatBuffer;
	
	public InferiorVBO( float[] vertices){
		/*
		int[] VBOIds=new int[1];
		GLES20.glGenBuffers(1, VBOIds,0);
		VBOId=VBOIds[0];*/
		//VBOId=id;
		VBOCount=vertices.length;
		floatBuffer=ByteBuffer.allocateDirect(VBOCount*4).
				order(ByteOrder.nativeOrder()).asFloatBuffer();
		
		floatBuffer.put(vertices);
		floatBuffer.position(0);
		
		//GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, VBOId);
		
		//GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, VBOCount*4, 
		//		floatBuffer, GLES20.GL_STATIC_DRAW);
		/*
		GLES20.glEnableVertexAttribArray(GLBaseShader.aPositionHandle);
		
		GLES20.glVertexAttribPointer(GLBaseShader.aPositionHandle, 3,
				GLES20.GL_FLOAT,false, 0, floatBuffer);
		*/
		//GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
		
	}
}
