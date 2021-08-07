package com.honestastrology.glblocks;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.util.Log;

public class GLInferiorObject extends GLBaseObject {
	
	public GLInferiorObject(String name){
		super(name);
	}
	
	public void setFlyweightModel(GLInferiorObject flyweightModel) {
		this.setIboGroup(flyweightModel.getIboGroup());
		this.setVboGroup(flyweightModel.getVboGroup());
		this.setNboGroup(flyweightModel.getNboGroup());
		this.setMaterial(flyweightModel.getMaterialForFlyweight());
	}
	
}