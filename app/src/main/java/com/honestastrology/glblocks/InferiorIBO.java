package com.honestastrology.glblocks;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES20;

public class InferiorIBO {
	
	int IBOId;
	int indexCount;
	ShortBuffer iBuffer;
	
	public InferiorIBO(short[] indices){
		
		indexCount=indices.length;
		
		//IBOId=id;
		
		iBuffer=ByteBuffer.allocateDirect(indexCount*2)
				.order(ByteOrder.nativeOrder()).asShortBuffer();
		
		iBuffer.put(indices);
		iBuffer.position(0);
		
		//GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, id);
		//GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, indexCount*2, iBuffer,
		//		GLES20.GL_STATIC_DRAW);
		
	}
	
	public void bind(){
		
	}
	
	public void unbind(){
		
	}
	
}
