package com.honestastrology.glblocks;

import java.util.ArrayList;

public class RadioUIGroup {
	
	private static RadioUIGroup         _radioUIGroup = new RadioUIGroup();
	private static ArrayList<GLRadioUI> _radioUIList  = new ArrayList<GLRadioUI>();
	private static float[]             _axisPlace    = new float[]{0.0f,0.0f,250.0f};
	private static boolean              axisBool    = false;
	
	private final int                     AXIS_X    = 6;
	private final int                     AXIS_Y    = 7;
	private final int                     AXIS_Z    = 8;
	
	private static final float[]         AXIS_FIX_X  = new float[]{90.0f,0.0f,90.0f,1.0f};
	private static final float[]         AXIS_FIX_Y  = new float[]{90.0f,90.0f,0.0f,1.0f};
	private static final float[]         AXIS_FIX_Z  = new float[]{0.0f,0.0f,0.0f,1.0f};
	
	private static float[]               selectedAxis = new float[]{0.0f,0.0f,0.0f,1.0f};
	
	private static int            checkedAxisForNiche = -1;
	
	private RadioUIGroup(){
	}
	
	public static RadioUIGroup getInstance(){
		return _radioUIGroup;
	}
	
	public int addRadio(GLRadioUI radioUI){
		_radioUIList.add(radioUI);
		return _radioUIList.indexOf(radioUI);
	}
	
	public static void changeAxisSelected(int index){
		decideAxis(index);
		checkChangingAxisForSearchNiche(index);
	}
	
	public static void decideAxis(int index){
		setAxisBool(true);
		for(int i=0,j=_radioUIList.size();i<j;i++){
			if(i==index){
				_radioUIList.get(i).setAxis();
			}else{
				_radioUIList.get(i).setUsualId();
			}
		}
	}
	
	public void clearInstance(){
		_radioUIList = new ArrayList<GLRadioUI>();
		setAxisBool(false);
		GeometricCalculator.setAxisRadioInt(-1);
		setCheckedAxisForNiche(-1);
	}
	
	public void clearRadio(){
		setAxisBool(false);
		GeometricCalculator.setAxisRadioInt(-1);
		setCheckedAxisForNiche(-1);
		for(int i=0,j=_radioUIList.size();i<j;i++){
			_radioUIList.get(i).setUsualId();
		}
	}

	public static float[] getAxisPlace() {
		return _axisPlace;
	}

	public static void setAxisPlace(float[] _axisPlace) {
		RadioUIGroup._axisPlace = _axisPlace;
	}
	
	public static boolean getAxisBool(){
		return axisBool;
	}
	
	public static void setAxisBool(boolean bool){
		axisBool = bool;
	}
	
	public static int getCheckedAxisForNiche() {
		return checkedAxisForNiche;
	}

	public static void setCheckedAxisForNiche(int checkedAxisForNiche) {
		RadioUIGroup.checkedAxisForNiche = checkedAxisForNiche;
	}

	public void selectAxis(int id){
		switch(id){
		case AXIS_X:
			selectedAxis = AXIS_FIX_X;
			GeometricCalculator.setAxisRadioInt(0);
			return;
		case AXIS_Y:
			selectedAxis = AXIS_FIX_Y;
			GeometricCalculator.setAxisRadioInt(1);
			return;
		case AXIS_Z:
			selectedAxis = AXIS_FIX_Z;
			GeometricCalculator.setAxisRadioInt(2);
			return;
		}
	}
	
	public static float[] getAxisRotate(){
		return selectedAxis;
	}
	
	private static void checkChangingAxisForSearchNiche(int index){
		if(index != getCheckedAxisForNiche()){
			GLIrregularFixedUI.changingOtherAxisIfInSelectedNiche(index);
		}
	}
	
}
