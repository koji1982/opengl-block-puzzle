package com.honestastrology.glblocks;

public class SceneManager {
	
	public SceneManager(){
		int sceneNumber = GLBlocksActivity.getSceneNumber();
		CurrentScene.createScene(sceneNumber);
	}
	
	public void update(int sceneNumber){
		CurrentScene.updateScene(sceneNumber);
	}
	
}
