package com.honestastrology.glblocks;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;

public class InferiorNBO {
	
	public int NBOId;
	public int NBOCount;
	public FloatBuffer nBuffer;
	
	public InferiorNBO(float[] normals){
		
		//NBOId=id;
		NBOCount=normals.length;
		
		nBuffer=ByteBuffer.allocateDirect(NBOCount*4)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		nBuffer.put(normals);
		nBuffer.position(0);
		
		//GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, NBOId);
		//GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, NBOCount*4, nBuffer,
		//		GLES20.GL_STATIC_DRAW);
		
		
	}
	
	
}
