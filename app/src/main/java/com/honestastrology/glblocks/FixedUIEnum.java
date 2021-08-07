package com.honestastrology.glblocks;

public enum FixedUIEnum {
	
	upButton(0,new float[]{-0.68f,-0.2f},200.0f,false),
	downButton(1,new float[]{-0.68f,-0.8f},200.0f,false),
	rightButton(2,new float[]{-0.48f,-0.5f},200.0f,false),
	leftButton(3,new float[]{-0.88f,-0.5f},200.0f,false),
	nearButton(4,new float[]{0.88f,-0.4f},200.0f,false),
	farButton(5,new float[]{0.88f,-0.8f},200.0f,false),
	axisXRadio(6,new float[]{0.9f,0.85f},150.0f,true),
	axisYRadio(7,new float[]{0.9f,0.55f},150.0f,true),
	axisZRadio(8,new float[]{0.9f,0.25f},150.0f,true),
	returnButton(9,new float[]{-0.9f,0.9f},150.0f,false),
	restartButton(10,new float[]{-0.688f,0.9f},150.0f,false),
	bgmButton(11,new float[]{-0.46f,0.9f},150.0f,false),
	readyButton(12,new float[]{0.65f,0.82f},190.0f,false),
	nicheButton(13,new float[]{0.40f,0.82f},190.0f,false),
	setButton(14,new float[]{0.00f,0.82f},190.0f,false);
	
	private final int _id;
	private final float[] _allocation;
	private final float _texsize;
	private final boolean _radioBool;
	private static final float _commonCameraRadius     = 0.14f;
	private static final float _commonAxisRadius       = 0.1f;
	private static final float _commonRectangleRadiusX = 0.11f;
	private static final float _commonRectangleRadiusY = 0.75f;
	
	FixedUIEnum(int id,float[] allocation,float size,boolean radio){
		_id         = id;
		_allocation = allocation;
		_texsize    = size;
		_radioBool  = radio;
	}
	
	public int getId() {
		return _id;
	}

	public float[] getAllocation() {
		return _allocation;
	}

	public float getTexsize() {
		return _texsize;
	}

	public static float getCommonCameraRadius() {
		return _commonCameraRadius;
	}
	
	public static float getCommonAxisRadius() {
		return _commonAxisRadius;
	}

	public static float getCommonRectangleRadiusX() {
		return _commonRectangleRadiusX;
	}

	public static float getCommonRectangleRadiusY() {
		return _commonRectangleRadiusY;
	}

	public boolean isRadioBool() {
		return _radioBool;
	}
	
}
