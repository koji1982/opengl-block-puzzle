package com.honestastrology.glblocks;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.util.Log;

public class TexManager {
	
	public static TexManager _texManager;
	
	private boolean initializeBool = false;
	private static int totalIdCount = 0;
	private int fixedTexCount;
	private int objectTexCount;
	private Context _context;
	private GLPlaneEffect planeEffect; // test plane effect"bluecircle.png"
	
	private static ArrayList<Integer> textureIdArray = new ArrayList<Integer>();
	
	private static Bitmap testImage;
	
	public TexManager(Context context){
		_context    = context;
		_texManager = this;
	}
	
	public static TexManager getReference(){
		return _texManager;
	}
	
	public static Bitmap getTestImage(){
		return testImage;
	}
	
	public BoundenTexImage loadTexImage(Texture texture){
		
		int boundenId;
		Bitmap texImage = null;
		InputStream is;
		Resources r     = _context.getResources();
		String filename = texture.getFileName();
		if(!filename.contains(".png")){
			filename = filename + ".png";
		}
		try{
			is       = r.getAssets().open(filename);
			texImage = BitmapFactory.decodeStream(is);
		}catch(IOException e){
			Log.e("loading_texture", e.getLocalizedMessage());
		}
		
		return setGLTexture(texImage,texture);
	}
	
	private BoundenTexImage setGLTexture(Bitmap bitmap,Texture texture){
		int[] texIds = new int[1];
		GLES20.glGenTextures(1, texIds,0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texIds[0]);
		textureIdArray.add(texIds[0]);
		ByteBuffer bitmapBuffer=ByteBuffer.allocateDirect(
				texture.getWidth()*texture.getHeight()*4).order(ByteOrder.nativeOrder());
		bitmap.copyPixelsToBuffer(bitmapBuffer);
		bitmapBuffer.position(0);
		GLES20.glPixelStorei(GLES20.GL_UNPACK_ALIGNMENT, 1);
		GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, texture.getWidth(), 
				texture.getHeight(), 0,GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, bitmapBuffer);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, 
				GLES20.GL_NEAREST);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,
				GLES20.GL_LINEAR);
		/*
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
				GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
				GLES20.GL_CLAMP_TO_EDGE);
		*/
		BoundenTexImage boundenTex = new BoundenTexImage();
		boundenTex.setBoundenId(texIds[0]);
		boundenTex.setTexImage(bitmap);
		return boundenTex;
	}
	
	public static ArrayList<Integer> getTextureIdArray() {
		return textureIdArray;
	}
	
}
