package com.honestastrology.glblocks;

public abstract class GLAbstractShader {
	
	abstract int loadShader(int shaderType,String shaderCode);
	
	abstract void createShaderProgram();
	
}
