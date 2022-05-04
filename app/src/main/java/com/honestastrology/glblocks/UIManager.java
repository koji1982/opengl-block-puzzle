package com.honestastrology.glblocks;

import android.opengl.GLES20;
import android.opengl.Matrix;

public class UIManager {
	
	private static UIManager _uiManager;
	private static GLBaseFixedParticle[] _uiObjects;
	private static int _uiCount;
	private static RadioUIGroup _radioUIGroup;
	
	private static ViewProcessor    _viewProcessorRef;
	
	private static GLInferiorObject _miniAxisX;
	private static GLInferiorObject _miniAxisY;
	private static GLInferiorObject _miniAxisZ;
	private static float[] 			_invertPMatrix = new float[16];
	private static float[]			_axisXMatrix 	= new float[16];
	private static float[]			_axisYMatrix 	= new float[16];
	private static float[]			_axisZMatrix 	= new float[16];
	private static float[]			_currentMiniXM 	= new float[16];
	private static float[]			_currentMiniYM  = new float[16];
	private static float[]			_currentMiniZM 	= new float[16];
	private static float[] 			_currentXMVP    = new float[16];
	private static float[]			_currentYMVP    = new float[16];
	private static float[]			_currentZMVP    = new float[16];
	
	private static final float[] MINI_BAR_SCALE = new float[]{0.05f, 0.05f, 10.0035f};
	private static final float[] MINI_BAR_COLOR
			= new float[]{ 0.5f, 0.5f, 1.0f, 1.0f,
						   0.2f, 0.2f, 0.4f, 1.0f };
	
	public static final int CAMERA_UI_NUMBER = 6;
	
	private static int     RETURN_BUTTON = 9;
	private static int    RESTART_BUTTON = 10;
	private static int        BGM_BUTTON = 11;
	private static int READY_SET_DIVIDED = 11;
	
	private UIManager(){
	}
	
	public static FixedUIEnum[] createUI(ViewProcessor viewProcessorRef){
		_uiManager            = new UIManager();
		FixedUIEnum[] uiEnums = FixedUIEnum.values();
		int uiCount          = uiEnums.length;
		_uiObjects            = new GLBaseFixedParticle[uiCount];
		for(int i=0;i<uiCount;i++){
			if(uiEnums[i].isRadioBool()){
				_uiObjects[i] = new GLRadioUI(uiEnums[i].getId(),
						uiEnums[i].getAllocation(),uiEnums[i].getTexsize());
			}else if(uiEnums[i].getId() == RETURN_BUTTON){
				_uiObjects[i] = new GLBaseFixedParticle(uiEnums[i].getId(),
						uiEnums[i].getAllocation(),uiEnums[i].getTexsize());
			}else if(uiEnums[i].getId() == RESTART_BUTTON){
				_uiObjects[i] = new GLBaseFixedParticle(uiEnums[i].getId(),
						uiEnums[i].getAllocation(),uiEnums[i].getTexsize());
			}else if(uiEnums[i].getId() == BGM_BUTTON){
				_uiObjects[i] = new GLToggleUIObject(uiEnums[i].getId(),
						uiEnums[i].getAllocation(),uiEnums[i].getTexsize());
			}else if(uiEnums[i].getId()>READY_SET_DIVIDED){
				_uiObjects[i] = new GLIrregularFixedUI(uiEnums[i].getId(),
						uiEnums[i].getAllocation(),uiEnums[i].getTexsize());
			}else{
				_uiObjects[i] = new GLBaseFixedParticle(uiEnums[i].getId(),
						uiEnums[i].getAllocation(),uiEnums[i].getTexsize());
			}
		}
		_uiCount      = uiCount;
		_radioUIGroup = RadioUIGroup.getInstance();
		
		return uiEnums;
	}
	
	private static void prepareMiniBar(){
		_miniAxisX = new GLInferiorObject("axisbar");
		ObjManager.copyFlyweightBar( _miniAxisX );
		updateMaterialColor( _miniAxisX );
		_miniAxisX.setScale( MINI_BAR_SCALE );
		_miniAxisX.setRotate( new float[]{90.0f,0.0f,1.0f,0.0f} );
		_miniAxisX.setPlace(
				new float[]{ 0.0f, 0.0f, 0.0f });
		_axisXMatrix = calcModelMatrix( _miniAxisX );
		_miniAxisX.setMVPMatrix( _axisXMatrix.clone() );
		_miniAxisY = new GLInferiorObject("axisbar");
		ObjManager.copyFlyweightBar( _miniAxisY );
		updateMaterialColor( _miniAxisY );
		_miniAxisY.setScale( MINI_BAR_SCALE );
		_miniAxisY.setRotate( new float[]{90.0f,1.0f,0.0f,0.0f} );
		_miniAxisY.setPlace(
				new float[]{ 0.0f, 0.0f, 0.0f });
		_axisYMatrix = calcModelMatrix( _miniAxisY );
		_miniAxisY.setMVPMatrix( _axisYMatrix );
		_miniAxisZ = new GLInferiorObject("axisbar");
		ObjManager.copyFlyweightBar( _miniAxisZ );
		updateMaterialColor( _miniAxisZ );
		_miniAxisZ.setScale( MINI_BAR_SCALE );
		_miniAxisZ.setRotate( new float[]{0.0f,0.0f,0.0f,1.0f} );
		_miniAxisZ.setPlace(
				new float[]{ 0.0f,0.0f,0.0f } );
		_axisZMatrix = calcModelMatrix( _miniAxisZ );
		_miniAxisZ.setMVPMatrix( _axisZMatrix.clone() );
	}
	
	private static float[] calcModelMatrix(GLBaseObject miniBar){
		float[] modelMatrix = new float[16];
		Matrix.setIdentityM( modelMatrix, 0 );
		Matrix.translateM(modelMatrix, 0,
				miniBar.getPlaceX(),
				miniBar.getPlaceY(),
				miniBar.getPlaceZ());
		Matrix.rotateM(modelMatrix, 0,
				miniBar.getRotate()[0],
				miniBar.getRotate()[1],
				miniBar.getRotate()[2],
				miniBar.getRotate()[3]);
		Matrix.scaleM(modelMatrix, 0,
				miniBar.getScale()[0],
				miniBar.getScale()[1],
				miniBar.getScale()[2]);
		return modelMatrix;
	}
	
	private static void updateMaterialColor(GLBaseObject miniBar){
		float[] colorArray = miniBar.getMtlGroup().get(0);
		colorArray[0] = MINI_BAR_COLOR[0];
		colorArray[1] = MINI_BAR_COLOR[1];
		colorArray[2] = MINI_BAR_COLOR[2];
		colorArray[3] = MINI_BAR_COLOR[3];
		colorArray[4] = MINI_BAR_COLOR[4];
		colorArray[5] = MINI_BAR_COLOR[5];
		colorArray[6] = MINI_BAR_COLOR[6];
		colorArray[7] = MINI_BAR_COLOR[7];
	}
	
	public void drawUI(){
		GLES20.glDisable( GLES20.GL_DEPTH_TEST );
		
		//GUIを描画する
		for(int i=0;i<_uiCount;i++){
			_uiObjects[i].drawParticle();
		}
		
		GLES20.glEnable( GLES20.GL_DEPTH_TEST );
	}
	
	public static UIManager getInstance(){
		return _uiManager;
	}
	
	public static void callDialog(int buttonId){
		CollateralConnector.getInstance().getGLBlocksActivity().touchedRestart(buttonId);
	}
	
	public void onTouchUI(int number){
		_uiObjects[number].onTouch();
	}
	
	public static void clearRadio(){
		_radioUIGroup.clearRadio();
	}
	
}
