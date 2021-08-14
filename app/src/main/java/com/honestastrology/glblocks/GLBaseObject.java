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

public class GLBaseObject {
	
	private float[] individualMVPMatrix = new float[16];
	
	public void updateMVPMatrix(float[] mvpMatrix){
		individualMVPMatrix = mvpMatrix.clone();
	}
	
	public String objName;
	int[] bufferIds;
	
	private ArrayList<ArrayList<InferiorVBO>> vboGroup;
	private ArrayList<ArrayList<InferiorNBO>> nboGroup;
	private ArrayList<ArrayList<InferiorIBO>> iboGroup;
	private ArrayList<float[]> mtlGroup;  //[0][]ambient,[1][]diffuse,[2][]specular,[3][]sExponent
	private ArrayList<float[]> defMtl;
	
	private boolean texBool;
	
	private float[] scaleXYZ={1.0f,1.0f,1.0f};
	
	private float[] rotateAngle; // 0.angle 1.axisX 2.axisY 3.axisZ
	
	private float[] placeXYZ = new float[3];
	
	protected static float   velocity = 0.18f;
	private int moveDirection; // 0.+x   1.-x   2.+y   3.-y   4.+z   5.-z
	private float travel;
	
	private boolean wallBool;
	private boolean selectedBool = false;
	private boolean movingBool;
	private boolean groupMovingBool;
	private boolean waitingBool;
	
	private ArrayList<float[]> _materialForFlyweight;
	
	public GLBaseObject(String name){
		setTexBool(false);
		if(name.contains("wall")||name.contains("bar"))setWallBool(true);
		else setWallBool(false);
	}
	
	public boolean isBesideWall(){
		return false;
	}
	
	public void setIds(int[] ids){
		bufferIds=ids;
	}
	
	public void setIBO(ArrayList<ArrayList<InferiorIBO>> indices){
		setIboGroup(indices);
	}
	
	public void setVBO(ArrayList<ArrayList<InferiorVBO>> vertices){
		setVboGroup(vertices);
	}
	
	public void setNBO(ArrayList<ArrayList<InferiorNBO>> normals){
		setNboGroup(normals);
	}
	
	
	
	public void setMaterial(ArrayList<float[]> mtlArray){
		setDefMtl(new ArrayList<float[]>());
		setMtlGroup(new ArrayList<float[]>());
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
			getDefMtl().add(def);
			getMtlGroup().add(mtl);
		}
	}
	
	public void setMaterialForFlyweight(ArrayList<float[]> mtlArray){
		_materialForFlyweight = mtlArray;
	}
	
	public ArrayList<float[]> getMaterialForFlyweight(){
		return _materialForFlyweight;
	}
	
	public void setScale(float[] xyz){
		scaleXYZ=xyz;
	}
	
	public void setRotate(float[] rotate){
		rotateAngle=rotate;
	}
	
	public void setPlace(float[] place){
		placeXYZ=place;
	}
	
	public void setPlaceX(float placeX){
		placeXYZ[0]=placeX;
	}
	
	public void setPlaceY(float placeY){
		placeXYZ[1]=placeY;
	}
	
	public void setPlaceZ(float placeZ){
		placeXYZ[2]=placeZ;
	}
	
	public void setPlace(float x, float y,float z){
		placeXYZ[0]=x;
		placeXYZ[1]=y;
		placeXYZ[2]=z;
	}
	
	public void setMovingBool(boolean moving){
		if(isWall()){
			movingBool=false;
		}else{
			if(!moving){
				adjustPosition();
			}
			movingBool=moving;
		}
	}
	
	public float[] getScale(){
		return scaleXYZ;
	}
	
	public float[] getRotate(){
		return rotateAngle;
	}
	
	public float[] getPlace(){
		return placeXYZ;
	}
	
	public float getPlaceX(){
		return placeXYZ[0];
	}
	
	public float getPlaceY(){
		return placeXYZ[1];
	}
	
	public float getPlaceZ(){
		return placeXYZ[2];
	}
	
	public boolean getMovingBool(){
		return movingBool;
	}
	
	public int getMoveDirection(){
		return moveDirection;
	}
	
	public void defaultMtl(){
//		for(int i=0;i<mtlGroup.size();i++){
//			mtlGroup.get(i)[0]=defMtl.get(i)[0];
//			mtlGroup.get(i)[1]=defMtl.get(i)[1];
//			mtlGroup.get(i)[2]=defMtl.get(i)[2];
//			mtlGroup.get(i)[3]=defMtl.get(i)[3];
//		}
	}
	
	public void setMVPMatrix(float[] matrix){
		individualMVPMatrix = matrix.clone();
	}
	
	public float[] getMVPMatrix(){
		return individualMVPMatrix;
	}
	
	public void setSelectedBool(boolean bool){
		selectedBool = bool;
	}
	
	public boolean isSelected(){
		return selectedBool;
	}
	
	public boolean isGroupMoving() {
		return groupMovingBool;
	}

	public void setGroupMovingBool(boolean groupMovingBool) {
		this.groupMovingBool = groupMovingBool;
	}

	public void setMoveDirection(int direction){
		movingBool     = true;
		moveDirection  = direction;
	}
	
	public static float getVelocity() {
		return velocity;
	}

	public static void setVelocity(float velocity) {
		GLBaseObject.velocity = velocity;
	}

	public void objMove(){
		switch(moveDirection){
		case 0:
			setPlaceX(getPlaceX()+getVelocity());
			break;
		case 1:
			setPlaceX(getPlaceX()-getVelocity());
			break;
		case 2:
			setPlaceY(getPlaceY()+getVelocity());
			break;
		case 3:
			setPlaceY(getPlaceY()-getVelocity());
			break;
		case 4:
			setPlaceZ(getPlaceZ()+getVelocity());
			break;
		case 5:
			setPlaceZ(getPlaceZ()-getVelocity());
			break;
		}
	}
	
	public void adjustPosition(){
		int axis = CubeCollision.directionToAxis(moveDirection);
		int convertAxisA   = CubeCollision.convertAxisInt(axis+1);
		int convertAxisB   = CubeCollision.convertAxisInt(axis-1);
		int adjustedInt    = Math.round(getPlace()[axis]);
		float adjusted     = (float)adjustedInt;
		float throughA     = getPlace()[convertAxisA];
		float throughB     = getPlace()[convertAxisB];
		float[] place      = new float[3];
		place[axis]         = adjusted;
		place[convertAxisA] = throughA;
		place[convertAxisB] = throughB;
		setPlace(place);
	}

	public boolean isWaiting() {
		return waitingBool;
	}

	public void setWaiting(boolean waitingBool) {
		this.waitingBool = waitingBool;
	}
	
	public void drawObject(){
		for(int i=0,j=getMtlGroup().size();i<j;i++){
			drawConcrete(i);
		}
	}
	
	public void drawObject(int mtlGroupIndex){
		drawConcrete(mtlGroupIndex);
	}
	
	public void drawConcrete(int mtlGroupIndex){
		GLES20.glUseProgram(GLBaseShader._baseShaderProgram);
		
		if(getPlaceZ() == 250.0f)return;
		
		GLES20.glVertexAttribPointer(GLBaseShader.aPositionHandle, 3, GLES20.GL_FLOAT,
				false, 0, getVboGroup().get(0).get(mtlGroupIndex).floatBuffer);
		
		GLES20.glEnableVertexAttribArray(GLBaseShader.aPositionHandle);
		
		GLES20.glVertexAttribPointer(GLBaseShader.aNormalHandle, 3, GLES20.GL_FLOAT,
				false, 0, getNboGroup().get(0).get(mtlGroupIndex).nBuffer);
		
		GLES20.glEnableVertexAttribArray(GLBaseShader.aNormalHandle);
		
		/*
		GLES20.glVertexAttribPointer(GLBaseShader.aTextureHandle, 2, GLES20.GL_FLOAT,
				false, 0, objArray.get(arrayIndex).tbo.tBuffer);
		*/
		
		GLES20.glUniformMatrix4fv(GLBaseShader.uMVPMatrixHandle,1,
				false,getMVPMatrix(),0);
		GLES20.glUniform4fv(GLBaseShader.material_AmbientHandle, 1, 
				getMtlGroup().get(mtlGroupIndex),0);
		GLES20.glUniform4fv(GLBaseShader.material_DiffuseHandle, 1, 
				getMtlGroup().get(mtlGroupIndex),4);
		GLES20.glUniform4fv(GLBaseShader.material_SpecularHandle, 1, 
				getMtlGroup().get(mtlGroupIndex),8);
		GLES20.glUniform1f(GLBaseShader.material_SpecularExponentHandle, 
				getMtlGroup().get(mtlGroupIndex)[12]);
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		/*
		GLES20.glEnableVertexAttribArray(GLBaseShader.aTextureHandle);
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texs[0]);
		
		GLES20.glUniform1i(GLBaseShader.uSampleTexHandle, 0);
		*/
		
		int count= getIboGroup().get(0).get(mtlGroupIndex).indexCount;
		GLES20.glDrawElements(GLES20.GL_TRIANGLES,count,GLES20.GL_UNSIGNED_SHORT,
				getIboGroup().get(0).get(mtlGroupIndex).iBuffer);
		
		
		GLES20.glDisableVertexAttribArray(GLBaseShader.aPositionHandle);
		GLES20.glDisableVertexAttribArray(GLBaseShader.uMVPMatrixHandle);
		
		setWaiting(false);
		
	}

	public ArrayList<ArrayList<InferiorVBO>> getVboGroup() {
		return vboGroup;
	}

	public void setVboGroup(ArrayList<ArrayList<InferiorVBO>> vboGroup) {
		this.vboGroup = vboGroup;
	}

	public ArrayList<ArrayList<InferiorNBO>> getNboGroup() {
		return nboGroup;
	}

	public void setNboGroup(ArrayList<ArrayList<InferiorNBO>> nboGroup) {
		this.nboGroup = nboGroup;
	}

	public ArrayList<ArrayList<InferiorIBO>> getIboGroup() {
		return iboGroup;
	}

	public void setIboGroup(ArrayList<ArrayList<InferiorIBO>> iboGroup) {
		this.iboGroup = iboGroup;
	}

	public ArrayList<float[]> getMtlGroup() {
		return mtlGroup;
	}

	public void setMtlGroup(ArrayList<float[]> mtlGroup) {
		this.mtlGroup = mtlGroup;
	}

	public ArrayList<float[]> getDefMtl() {
		return defMtl;
	}

	public void setDefMtl(ArrayList<float[]> defMtl) {
		this.defMtl = defMtl;
	}

	public boolean isWall() {
		return wallBool;
	}

	public void setWallBool(boolean wallBool) {
		this.wallBool = wallBool;
	}

	public boolean isTexObject() {
		return texBool;
	}

	public void setTexBool(boolean texBool) {
		this.texBool = texBool;
	}
	
}
