package com.honestastrology.glblocks;

import android.opengl.GLES20;
import android.util.Log;

public class GLBaseParticleShader {
	
	public static final String particleVertexShaderCode=
			"uniform float uCurrentTime;" +
			"uniform vec4 uCenterPosition;" +
			"uniform vec3 uEffectEyeVector;" +
			"uniform vec3 uCollisionNormal;" +
			"uniform vec4 uScreenEffectNormal;" +
			"attribute float aLifetime;" +
			"varying float vLifetime;" +
			"varying float dotXZ;" +
			"varying float dotYZ;" +
			"varying vec2 vDepthW;" +
			"void main()" +
			"{" +
			" vec3 effectEyeNormal;" +
			" float dotDirection;" +
			" float radian;" +
			" float correctZ;" +
			" vec2 effectEyeXZ;" +
			" vec2 effectEyeYZ;" +
			" vec2 uColXZ;" +
			" vec2 uColYZ;" +
			" effectEyeNormal=normalize(uEffectEyeVector);" +
			" dotDirection=dot(effectEyeNormal,uCollisionNormal);" +
			" radian=acos(dotDirection);" +
			" correctZ=sin(radian);" +
			" gl_Position=uCenterPosition;" +
			" effectEyeXZ=vec2(uCenterPosition.x,uCenterPosition.z);" +
			" effectEyeYZ=vec2(uCenterPosition.y,uCenterPosition.z);" +
			" uColXZ=vec2(uScreenEffectNormal.x,uScreenEffectNormal.z);" +
			" uColYZ=vec2(uScreenEffectNormal.y,uScreenEffectNormal.z);" +
			" dotXZ=dot(effectEyeXZ,uColXZ);" +
			" dotYZ=dot(effectEyeYZ,uColYZ);" +
			" if(dotDirection<0.0)" +
			" {" +
			"  gl_Position.z+=correctZ;" +
			" }" +
			" else" +
			" {" +
			"  gl_Position.z-=correctZ;" +
			" }" +
			" vLifetime=float(aLifetime-uCurrentTime);" +
			" vLifetime=clamp(vLifetime*0.8,0.0,1.0);" +
			" vDepthW=vec2(gl_Position.z,gl_Position.w);" +
			" gl_PointSize=5000.0*(uCurrentTime/uCenterPosition.w);" +
			"}";
	
	public static final String particleFragmentShaderCode=
			"precision mediump float;" +
			"uniform vec4 uColor;" +
			"uniform float uHsec;" +
			"uniform float uVsec;" +
			"uniform mat4 uMVPMatrix;" +
			"varying float vLifetime;" +
			"varying float dotXZ;" +
			"varying float dotYZ;" +
			"varying vec2 vDepthW;" +
			"uniform sampler2D sTexture;" +
			"void main()" +
			"{" +
			" vec2 tCoord;" +
			" vec4 texColor;" +
			" vec4 correctedPoint;" +
			" vec4 affineCoord;" +
			" float secX;" +
			" float secY;" +
			//" secXZ=1.0/dotXZ;" +
			//" secYZ=1.0/dotYZ;" +
			//" correctedCoord.x=clamp(2.0*(gl_PointCoord.x-0.5)+0.5,0.0,1.0);" +
			//" correctedCoord.y=clamp((gl_PointCoord.y-0.5)+0.5,0.0,1.0);" +
			//" tCoord.x=clamp(gl_PointCoord.x*uHsec+0.5-1.0,-1.0,1.0);" +//�ꎟ�ϊ�
			//" tCoord.x=gl_PointCoord.x*uHsec;" +
			//" tCoord.y=gl_PointCoord.y*uVsec;" +
			" correctedPoint=vec4(gl_PointCoord.x,gl_PointCoord.y,0.0,0.0);" +
			" affineCoord=uMVPMatrix*correctedPoint;" +
			" secX=clamp(affineCoord.x*(1.0-(1.0/(2.0*gl_PointCoord.x)))+0.5,0.0,1.0);" +
			" secY=clamp(affineCoord.y*(1.0-(1.0/(2.0*gl_PointCoord.y)))+0.5,0.0,1.0);" +
			" tCoord=vec2(secX,secY);" +
			" texColor=texture2D(sTexture,tCoord);" +
			" gl_FragColor=vec4(1.0)*texColor;" +
			" gl_FragColor.a*=vLifetime;" +
			"}";
	
	public static int mParticleProgram;
	
	public static int uCurrentTimeHandle;
	public static int uCenterPositionHandle;
	public static int uEffectEyeVectorHandle;
	public static int uCollisionNormalHandle;
	public static int uScreenEffectNormalHandle;
	public static int uMVPMatrixHundle;
	public static int aLifetimeHandle;
	public static int aStartPositionHandle;
	public static int aEndPositionHandle;
	
	public static int uColorHandle;
	public static int uHorizontalSecantHandle;
	public static int uVerticalSecantHandle;
	public static int uSampler2DHandle;
	
	public static int loadShader(int shaderType, String shaderCode){
		int shader=GLES20.glCreateShader(shaderType);
		GLES20.glShaderSource(shader,shaderCode);
		GLES20.glCompileShader(shader);
		int[] compileCheck=new int[1];
		GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compileCheck, 0);
		if(compileCheck[0]==0){
			Log.e("shader compile", GLES20.glGetShaderInfoLog(shader));
		}
		return shader;
	}
	
	public static void createProgram(){
		int particleVShader=loadShader(GLES20.GL_VERTEX_SHADER, particleVertexShaderCode);
		int particleFShader=loadShader(GLES20.GL_FRAGMENT_SHADER, particleFragmentShaderCode);
		
		mParticleProgram=GLES20.glCreateProgram();
		GLES20.glAttachShader(mParticleProgram, particleVShader);
		GLES20.glAttachShader(mParticleProgram, particleFShader);
		GLES20.glLinkProgram(mParticleProgram);
		int[] linkedCheck=new int[1];
		GLES20.glGetProgramiv(mParticleProgram, GLES20.GL_LINK_STATUS, linkedCheck, 0);
		if(linkedCheck[0]<0){
			Log.e("program_link", "failed");
		}
		GLES20.glUseProgram(mParticleProgram);
		GLBaseError.errorLog("p_shader_1");
		uCurrentTimeHandle=GLES20.glGetUniformLocation(mParticleProgram, "uCurrentTime");
		GLBaseError.errorLog("p_shader_2");
		uCenterPositionHandle=GLES20.glGetUniformLocation(mParticleProgram, "uCenterPosition");
		uEffectEyeVectorHandle=GLES20.glGetUniformLocation(mParticleProgram, "uEffectEyeVector");
		uCollisionNormalHandle=GLES20.glGetUniformLocation(mParticleProgram, "uCollisionNormal");
		uScreenEffectNormalHandle=GLES20.glGetUniformLocation(mParticleProgram, "uScreenEffectNormal");
		uMVPMatrixHundle=GLES20.glGetUniformLocation(mParticleProgram, "uMVPMatrix");
		GLBaseError.errorLog("p_shader_3");
		aLifetimeHandle=GLES20.glGetAttribLocation(mParticleProgram, "aLifetime");
		GLBaseError.errorLog("p_shader_4");
		aStartPositionHandle=GLES20.glGetAttribLocation(mParticleProgram, "aStartPosition");
		GLBaseError.errorLog("p_shader_5");
		aEndPositionHandle=GLES20.glGetAttribLocation(mParticleProgram, "aEndPosition");
		GLBaseError.errorLog("p_shader_6");
		
		uColorHandle=GLES20.glGetUniformLocation(mParticleProgram, "uColor");
		uHorizontalSecantHandle=GLES20.glGetUniformLocation(mParticleProgram, "uHsec");
		uVerticalSecantHandle=GLES20.glGetUniformLocation(mParticleProgram, "uVsec");
		GLBaseError.errorLog("p_shader_7");
		uSampler2DHandle=GLES20.glGetUniformLocation(mParticleProgram, "sTexture");
		GLBaseError.errorLog("p_shader_8");
		GLES20.glDeleteShader(particleVShader);
		GLES20.glDeleteShader(particleFShader);
		GLBaseError.errorLog("p_shader_9");
	}
}
