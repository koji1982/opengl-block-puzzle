package com.honestastrology.glblocks;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class InferiorTBO {
	
	public int TBOId;
	public int TBOCount;
	public FloatBuffer tBuffer;
	
	public InferiorTBO(float[] texs){
		
		//TBOId=id;
		TBOCount=texs.length;
		
		tBuffer=ByteBuffer.allocateDirect(TBOCount*4)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		tBuffer.put(texs);
		tBuffer.position(0);
		
		//GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, TBOId);
		//GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, TBOCount, tBuffer, 
		//		GLES20.GL_STATIC_DRAW);
		
	}
	
}
