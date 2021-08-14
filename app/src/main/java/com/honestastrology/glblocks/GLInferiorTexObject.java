package com.honestastrology.glblocks;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.util.Log;

public class GLInferiorTexObject extends GLBaseObject implements Cloneable {
	
	private Map<Integer,Bitmap> textureMap;
	private int[]  texBoundenIds;
	private Texture texForSample = new Texture(512,512);
	
	
	private static ArrayList<ArrayList<InferiorVBO>> cubeVboGroup;
	private static ArrayList<ArrayList<InferiorNBO>> cubeNboGroup;
	private static ArrayList<ArrayList<InferiorTBO>> cubeTboGroup;
	private static ArrayList<ArrayList<InferiorIBO>> cubeIboGroup;
	private ArrayList<float[]> cubeMtlGroup;  //[0][]ambient,[1][]diffuse,[2][]specular,[3][]sExponent
	private static ArrayList<float[]> cubeDefMtl;
	private static ArrayList<float[]> downMtl;
	
	private static int    lastMtlPattern = -1;
	private int                mtlPattern = 0;
	private boolean         isBesideWall = false;
	private static boolean isFirstDrawn = true;
	
	private static float mtlMag;
	private static int mtlMagCounter = 0;
	private static final float[] enhancedSpecular = {1.0f,1.0f,1.0f,1.0f};
	private static final float downHighlightCoefficiant = 0.45f;
	
	
	public static final int       MTL_INITIALIZE = -1;
	private static final int         MTL_DEFAULT = 0;
	private static final int  MTL_DOWN_HIGHLIGHT = 1;
	private static final int MTL_BLINK_HIGHLIGHT = 2;
	
	public GLInferiorTexObject(String name){
		super(name);
		setTexBool(true);
	}
	
	public GLInferiorTexObject cloneObj() throws CloneNotSupportedException{
		return (GLInferiorTexObject) this.clone();
	}
	
	public static ArrayList<ArrayList<InferiorVBO>> getCubeVboGroup() {
		return cubeVboGroup;
	}

	public static void setCubeVboGroup(ArrayList<ArrayList<InferiorVBO>> cubeVboGroup) {
		GLInferiorTexObject.cubeVboGroup = cubeVboGroup;
	}

	public static ArrayList<ArrayList<InferiorNBO>> getCubeNboGroup() {
		return cubeNboGroup;
	}

	public static void setCubeNboGroup(ArrayList<ArrayList<InferiorNBO>> cubeNboGroup) {
		GLInferiorTexObject.cubeNboGroup = cubeNboGroup;
	}

	public static void setCubeTboGroup(ArrayList<ArrayList<InferiorTBO>> textures){
		cubeTboGroup=textures;
	}
	
	public static ArrayList<ArrayList<InferiorIBO>> getCubeIboGroup() {
		return cubeIboGroup;
	}

	public static void setCubeIboGroup(ArrayList<ArrayList<InferiorIBO>> cubeIboGroup) {
		GLInferiorTexObject.cubeIboGroup = cubeIboGroup;
	}

	public void setTexImage(Map<String,String> imageList){
		Bitmap texImage;
		setTexBoundenIds(new int[imageList.size()]);
		setTextureMap(new HashMap<Integer,Bitmap>());
		Iterator keyIterator=imageList.keySet().iterator();
		int i = 0;
		while(keyIterator.hasNext()){
			String key=keyIterator.next().toString();
			texForSample.setFileName(imageList.get(key));
			BoundenTexImage bounden = TexManager.getReference().loadTexImage(texForSample);
			getTexBoundenIds()[i]   = bounden.getBoundenId();
			texImage                = bounden.getTexImage();
			getTextureMap().put(i, texImage);
			i++;
		}
	}
	
	public void exchengeTexImage(String texName){
		Bitmap texImage;
		texForSample.setFileName(texName);
		if(textureMap.isEmpty())return;
		int size     = textureMap.size();
		texBoundenIds = new int[size];
		for(int i=0;i<size;i++	){
			BoundenTexImage bounden = TexManager.getReference().loadTexImage(texForSample);
			getTexBoundenIds()[i]   = bounden.getBoundenId();
			texImage                = bounden.getTexImage();
			getTextureMap().put(i, texImage);
		}
	}
	
	public void setIndividualMtl(){
		cubeMtlGroup=new ArrayList<float[]>();
		for(int i=0;i<cubeDefMtl.size();i++){
			float[] mtl=new float[]{
					cubeDefMtl.get(i)[0],cubeDefMtl.get(i)[1],cubeDefMtl.get(i)[2],cubeDefMtl.get(i)[3],
					cubeDefMtl.get(i)[4],cubeDefMtl.get(i)[5],cubeDefMtl.get(i)[6],cubeDefMtl.get(i)[7],
					cubeDefMtl.get(i)[8],cubeDefMtl.get(i)[9],cubeDefMtl.get(i)[10],cubeDefMtl.get(i)[11],
					cubeDefMtl.get(i)[12]
			};
			cubeMtlGroup.add(mtl);
		}
	}
	
	public void setCubeMaterial(ArrayList<float[]> mtlArray){
		cubeDefMtl=new ArrayList<float[]>();
		cubeMtlGroup=new ArrayList<float[]>();
		for(int i=0;i<mtlArray.size();i++){
			float[] def=new float[]{
					mtlArray.get(i)[0],mtlArray.get(i)[1],mtlArray.get(i)[2],mtlArray.get(i)[3],
					mtlArray.get(i)[4],mtlArray.get(i)[5],mtlArray.get(i)[6],mtlArray.get(i)[7],
					mtlArray.get(i)[8],mtlArray.get(i)[9],mtlArray.get(i)[10],mtlArray.get(i)[11],
					mtlArray.get(i)[12]
			};
			float[] mtl=new float[]{
					mtlArray.get(i)[0],mtlArray.get(i)[1],mtlArray.get(i)[2],mtlArray.get(i)[3],
					mtlArray.get(i)[4],mtlArray.get(i)[5],mtlArray.get(i)[6],mtlArray.get(i)[7],
					mtlArray.get(i)[8],mtlArray.get(i)[9],mtlArray.get(i)[10],mtlArray.get(i)[11],
					mtlArray.get(i)[12]
			};
			cubeDefMtl.add(def);
			cubeMtlGroup.add(mtl);
		}
		prepareDownHighlight();
	}
	
	public void blinkMtlHighlight(int mtlGroupIndex){
		setMtlPattern(MTL_BLINK_HIGHLIGHT);
		float[] enhance=BaseCalculator.calcEnhance(enhancedSpecular, mtlMag);
		cubeMtlGroup.get(mtlGroupIndex)[0]=enhance[0];
		cubeMtlGroup.get(mtlGroupIndex)[1]=enhance[1];
		cubeMtlGroup.get(mtlGroupIndex)[2]=enhance[2];
		cubeMtlGroup.get(mtlGroupIndex)[3]=enhance[3];
	}
	
	public static void frameProgressHighlight(){
		mtlMag=Math.abs((float)Math.cos(0.08*mtlMagCounter));
		mtlMagCounter++;
		if(mtlMagCounter>5000)mtlMagCounter=0;
	}
	
	public void downHighlightMtl(){
		setMtlPattern(MTL_DOWN_HIGHLIGHT);
		
	}
	
	public static void prepareDownHighlight(){
		downMtl = new ArrayList<float[]>();
		for(int i=0,j=cubeDefMtl.size();i<j;i++){
			float[] mtlArray = new float[cubeDefMtl.get(i).length];
			for(int k=0,l=cubeDefMtl.get(i).length;k<l;k++){
				if(k==3||k==7||k==11){
					mtlArray[k] = cubeDefMtl.get(i)[k];
				}else{
					mtlArray[k] = cubeDefMtl.get(i)[k] * downHighlightCoefficiant;
				}
			}
			downMtl.add(mtlArray);
		}
		Log.e("down mtl size",String.valueOf(downMtl.size()));
	}
	
	@Override
	public void defaultMtl(){
		setMtlPattern(MTL_DEFAULT);
	}
	
	public void checkAndDesideDownHighlight(){
		if(isBesideWall){
			downHighlightMtl();
		}
	}
	
	@Override
	public boolean isBesideWall() {
		return isBesideWall;
	}

	public void setBesideWall(boolean isBesideWall) {
		this.isBesideWall = isBesideWall;
	}

	public static boolean isFirstDrawn() {
		return isFirstDrawn;
	}

	public static void setFirstDrawn(boolean isfirstDrawn) {
		GLInferiorTexObject.isFirstDrawn = isfirstDrawn;
	}
	
	public static int getLastMtlPattern() {
		return lastMtlPattern;
	}

	public static void setLastMtlPattern(int lastMtlPattern) {
		GLInferiorTexObject.lastMtlPattern = lastMtlPattern;
	}

	public int getMtlPattern() {
		return mtlPattern;
	}

	public void setMtlPattern(int pattern) {
		this.mtlPattern = pattern;
	}
	
	public void drawConcrete(int mtlGroupIndex){
		GLES20.glUseProgram(GLBaseTexShader._baseShaderProgram);
		
		if(isSelected()){
			blinkMtlHighlight(mtlGroupIndex);
		}
		
		if(isFirstDrawn()){
			GLES20.glVertexAttribPointer(GLBaseTexShader.aPositionHandle, 3, GLES20.GL_FLOAT,
					false, 0, getCubeVboGroup().get(0).get(mtlGroupIndex).floatBuffer);
			GLES20.glEnableVertexAttribArray(GLBaseTexShader.aPositionHandle);
			
			GLES20.glVertexAttribPointer(GLBaseTexShader.aTextureUVHandle,2,GLES20.GL_FLOAT,
					false, 0, cubeTboGroup.get(0).get(mtlGroupIndex).tBuffer);
			GLES20.glEnableVertexAttribArray(GLBaseTexShader.aTextureUVHandle);
			
			GLES20.glVertexAttribPointer(GLBaseTexShader.aNormalHandle, 3, GLES20.GL_FLOAT,
					false, 0, getCubeNboGroup().get(0).get(mtlGroupIndex).nBuffer);
			GLES20.glEnableVertexAttribArray(GLBaseTexShader.aNormalHandle);
			
			GLES20.glEnableVertexAttribArray(GLBaseTexShader.aTextureUVHandle);
			GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, getTexBoundenIds()[mtlGroupIndex]);
			
			GLES20.glUniform1i(GLBaseTexShader.uSampleTexHandle, 0);
			setFirstDrawn(false);
		}
		
		GLES20.glUniformMatrix4fv(GLBaseTexShader.uMVPMatrixHandle,1,
				false,getMVPMatrix(),0);
		
		selectMtlPattern(mtlGroupIndex);
		
		int count= getCubeIboGroup().get(0).get(mtlGroupIndex).indexCount;
		GLES20.glDrawElements(GLES20.GL_TRIANGLES,count,GLES20.GL_UNSIGNED_SHORT,
				getCubeIboGroup().get(0).get(mtlGroupIndex).iBuffer);
		
		setWaiting(false);
		
	}
	
	public static void separatedDisableVertexAttribArray(){
		GLES20.glDisableVertexAttribArray(GLBaseTexShader.aPositionHandle);
		GLES20.glDisableVertexAttribArray(GLBaseTexShader.uMVPMatrixHandle);
	}
	
	private void selectMtlPattern(int mtlGroupIndex){
		if(mtlPattern == lastMtlPattern)return;
		if(mtlPattern == MTL_DOWN_HIGHLIGHT){
			GLES20.glUniform4fv(GLBaseTexShader.material_AmbientHandle, 1, 
					downMtl.get(mtlGroupIndex),0);
			GLES20.glUniform4fv(GLBaseTexShader.material_DiffuseHandle, 1, 
					downMtl.get(mtlGroupIndex),4);
			GLES20.glUniform4fv(GLBaseTexShader.material_SpecularHandle, 1, 
					downMtl.get(mtlGroupIndex),8);
			GLES20.glUniform1f(GLBaseTexShader.material_SpecularExponentHandle, 
					downMtl.get(mtlGroupIndex)[12]);
		}else if(mtlPattern == MTL_DEFAULT){
			GLES20.glUniform4fv(GLBaseTexShader.material_AmbientHandle, 1, 
					cubeDefMtl.get(mtlGroupIndex),0);
			GLES20.glUniform4fv(GLBaseTexShader.material_DiffuseHandle, 1, 
					cubeDefMtl.get(mtlGroupIndex),4);
			GLES20.glUniform4fv(GLBaseTexShader.material_SpecularHandle, 1, 
					cubeDefMtl.get(mtlGroupIndex),8);
			GLES20.glUniform1f(GLBaseTexShader.material_SpecularExponentHandle, 
					cubeDefMtl.get(mtlGroupIndex)[12]);
		}else{
			GLES20.glUniform4fv(GLBaseTexShader.material_AmbientHandle, 1, 
					cubeMtlGroup.get(mtlGroupIndex),0);
			GLES20.glUniform4fv(GLBaseTexShader.material_DiffuseHandle, 1, 
					cubeMtlGroup.get(mtlGroupIndex),4);
			GLES20.glUniform4fv(GLBaseTexShader.material_SpecularHandle, 1, 
					cubeMtlGroup.get(mtlGroupIndex),8);
			GLES20.glUniform1f(GLBaseTexShader.material_SpecularExponentHandle, 
					cubeMtlGroup.get(mtlGroupIndex)[12]);
		}
		lastMtlPattern = mtlPattern;
	}
	
	public void setCubeFlyweightModel(GLInferiorTexObject flyweightModel) {
		setIndividualMtl();
		setTexBoundenIds(flyweightModel.getTexBoundenIds());
		setTextureMap(flyweightModel.getTextureMap());
	}

	public int[] getTexBoundenIds() {
		return texBoundenIds;
	}

	public void setTexBoundenIds(int[] texboundenids) {
		texBoundenIds = texboundenids;
	}

	public Map<Integer,Bitmap> getTextureMap() {
		return textureMap;
	}

	public void setTextureMap(Map<Integer,Bitmap> textureMap) {
		this.textureMap = textureMap;
	}
		
	
	
}
