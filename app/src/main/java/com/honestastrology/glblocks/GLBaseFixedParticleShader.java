package com.honestastrology.glblocks;

import android.opengl.GLES20;
import android.util.Log;

public class GLBaseFixedParticleShader {
	
	public static final String particleVertexShaderCode =
			"uniform vec4 uCenterPosition;" +
			"uniform float uTexSize;" +
			"varying vec2 vDepthW;" +
			"void main()" +
			"{" +
			" gl_Position = uCenterPosition;" +
			" gl_PointSize = uTexSize;" +
			" vDepthW=vec2(0.0,1.0);" + //vec2(gl_Position.z,gl_Position.w)
			"}";
	
	public static final String particleFragmentShaderCode =
			"precision mediump float;" +
			"varying vec2 vDepthW;" +
			"uniform sampler2D sTexture;" +
			"void main()" +
			"{" +
			" gl_FragColor = texture2D(sTexture,gl_PointCoord);" +
			"}";
	
	public static final String adjustedFragmentShaderCode =
			"precision mediump float;" +
			"varying vec2 vDepthW;" +
			"uniform sampler2D sTexture;" +
			"void main()" +
			"{" +
			" float transparent;" +
			" float dy;" +
			" vec2 dCoord;" +
			" transparent = 0.0;" +
			" dy = gl_PointCoord.y * 2.0;" +
			" if(dy > 1.0) discard;" +
			" dCoord = vec2(gl_PointCoord.x,dy);" +
			" gl_FragColor = texture2D(sTexture,dCoord);" +
			"}";
	
	public static int mFixedParticleProgram;
	public static int mAdjustedProgram;
	
	public static int uCenterPositionHandle;
	public static int uTexSizeHandle;
	
	public static int uColorHandle;
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
	
	public static void createAdjustedProgram(){
		int particleVShader = loadShader(GLES20.GL_VERTEX_SHADER, GLBaseFixedParticleShader.particleVertexShaderCode);
		int particleFShader = loadShader(GLES20.GL_FRAGMENT_SHADER, GLBaseFixedParticleShader.adjustedFragmentShaderCode);
		
		mAdjustedProgram = GLES20.glCreateProgram();
		GLES20.glAttachShader(mAdjustedProgram, particleVShader);
		GLES20.glAttachShader(mAdjustedProgram, particleFShader);
		GLES20.glLinkProgram(mAdjustedProgram);
		checkLinkStatus();
		
		GLES20.glUseProgram(mAdjustedProgram);
		uCenterPositionHandle=GLES20.glGetUniformLocation(mAdjustedProgram, "uCenterPosition");
		uTexSizeHandle= GLES20.glGetUniformLocation(mAdjustedProgram, "uTexSize");
		uColorHandle=GLES20.glGetUniformLocation(mAdjustedProgram, "uColor");
		uSampler2DHandle=GLES20.glGetUniformLocation(mAdjustedProgram, "sTexture");
		GLES20.glDeleteShader(particleVShader);
		GLES20.glDeleteShader(particleFShader);
	}
	
	public static void createProgram(){
		int particleVShader=loadShader(GLES20.GL_VERTEX_SHADER, GLBaseFixedParticleShader.particleVertexShaderCode);
		int particleFShader=loadShader(GLES20.GL_FRAGMENT_SHADER, GLBaseFixedParticleShader.particleFragmentShaderCode);
		
		mFixedParticleProgram=GLES20.glCreateProgram();
		GLES20.glAttachShader(mFixedParticleProgram, particleVShader);
		GLES20.glAttachShader(mFixedParticleProgram, particleFShader);
		GLES20.glLinkProgram(mFixedParticleProgram);
		checkLinkStatus();
		
		GLES20.glUseProgram(mFixedParticleProgram);
		uCenterPositionHandle=GLES20.glGetUniformLocation(mFixedParticleProgram, "uCenterPosition");
		uTexSizeHandle= GLES20.glGetUniformLocation(mFixedParticleProgram, "uTexSize");
		uColorHandle=GLES20.glGetUniformLocation(mFixedParticleProgram, "uColor");
		uSampler2DHandle=GLES20.glGetUniformLocation(mFixedParticleProgram, "sTexture");
		GLBaseError.errorLog("p_shader_8");
		GLES20.glDeleteShader(particleVShader);
		GLES20.glDeleteShader(particleFShader);
		GLBaseError.errorLog("p_shader_9");
	}
	
	public static void checkLinkStatus(){
		int[] linkedCheck=new int[1];
		GLES20.glGetProgramiv(mFixedParticleProgram, GLES20.GL_LINK_STATUS, linkedCheck, 0);
		if(linkedCheck[0]<0){
			Log.e("program_link", "failed");
		}
	}
	
}
