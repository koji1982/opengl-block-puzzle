package com.honestastrology.glblocks;

public class SceneData {
	
	private float[] initialPositionArray;//各cubeに対してそれぞれのx,y,z座標
	private float[] areaSpace;//0.x,1.y,2.z
	
	public static final int TEST_STAGE  = 0;//TouchDetectorのareaFloat更新ステージごと
	
	public static final int LAST_STAGE = 40;
	
	public static void setStage(int sceneNumber,CurrentScene currentScene){
		int cubeCount       = 0;
		int multipleCount   = 0;
		int cubeType        = 0;
		int wallDimension   = 1;
		int[]   preStagePositionArray = null;
		float[] stagePositionArray    = null;
		float[] stageAreaFloat        = null;
		float stageEyeDistance        = 0.0f;
		switch(sceneNumber){
			case 1://min 3  6
				cubeCount     = 16;
				multipleCount = 0;
				cubeType      = ObjLoader.BLUE_CUBE;
				preStagePositionArray = new int[]{
						1,4,1, 2,4,1, 3,4,1, 4,4,1,
						2,3,3, 2,3,1, 4,3,3, 4,3,1,
						1,2,1, 2,2,1, 3,2,1, 4,2,1,
						1,1,1, 2,1,1, 3,1,1, 4,1,1
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = 0.1f;
				stageAreaFloat     = new float[]{
						8.0f,8.0f,8.0f
				};
				break;
			case 2://min 5  9
				cubeCount     = 16;
				multipleCount = 0;
				cubeType      = ObjLoader.BLUE_CUBE;
				preStagePositionArray = new int[]{
						1,4,1, 2,4,1, 4,4,3, 4,4,1,
						2,4,3, 2,3,1, 3,3,1, 4,3,1,
						1,2,1, 2,2,1, 3,2,1, 4,2,1,
						1,2,3, 2,1,1, 3,1,1, 4,1,1
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = 0.1f;
				stageAreaFloat     = new float[]{
						8.0f,8.0f,8.0f
				};
				break;
			case 3://min 3  6
				cubeCount     = 16;
				multipleCount = 0;
				cubeType      = ObjLoader.BLUE_CUBE;
				preStagePositionArray = new int[]{
						1,4,1, 1,4,4, 3,4,1, 3,4,3,
						1,3,1, 2,3,1, 3,3,1, 4,3,1,
						1,2,1, 2,2,1, 3,2,1, 4,2,1,
						1,1,1, 2,1,1, 3,1,1, 4,1,1
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = 0.1f;
				stageAreaFloat     = new float[]{
						8.0f,8.0f,8.0f
				};
				break;
			case 4://min 7 12
				cubeCount     = 16;
				multipleCount = 0;
				cubeType      = ObjLoader.BLUE_CUBE;
				preStagePositionArray = new int[]{
						1,4,3, 2,4,1, 3,4,1, 4,4,1,
						2,2,3, 2,3,1, 3,4,3, 4,3,1,
						1,2,1, 2,2,1, 3,2,1, 4,2,1,
						1,1,1, 2,1,1, 4,2,3, 4,1,1
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = 0.1f;
				stageAreaFloat     = new float[]{
						8.0f,8.0f,8.0f
				};
				break;
			case 5://min 7 9
				cubeCount     = 16;
				multipleCount = 0;
				cubeType      = ObjLoader.BLUE_CUBE;
				preStagePositionArray = new int[]{
						1,4,1, 2,4,1, 4,4,4, 4,4,1,
						2,4,4, 2,3,1, 3,3,1, 4,3,1,
						1,2,1, 2,2,1, 4,2,4, 4,2,1,
						2,2,4, 2,1,1, 3,1,1, 4,1,1
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = 0.1f;
				stageAreaFloat     = new float[]{
						8.0f,8.0f,8.0f
				};
				break;
			case 6://min 3 8
				cubeCount     = 16;
				multipleCount = 1;
				cubeType      = ObjLoader.BLUE_CUBE;
				preStagePositionArray = new int[]{
						1,1,1, 1,2,1, 1,3,1, 1,4,1,
						2,1,1, 2,2,3, 2,3,1, 2,4,3,
						3,1,1, 3,3,1, 3,1,2, 3,3,2,
						4,2,4, 4,4,4, 4,2,1, 4,4,1
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = 0.1f;
				stageAreaFloat     = new float[]{
						8.0f,8.0f,8.0f
				};
				break;
			case 7://min 4  8
				cubeCount     = 16;
				multipleCount = 1;
				cubeType      = ObjLoader.BLUE_CUBE;
				preStagePositionArray = new int[]{
						1,4,1, 2,4,1, 4,4,4, 4,4,1,
						2,4,4, 2,3,1, 3,3,1, 4,3,1,
						1,2,1, 2,2,1, 3,2,1, 4,2,1,
						1,1,1, 1,2,4, 3,1,1, 3,1,4
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = 0.1f;
				stageAreaFloat     = new float[]{
						8.0f,8.0f,8.0f
				};
				break;
			case 8://min 5  9
				cubeCount     = 16;
				multipleCount = 1;
				cubeType      = ObjLoader.BLUE_CUBE;
				preStagePositionArray = new int[]{
						1,4,2, 2,4,1, 4,4,4, 4,4,1,
						1,3,1, 2,4,4, 3,3,1, 3,3,3,
						1,2,1, 2,2,1, 3,2,1, 4,2,1,
						1,2,3, 2,1,1, 3,1,1, 4,1,1
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = 0.1f;
				stageAreaFloat     = new float[]{
						8.0f,8.0f,8.0f
				};
				break;
			case 9://min 5  10 左↓　中央↑　右↑
				cubeCount     = 16;
				multipleCount = 1;
				cubeType      = ObjLoader.BLUE_CUBE;
				preStagePositionArray = new int[]{
						1,4,1, 2,4,1, 4,4,4, 3,1,4,
						2,4,4, 2,3,1, 4,2,4, 4,3,1,
						1,2,1, 2,2,1, 3,2,1, 4,2,1,
						1,1,1, 2,2,4, 3,1,1, 4,1,1
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = 0.1f;
				stageAreaFloat     = new float[]{
						8.0f,8.0f,8.0f
				};
				break;
			case 10://min 6 12  
				cubeCount     = 16;
				multipleCount = 1;
				cubeType      = ObjLoader.BLUE_CUBE;
				preStagePositionArray = new int[]{
						2,4,2, 2,4,1, 4,4,4, 4,4,1,
						1,3,1, 2,4,4, 4,4,2, 3,4,3,
						2,2,2, 2,2,1, 4,2,4, 4,2,1,
						1,1,1, 2,2,4, 4,2,2, 3,2,3
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = 0.1f;
				stageAreaFloat     = new float[]{
						8.0f,8.0f,8.0f
				};
				break;
			case 11://min 5 12
				cubeCount     = 36;
				multipleCount = 1;
				cubeType      = ObjLoader.BLUE_CUBE;
				preStagePositionArray = new int[]{
						3,4,4, 2,6,1, 3,5,4, 4,6,1, 5,6,1, 6,6,1,
						1,5,1, 2,5,1, 3,5,1, 4,5,1, 5,5,1, 6,5,1,
						1,4,1, 2,4,1, 3,4,1, 6,4,4, 5,4,1, 6,4,1,
						1,1,4, 2,3,1, 3,1,6, 4,3,1, 5,3,1, 6,3,1,
						1,2,1, 2,2,1, 3,2,1, 4,2,1, 5,2,1, 6,2,1,
						1,1,1, 2,1,1, 3,1,3, 4,1,1, 5,1,1, 6,1,1
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = 0.0f;
				stageAreaFloat     = new float[]{
						12.0f,12.0f,12.0f
				};
				break;
			case 12://min 7 12
				cubeCount     = 36;
				multipleCount = 1;
				cubeType      = ObjLoader.BLUE_CUBE;
				preStagePositionArray = new int[]{
						1,6,1, 2,6,1, 3,6,1, 4,6,1, 5,6,1, 6,6,1,
						1,5,1, 2,6,6, 3,5,1, 4,5,1, 5,6,6, 6,5,1,
						1,6,6, 2,4,1, 3,6,6, 4,6,6, 5,4,1, 6,6,6,
						1,3,1, 2,4,6, 3,3,1, 4,3,1, 5,4,6, 6,3,1,
						1,2,1, 2,2,1, 3,2,1, 4,2,1, 5,2,1, 6,2,1,
						1,3,6, 2,2,6, 3,3,6, 4,3,6, 5,2,6, 6,3,6
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = 0.0f;
				stageAreaFloat     = new float[]{
						12.0f,12.0f,12.0f
				};
				break;
			case 13://min 12 15
				cubeCount     = 36;
				multipleCount = 1;
				cubeType      = ObjLoader.BLUE_CUBE;
				preStagePositionArray = new int[]{
						1,6,1, 2,6,1, 3,6,1, 4,6,1, 5,6,1, 6,6,1,
						1,6,3, 2,5,1, 3,6,3, 4,5,1, 5,6,3, 6,5,1,
						2,6,6, 2,4,1, 4,6,6, 4,4,1, 6,6,6, 6,4,1,
						1,3,1, 2,3,1, 3,3,1, 4,3,1, 5,3,1, 6,3,1,
						1,2,1, 2,2,1, 3,2,1, 4,2,1, 5,2,1, 6,2,1,
						1,3,4, 1,4,2, 3,3,4, 3,4,2, 5,3,4, 5,4,2
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = 0.0f;
				stageAreaFloat     = new float[]{
						12.0f,12.0f,12.0f
				};
				break;
			case 14://min 
				cubeCount     = 36;
				multipleCount = 1;
				cubeType      = ObjLoader.BLUE_CUBE;
				preStagePositionArray = new int[]{
						1,4,5, 2,6,1, 3,6,1, 4,6,1, 5,6,1, 6,6,1,
						1,5,1, 2,5,1, 3,5,1, 4,5,1, 5,5,1, 6,5,1,
						1,4,1, 2,4,1, 3,4,1, 4,4,1, 5,4,1, 6,4,1,
						3,6,6, 2,3,1, 3,3,1, 4,3,1, 5,3,1, 6,3,1,
						1,2,1, 2,2,1, 3,2,1, 4,2,1, 5,2,1, 6,2,1,
						1,1,4, 4,6,6, 3,1,1, 4,1,3, 6,1,6, 6,1,1
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = 0.0f;
				stageAreaFloat     = new float[]{
						12.0f,12.0f,12.0f
				};
				break;
			case 15://min
				cubeCount     = 36;
				multipleCount = 1;
				cubeType      = ObjLoader.BLUE_CUBE;
				preStagePositionArray = new int[]{
						1,4,3, 2,6,1, 1,6,6, 4,6,1, 5,6,1, 6,6,4,
						1,5,1, 2,5,1, 3,5,1, 4,5,1, 5,5,1, 6,5,1,
						1,4,1, 2,4,1, 3,4,1, 4,4,1, 5,4,1, 4,6,6,
						3,1,4, 2,3,1, 3,3,1, 4,3,1, 5,3,1, 6,3,1,
						1,2,1, 2,2,1, 3,2,1, 4,2,1, 5,2,1, 6,2,1,
						1,1,2, 2,1,1, 3,1,1, 6,3,5, 5,1,1, 6,1,3
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = 0.0f;
				stageAreaFloat     = new float[]{
						12.0f,12.0f,12.0f
				};
				break;
			case 16://min
				cubeCount     = 36;
				multipleCount = 1;
				cubeType      = ObjLoader.BLUE_CUBE;
				preStagePositionArray = new int[]{
						1,6,1, 2,6,1, 3,6,1, 4,6,2, 5,6,1, 6,6,2,
						2,5,3, 2,5,1, 4,6,4, 4,5,1, 6,6,5, 6,5,1,
						1,4,1, 2,4,1, 3,4,1, 4,4,2, 5,4,1, 6,4,2,
						2,3,3, 2,3,1, 4,4,6, 4,3,1, 6,4,4, 6,3,1,
						1,2,1, 2,2,1, 3,2,1, 4,2,2, 5,2,1, 6,2,2,
						2,1,3, 2,1,1, 4,2,5, 4,1,1, 6,2,6, 6,1,1
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = 0.0f;
				stageAreaFloat     = new float[]{
						12.0f,12.0f,12.0f
				};
				break;
			case 17://min
				cubeCount     = 36;
				multipleCount = 1;
				cubeType      = ObjLoader.BLUE_CUBE;
				preStagePositionArray = new int[]{
						1,6,1, 2,6,1, 3,6,1, 4,6,1, 6,6,1, 6,6,5,
						1,5,1, 2,5,1, 3,5,1, 4,5,1, 5,5,1, 6,5,1,
						1,4,1, 2,4,1, 4,6,2, 4,4,1, 5,4,1, 6,6,3,
						2,6,2, 2,3,1, 3,3,1, 4,3,1, 5,3,1, 6,3,1,
						1,2,1, 2,2,1, 3,2,1, 4,2,1, 5,2,1, 6,2,1,
						1,2,2, 2,1,1, 3,3,2, 4,1,1, 5,1,1, 6,3,4
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = 0.0f;
				stageAreaFloat     = new float[]{
						12.0f,12.0f,12.0f
				};
				break;
			case 18://min
				cubeCount     = 36;
				multipleCount = 1;
				cubeType      = ObjLoader.BLUE_CUBE;
				preStagePositionArray = new int[]{
						1,6,2, 2,6,1, 5,6,4, 2,6,4, 5,6,1, 5,6,3,
						1,5,1, 2,5,1, 3,5,1, 4,5,1, 5,5,1, 6,5,1,
						1,4,1, 2,4,1, 3,4,1, 4,4,1, 5,4,1, 6,4,1,
						1,3,1, 2,3,1, 3,3,1, 4,6,5, 5,3,1, 6,3,1,
						1,2,1, 2,2,1, 3,2,1, 4,2,1, 5,2,1, 6,2,1,
						1,1,1, 2,1,1, 3,1,1, 4,2,6, 5,1,1, 6,1,1
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = 0.0f;
				stageAreaFloat     = new float[]{
						12.0f,12.0f,12.0f
				};
				break;
			case 19://min
				cubeCount     = 36;
				multipleCount = 1;
				cubeType      = ObjLoader.BLUE_CUBE;
				preStagePositionArray = new int[]{
						1,6,1, 2,6,1, 3,6,1, 4,6,1, 5,6,1, 6,6,1,
						1,6,2, 2,5,1, 3,6,6, 4,5,1, 5,5,1, 6,5,1,
						1,4,1, 2,4,1, 3,4,1, 4,4,1, 5,4,1, 6,4,1,
						1,4,2, 2,3,1, 3,4,6, 4,3,1, 5,3,1, 6,3,1,
						1,2,1, 2,2,1, 3,2,1, 4,2,1, 5,2,1, 6,2,1,
						1,2,2, 1,1,4, 3,1,1, 3,2,6, 5,1,1, 5,1,6
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = 0.0f;
				stageAreaFloat     = new float[]{
						12.0f,12.0f,12.0f
				};
				break;
			case 20://min
				cubeCount     = 36;
				multipleCount = 1;
				cubeType      = ObjLoader.BLUE_CUBE;
				preStagePositionArray = new int[]{
						1,6,1, 2,6,2, 3,6,1, 2,6,4, 5,6,1, 5,6,3,
						1,5,1, 2,5,3, 3,5,1, 2,6,6, 5,5,1, 5,5,4,
						1,4,1, 2,4,1, 3,4,1, 4,4,1, 5,4,1, 6,4,1,
						1,3,1, 2,3,4, 3,3,1, 2,4,6, 5,3,1, 5,3,5,
						1,2,1, 2,2,1, 3,2,1, 4,2,1, 5,2,1, 6,2,1,
						1,1,1, 2,1,1, 3,1,1, 2,2,6, 5,1,1, 5,1,6
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = 0.0f;
				stageAreaFloat     = new float[]{
						12.0f,12.0f,12.0f
				};
				break;
			case 21://min 
				cubeCount     = 49;
				multipleCount = 2;
				cubeType      = ObjLoader.GREEN_CUBE;
				preStagePositionArray = new int[]{
						1,7,1, 2,7,1, 3,7,1, 4,7,7, 5,7,1, 6,7,1, 7,7,1,
						1,6,1, 2,6,1, 3,6,1, 4,6,1, 5,6,1, 6,6,1, 7,6,1,
						1,5,1, 2,5,1, 3,7,7, 4,6,7, 5,7,7, 6,5,1, 7,5,1,
						1,4,1, 2,4,1, 3,4,1, 4,4,1, 5,4,1, 6,4,1, 7,4,1,
						1,3,1, 2,7,7, 3,4,7, 4,4,7, 5,4,7, 6,7,7, 7,3,1,
						1,2,1, 2,2,1, 3,2,1, 4,2,1, 5,2,1, 6,2,1, 7,2,1,
						1,7,7, 2,2,7, 3,2,7, 4,2,7, 5,2,7, 6,2,7, 7,7,7
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = -0.1f;
				stageAreaFloat     = new float[]{
						14.0f,14.0f,14.0f
				};
				break;
			case 22://min 最下段左２回、上向きmulti
				cubeCount     = 49;
				multipleCount = 2;
				cubeType      = ObjLoader.GREEN_CUBE;
				preStagePositionArray = new int[]{
						1,3,4, 2,7,1, 3,7,1, 4,7,1, 5,7,1, 6,6,6, 7,6,7,
						1,6,1, 2,6,1, 3,6,1, 4,6,1, 5,6,1, 6,6,1, 7,6,1,
						1,5,1, 2,5,1, 3,5,1, 4,5,1, 5,5,1, 6,4,6, 7,1,5,
						1,4,1, 2,4,1, 3,4,1, 4,4,1, 5,4,1, 6,4,1, 7,4,1,
						1,3,1, 2,3,1, 3,3,1, 4,3,1, 5,3,1, 6,2,6, 7,3,1,
						2,1,4, 2,2,1, 3,2,1, 4,2,1, 5,2,1, 6,2,1, 7,2,1,
						1,1,1, 4,1,4, 3,1,1, 7,1,4, 5,1,1, 7,1,3, 7,1,2
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = -0.1f;
				stageAreaFloat     = new float[]{
						14.0f,14.0f,14.0f
				};
				break;
			case 23://min 
				cubeCount     = 49;
				multipleCount = 2;
				cubeType      = ObjLoader.GREEN_CUBE;
				preStagePositionArray = new int[]{
						1,6,5, 2,7,1, 3,7,1, 4,7,1, 5,7,1, 6,7,1, 7,7,5,
						1,6,1, 2,6,1, 3,6,1, 4,6,1, 5,6,1, 6,6,1, 7,6,1,
						1,4,5, 2,5,1, 3,5,1, 5,5,5, 5,5,1, 7,5,5, 7,5,1,
						1,4,1, 2,4,1, 3,4,1, 4,4,1, 5,4,1, 6,4,1, 7,4,1,
						2,2,5, 2,3,1, 3,3,1, 4,3,1, 5,3,1, 6,3,1, 7,3,1,
						1,2,1, 2,2,1, 5,2,5, 4,2,1, 5,2,1, 7,2,5, 7,2,1,
						1,1,1, 2,1,1, 3,5,5, 4,1,5, 5,1,1, 6,1,1, 5,7,5
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = -0.1f;
				stageAreaFloat     = new float[]{
						14.0f,14.0f,14.0f
				};
				break;
			case 24://min 
				cubeCount     = 49;
				multipleCount = 2;
				cubeType      = ObjLoader.GREEN_CUBE;
				preStagePositionArray = new int[]{
						1,7,1, 2,7,1, 3,7,1, 4,7,1, 5,7,1, 6,6,2, 7,7,1,
						1,6,1, 2,6,1, 3,6,1, 4,6,1, 5,6,1, 6,6,1, 7,7,7,
						2,5,2, 2,5,1, 4,5,2, 3,5,4, 6,4,2, 6,5,1, 5,5,5,
						1,4,1, 2,4,1, 3,4,1, 4,4,1, 5,4,1, 6,4,1, 7,4,1,
						3,3,2, 2,3,1, 3,5,6, 6,2,2, 5,3,1, 6,3,1, 7,5,7,
						1,2,1, 2,2,1, 3,2,1, 4,2,1, 5,2,1, 6,2,1, 7,2,1,
						1,1,1, 2,1,1, 3,2,7, 4,1,1, 5,1,1, 6,1,1, 7,2,7
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = -0.1f;
				stageAreaFloat     = new float[]{
						14.0f,14.0f,14.0f
				};
				break;
			case 25://min 最上段三つを↓　最上段→　中段←　下段真ん中辺り↑
				cubeCount     = 49;
				multipleCount = 2;
				cubeType      = ObjLoader.GREEN_CUBE;
				preStagePositionArray = new int[]{
						1,7,1, 2,7,1, 1,7,5, 4,7,1, 4,7,5, 6,7,1, 6,7,5,
						1,7,3, 2,6,1, 3,6,1, 4,6,1, 5,4,5, 6,6,1, 7,6,1,
						1,5,1, 2,5,1, 3,5,1, 4,5,1, 5,5,1, 6,5,1, 7,7,7,
						2,4,5, 2,4,1, 3,7,5, 4,4,5, 5,4,1, 7,4,5, 7,4,1,
						1,5,3, 2,3,1, 3,3,1, 4,3,1, 5,1,5, 6,3,1, 7,4,7,
						1,2,1, 2,2,1, 3,2,1, 4,2,1, 5,2,1, 6,2,1, 7,2,1,
						1,2,3, 2,1,1, 3,3,5, 4,1,1, 5,1,1, 6,1,1, 7,2,7
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = -0.1f;
				stageAreaFloat     = new float[]{
						14.0f,14.0f,14.0f
				};
				break;
			case 26://min 3 6
				cubeCount     = 49;
				multipleCount = 2;
				cubeType      = ObjLoader.GREEN_CUBE;
				preStagePositionArray = new int[]{
						3,7,5, 2,7,1, 3,7,1, 4,7,1, 5,7,1, 7,7,5, 7,7,1,
						1,6,7, 3,6,7, 4,6,7, 4,6,1, 7,6,7, 6,6,1, 7,6,1,
						1,5,1, 5,7,5, 3,5,1, 4,5,1, 5,5,1, 6,5,1, 7,5,1,
						4,4,5, 2,4,1, 3,4,1, 4,4,1, 5,4,1, 7,4,5, 7,4,1,
						1,3,1, 5,4,5, 3,3,1, 4,3,1, 5,3,1, 6,3,1, 7,3,1,
						1,2,1, 2,2,1, 3,2,1, 4,2,1, 5,2,1, 6,2,1, 7,2,1,
						1,1,1, 2,2,5, 3,1,1, 4,1,1, 5,1,1, 6,1,1, 7,1,1
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = -0.1f;
				stageAreaFloat     = new float[]{
						14.0f,14.0f,14.0f
				};
				break;
			case 27://min 
				cubeCount     = 49;
				multipleCount = 2;
				cubeType      = ObjLoader.GREEN_CUBE;
				preStagePositionArray = new int[]{
						1,7,7, 2,7,1, 1,7,2, 4,7,1, 4,7,4, 6,7,1, 6,7,4,
						1,6,1, 2,6,1, 3,6,1, 4,6,1, 5,6,1, 6,6,1, 7,6,1,
						1,5,1, 2,5,1, 3,5,1, 4,5,1, 5,5,1, 6,5,1, 7,5,1,
						1,4,1, 2,4,1, 3,4,1, 1,7,5, 5,4,1, 6,4,1, 5,4,6,
						1,3,1, 2,3,1, 3,3,1, 4,3,1, 5,3,1, 6,3,1, 7,3,1,
						1,2,1, 2,2,1, 3,2,1, 4,2,1, 5,2,1, 6,2,1, 7,2,1,
						1,1,1, 1,3,6, 3,1,1, 3,1,6, 5,1,1, 6,1,1, 5,1,6
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = -0.1f;
				stageAreaFloat     = new float[]{
						14.0f,14.0f,14.0f
				};
				break;
			case 28://min 5   最上段↓　下段→　←multi４つ中飛ばし有り　最下段↑
				cubeCount     = 49;
				multipleCount = 2;
				cubeType      = ObjLoader.GREEN_CUBE;
				preStagePositionArray = new int[]{
						1,7,1, 2,7,1, 3,7,1, 4,7,1, 6,5,7, 6,7,1, 7,7,1,
						2,6,7, 5,7,7, 3,6,1, 4,6,1, 5,6,1, 7,6,7, 7,6,1,
						4,5,7, 2,5,1, 3,5,1, 4,5,1, 5,5,1, 6,5,1, 7,5,1,
						3,4,7, 2,4,1, 3,4,1, 5,5,7, 3,3,7, 7,4,7, 7,4,1,
						1,3,1, 1,3,7, 3,3,1, 4,3,1, 5,3,1, 6,3,1, 6,3,7,
						1,2,1, 2,2,1, 3,2,1, 4,2,1, 5,3,7, 6,2,1, 7,2,1,
						1,1,1, 2,1,1, 3,1,1, 4,1,1, 5,1,1, 6,1,1, 7,1,1
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = -0.1f;
				stageAreaFloat     = new float[]{
						14.0f,14.0f,14.0f
				};
				break;
			case 29://min 左回りと右回り、上向きでmulti
				cubeCount     = 49;
				multipleCount = 2;
				cubeType      = ObjLoader.GREEN_CUBE;
				preStagePositionArray = new int[]{
						1,3,4, 2,7,1, 2,7,4, 7,7,7, 4,7,4, 6,7,1, 7,6,7,
						1,6,1, 2,6,1, 3,6,1, 4,6,1, 5,6,1, 6,6,1, 6,7,4,
						3,7,7, 2,5,1, 3,5,1, 4,5,1, 5,5,1, 6,5,1, 7,3,7,
						1,4,1, 2,4,1, 3,4,1, 4,4,1, 5,4,1, 6,4,1, 7,5,4,
						1,4,7, 2,3,1, 3,3,1, 4,3,1, 5,3,1, 6,3,1, 7,3,1,
						3,1,4, 2,2,1, 3,2,1, 4,2,1, 5,2,1, 6,2,1, 6,1,7,
						1,1,1, 2,1,1, 1,2,7, 7,3,4, 4,1,7, 6,1,1, 7,1,1
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = -0.1f;
				stageAreaFloat     = new float[]{
						14.0f,14.0f,14.0f
				};
				break;
			case 30://min 左下段を↑　その中段を→　multi↓　最下段を一つ←
				cubeCount     = 49;
				multipleCount = 2;
				cubeType      = ObjLoader.GREEN_CUBE;
				preStagePositionArray = new int[]{
						1,5,7, 2,7,1, 3,7,1, 4,7,1, 5,7,1, 6,7,1, 7,7,1,
						1,6,1, 2,6,1, 3,7,6, 4,6,1, 5,6,1, 6,7,4, 7,6,1,
						1,5,1, 2,7,5, 3,5,1, 4,7,4, 5,5,1, 6,5,1, 7,7,3,
						1,4,1, 1,3,7, 3,4,1, 4,7,7, 3,4,7, 6,4,1, 6,4,7,
						1,3,1, 2,3,1, 3,5,6, 4,4,4, 5,3,1, 6,7,6, 7,4,3,
						1,1,7, 2,2,1, 3,2,1, 4,2,1, 5,2,1, 6,2,1, 7,2,1,
						4,2,4, 2,4,5, 3,2,6, 4,3,7, 6,5,4, 6,2,6, 7,2,3
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = -0.1f;
				stageAreaFloat     = new float[]{
						14.0f,14.0f,14.0f
				};
				break;
			case 31://min 
				cubeCount     = 64;
				multipleCount = 3;
				cubeType      = ObjLoader.GREEN_CUBE;
				preStagePositionArray = new int[]{
						2,8,3, 2,8,1, 4,8,3, 4,8,1, 6,8,3, 6,8,1, 8,8,3, 8,8,1,
						1,7,1, 1,7,8, 3,7,1, 3,7,8, 5,7,1, 5,7,8, 7,7,1, 7,7,8,
						2,6,4, 2,6,1, 4,6,4, 4,6,1, 6,6,4, 6,6,1, 8,6,4, 8,6,1,
						1,5,1, 1,5,7, 3,5,1, 3,5,7, 5,5,1, 5,5,7, 7,5,1, 7,5,7,
						2,4,5, 2,4,1, 4,4,5, 4,4,1, 6,4,5, 6,4,1, 8,4,5, 8,4,1,
						1,3,1, 1,3,6, 3,3,1, 3,3,6, 5,3,1, 5,3,6, 7,3,1, 7,3,6,
						2,2,6, 2,2,1, 4,2,6, 4,2,1, 6,2,6, 6,2,1, 8,2,6, 8,2,1,
						1,1,1, 1,1,5, 3,1,1, 3,1,5, 5,1,1, 5,1,5, 7,1,1, 7,1,5
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = -0.2f;
				stageAreaFloat     = new float[]{
						16.0f,16.0f,16.0f
				};
				break;
			case 32://min  multi左側三つを→　multi最上段を複数↓
				cubeCount     = 64;
				multipleCount = 3;
				cubeType      = ObjLoader.GREEN_CUBE;
				preStagePositionArray = new int[]{
						1,8,1, 2,8,1, 3,8,1, 4,8,1, 5,8,1, 6,8,1, 7,8,1, 7,8,2,
						1,7,1, 2,7,1, 3,7,1, 4,7,1, 5,8,5, 1,8,2, 7,8,7, 8,7,1,
						1,6,1, 2,6,1, 3,6,1, 4,6,1, 5,6,1, 6,6,1, 7,6,1, 8,6,1,
						1,5,1, 2,5,1, 3,6,5, 4,5,1, 5,5,1, 6,5,1, 7,5,1, 8,5,1,
						1,4,1, 2,4,1, 1,4,8, 4,4,1, 5,4,1, 6,4,1, 7,4,1, 4,4,8,
						1,3,1, 2,7,5, 3,3,1, 4,3,1, 5,6,5, 6,6,2, 7,6,7, 8,3,1,
						1,2,1, 2,2,1, 3,2,1, 4,2,1, 5,2,1, 6,2,1, 7,2,1, 6,2,5,
						1,1,1, 1,2,5, 3,4,5, 4,1,1, 3,2,5, 6,2,2, 7,2,7, 8,1,1
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = -0.2f;
				stageAreaFloat     = new float[]{
						16.0f,16.0f,16.0f
				};
				break;
			case 33://min 右４つ↓　下４つを空けて←　左下二つを↑
				cubeCount     = 64;
				multipleCount = 3;
				cubeType      = ObjLoader.GREEN_CUBE;
				preStagePositionArray = new int[]{
						1,6,7, 2,4,3, 3,8,1, 4,8,1, 5,8,1, 6,8,1, 7,8,1, 8,8,1,
						1,7,1, 2,7,1, 3,7,1, 4,8,7, 5,7,1, 6,7,1, 7,7,1, 8,8,3,
						1,6,1, 2,6,1, 3,6,1, 4,6,1, 5,6,1, 6,6,1, 7,6,1, 8,6,1,
						4,6,7, 2,5,1, 3,5,1, 4,5,1, 5,5,1, 6,5,1, 7,5,1, 8,5,1,
						1,4,1, 2,4,1, 3,4,1, 4,4,1, 5,4,1, 6,4,1, 7,4,1, 8,4,1,
						1,3,1, 5,1,3, 3,3,1, 4,3,1, 5,3,1, 6,3,1, 7,3,1, 8,3,1,
						1,2,1, 2,2,1, 3,2,1, 4,2,1, 5,2,1, 6,2,1, 7,2,1, 8,2,1,
						4,6,3, 6,1,7, 3,1,1, 4,1,1, 5,1,1, 8,6,3, 8,6,7, 8,1,1
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = -0.2f;
				stageAreaFloat     = new float[]{
						16.0f,16.0f,16.0f
				};
				break;
			case 34://min 1.上段左側から６つと中段で穴の無い所に合わせて左側中１つと中側同y軸下１つ
				//と右側同y軸２つをy↓multi
				cubeCount     = 64;
				multipleCount = 3;
				cubeType      = ObjLoader.GREEN_CUBE;
				preStagePositionArray = new int[]{
						8,8,5, 2,8,1, 3,8,1, 4,8,1, 5,8,1, 6,8,1, 7,8,1, 8,8,1,
						1,7,1, 2,8,5, 3,7,1, 4,8,5, 5,8,5, 6,8,5, 7,7,1, 8,7,1,
						8,6,5, 2,6,1, 3,6,1, 4,6,1, 5,6,1, 6,6,1, 7,6,1, 8,6,1,
						1,5,1, 2,6,5, 3,8,5, 4,6,5, 5,5,1, 6,6,5, 7,8,5, 8,5,1,
						8,4,5, 2,4,1, 3,4,1, 4,4,1, 5,4,1, 6,4,1, 7,4,1, 8,4,1,
						1,3,1, 2,3,1, 3,3,1, 4,3,1, 5,3,1, 6,3,1, 7,6,5, 8,3,1,
						1,2,1, 2,2,1, 3,6,5, 4,2,1, 5,6,5, 6,2,1, 7,4,5, 8,2,1,
						1,1,1, 2,4,5, 3,3,5, 4,4,5, 5,3,5, 6,4,5, 7,2,5, 8,1,1
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = -0.2f;
				stageAreaFloat     = new float[]{
						16.0f,16.0f,16.0f
				};
				break;
			case 35://min 最上段最下段をそれぞれ移動、
				//右端または左端の同y軸２つをmultiで左右移動
				cubeCount     = 64;
				multipleCount = 3;
				cubeType      = ObjLoader.GREEN_CUBE;
				preStagePositionArray = new int[]{
						1,8,1, 2,8,1, 3,8,1, 4,3,5, 5,3,4, 6,8,1, 7,8,1, 8,8,1,
						1,7,1, 2,7,1, 3,7,1, 4,7,1, 5,7,1, 6,7,1, 7,7,1, 8,7,1,
						1,6,1, 2,6,1, 3,6,1, 4,6,1, 5,6,1, 6,6,1, 7,6,1, 8,6,1,
						1,5,4, 1,5,5, 4,8,5, 4,5,1, 5,5,1, 5,8,4, 8,5,4, 8,5,5,
						1,4,5, 1,4,4, 3,4,1, 4,1,4, 5,1,5, 6,4,1, 8,4,5, 8,4,4,
						1,3,1, 2,3,1, 3,3,1, 4,3,1, 5,3,1, 6,3,1, 7,3,1, 8,3,1,
						1,2,1, 2,2,1, 3,2,1, 4,2,1, 5,2,1, 6,2,1, 7,2,1, 8,2,1,
						1,1,1, 2,1,1, 3,1,1, 4,6,4, 5,6,5, 6,1,1, 7,1,1, 8,1,1
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = -0.2f;
				stageAreaFloat     = new float[]{
						16.0f,16.0f,16.0f
				};
				break;
			case 36://min 1.網目状手前側８つ、直線状奥側全部と手前側上から２、４、６番目をx→multi
				//2.上側４つをy↓multi
				cubeCount     = 134;
				multipleCount = 3;
				cubeType      = ObjLoader.GREEN_CUBE;
				preStagePositionArray = new int[]{
						1,10,1, 2,10,1, 3,10,1, 4,10,1, 5,10,1, 6,10,1, 7,10,1, 8,10,1, 9,10,1, 10,10,1,
						1,9,1,  2,9,1,  3,9,1,  4,9,1,  5,9,1,  6,9,1,  7,9,1,  8,9,1,  9,9,1,  10,9,1,
						1,8,1,  2,8,1,  3,8,1,  4,8,1,  5,8,1,  6,8,1,          8,8,1,          10,8,1,
						1,7,1,  2,7,1,  3,7,1,  4,7,1,  5,7,1,  6,7,1,  7,7,1,  8,7,1,  9,7,1,  10,7,1,
						1,6,1,  2,6,1,  3,6,1,  4,6,1,  5,6,1,  6,6,1,          8,6,1,  	    10,6,1,
						1,5,1,  2,5,1,  3,5,1,  4,5,1,  5,5,1,  6,5,1,  7,5,1,  8,5,1,  9,5,1,  10,5,1,
						1,4,1,  2,4,1,  3,4,1,  4,4,1,  5,4,1,  6,4,1,  7,4,1,  8,4,1,  9,4,1,  10,4,1,
						1,3,1,  2,3,1,  3,3,1,  4,3,1,  5,3,1,  6,3,1,  7,3,1,  8,3,1,  9,3,1,  10,3,1,
						1,2,1,  2,2,1,  3,2,1,  4,2,1,  5,2,1,  6,2,1,          8,2,1,          10,2,1,
						1,1,1,  2,1,1,  3,1,1,  4,1,1,  5,1,1,  6,1,1,  7,1,1,  8,1,1,  9,1,1,  10,1,1,
						
						9,10,6, 9,9,6,  9,8,6,  9,7,6,  9,6,6,  9,5,6,  9,4,6,
						9,10,7, 9,9,7,  9,8,7,  9,7,7,  9,6,7,  9,5,7,  9,4,7,
						
						1,8,6,  1,6,6,  1,4,6,
						2,9,6,  2,7,6,  2,5,6,
						3,8,6,  3,6,6,  3,4,6,
						4,9,6,  4,7,6,  4,5,6,
						
						3,9,7,  3,7,7,  3,5,7,
						4,10,7, 4,8,7,  4,6,7,  4,4,7,
						1,9,7,  1,7,7,  1,5,7,
						2,10,7, 2,8,7,  2,6,7,  2,4,7,
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = -0.2f;
				stageAreaFloat     = new float[]{
						20.0f,20.0f,20.0f
				};
				break;
			case 37://min 1.左側から５つの塊をx→
				//2.下から６つy↑multi
				//3.上側２つをy↓multi
				cubeCount     = 117;
				multipleCount = 4;
				cubeType      = ObjLoader.GREEN_CUBE;
				preStagePositionArray = new int[]{
						1,10,1, 2,10,1, 3,10,1, 4,10,1, 5,10,1,         7,10,1, 8,10,1, 9,10,1, 10,10,1,
						1,9,1,  2,9,1,  3,9,1,  4,9,1,  5,9,1,  6,9,1,  7,9,1,  8,9,1,  9,9,1,  10,9,1,
						1,8,1,  2,8,1,  3,8,1,  4,8,1,  5,8,1,  6,8,1,  7,8,1,  8,8,1,  9,8,1,  10,8,1,
						1,7,1,  2,7,1,  3,7,1,  4,7,1,  5,7,1,  6,7,1,  7,7,1,          9,7,1,  10,7,1,
						1,6,1,  2,6,1,  3,6,1,  4,6,1,  5,6,1,          7,6,1,          9,6,1,  
						1,5,1,  2,5,1,  3,5,1,  4,5,1,  5,5,1,          7,5,1,  8,5,1,  9,5,1,  
						1,4,1,  2,4,1,  3,4,1,  4,4,1,  5,4,1,  6,4,1,  7,4,1,  8,4,1,  9,4,1,  10,4,1,
						1,3,1,  2,3,1,  3,3,1,  4,3,1,  5,3,1,  6,3,1,  7,3,1,  8,3,1,  9,3,1,  10,3,1,
						1,2,1,  2,2,1,  3,2,1,  4,2,1,  5,2,1,  6,2,1,  7,2,1,  8,2,1,  9,2,1,  10,2,1,
						1,1,1,  2,1,1,  3,1,1,  4,1,1,  5,1,1,  6,1,1,  7,1,1,  8,1,1,  9,1,1,  10,1,1,
						
						2,8,9,   3,8,7,  4,8,9,                    7,8,7,  8,8,9,   9,8,7,
						2,7,10,  3,7,8,  4,7,10,                   7,7,8,  8,7,10,  9,7,8,
						
						2,4,9,  3,4,7,                  6,4,9,  7,4,7,  8,4,9,  9,4,7,
						2,3,10, 3,3,8,                  6,3,10, 7,3,8,  8,3,10, 9,3,8,
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = -0.2f;
				stageAreaFloat     = new float[]{
						20.0f,20.0f,20.0f
				};
				break;
			case 38://min 1.手前側下段一つと真ん中辺り下段２つをy↑multi,
				//2.奥側上段1つと真ん中辺り上段２つをy	↓multi
				cubeCount     = 108;
				multipleCount = 3;
				cubeType      = ObjLoader.GREEN_CUBE;
				preStagePositionArray = new int[]{
						1,10,1, 2,10,1, 3,10,1, 4,10,1, 5,10,1, 6,10,1, 7,10,1,         9,10,1, 10,10,1,
						1,9,1,  2,9,1,  3,9,1,  4,9,1,  5,9,1,  6,9,1,  7,9,1,  8,9,1,  9,9,1,  10,9,1,
						1,8,1,  2,8,1,  3,8,1,  4,8,1,  5,8,1,  6,8,1,  7,8,1,  8,8,1,  9,8,1,  10,8,1,
						        2,7,1,          4,7,1,          6,7,1,  7,7,1,  8,7,1,          10,7,1,
						1,6,1,  2,6,1,  3,6,1,  4,6,1,  5,6,1,  6,6,1,  7,6,1,          9,6,1,  10,6,1,
						1,5,1,  2,5,1,          4,5,1,                  7,5,1,  8,5,1,  9,5,1,
						1,4,1,  2,4,1,  3,4,1,  4,4,1,  5,4,1,  6,4,1,  7,4,1,  8,4,1,  9,4,1,  10,4,1,
						        2,3,1,  3,3,1,          5,3,1,  6,3,1,  7,3,1,          9,3,1,  10,3,1,
						1,2,1,  2,2,1,  3,2,1,  4,2,1,  5,2,1,  6,2,1,  7,2,1,          9,2,1,  10,2,1,
						1,1,1,  2,1,1,  3,1,1,  4,1,1,          6,1,1,  7,1,1,          9,1,1,  10,1,1,
						
						8,1,9,  8,3,9,  8,8,9,  10,7,9, 2,7,9,
						5,9,3,  5,6,3,  5,2,3,  3,3,3,  2,3,3,
						1,5,6,  4,1,6,  6,10,6, 7,10,6,
						9,5,6,  8,10,6, 8,2,6,
						4,6,6,  5,7,6,  6,4,6,
						5,1,6,  5,3,6,  7,3,6,  7,7,6
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = -0.2f;
				stageAreaFloat     = new float[]{
						20.0f,20.0f,20.0f
				};
				break;
			case 39://min 1.最下段手前４つと、手前側左中段１つ、下段左から２番目手前から間２つ飛ばして１つ、
				//計６つをz方向奥へmulti
				//2.縦に３つ並んだブロックの真ん中１つ、同x軸上中段２つ、最下段同z軸上4つ並んだ手前から２番目を１つ、
				//計４つをx→方向へmulti
				//3.最下段５つをy↑方向にmulti
				cubeCount     = 110;
				multipleCount = 4;
				cubeType      = ObjLoader.GREEN_CUBE;
				preStagePositionArray = new int[]{
						1,10,1, 2,10,1, 3,10,1, 4,10,1, 5,10,1, 6,10,1, 7,10,1, 8,10,1, 9,10,1, 10,10,1,
						1,9,1,  2,9,1,  3,9,1,  4,9,1,  5,9,1,  6,9,1,  7,9,1,  8,9,1,  9,9,1,  10,9,1,
						1,8,1,  2,8,1,  3,8,1,          5,8,1,  6,8,1,  7,8,1,  8,8,1,  9,8,1,  10,8,1,
						1,7,1,  2,7,1,  3,7,1,  4,7,1,  5,7,1,  6,7,1,  7,7,1,  8,7,1,  9,7,1,  10,7,1,
						1,6,1,  2,6,1,  3,6,1,          5,6,1,  6,6,1,  7,6,1,  8,6,1,  9,6,1,  10,6,1,
						1,5,1,  2,5,1,  3,5,1,          5,5,1,  6,5,1,          8,5,1,          
						1,4,1,  2,4,1,  3,4,1,          5,4,1,          7,4,1,  8,4,1,  9,4,1,  
						        2,3,1,  3,3,1,  4,3,1,  5,3,1,  6,3,1,  7,3,1,  8,3,1,  9,3,1,  10,3,1,
						1,2,1,  2,2,1,  3,2,1,  4,2,1,  5,2,1,  6,2,1,  7,2,1,  8,2,1,  9,2,1,  10,2,1,
						        2,1,1,  3,1,1,          5,1,1,  6,1,1,          8,1,1,  9,1,1,  
						
						
						1,1,3,  1,4,4,  1,5,4,  1,6,4,  1,1,10,
						4,1,2,  4,1,4,  4,1,6,  4,1,8,  4,1,10,
						4,5,4,  4,5,8,  4,5,10, 
						7,1,5,  7,1,10, 7,5,2,  7,5,5,
						10,1,3, 10,1,7, 
						4,7,4,  4,9,9,  7,1,6,  6,5,7,  10,5,6
						
						
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = -0.2f;
				stageAreaFloat     = new float[]{
						20.0f,20.0f,20.0f
				};
				break;
			case 40://min 1.z11面全てと、z3面z7面の左側隙間ブロックを一つ置きに→multi	
				//2.z3面右から並んだ四つずつ、z7面右から並んだ三つずつを全行←multi
				//3.z7面左側に移動した分（離れているものも）と、z3面の左側に移動した分のうち離れている一つと右側に残った分の一番右、z11面の一番左側を↓multi
				//4.z3面右側２列を全行、z7面右から２番目を全行←multi
				cubeCount     = 178;
				multipleCount = 5;
				cubeType      = ObjLoader.GREEN_CUBE;
				preStagePositionArray = new int[]{
						1,11,1, 2,11,1, 3,11,1, 4,11,1, 5,11,1, 6,11,1, 7,11,1, 8,11,1, 9,11,1, 10,11,1, 11,11,1,
						1,10,1,                                                                          11,10,1,
						1,9,1,                                                                           11,9,1,  
						1,8,1,                                                                           11,8,1,
						1,7,1,                                                                           11,7,1,
						1,6,1,                                                                           11,6,1,
						1,5,1,                                                                           11,5,1,
						1,4,1,                                                                           11,4,1,
						1,3,1,                                                                           11,3,1,
						1,2,1,                                                                           11,2,1,
						1,1,1,  2,1,1,  3,1,1,  4,1,1,  5,1,1,  6,1,1,  7,1,1,  8,1,1,  9,1,1,  10,1,1,  11,1,1,
						
						1,11,3,   3,11,3,  5,11,3,   7,11,3,  9,11,3,  11,11,3,
						1,10,3,                                        11,10,3,
						1,9,3,    3,9,3,   5,9,3,    7,9,3,    9,9,3,  11,9,3,
						1,8,3,                                         11,8,3,
						1,7,3,    3,7,3,   5,7,3,    7,7,3,    9,7,3,  11,7,3,
						1,6,3,                                         11,6,3,
						1,5,3,    3,5,3,   5,5,3,    7,5,3,    9,5,3,  11,5,3,
						1,4,3,                                         11,4,3,
						1,3,3,    3,3,3,   5,3,3,    7,3,3,    9,3,3,  11,3,3,
						1,2,3,                                         11,2,3,
						1,1,3,    3,1,3,   5,1,3,    7,1,3,    9,1,3,  11,1,3,
						
						1,11,7,   3,11,7,  5,11,7,   7,11,7,   9,11,7, 11,11,7,
						1,10,7,                                        11,10,7,
						1,9,7,    3,9,7,   5,9,7,     7,9,7,   9,9,7,  11,9,7,
						1,8,7,                                         11,8,7,
						1,7,7,    3,7,7,   5,7,7,     7,7,7,   9,7,7,  11,7,7,
						1,6,7,                                         11,6,7,
						1,5,7,    3,5,7,   5,5,7,     7,5,7,   9,5,7,  11,5,7,
						1,4,7,                                         11,4,7,
						1,3,7,    3,3,7,   5,3,7,     7,3,7,   9,3,7,  11,3,7,
						1,2,7,                                         11,2,7,
						1,1,7,    3,1,7,   5,1,7,     7,1,7,   9,1,7,  11,1,7,
						
						1,11,11,   3,11,11,  5,11,11,  7,11,11,   9,11,11, 11,11,11,
						1,10,11,                                           11,10,11,
						1,9,11,    3,9,11,   5,9,11,   7,9,11,   9,9,11,   11,9,11,
						1,8,11,                                            11,8,11,
						1,7,11,    3,7,11,   5,7,11,   7,7,11,   9,7,11,   11,7,11,
						1,6,11,                                            11,6,11,
						1,5,11,    3,5,11,   5,5,11,   7,5,11,   9,5,11,   11,5,11,
						1,4,11,                                            11,4,11,
						1,3,11,    3,3,11,   5,3,11,   7,3,11,   9,3,11,   11,3,11,
						1,2,11,                                            11,2,11,
						1,1,11,    3,1,11,   5,1,11,   7,1,11,   9,1,11,   11,1,11,
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = -0.4f;
				stageAreaFloat     = new float[]{
						22.0f,22.0f,22.0f
				};
				break;
			case 0:
				cubeCount     = 169;//三面分のcubeから重複分をマイナス
				multipleCount = 5;
				cubeType      = ObjLoader.GRAY_CUBE;
				wallDimension = 3;
				preStagePositionArray = new int[]{
						1,8,1, 2,8,1, 3,8,1, 4,8,1, 5,8,1, 6,8,1,        8,8,1,
						1,7,1, 2,7,1, 3,7,1, 4,7,1, 5,7,1, 6,7,1, 7,7,1, 8,7,1,
						1,6,1, 2,6,1, 3,6,1, 4,6,1, 5,6,1, 6,6,1, 7,6,1, 8,6,1,
						1,5,1, 2,5,1, 3,5,1,        	   6,5,1, 7,5,1, 8,5,1,
						1,4,1, 2,4,1, 3,4,1, 4,4,1, 5,4,1, 6,4,1, 7,4,1, 8,4,1,
						1,3,1, 2,3,1, 3,3,1, 4,3,1, 5,3,1, 6,3,1, 7,3,1, 8,3,1,
						1,2,1, 2,2,1, 3,2,1, 4,2,1, 5,2,1, 6,2,1, 7,2,1, 8,2,1,
						1,1,1, 2,1,1, 3,1,1, 4,1,1, 5,1,1, 6,1,1, 7,1,1, 8,1,1,
						
							   1,8,2, 1,8,3, 1,8,4, 1,8,5, 1,8,6, 1,8,7, 
							   1,7,2, 1,7,3, 1,7,4, 1,7,5, 1,7,6, 1,7,7, 1,7,8,
							   1,6,2, 1,6,3, 1,6,4, 1,6,5, 1,6,6, 1,6,7, 1,6,8,
							   1,5,2, 1,5,3, 1,5,4, 1,5,5, 1,5,6, 1,5,7, 
							   1,4,2, 1,4,3, 1,4,4, 1,4,5, 1,4,6, 1,4,7, 1,4,8,
							   1,3,2, 1,3,3, 1,3,4, 1,3,5, 1,3,6, 1,3,7, 1,3,8,
							   1,2,2, 1,2,3, 1,2,4, 1,2,5, 1,2,6, 1,2,7, 1,2,8,
							  
						
						1,1,8, 2,1,8, 3,1,8, 4,1,8, 5,1,8, 6,1,8, 7,1,8, 8,1,8,
						1,1,7,        3,1,7, 4,1,7, 5,1,7, 6,1,7, 7,1,7, 8,1,7,
						1,1,6, 2,1,6, 3,1,6, 4,1,6, 5,1,6, 6,1,6, 7,1,6, 
						1,1,5, 2,1,5, 3,1,5, 4,1,5, 5,1,5, 6,1,5, 7,1,5, 8,1,5,
						1,1,4, 2,1,4, 3,1,4, 4,1,4, 5,1,4, 6,1,4, 7,1,4, 8,1,4,
						1,1,3, 		  3,1,3, 4,1,3, 5,1,3, 6,1,3, 7,1,3, 
						1,1,2, 2,1,2, 3,1,2, 4,1,2, 5,1,2, 6,1,2, 7,1,2, 8,1,2,
						
						5,5,5, 7,8,7, 4,5,3,
						
						8,8,8, 6,5,8,
						
						2,5,3, 2,8,7, 8,8,6, 8,8,3,
						
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance           = -0.3f;
				stageAreaFloat     = new float[]{
						16.0f,16.0f,16.0f
				};
				break;
			case -1://min 
				cubeCount     = 178;
				multipleCount = 5;
				cubeType      = ObjLoader.GRAY_CUBE;
				preStagePositionArray = new int[]{
						1,11,1, 2,11,1, 3,11,1, 4,11,1, 5,11,1, 6,11,1, 7,11,1, 8,11,1, 9,11,1, 10,11,1, 11,11,1,
						1,10,1,                                                                          11,10,1,
						1,9,1,                                                                           11,9,1,  
						1,8,1,                                                                           11,8,1,
						1,7,1,                                                                           11,7,1,
						1,6,1,                                                                           11,6,1,
						1,5,1,                                                                           11,5,1,
						1,4,1,                                                                           11,4,1,
						1,3,1,                                                                           11,3,1,
						1,2,1,                                                                           11,2,1,
						1,1,1,  2,1,1,  3,1,1,  4,1,1,  5,1,1,  6,1,1,  7,1,1,  8,1,1,  9,1,1,  10,1,1,  11,1,1,
						
						1,11,3,   3,11,3,  5,11,3,   7,11,3,  9,11,3,  11,11,3,
						1,10,3,                                        11,10,3,
						1,9,3,    3,9,3,   5,9,3,    7,9,3,    9,9,3,  11,9,3,
						1,8,3,                                         11,8,3,
						1,7,3,    3,7,3,   5,7,3,    7,7,3,    9,7,3,  11,7,3,
						1,6,3,                                         11,6,3,
						1,5,3,    3,5,3,   5,5,3,    7,5,3,    9,5,3,  11,5,3,
						1,4,3,                                         11,4,3,
						1,3,3,    3,3,3,   5,3,3,    7,3,3,    9,3,3,  11,3,3,
						1,2,3,                                         11,2,3,
						1,1,3,    3,1,3,   5,1,3,    7,1,3,    9,1,3,  11,1,3,
						
						1,11,7,   3,11,7,  5,11,7,   7,11,7,   9,11,7, 11,11,7,
						1,10,7,                                        11,10,7,
						1,9,7,    3,9,7,   5,9,7,     7,9,7,   9,9,7,  11,9,7,
						1,8,7,                                         11,8,7,
						1,7,7,    3,7,7,   5,7,7,     7,7,7,   9,7,7,  11,7,7,
						1,6,7,                                         11,6,7,
						1,5,7,    3,5,7,   5,5,7,     7,5,7,   9,5,7,  11,5,7,
						1,4,7,                                         11,4,7,
						1,3,7,    3,3,7,   5,3,7,     7,3,7,   9,3,7,  11,3,7,
						1,2,7,                                         11,2,7,
						1,1,7,    3,1,7,   5,1,7,     7,1,7,   9,1,7,  11,1,7,
						
						1,11,11,   3,11,11,  5,11,11,  7,11,11,   9,11,11, 11,11,11,
						1,10,11,                                           11,10,11,
						1,9,11,    3,9,11,   5,9,11,   7,9,11,   9,9,11,   11,9,11,
						1,8,11,                                            11,8,11,
						1,7,11,    3,7,11,   5,7,11,   7,7,11,   9,7,11,   11,7,11,
						1,6,11,                                            11,6,11,
						1,5,11,    3,5,11,   5,5,11,   7,5,11,   9,5,11,   11,5,11,
						1,4,11,                                            11,4,11,
						1,3,11,    3,3,11,   5,3,11,   7,3,11,   9,3,11,   11,3,11,
						1,2,11,                                            11,2,11,
						1,1,11,    3,1,11,   5,1,11,   7,1,11,   9,1,11,   11,1,11,
				};
				stagePositionArray = convertPositionToFloat(preStagePositionArray);
				stageEyeDistance   = -0.2f;
				stageAreaFloat     = new float[]{
						22.0f,22.0f,22.0f
				};
				break;
		}
		currentScene.setInitialize(cubeCount);
		currentScene.setWallDimension(wallDimension);
		currentScene.setCubeType(cubeType);
		currentScene.setPositionArray(stagePositionArray);
		currentScene.setEyeDistance(stageEyeDistance);
		currentScene.setAreaFloat(stageAreaFloat);
		currentScene.setMultipleCount(multipleCount);
	}
	
	private static float[] convertPositionToFloat(int[] preArray){
		float[] postArray = new float[preArray.length];
		for(int i = 0 ; i < preArray.length;i++){
			postArray[i] = (float)(preArray[i]*2-1);
		}
		return postArray;
	}
	
}
