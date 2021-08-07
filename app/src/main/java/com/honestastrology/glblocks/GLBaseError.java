package com.honestastrology.glblocks;

import android.opengl.GLES20;
import android.util.Log;

public class GLBaseError {
	
	static int error;
	
	public static void errorLog(String identify){
		error=GLES20.glGetError();
		if(error!=GLES20.GL_NO_ERROR){
			Log.e("gl_base", identify+error);
		}
	}
	
	public static void anotherThreadErrorLog(String identify){
		
	}
	
}