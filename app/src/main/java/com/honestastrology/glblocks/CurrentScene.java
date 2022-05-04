package com.honestastrology.glblocks;

public class CurrentScene {
	
	private static CurrentScene currentScene;
	
	
	private static int sceneNumber;
	private static int cubeType;
	private static int cubeCount;
	private static int multipleCount;
	private static float[] scaleArray;
	private static float[] rotateArray;
	private static float[] positionArray; // (area-1) >= position >= 1.0　の範囲の奇数で指定
	private static float[] areaFloat;// 0.width(x),1.height(y),2.depth(z) それぞれ0から正の方向への距離として
	private static float eyeDistance;
	private static int wallDimension;
	
	private CurrentScene(int number){
		sceneNumber = number;
		SceneData.setStage(number, this);
	}
	
	public static CurrentScene createScene(int sceneNumber){
		currentScene=new CurrentScene(sceneNumber);
		return currentScene;
	}
	
	public static CurrentScene updateScene(int number){
		//sceneNumber=number;
		SceneData.setStage(number, currentScene);
		return currentScene;
	}
	
	public void setInitialize(int stageCubeCount){
		setCubeCount(stageCubeCount);
		scaleArray=new float[stageCubeCount*3];
		rotateArray=new float[stageCubeCount*4];
		for(int i=0;i<stageCubeCount-1;i++){
			scaleArray[i]=1.0f;
			scaleArray[i+1]=1.0f;
			scaleArray[i+2]=1.0f;
			rotateArray[i]=0.0f;
			rotateArray[i+1]=0.0f;
			rotateArray[i+2]=0.0f;
			rotateArray[i+3]=1.0f;
		}
	}
	
	public static int getSceneNumber(){
		return sceneNumber;
	}
	
	public static float[] getScaleArray() {
		return scaleArray;
	}

	public static void setScaleArray(float[] scalearray) {
		scaleArray = scalearray;
	}

	public static float[] getRotateArray() {
		return rotateArray;
	}

	public static void setRotateArray(float[] rotatearray) {
		rotateArray = rotatearray;
	}

	public static float[] getPositionArray() {
		return positionArray;
	}

	public static void setPositionArray(float[] positionarray) {
		positionArray = positionarray;
	}

	public static float[] getAreaFloat() {
		return areaFloat;
	}

	public static void setAreaFloat(float[] areafloat) {
		areaFloat = areafloat;
	}
	
	public static int getCubeCount(){
		return cubeCount;
	}
	
	public static void setCubeCount(int count){
		cubeCount = count;
	}

	public static int getMultipleCount() {
		return multipleCount;
	}

	public static void setMultipleCount(int multipleCount) {
		CurrentScene.multipleCount = multipleCount;
	}

	public static int getCubeType() {
		return cubeType;
	}

	public static void setCubeType(int cubeType) {
		CurrentScene.cubeType = cubeType;
	}

	public static float getEyeDistance() {
		return eyeDistance;
	}

	public static void setEyeDistance(float eyeDistance) {
		CurrentScene.eyeDistance = eyeDistance;
	}

	public static int getWallDimension() {
		return wallDimension;
	}

	public static void setWallDimension(int wallDimension) {
		CurrentScene.wallDimension = wallDimension;
	}
	
}
