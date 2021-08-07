package com.honestastrology.glblocks;

import android.opengl.GLES20;
import android.util.Log;

public class GLBaseTexShader {
	
	public static final String _vertexShaderCode=
			"uniform mat4 uMVPMatrix;" +
			"attribute vec4 aPosition;" +
			"attribute vec2 aTexUV;" +
			"attribute vec3 aNormal;" +
			"struct directional_light{" +
			" vec3 direction;" +
			" vec3 halfplane;" +
			" vec4 ambient_color;" +
			" vec4 diffuse_color;" +
			" vec4 specular_color;" +
			"};" +
			"struct material_properties{" +
			" vec4 ambient_color;" +
			" vec4 diffuse_color;" +
			" vec4 specular_color;" +
			" float specular_exponent;" +
			"};" +
			"const float c_zero=0.0;" +
			"const float c_one=1.0;" +
			"uniform material_properties material;" +
			"uniform directional_light light;" +
			"varying vec4 vColorVector;" +
			"varying vec2 vTexCoord;" +
			"void main() {" +
			" gl_Position=uMVPMatrix*aPosition;" +
			" vTexCoord=aTexUV;" +
			" vec4 computed_color=vec4(c_zero, c_zero, c_zero, c_zero);" +
			" float ndotl;" +
			" float ndoth;" +
			" ndotl=max(c_zero, dot(aNormal, normalize(light.direction)));" +
			" ndoth=max(c_zero, dot(aNormal, normalize(light.halfplane)));" +
			" computed_color += (light.ambient_color*material.ambient_color);" +
			" computed_color += (ndotl*light.diffuse_color*material.diffuse_color);" +
			" if(ndoth > c_zero)" +
			" {" +
			"  computed_color += (pow(ndoth, material.specular_exponent)*material.specular_color*light.specular_color);" +
			" }" +
			" vColorVector=computed_color;" +
			"}";
	public static final String  _fragmentShaderCode=
			"precision mediump float;" +
			"varying vec4 vColorVector;" +
			"varying vec2 vTexCoord;" +
			"uniform sampler2D uTexture;" +
			"void main() {" +
			" gl_FragColor=vColorVector * texture2D(uTexture,vTexCoord);" +
			"}";
	
	
	public static int _baseShaderProgram;
	public static int uMVPMatrixHandle;
	public static int uSampleTexHandle;
	public static int aPositionHandle;
	public static int aNormalHandle;
	public static int aTextureUVHandle;
	public static int dirLight_Direction;
	public static int dirLight_Halfplane;
	public static int dirLight_AmbientHandle;
	public static int dirLight_DiffuseHandle;
	public static int dirLight_SpecularHandle;
	public static int material_AmbientHandle;
	public static int material_DiffuseHandle;
	public static int material_SpecularHandle;
	public static int material_SpecularExponentHandle;
	
	
	public static int loadShader(int shaderType,String shaderCode) {
		int shader=GLES20.glCreateShader(shaderType);
		GLES20.glShaderSource(shader,shaderCode);
		GLES20.glCompileShader(shader);
		int[] compileCheck=new int[1];
		GLES20.glGetShaderiv(shader,GLES20.GL_COMPILE_STATUS,compileCheck,0);
		if(compileCheck[0] == 0){
			Log.e("base_shader_compile", GLES20.glGetShaderInfoLog(shader));
		}
		return shader;
	}
	
	public static void createShaderProgram() {
		int vertexShader   = loadShader(GLES20.GL_VERTEX_SHADER,_vertexShaderCode);
		int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER,_fragmentShaderCode);
		
		_baseShaderProgram=GLES20.glCreateProgram();
		
		GLES20.glAttachShader(_baseShaderProgram, vertexShader);
		GLES20.glAttachShader(_baseShaderProgram, fragmentShader);
		GLES20.glLinkProgram(_baseShaderProgram);
		GLES20.glUseProgram(_baseShaderProgram);
		
		uMVPMatrixHandle=GLES20.glGetUniformLocation(_baseShaderProgram, "uMVPMatrix");
		uSampleTexHandle=GLES20.glGetUniformLocation(_baseShaderProgram, "uTexture");
		
		aPositionHandle=GLES20.glGetAttribLocation(_baseShaderProgram, "aPosition");
		aNormalHandle=GLES20.glGetAttribLocation(_baseShaderProgram,"aNormal");
		aTextureUVHandle=GLES20.glGetAttribLocation(_baseShaderProgram,"aTexUV");
		
		dirLight_Direction=GLES20.glGetUniformLocation(_baseShaderProgram,"light.direction");
		dirLight_Halfplane=GLES20.glGetUniformLocation(_baseShaderProgram,"light.halfplane");
		dirLight_AmbientHandle=GLES20.glGetUniformLocation(_baseShaderProgram, "light.ambient_color");
		dirLight_DiffuseHandle=GLES20.glGetUniformLocation(_baseShaderProgram, "light.diffuse_color");
		dirLight_SpecularHandle=GLES20.glGetUniformLocation(_baseShaderProgram,"light.specular_color");
		material_AmbientHandle=GLES20.glGetUniformLocation(_baseShaderProgram, "material.ambient_color");
		material_DiffuseHandle=GLES20.glGetUniformLocation(_baseShaderProgram, "material.diffuse_color");
		material_SpecularHandle=GLES20.glGetUniformLocation(_baseShaderProgram, "material.specular_color");
		material_SpecularExponentHandle=GLES20.glGetUniformLocation(_baseShaderProgram, "material.specular_exponent");
		
		GLES20.glUniform4f(dirLight_AmbientHandle, 0.8f, 0.8f, 0.8f, 1.0f);
		GLES20.glUniform4f(dirLight_DiffuseHandle, 0.8f, 0.8f, 0.8f, 1.0f);
		GLES20.glUniform4f(dirLight_SpecularHandle, 0.8f, 0.8f, 0.8f, 1.0f);
		GLES20.glUniform3f(dirLight_Direction, 5.30f, 20.00f, 5.45f);
		GLES20.glUniform3f(dirLight_Halfplane, 5.30f, 20.00f, 6.45f);
		
		GLES20.glDeleteShader(vertexShader);
		GLES20.glDeleteShader(fragmentShader);
		
	}

}
