package com.honestastrology.glblocks;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;

public class ObjLoader {
	
	public static int    BLUE_CUBE = 0;//cube type
	public static int   GREEN_CUBE = 1;
	public static int    GRAY_CUBE = 2;
	public static int     RED_CUBE = 3;
	
	private OBJFileLoader _objFileLoader;
	private Context _context;
	
	private static ArrayList<GLInferiorTexObject> _flyModelArray;
	private GLInferiorObject _flyweightBar;
	
	public ObjLoader(Context context){
		_context       = context;
		_objFileLoader = new OBJFileLoader();
		_flyModelArray = new ArrayList<GLInferiorTexObject>();
		try {
			_flyweightBar   = (GLInferiorObject)_objFileLoader.objLoad(_context, "axisbar.obj");
			GLInferiorTexObject modelObject   = (GLInferiorTexObject) _objFileLoader.objLoad(_context, "bluetexcube.obj");
			GLInferiorTexObject omitObjGreen  = (GLInferiorTexObject) modelObject.cloneObj();
			omitObjGreen.exchengeTexImage("greencubetex.png");
			GLInferiorTexObject omitObjGray   = (GLInferiorTexObject) modelObject.cloneObj();
			omitObjGray.exchengeTexImage("graycubetex.png");
			GLInferiorTexObject omitObjRed    = (GLInferiorTexObject) modelObject.cloneObj();
			omitObjRed.exchengeTexImage("redcubetex.png");
			_flyModelArray.add(BLUE_CUBE,modelObject);
			_flyModelArray.add(GREEN_CUBE,omitObjGreen);
			_flyModelArray.add(GRAY_CUBE,omitObjGray);
			_flyModelArray.add(RED_CUBE,omitObjRed);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CloneNotSupportedException e){
			e.printStackTrace();
		}
	}
	
	public ArrayList<GLBaseObject> loadObjArray(){
		
		ArrayList<GLBaseObject> objList = new ArrayList<GLBaseObject>();
		int cubeType = CurrentScene.getCubeType();
		
		for(int i=0,j=CurrentScene.getCubeCount();i<j;i++){
			
			objList.add(new GLInferiorTexObject("cube"));
			((GLInferiorTexObject)objList.get(i)).setCubeFlyweightModel(_flyModelArray.get(cubeType));
			//setTex(objArray.get(i).getTexImage());
			objList.get(i).setScale(new float[]{1.0f,1.0f,1.0f});
			objList.get(i).setRotate(new float[]{0.0f,0.0f,0.0f,1.0f});
			objList.get(i).setPlace(
					CurrentScene.getPositionArray()[3*i],
					CurrentScene.getPositionArray()[3*i+1],
					CurrentScene.getPositionArray()[3*i+2]);
		}
		return objList;
	}
	
	public ArrayList<GLBaseObject> loadWallObject(){
		ArrayList<GLBaseObject> commonObjs = new ArrayList<GLBaseObject>();
		try{
			commonObjs.add((GLInferiorObject) _objFileLoader.objLoad(_context,"bluecubewall.obj"));
			commonObjs.add((GLInferiorObject) _objFileLoader.objLoad(_context,"testboard.obj"));
		}catch(IOException e){
			e.printStackTrace();
		}
		float[] areafloat = CurrentScene.getAreaFloat();
		commonObjs.get(0).setScale(new float[]{areafloat[0]/2+0.15f,areafloat[1]/2+0.15f,areafloat[2]/2+0.15f});
		commonObjs.get(0).setRotate(new float[]{0.0f,0.0f,0.0f,1.0f});
		commonObjs.get(0).setPlace(new float[]{areafloat[0]/2,areafloat[1]/2,areafloat[2]/2});
		commonObjs.get(1).setScale(new float[]{areafloat[0]/2+0.01f,1.0f,areafloat[2]/2+0.01f});
		commonObjs.get(1).setRotate(new float[]{90.0f,1.0f,0.0f,0.0f});
		commonObjs.get(1).setPlace(new float[]{areafloat[0]/2,areafloat[1]/2,-0.1f});
		return commonObjs;
	}
	
	public ArrayList<GLBaseObject> loadBarObject(){
		ArrayList<GLBaseObject> barObjs = new ArrayList<GLBaseObject>();
		
		for(int i=0,j=CurrentScene.getCubeCount();i<j;i++){
			barObjs.add(new GLInferiorObject("axisbar"));
			((GLInferiorObject)barObjs.get(i)).setFlyweightModel(_flyweightBar);
			barObjs.get(i).setScale(new float[]{1.0f,1.0f,5.0f});
			barObjs.get(i).setRotate(new float[]{0.0f,0.0f,0.0f,1.0f});
			barObjs.get(i).setPlace(new float[]{0.0f,7.0f,250.0f});
		}
		return barObjs;
	}
	
	public void registerFlyWeightBar(GLInferiorObject miniBar){
		miniBar.setFlyweightModel( _flyweightBar );
	}
	
	public ArrayList<GLBaseObject> update(int sceneNumber){
		return null;
	}
	
}
