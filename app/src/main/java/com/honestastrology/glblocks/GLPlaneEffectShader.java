package com.honestastrology.glblocks;

import android.opengl.GLES20;
import android.util.Log;

public class GLPlaneEffectShader extends GLAbstractShader {
	
	private final String planeVertexShaderCode=
			"uniform mat4 uMVPMatrix;" +
			"uniform vec4 uCenterPosition;" +
			"uniform vec3 uPlaneNormal;" +
			"uniform float uCurrentTime;" +
			"uniform float uLifeTime;" +
			"attribute vec4 aEffectVertices;" +
			"attribute vec2 aTexUV;" +
			"varying vec2 vTexUV;" +
			"varying float vLifeTime;" +
			"void main(){" +
			" gl_Position=uMVPMatrix*aEffectVertices;" +
			" vLifeTime=float(uLifeTime-uCurrentTime);" +
			" vLifeTime=clamp(vLifeTime*0.8,0.0,1.0);" +
			" vTexUV=aTexUV;" +
			"}";
	private final String planeFragmentShaderCode=
			"precision mediump float;" +
			"uniform sampler2D uEffectTexture;" +
			"varying vec2 vTexUV;" +
			"varying float vLifeTime;" +
			"void main(){" +
			" vec4 texColor;" +
			" texColor=texture2D(uEffectTexture,vTexUV);" +
			" gl_FragColor=vec4(1.0)*texColor;" +
			" gl_FragColor.a*=vLifeTime;" +
			"}";
	
	private int _planeProgram;
	private int uMVPMatrixHandle;
	private int uCenterPositionHandle;
	private int uPlaneNormalHandle;
	private int uCurrentTimeHandle;
	private int uLifeTimeHandle;
	private int aEffectVerticesHandle;
	private int aTexUVHandle;
	private int uEffectTextureHandle;
	
	
	@Override
	public int loadShader(int shaderType, String shaderCode) {
		int shader=GLES20.glCreateShader(shaderType);
		GLES20.glShaderSource(shader, shaderCode);
		GLES20.glCompileShader(shader);
		int[] compileCheck=new int[1];
		GLES20.glGetShaderiv(shader,GLES20.GL_COMPILE_STATUS,compileCheck,0);
		if(compileCheck[0]==0){
			Log.e("plane_shader_compile", GLES20.glGetShaderInfoLog(shader));
		}
		return shader;
	}
	
	@Override
	public void createShaderProgram() {
		int planeVertexShader=loadShader(GLES20.GL_VERTEX_SHADER,planeVertexShaderCode);
		int planeFragmentShader=loadShader(GLES20.GL_FRAGMENT_SHADER,planeFragmentShaderCode);
		_planeProgram=GLES20.glCreateProgram();
		GLES20.glAttachShader(_planeProgram,planeVertexShader);
		GLES20.glAttachShader(_planeProgram, planeFragmentShader);
		GLES20.glLinkProgram(_planeProgram);
		int[] linkedCheck=new int[1];
		GLES20.glGetProgramiv(_planeProgram, GLES20.GL_LINK_STATUS, linkedCheck,0);
		if(linkedCheck[0]==0){
			Log.e("plane_program_link", GLES20.glGetProgramInfoLog(_planeProgram));
		}
		GLES20.glUseProgram(_planeProgram);
		uMVPMatrixHandle=GLES20.glGetUniformLocation(_planeProgram, "uMVPMatrix");
		uCenterPositionHandle=GLES20.glGetUniformLocation(_planeProgram, "uCenterPosition");
		uPlaneNormalHandle=GLES20.glGetUniformLocation(_planeProgram,"uPlaneNormal");
		uCurrentTimeHandle=GLES20.glGetUniformLocation(_planeProgram, "uCurrentTime");
		uLifeTimeHandle=GLES20.glGetUniformLocation(_planeProgram,"uLifeTime");
		aEffectVerticesHandle=GLES20.glGetAttribLocation(_planeProgram,"aEffectVertices");
		aTexUVHandle=GLES20.glGetAttribLocation(_planeProgram, "aTexUV");
		uEffectTextureHandle=GLES20.glGetUniformLocation(_planeProgram,"uEffectTexture");
		GLES20.glDeleteShader(planeVertexShader);
		GLES20.glDeleteShader(planeFragmentShader);
		
	}
	
}
