package com.honestastrology.glblocks;

public class UIManager {
	
	private static UIManager _uiManager;
	private static GLBaseFixedParticle[] _uiObjects;
	private static int _uiCount;
	private static RadioUIGroup _radioUIGroup;
	
	public static final int CAMERA_UI_NUMBER = 6;
	
	private static int     RETURN_BUTTON = 9;
	private static int    RESTART_BUTTON = 10;
	private static int        BGM_BUTTON = 11;
	private static int READY_SET_DIVIDED = 11;
	
	private UIManager(){
	}
	
	public static FixedUIEnum[] createUI(){
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
	
	public void drawUI(){
		for(int i=0;i<_uiCount;i++){
			_uiObjects[i].drawParticle();
		}
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
